package dev.hexa.pmsservice.application;

import dev.hexa.pmsservice.application.dto.*;
import dev.hexa.pmsservice.application.port.secondary.PmsLegacyQueryPort;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PmsLegacyServiceTest {

    @Test
    void shouldDelegateToQueryPort() {
        PmsLegacyQueryPort port = new PmsLegacyQueryPort() {
            @Override
            public List<LegacyClientDto> findAllClients() { return List.of(new LegacyClientDto("C1", "N1", "4", "001", "G1", "CAT", "Client 1")); }
            @Override
            public List<LegacyClientNomQuaDto> findAllByNomAndQua() { return List.of(new LegacyClientNomQuaDto("C1", "N1", "4")); }
            @Override
            public List<LegacyClientDto> getDetailOD(String pcli) { return List.of(new LegacyClientDto(pcli, "N1", "4", "001", "G1", "CAT", "Client 1")); }
            @Override
            public List<CustomerContagionDto> getClassCustomerContagion(int marge, int observation, String startFrom, int minId, int maxId) {
                return List.of(new CustomerContagionDto("C1", "Client 1", "Client sain", 0, 4, "RET",
                        BigDecimal.TEN, BigDecimal.ONE, BigDecimal.ZERO, "4", 0));
            }
            @Override
            public int getNumberCli() { return 7; }
            @Override
            public List<CustomerOdDto> getCustomerOD(String pcli) { return List.of(new CustomerOdDto(pcli, "1001", BigDecimal.ONE, BigDecimal.TEN, "Ouverte", "Permanente", LocalDate.now(), LocalDate.now(), 0, LocalDate.now(), "371", "En cours")); }
            @Override
            public List<CustomerLoanDto> getCustomerLoans(String pcli) { return List.of(new CustomerLoanDto("E1", "001", "XAF", BigDecimal.TEN, BigDecimal.ZERO, LocalDate.now(), 12, 24, LocalDate.now())); }
            @Override
            public List<LoanDetailDto> getLoanDetail(String eve, String age, String dev) { return List.of(new LoanDetailDto("1", 1, LocalDate.now(), BigDecimal.ONE, BigDecimal.ONE)); }
            @Override
            public List<CustomerProfileDetailDto> getCustomerProfileDetail(int monthEndId) {
                return List.of(new CustomerProfileDetailDto(LocalDate.now(), LocalDate.now(), "371", "XAF", "C1", "Client 1", "1001", "D1", "PRT", "AO", "001", "Agence", "", "", 1, 0, BigDecimal.ONE, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, BigDecimal.ZERO, "371", BigDecimal.ZERO, BigDecimal.ZERO));
            }
            @Override
            public int simulate(int monthEndId) { return monthEndId; }
            @Override
            public LocalDate getExtractDate() { return LocalDate.of(2026, 3, 1); }
            @Override
            public LocalDate getExtractionDate() { return LocalDate.of(2026, 3, 2); }
        };

        PmsLegacyService service = new PmsLegacyService(port);

        assertEquals("C1", service.findAllClients().get(0).cli());
        assertEquals(7, service.getNumberCli());
        assertEquals(202601, service.simulate(202601));
        assertEquals(LocalDate.of(2026, 3, 1), service.getExtractDate());
        assertEquals(LocalDate.of(2026, 3, 2), service.getExtractionDate());
    }
}
