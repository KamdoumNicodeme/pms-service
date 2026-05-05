package dev.hexa.pmsservice.infrastructure.adapters.secondary;

import dev.hexa.pmsservice.application.MonthEndPeriod;
import dev.hexa.pmsservice.application.dto.*;
import dev.hexa.pmsservice.application.port.secondary.PmsLegacyQueryPort;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
public class PmsLegacyJdbcAdapter implements PmsLegacyQueryPort {

    private final NamedParameterJdbcTemplate jdbc;
    private static final int DEFAULT_MIN_DEMO_CLIENTS = 1200;

    @Value("${pms.demo.auto-seed-enabled:true}")
    private boolean autoSeedEnabled;

    @Value("${pms.demo.min-clients:1200}")
    private int minDemoClients;

    @Override
    public List<LegacyClientDto> findAllClients() {
        return jdbc.queryForList("""
                SELECT cli, nom, qua, age, ges, catn, nomrest
                FROM bkcli
                ORDER BY cli
                """, new MapSqlParameterSource()).stream()
                .map(row -> new LegacyClientDto(
                        text(row, "cli"),
                        text(row, "nom"),
                        text(row, "qua"),
                        text(row, "age"),
                        text(row, "ges"),
                        text(row, "catn"),
                        text(row, "nomrest")
                ))
                .toList();
    }

    @Override
    public List<LegacyClientNomQuaDto> findAllByNomAndQua() {
        return jdbc.queryForList("""
                SELECT cli, nom, qua
                FROM bkcli
                ORDER BY nom NULLS LAST
                """, new MapSqlParameterSource()).stream()
                .map(row -> new LegacyClientNomQuaDto(
                        text(row, "cli"),
                        text(row, "nom"),
                        text(row, "qua")
                ))
                .toList();
    }

    @Override
    public List<LegacyClientDto> getDetailOD(String pcli) {
        return jdbc.queryForList("""
                SELECT cli, nom, qua, age, ges, catn, nomrest
                FROM bkcli
                WHERE trim(cli) = trim(:pcli)
                """, new MapSqlParameterSource("pcli", pcli)).stream()
                .map(row -> new LegacyClientDto(
                        text(row, "cli"),
                        text(row, "nom"),
                        text(row, "qua"),
                        text(row, "age"),
                        text(row, "ges"),
                        text(row, "catn"),
                        text(row, "nomrest")
                ))
                .toList();
    }

