package dev.hexa.pmsservice.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustomerProfileDetailDto(
        LocalDate prevMonth,
        LocalDate curvmonth,
        String Lib1,
        String CurrencyName,
        String client_number,
        String client_name,
        String account_ref,
        String file_number,
        String commitmenttype,
        String accountofficename,
        String branch_code,
        String branch_name,
        String ibfs_code,
        String pro,
        Integer crr,
        Integer previous_crr,
        BigDecimal bookbalance,
        BigDecimal od_balance,
        BigDecimal baseamount,
        BigDecimal ln_principal,
        BigDecimal ln_unpaid_principal,
        String chapitre_comptable,
        BigDecimal provision_amount,
        BigDecimal previous_prov_amount
) {
}
