package dev.hexa.pmsservice.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record LoanDetailDto(
        String CodeTraitement,
        Integer NumeroEcheance,
        LocalDate DateEcheance,
        BigDecimal MontantEcheance,
        BigDecimal MontantEcheanceConstante
) {
}
