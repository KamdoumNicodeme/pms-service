package dev.hexa.pmsservice.application.dto;

import java.math.BigDecimal;

public record CustomerContagionDto(
        String Matricule,
        String RaisonSociale,
        String Situation,
        Integer CodeSituation,
        Integer CRRClient,
        String TypeClient,
        BigDecimal MontantAutorisations,
        BigDecimal EnCours,
        BigDecimal MontantImpayes,
        String CodeQualite,
        Integer CodeSegment
) {
}