    @Override
    public List<CustomerContagionDto> getClassCustomerContagion(int marge, int observation, String startFrom, int minId, int maxId) {
        ensureDemoDataset();
        upsertRiskStateForCurrentDefaults(minId, maxId);

        return jdbc.queryForList("""
                WITH base AS (
                    SELECT DISTINCT cli FROM bkcom
                    UNION
                    SELECT DISTINCT cli FROM bkdosprt
                ),
                ranked AS (
                    SELECT
                        b.cli,
                        c.nomrest,
                        c.qua,
                        row_number() OVER (ORDER BY b.cli) rn
                    FROM base b
                    LEFT JOIN bkcli c ON c.cli = b.cli
                ),
                scoped AS (
                    SELECT * FROM ranked WHERE rn > :minId AND rn <= :maxId
                ),
                mon AS (
                    SELECT
                        s.cli,
                        COALESCE((SELECT SUM(CASE WHEN x.sde < 0 THEN -x.sde ELSE 0 END) FROM bkcom x WHERE x.cli = s.cli), 0) montant_auto,
                        COALESCE((SELECT SUM(COALESCE(x.mon,0)) FROM bkdosprt x WHERE x.cli = s.cli), 0) montant_credit,
                        COALESCE((SELECT SUM(COALESCE(x.mimp,0)) FROM bkdosprt x WHERE x.cli = s.cli), 0) montant_imp,
                        COALESCE((
                            SELECT MAX(
                                CASE
                                    WHEN COALESCE(x.mimp, 0) > 0 AND x.dimp IS NOT NULL THEN CURRENT_DATE - x.dimp
                                    ELSE 0
                                END
                            )
                            FROM bkdosprt x
                            WHERE x.cli = s.cli
                        ), 0) max_overdue_days
                    FROM scoped s
                ),
                eval AS (
                    SELECT
                        s.cli,
                        s.nomrest,
                        s.qua,
                        m.montant_auto,
                        m.montant_credit,
                        m.montant_imp,
                        m.max_overdue_days,
                        COALESCE(s.qua, '1') IN ('8','7') AS is_default_now,
                        (COALESCE(m.montant_imp, 0) > :marge AND :observation >= 90) OR COALESCE(s.qua, '1') = '6' AS is_incident_now,
                        rs.last_default_date
                    FROM scoped s
                    LEFT JOIN mon m ON m.cli = s.cli
                    LEFT JOIN pms_client_risk_state rs ON rs.cli = s.cli
                )
                SELECT
                    e.cli AS "Matricule",
                    COALESCE(e.nomrest, '') AS "RaisonSociale",
                    CASE
                        WHEN e.max_overdue_days > 90 OR e.is_default_now THEN 'Client en Defaut'
                        WHEN e.is_incident_now THEN 'Client en incident'
                        WHEN e.last_default_date IS NOT NULL
                             AND date_trunc('month', CURRENT_DATE) > date_trunc('month', e.last_default_date)
                             AND date_trunc('month', CURRENT_DATE) <= date_trunc('month', e.last_default_date) + INTERVAL '3 months'
                            THEN 'Client en probation'
                        ELSE 'Client sain'
                    END AS "Situation",
                    CASE
                        WHEN e.max_overdue_days > 90 OR e.is_default_now THEN 3
                        WHEN e.is_incident_now THEN 2
                        WHEN e.last_default_date IS NOT NULL
                             AND date_trunc('month', CURRENT_DATE) > date_trunc('month', e.last_default_date)
                             AND date_trunc('month', CURRENT_DATE) <= date_trunc('month', e.last_default_date) + INTERVAL '3 months'
                            THEN 1
                        ELSE 0
                    END AS "CodeSituation",
                    COALESCE(NULLIF(e.qua, '')::int, 1) AS "CRRClient",
                    COALESCE(c.tcli, '') AS "TypeClient",
                    e.montant_auto AS "MontantAutorisations",
                    e.montant_credit AS "EnCours",
                    e.montant_imp AS "MontantImpayes",
                    COALESCE(e.qua, '') AS "CodeQualite",
                    0 AS "CodeSegment"
                FROM eval e
                LEFT JOIN bkcli c ON c.cli = e.cli
                ORDER BY e.cli
                """, new MapSqlParameterSource()
                .addValue("marge", marge)
                .addValue("observation", observation)
                .addValue("minId", minId)
                .addValue("maxId", maxId)).stream()
                .map(row -> new CustomerContagionDto(
                        text(row, "Matricule"),
                        text(row, "RaisonSociale"),
                        text(row, "Situation"),
                        integer(row, "CodeSituation"),
                        integer(row, "CRRClient"),
                        text(row, "TypeClient"),
                        decimal(row, "MontantAutorisations"),
                        decimal(row, "EnCours"),
                        decimal(row, "MontantImpayes"),
                        text(row, "CodeQualite"),
                        integer(row, "CodeSegment")
                ))
                .toList();
    }

    @Override
    public int getNumberCli() {
        ensureDemoDataset();

        Integer total = jdbc.queryForObject("""
                SELECT COUNT(*)
                FROM (
                    SELECT DISTINCT a.cli
                    FROM bkcom a
                    INNER JOIN bkautc b ON a.ncp = b.ncp AND a.age = b.age AND a.dev = b.dev
                    INNER JOIN bkcli c ON a.cli = c.cli
                    UNION
                    SELECT DISTINCT d.cli
                    FROM bkdosprt d
                    INNER JOIN bkcli c2 ON d.cli = c2.cli
                ) x
                """, new MapSqlParameterSource(), Integer.class);
        return total == null ? 0 : total;
    }

