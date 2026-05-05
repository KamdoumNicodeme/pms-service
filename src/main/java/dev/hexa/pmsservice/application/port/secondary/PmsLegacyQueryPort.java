package dev.hexa.pmsservice.application.port.secondary;

import dev.hexa.pmsservice.application.dto.*;

import java.time.LocalDate;
import java.util.List;

public interface PmsLegacyQueryPort {
    List<LegacyClientDto> findAllClients();
    List<LegacyClientNomQuaDto> findAllByNomAndQua();
    List<LegacyClientDto> getDetailOD(String pcli);
    List<CustomerContagionDto> getClassCustomerContagion(int marge, int observation, String startFrom, int minId, int maxId);
    int getNumberCli();
    List<CustomerOdDto> getCustomerOD(String pcli);
    List<CustomerLoanDto> getCustomerLoans(String pcli);
    List<LoanDetailDto> getLoanDetail(String eve, String age, String dev);
    List<CustomerProfileDetailDto> getCustomerProfileDetail(int monthEndId);
    int simulate(int monthEndId);
    LocalDate getExtractDate();
    LocalDate getExtractionDate();
}
