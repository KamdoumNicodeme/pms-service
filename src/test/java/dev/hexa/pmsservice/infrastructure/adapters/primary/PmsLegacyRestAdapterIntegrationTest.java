package dev.hexa.pmsservice.infrastructure.adapters.primary;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.DockerClientFactory;
import org.testcontainers.containers.PostgreSQLContainer;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest(
        webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "pms.demo.auto-seed-enabled=false"
        }
)
class PmsLegacyRestAdapterIntegrationTest {

    static PostgreSQLContainer<?> postgres;

    @DynamicPropertySource
    static void configureDatasource(DynamicPropertyRegistry registry) {
        boolean dockerAvailable;
        try {
            dockerAvailable = DockerClientFactory.instance().isDockerAvailable();
        } catch (Exception ignored) {
            dockerAvailable = false;
        }

        if (dockerAvailable) {
            postgres = new PostgreSQLContainer<>("postgres:17")
                    .withDatabaseName("pms_db")
                    .withUsername("pms_user")
                    .withPassword("pms_pass");
            postgres.start();

            registry.add("spring.datasource.url", postgres::getJdbcUrl);
            registry.add("spring.datasource.username", postgres::getUsername);
            registry.add("spring.datasource.password", postgres::getPassword);
            registry.add("spring.datasource.driver-class-name", postgres::getDriverClassName);
            return;
        }

        registry.add("spring.datasource.url", () -> "jdbc:postgresql://localhost:5432/pms_db");
        registry.add("spring.datasource.username", () -> "pms_user");
        registry.add("spring.datasource.password", () -> "pms_pass");
        registry.add("spring.datasource.driver-class-name", () -> "org.postgresql.Driver");
    }

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${local.server.port}")
    private int port;

    private final HttpClient http = HttpClient.newHttpClient();
    private String accessToken;

    @BeforeEach
    void seedData() {
        jdbcTemplate.execute("DELETE FROM pms_commitment");
        jdbcTemplate.execute("DELETE FROM pms_month_end");
        jdbcTemplate.execute("DELETE FROM pms_client_risk_state");
        jdbcTemplate.execute("DELETE FROM bkechprt");
        jdbcTemplate.execute("DELETE FROM bkdosprt");
        jdbcTemplate.execute("DELETE FROM bkautc");
        jdbcTemplate.execute("DELETE FROM bkcom");
        jdbcTemplate.execute("DELETE FROM bkcli");

        jdbcTemplate.execute("INSERT INTO bkcli(cli, nom, nomrest, qua, tcli, age, ges, catn) VALUES ('C1','N1','Client One','5','RET','001','G1','CAT')");
        jdbcTemplate.execute("INSERT INTO bkcli(cli, nom, nomrest, qua, tcli, age, ges, catn) VALUES ('C2','N2','Client Two','6','ENT','002','G2','CAT')");

        jdbcTemplate.execute("INSERT INTO bkcom(ncp,suf,dev,age,cli,cpro,sde,daut,cha) VALUES ('1001','00','XAF','001','C1','PR1',-5000, CURRENT_DATE - 3, '371')");
        jdbcTemplate.execute("INSERT INTO bkautc(naut,ncp,age,dev,sit,eta,typ,debut,fin,maut) VALUES (1,'1001','001','XAF','O','VA','N',CURRENT_DATE - 30, CURRENT_DATE + 30,10000)");

        jdbcTemplate.execute("INSERT INTO bkdosprt(age,eve,ord,ave,cli,dev,eta,ctr,typ,mon,mimp,dimp,nbe,tech,dmep,map,cum_amo) VALUES ('001','EVE1','001',1,'C1','XAF','VA','1','PRT',20000,1500,CURRENT_DATE - 2,12,24,CURRENT_DATE - 300,1000,500)");
        jdbcTemplate.execute("INSERT INTO bkechprt(age,eve,ord,ave,num,dev,ctr,dva,tot_ech) VALUES ('001','EVE1','001',1,1,'XAF','1',CURRENT_DATE + 1,300)");

        jdbcTemplate.execute("INSERT INTO pms_month_end(monthendid, extraction_date) VALUES (202512, DATE '2025-12-31')");
        jdbcTemplate.execute("INSERT INTO pms_month_end(monthendid, extraction_date) VALUES (202601, DATE '2026-01-31')");

        accessToken = authenticate();
    }

    @Test
    void shouldReturnNumberOfDistinctClients() throws Exception {
        HttpResponse<String> response = get("/pms/v1/getNumberCli", true);
        assertEquals(200, response.statusCode());
        assertEquals("1", response.body());
    }

    @Test
    void shouldReturnLatestExtractionDate() throws Exception {
        HttpResponse<String> response = get("/pms/v1/getextractDate", true);
        assertEquals(200, response.statusCode());
        assertEquals("\"2026-01-31\"", response.body());
    }

    @Test
    void shouldSimulateOnlyOncePerMonthEnd() throws Exception {
        HttpResponse<String> first = get("/pms/v1/simulateData1/202601", true);
        HttpResponse<String> second = get("/pms/v1/simulateData1/202601", true);
        assertEquals(200, first.statusCode());
        assertEquals("1", first.body());
        assertEquals(200, second.statusCode());
        assertEquals("0", second.body());
    }

    @Test
    void shouldReturnCustomerLoans() throws Exception {
        HttpResponse<String> response = get("/pms/v1/getCustomerLoans/C1", true);
        assertEquals(200, response.statusCode());
        assertTrue(response.body().contains("\"NumeroDossier\":\"EVE1\""));
    }

    @Test
    void shouldRejectWithoutToken() throws Exception {
        HttpResponse<String> response = get("/pms/v1/getNumberCli", false);
        assertTrue(response.statusCode() == 401 || response.statusCode() == 403);
    }

    private HttpResponse<String> get(String path, boolean withToken) throws Exception {
        HttpRequest.Builder builder = HttpRequest.newBuilder()
                .uri(URI.create("http://localhost:" + port + path))
                .GET();
        if (withToken) {
            builder.header("Authorization", "Bearer " + accessToken);
        }
        return http.send(builder.build(), HttpResponse.BodyHandlers.ofString());
    }

    private String authenticate() {
        try {
            HttpRequest request = HttpRequest.newBuilder()
                    .uri(URI.create("http://localhost:" + port + "/pms/v1/auth/authenticate"))
                    .header("Content-Type", "application/json")
                    .POST(HttpRequest.BodyPublishers.ofString("{\"login\":\"admin@pms.local\",\"password\":\"secret\"}"))
                    .build();
            HttpResponse<String> response = http.send(request, HttpResponse.BodyHandlers.ofString());
            assertEquals(200, response.statusCode());
            String body = response.body();
            int tokenStart = body.indexOf("\"accessToken\":\"");
            int start = tokenStart + "\"accessToken\":\"".length();
            int end = body.indexOf("\"", start);
            return body.substring(start, end);
        } catch (Exception e) {
            throw new RuntimeException("Cannot authenticate test client", e);
        }
    }
}