    @Override
    public List<CustomerOdDto> getCustomerOD(String pcli) {
        return jdbc.queryForList("""
                SELECT
                    a.cli AS "CodeClient",
                    a.ncp AS "Numerocompte",
                    a.sde AS "Soldecompte",
                    b.maut AS "MontantAuto",
                    CASE
                        WHEN b.sit = 'O' THEN 'Ouverte'
                        WHEN b.sit = 'M' THEN 'Modifiee'
                    END AS "SituationAuto",
                    CASE
                        WHEN b.typ = 'O' THEN 'Ponctuelle'
                        WHEN b.typ = 'N' THEN 'Permanente'
                    END AS "TypeAuto",
                    b.debut AS "Debut",
                    b.fin AS "Fin",
                    CASE
                        WHEN -a.sde < b.maut AND CURRENT_DATE > b.fin THEN CAST((CURRENT_DATE - b.fin) AS INTEGER)
                        WHEN -a.sde < b.maut AND CURRENT_DATE < b.fin THEN 0
                        WHEN -a.sde > b.maut AND CURRENT_DATE > b.fin THEN CAST((CURRENT_DATE - a.daut) AS INTEGER)
                        ELSE 0
                    END AS "NombreJourDepassement",
                    a.daut AS "DateDepassement",
                    a.cha AS "ChapitreComptable",
                    CASE
                        WHEN b.fin > CURRENT_DATE THEN 'En cours'
                        WHEN b.fin <= CURRENT_DATE THEN 'Echue'
                    END AS "EtatAuto"
                FROM bkcom a
                INNER JOIN (
                    SELECT
                        a.naut,
                        b.sde,
                        b.cli,
                        a.ncp,
                        a.fin,
                        a.eta,
                        a.maut,
                        a.dev,
                        a.age,
                        a.debut,
                        a.typ,
                        a.sit
                    FROM bkautc a
                    INNER JOIN bkcom b ON a.ncp = b.ncp AND a.age = b.age AND a.dev = b.dev
                    WHERE a.naut IN (SELECT MAX(naut) FROM bkautc GROUP BY ncp)
                      AND trim(b.cli) = trim(:pcli)
                      AND a.sit <> 'A'
                      AND a.eta IN ('VA', 'VF')
                ) b ON a.age = b.age AND a.ncp = b.ncp AND a.dev = b.dev
                WHERE trim(a.cli) = trim(:pcli)
                """, new MapSqlParameterSource("pcli", pcli)).stream()
                .map(row -> new CustomerOdDto(
                        text(row, "CodeClient"),
                        text(row, "Numerocompte"),
                        decimal(row, "Soldecompte"),
                        decimal(row, "MontantAuto"),
                        text(row, "SituationAuto"),
                        text(row, "TypeAuto"),
                        date(row, "Debut"),
                        date(row, "Fin"),
                        integer(row, "NombreJourDepassement"),
                        date(row, "DateDepassement"),
                        text(row, "ChapitreComptable"),
                        text(row, "EtatAuto")
                ))
                .toList();
    }

    @Override
    public List<CustomerLoanDto> getCustomerLoans(String pcli) {
        return jdbc.queryForList("""
                SELECT
                    eve AS "NumeroDossier",
                    age AS "Agence",
                    dev AS "CodeDevise",
                    mon AS "MontantCredit",
                    mimp AS "CumulImpayes",
                    dimp AS "DernierImpaye",
                    nbe AS "Echeances",
                    tech AS "EcheanceTotale",
                    dmep AS "MisePlace"
                FROM bkdosprt
                WHERE trim(cli) = trim(:pcli)
                  AND eta IN ('VA', 'DE')
                  AND ctr <> '9'
                """, new MapSqlParameterSource("pcli", pcli)).stream()
                .map(row -> new CustomerLoanDto(
                        text(row, "NumeroDossier"),
                        text(row, "Agence"),
                        text(row, "CodeDevise"),
                        decimal(row, "MontantCredit"),
                        decimal(row, "CumulImpayes"),
                        date(row, "DernierImpaye"),
                        integer(row, "Echeances"),
                        integer(row, "EcheanceTotale"),
                        date(row, "MisePlace")
                ))
                .toList();
    }

