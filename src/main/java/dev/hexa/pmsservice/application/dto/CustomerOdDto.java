package dev.hexa.pmsservice.application.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CustomerOdDto(
        String CodeClient,
        String Numerocompte,
        BigDecimal Soldecompte,
        BigDecimal MontantAuto,
        String SituationAuto,
        String TypeAuto,
        LocalDate Debut,
        LocalDate Fin,
        Integer NombreJourDepassement,
        LocalDate DateDepassement,
        String ChapitreComptable,
        String EtatAuto
) {
}
