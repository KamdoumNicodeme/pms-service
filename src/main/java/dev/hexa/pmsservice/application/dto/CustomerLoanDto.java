package dev.hexa.pmsservice.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustomerLoanDto(
        String NumeroDossier,
        String Agence,
        String CodeDevise,
        BigDecimal MontantCredit,
        BigDecimal CumulImpayes,
        LocalDate DernierImpaye,
        Integer Echeances,
        Integer EcheanceTotale,
        LocalDate MisePlace
) {
}