    @Override
    public List<LoanDetailDto> getLoanDetail(String eve, String age, String dev) {
        return jdbc.queryForList("""
                SELECT
                    b.ctr AS "CodeTraitement",
                    b.num AS "NumeroEcheance",
                    b.dva AS "DateEcheance",
                    b.tot_ech AS "MontantEcheance",
                    a.map AS "MontantEcheanceConstante"
                FROM bkdosprt a
                INNER JOIN bkechprt b ON trim(a.age) = trim(b.age)
                                     AND trim(a.eve) = trim(b.eve)
                                     AND trim(CAST(a.ave AS text)) = trim(CAST(b.ave AS text))
                WHERE trim(b.eve) = trim(:eve)
                  AND trim(b.age) = trim(:age)
                  AND trim(b.dev) = trim(:dev)
                  AND ((a.ctr IN ('1', '3', '4', '5') AND a.eta = 'VA') OR (a.eta = 'DE' AND a.ctr = '2'))
                ORDER BY b.dva
                """, new MapSqlParameterSource()
                .addValue("eve", eve)
                .addValue("age", age)
                .addValue("dev", dev)).stream()
                .map(row -> new LoanDetailDto(
                        text(row, "CodeTraitement"),
                        integer(row, "NumeroEcheance"),
                        date(row, "DateEcheance"),
                        decimal(row, "MontantEcheance"),
                        decimal(row, "MontantEcheanceConstante")
                ))
                .toList();
    }

    @Override
    public List<CustomerProfileDetailDto> getCustomerProfileDetail(int monthEndId) {
        MonthEndPeriod period = MonthEndPeriod.from(monthEndId);

        return jdbc.queryForList("""
                SELECT
                    :prevMonthStart::date AS "prevMonth",
                    :currentMonthStart::date AS "curvmonth",
                    COALESCE(cha, '') AS "Lib1",
                    currencycode AS "CurrencyName",
                    trim(client_number) AS "client_number",
                    client_name,
                    account_ref,
                    file_number,
                    commitmenttype,
                    accountofficename,
                    trim(branch_code) AS branch_code,
                    trim(branch_name) AS branch_name,
                    '' AS ibfs_code,
                    '' AS pro,
                    MAX(CASE WHEN monthendid = :monthEndId THEN COALESCE(crr, 0) ELSE 0 END) AS crr,
                    MAX(CASE WHEN monthendid = :monthEndId THEN 0 ELSE COALESCE(crr, 0) END) AS previous_crr,
                    SUM(CASE WHEN monthendid = :monthEndId
                             THEN COALESCE(od_balance, 0) + COALESCE(ln_principal, 0) + COALESCE(ln_unpaid_principal, 0)
                             ELSE 0 END) AS bookbalance,
                    SUM(CASE WHEN monthendid = :monthEndId THEN COALESCE(od_balance, 0) ELSE 0 END) AS od_balance,
                    SUM(CASE WHEN monthendid = :monthEndId THEN COALESCE(baseamount, 0) ELSE 0 END) AS baseamount,
                    SUM(CASE WHEN monthendid = :monthEndId THEN COALESCE(ln_principal, 0) ELSE 0 END) AS ln_principal,
                    SUM(CASE WHEN monthendid = :monthEndId THEN COALESCE(ln_unpaid_principal, 0) ELSE 0 END) AS ln_unpaid_principal,
                    COALESCE(cha, '') AS chapitre_comptable,
                    SUM(CASE WHEN monthendid = :monthEndId THEN COALESCE(provision_amount, 0) ELSE 0 END) AS provision_amount,
                    SUM(CASE WHEN monthendid = :monthEndId THEN 0 ELSE COALESCE(provision_amount, 0) END) AS previous_prov_amount
                FROM pms_commitment
                WHERE monthendid IN (:monthEndId, :previousMonthEndId)
                GROUP BY
                    cha, currencycode, client_number, client_name, account_ref, file_number,
                    commitmenttype, accountofficename, branch_code, branch_name
                ORDER BY client_name, crr DESC, previous_crr ASC
                """, new MapSqlParameterSource()
                .addValue("monthEndId", period.monthEndId())
                .addValue("previousMonthEndId", period.previousMonthEndId())
                .addValue("prevMonthStart", period.previousMonthStart())
                .addValue("currentMonthStart", period.currentMonthStart())).stream()
                .map(row -> new CustomerProfileDetailDto(
                        date(row, "prevMonth"),
                        date(row, "curvmonth"),
                        text(row, "Lib1"),
                        text(row, "CurrencyName"),
                        text(row, "client_number"),
                        text(row, "client_name"),
                        text(row, "account_ref"),
                        text(row, "file_number"),
                        text(row, "commitmenttype"),
                        text(row, "accountofficename"),
                        text(row, "branch_code"),
                        text(row, "branch_name"),
                        text(row, "ibfs_code"),
                        text(row, "pro"),
                        integer(row, "crr"),
                        integer(row, "previous_crr"),
                        decimal(row, "bookbalance"),
                        decimal(row, "od_balance"),
                        decimal(row, "baseamount"),
                        decimal(row, "ln_principal"),
                        decimal(row, "ln_unpaid_principal"),
                        text(row, "chapitre_comptable"),
                        decimal(row, "provision_amount"),
                        decimal(row, "previous_prov_amount")
                ))
                .toList();
    }

