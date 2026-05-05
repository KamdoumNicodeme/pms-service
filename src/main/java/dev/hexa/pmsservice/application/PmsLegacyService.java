package dev.hexa.pmsservice.application;

import dev.hexa.pmsservice.application.annotation.DomainService;
import dev.hexa.pmsservice.application.dto.*;
import dev.hexa.pmsservice.application.port.primary.PmsLegacyServicePort;
import dev.hexa.pmsservice.application.port.secondary.PmsLegacyQueryPort;
import lombok.RequiredArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@DomainService
@RequiredArgsConstructor
public class PmsLegacyService implements PmsLegacyServicePort {

    private final PmsLegacyQueryPort queryPort;

    @Override
    public List<LegacyClientDto> findAllClients() {
        return queryPort.findAllClients();
    }

    @Override
    public List<LegacyClientNomQuaDto> findAllByNomAndQua() {
        return queryPort.findAllByNomAndQua();
    }

    @Override
    public List<LegacyClientDto> getDetailOD(String pcli) {
        return queryPort.getDetailOD(pcli);
    }

    @Override
    public List<CustomerContagionDto> getClassCustomerContagion(int marge, int observation, String startFrom, int minId, int maxId) {
        return queryPort.getClassCustomerContagion(marge, observation, startFrom, minId, maxId);
    }

    @Override
    public int getNumberCli() {
        return queryPort.getNumberCli();
    }

    @Override
    public List<CustomerOdDto> getCustomerOD(String pcli) {
        return queryPort.getCustomerOD(pcli);
    }

    @Override
    public List<CustomerLoanDto> getCustomerLoans(String pcli) {
        return queryPort.getCustomerLoans(pcli);
    }

    @Override
    public List<LoanDetailDto> getLoanDetail(String eve, String age, String dev) {
        return queryPort.getLoanDetail(eve, age, dev);
    }

    @Override
    public List<CustomerProfileDetailDto> getCustomerProfileDetail(int monthEndId) {
        return queryPort.getCustomerProfileDetail(monthEndId);
    }

    @Override
    public int simulate(int monthEndId) {
        return queryPort.simulate(monthEndId);
    }

    @Override
    public LocalDate getExtractDate() {
        return queryPort.getExtractDate();
    }

    @Override
    public LocalDate getExtractionDate() {
        return queryPort.getExtractionDate();
    }
}
