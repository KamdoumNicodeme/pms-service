package dev.hexa.pmsservice.infrastructure.adapters.primary;

import dev.hexa.pmsservice.application.dto.*;
import dev.hexa.pmsservice.application.port.primary.PmsLegacyServicePort;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequiredArgsConstructor
@CrossOrigin("*")
@RequestMapping("pms/v1")
public class PmsLegacyRestAdapter {

    private final PmsLegacyServicePort service;

    @GetMapping("/client/all")
    public List<LegacyClientDto> findAll() {
        return service.findAllClients();
    }

    @GetMapping("/client")
    public List<LegacyClientNomQuaDto> findAllByNomAndQua() {
        return service.findAllByNomAndQua();
    }

    @GetMapping("/getDetailOD/{pcli}")
    public List<LegacyClientDto> getDetailOD(@PathVariable String pcli) {
        return service.getDetailOD(pcli);
    }

    @GetMapping("/getClassCustomerContagion")
    public List<CustomerContagionDto> getClassCustomerContagion(@RequestParam("marge") int marge,
                                                                @RequestParam("observation") int observation,
                                                                @RequestParam("startFrom") String startFrom,
                                                                @RequestParam("minId") int minId,
                                                                @RequestParam("maxId") int maxId) {
        return service.getClassCustomerContagion(marge, observation, startFrom, minId, maxId);
    }

    @GetMapping("/getNumberCli")
    public int getNumberCli() {
        return service.getNumberCli();
    }

    @GetMapping("/getCustomerOd/{pcli}")
    public List<CustomerOdDto> getCustomerOD(@PathVariable("pcli") String pcli) {
        return service.getCustomerOD(pcli);
    }

    @GetMapping("/getCustomerLoans/{pcli}")
    public List<CustomerLoanDto> getCustomerLoans(@PathVariable("pcli") String pcli) {
        return service.getCustomerLoans(pcli);
    }

    @GetMapping("/getCustomerLoanDetail/{eve}/{age}/{dev}")
    public List<LoanDetailDto> getLoanDetail(@PathVariable String eve,
                                             @PathVariable String age,
                                             @PathVariable String dev) {
        return service.getLoanDetail(eve, age, dev);
    }

    @GetMapping("/getCustomerProfilDetail/{monthEndId}")
    public List<CustomerProfileDetailDto> getCustomerProfileDetail(@PathVariable int monthEndId) {
        return service.getCustomerProfileDetail(monthEndId);
    }

    @GetMapping("/simulateData1/{strmonthend}")
    public int simulate(@PathVariable("strmonthend") int monthEndId) {
        return service.simulate(monthEndId);
    }

    @GetMapping("/extractDate")
    public LocalDate getExtractDate() {
        return service.getExtractDate();
    }

    @GetMapping("/getextractDate")
    public LocalDate getExtractionDate() {
        return service.getExtractionDate();
    }
}