    @Override
    @Transactional
    public int simulate(int monthEndId) {
        Integer existing = jdbc.queryForObject("""
                SELECT COUNT(*) FROM pms_commitment WHERE monthendid = :monthEndId
                """, new MapSqlParameterSource("monthEndId", monthEndId), Integer.class);

        if (existing != null && existing > 0) {
            return 0;
        }

        jdbc.update("""
                INSERT INTO pms_commitment (
                    monthendid, commitmenttype, file_number, client_number,
                    client_name, account_ref, bad_debt_ref, crr, client_crr,
                    product_code, branch_code, branch_name, accountofficercode,
                    accountofficename, ln_principal, ln_unpaid_principal,
                    od_balance, od_unpaid_interest, baseamount, currencycode,
                    provision_rate, provision_amount, cbc, num_overdue_days,
                    num_pastdue_days, interest_in_suspense, cha, currency_rate, collateral_amount
                )
                SELECT
                    :monthEndId,
                    'AUT',
                    b.ncp,
                    b.cli,
                    COALESCE(c.nomrest, c.nom, ''),
                    b.ncp,
                    '',
                    1,
                    COALESCE(c.qua, '1'),
                    b.cpro,
                    b.age,
                    b.age,
                    COALESCE(c.ges, ''),
                    '',
                    0,
                    0,
                    (b.sde * -1),
                    0,
                    0,
                    b.dev,
                    0,
                    0,
                    COALESCE(c.catn, ''),
                    0,
                    0,
                    0,
                    b.cha,
                    1,
                    0
                FROM bkcom b
                LEFT JOIN bkcli c ON c.cli = b.cli
                WHERE b.sde < 0 AND trim(b.cli) <> ''
                """, new MapSqlParameterSource("monthEndId", monthEndId));

        jdbc.update("""
                INSERT INTO pms_commitment (
                    monthendid, commitmenttype, file_number, client_number,
                    client_name, account_ref, bad_debt_ref, crr, client_crr,
                    product_code, branch_code, branch_name, accountofficercode,
                    accountofficename, ln_principal, ln_unpaid_principal,
                    od_balance, od_unpaid_interest, baseamount, currencycode,
                    provision_rate, provision_amount, cbc, num_overdue_days,
                    num_pastdue_days, interest_in_suspense, cha, currency_rate, collateral_amount
                )
                SELECT
                    :monthEndId,
                    'PRT',
                    d.eve,
                    d.cli,
                    COALESCE(c.nomrest, c.nom, ''),
                    d.eve,
                    '',
                    1,
                    COALESCE(c.qua, '1'),
                    d.typ,
                    d.age,
                    d.age,
                    COALESCE(c.ges, ''),
                    '',
                    (COALESCE(d.mon, 0) - COALESCE(d.cum_amo, 0)),
                    COALESCE(d.mimp, 0),
                    0,
                    0,
                    0,
                    d.dev,
                    0,
                    0,
                    COALESCE(c.catn, ''),
                    0,
                    0,
                    0,
                    '',
                    1,
                    0
                FROM bkdosprt d
                LEFT JOIN bkcli c ON c.cli = d.cli
                WHERE ((d.ctr IN ('1', '3', '4', '5') AND d.eta = 'VA') OR (d.eta = 'DE' AND d.ctr = '2'))
                """, new MapSqlParameterSource("monthEndId", monthEndId));

        return 1;
    }

    @Override
    public LocalDate getExtractDate() {
        return jdbc.queryForObject("""
                SELECT extraction_date
                FROM pms_month_end
                ORDER BY monthendid DESC
                LIMIT 1
                """, new MapSqlParameterSource(), LocalDate.class);
    }

    @Override
    public LocalDate getExtractionDate() {
        return getExtractDate();
    }

    private void ensureDemoDataset() {
        if (!autoSeedEnabled) {
            return;
        }

        int minimum = Math.max(minDemoClients, DEFAULT_MIN_DEMO_CLIENTS);
        Integer current = jdbc.queryForObject("""
                SELECT COUNT(*)
                FROM (
                    SELECT DISTINCT cli FROM bkcom
                    UNION
                    SELECT DISTINCT cli FROM bkdosprt
                ) x
                """, new MapSqlParameterSource(), Integer.class);

        int currentTotal = current == null ? 0 : current;
        if (currentTotal >= minimum) {
            return;
        }

        int toCreate = minimum - currentTotal;
        Integer syntheticBase = jdbc.queryForObject("""
                SELECT COALESCE(MAX(CAST(SUBSTRING(cli FROM 2) AS INTEGER)), 0)
                FROM bkcli
                WHERE cli ~ '^F[0-9]+$'
                """, new MapSqlParameterSource(), Integer.class);
        int base = syntheticBase == null ? 0 : syntheticBase;

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("base", base)
                .addValue("toCreate", toCreate);

        jdbc.update("""
                INSERT INTO bkcli(cli, nom, nomrest, qua, tcli, age, ges, catn)
                SELECT
                    'F' || LPAD((:base + g)::text, 5, '0') AS cli,
                    'Demo ' || (:base + g),
                    'Client Demo ' || (:base + g),
                    CASE
                        WHEN (:base + g) % 4 = 0 THEN '7'
                        WHEN (:base + g) % 4 = 1 THEN '6'
                        ELSE '4'
                    END,
                    CASE WHEN (:base + g) % 2 = 0 THEN 'RET' ELSE 'ENT' END,
                    '001',
                    'G1',
                    'CAT'
                FROM generate_series(1, :toCreate) g
                ON CONFLICT (cli) DO NOTHING
                """, params);

        jdbc.update("""
                INSERT INTO bkcom(ncp, suf, dev, age, cli, cpro, sde, daut, cha)
                SELECT
                    LPAD((700000 + :base + g)::text, 11, '0') AS ncp,
                    '00',
                    'XAF',
                    '001',
                    'F' || LPAD((:base + g)::text, 5, '0'),
                    'PR1',
                    -1 * (1000 + ((:base + g) % 50) * 100),
                    CURRENT_DATE - ((:base + g) % 20),
                    '371'
                FROM generate_series(1, :toCreate) g
                ON CONFLICT (ncp, suf, dev, age) DO NOTHING
                """, params);

        jdbc.update("""
                INSERT INTO bkautc(naut, ncp, age, dev, sit, eta, typ, debut, fin, maut)
                SELECT
                    1000000 + :base + g,
                    LPAD((700000 + :base + g)::text, 11, '0'),
                    '001',
                    'XAF',
                    'O',
                    'VA',
                    'N',
                    CURRENT_DATE - 30,
                    CURRENT_DATE + 60,
                    20000
                FROM generate_series(1, :toCreate) g
                ON CONFLICT (naut) DO NOTHING
                """, params);

        jdbc.update("""
                INSERT INTO bkdosprt(age, eve, ord, ave, cli, dev, eta, ctr, typ, mon, mimp, dimp, nbe, tech, dmep, map, cum_amo)
                SELECT
                    '001',
                    LPAD((200000 + :base + g)::text, 6, '0'),
                    '001',
                    1,
                    'F' || LPAD((:base + g)::text, 5, '0'),
                    'XAF',
                    'VA',
                    '1',
                    'PRT',
                    5000 + ((:base + g) % 15) * 300,
                    CASE WHEN (:base + g) % 4 = 1 THEN 2500 ELSE 0 END,
                    CURRENT_DATE - ((:base + g) % 12),
                    12,
                    24,
                    CURRENT_DATE - 120,
                    1000,
                    300
                FROM generate_series(1, :toCreate) g
                ON CONFLICT (age, eve, ord, ave) DO NOTHING
                """, params);

        jdbc.update("""
                INSERT INTO bkechprt(age, eve, ord, ave, num, dev, ctr, dva, tot_ech)
                SELECT
                    '001',
                    LPAD((200000 + :base + g)::text, 6, '0'),
                    '001',
                    1,
                    1,
                    'XAF',
                    '1',
                    CURRENT_DATE + 30,
                    350
                FROM generate_series(1, :toCreate) g
                ON CONFLICT (age, eve, ord, ave, num) DO NOTHING
                """, params);

        jdbc.update("""
                INSERT INTO pms_client_risk_state(cli, last_default_date, updated_at)
                SELECT
                    'F' || LPAD((:base + g)::text, 5, '0'),
                    CASE
                        WHEN (:base + g) % 4 = 2 THEN CURRENT_DATE - INTERVAL '2 months'
                        WHEN (:base + g) % 4 = 3 THEN CURRENT_DATE - INTERVAL '5 months'
                        ELSE NULL
                    END,
                    CURRENT_TIMESTAMP
                FROM generate_series(1, :toCreate) g
                WHERE (:base + g) % 4 IN (2,3)
                ON CONFLICT (cli) DO NOTHING
                """, params);
    }

    private void upsertRiskStateForCurrentDefaults(int minId, int maxId) {
        jdbc.update("""
                WITH base AS (
                    SELECT DISTINCT cli FROM bkcom
                    UNION
                    SELECT DISTINCT cli FROM bkdosprt
                ),
                ranked AS (
                    SELECT
                        b.cli,
                        c.qua,
                        row_number() OVER (ORDER BY b.cli) rn
                    FROM base b
                    LEFT JOIN bkcli c ON c.cli = b.cli
                ),
                scoped AS (
                    SELECT * FROM ranked WHERE rn > :minId AND rn <= :maxId
                )
                INSERT INTO pms_client_risk_state(cli, last_default_date, updated_at)
                SELECT
                    s.cli,
                    CURRENT_DATE,
                    CURRENT_TIMESTAMP
                FROM scoped s
                WHERE COALESCE(s.qua, '1') IN ('8','7')
                ON CONFLICT (cli) DO UPDATE
                SET last_default_date = EXCLUDED.last_default_date,
                    updated_at = EXCLUDED.updated_at
                """, new MapSqlParameterSource()
                .addValue("minId", minId)
                .addValue("maxId", maxId));
    }

    private String text(Map<String, Object> row, String key) {
        Object value = get(row, key);
        return value == null ? null : String.valueOf(value);
    }

    private Integer integer(Map<String, Object> row, String key) {
        Object value = get(row, key);
        if (value == null) return null;
        if (value instanceof Number n) return n.intValue();
        return Integer.parseInt(String.valueOf(value));
    }

    private BigDecimal decimal(Map<String, Object> row, String key) {
        Object value = get(row, key);
        if (value == null) return BigDecimal.ZERO;
        if (value instanceof BigDecimal bd) return bd;
        if (value instanceof Number n) return BigDecimal.valueOf(n.doubleValue());
        return new BigDecimal(String.valueOf(value));
    }

    private LocalDate date(Map<String, Object> row, String key) {
        Object value = get(row, key);
        if (value == null) return null;
        if (value instanceof LocalDate d) return d;
        return LocalDate.parse(String.valueOf(value));
    }

    private Object get(Map<String, Object> row, String key) {
        if (row.containsKey(key)) return row.get(key);
        String lower = key.toLowerCase();
        if (row.containsKey(lower)) return row.get(lower);
        return row.entrySet().stream()
                .filter(e -> e.getKey().equalsIgnoreCase(key))
                .map(Map.Entry::getValue)
                .findFirst()
                .orElse(null);
    }
}
