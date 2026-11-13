
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ReferenceController.class)
@AutoConfigureMockMvc(addFilters = false)
class ReferenceControllerMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReferenceService referenceService;

    @MockitoBean
    private ReferenceDtoMapper referenceDtoMapper;

    private static final String URL = "/references/by-value";
    private static final String DOMAIN = "YESNO";
    private static final String VALUE = "YES";

    @Test
    void shouldReturnOkWhenReferenceExists() throws Exception {
        // Given
        Reference reference = mock(Reference.class);
        ReferenceDto dto = mock(ReferenceDto.class);

        when(referenceService.getReferencesByDomainAndValue(DOMAIN, VALUE))
                .thenReturn(Optional.of(reference));

        when(referenceDtoMapper.toDto(reference))
                .thenReturn(dto);

        // When / Then
        mockMvc.perform(get(URL)
                        .param("domain", DOMAIN)
                        .param("value", VALUE))
                .andExpect(status().isOk());

        verify(referenceService)
                .getReferencesByDomainAndValue(DOMAIN, VALUE);

        verify(referenceDtoMapper).toDto(reference);
    }

    @Test
    void shouldReturnNotFoundWhenReferenceDoesNotExist()
            throws Exception {

        // Given
        when(referenceService.getReferencesByDomainAndValue(DOMAIN, VALUE))
                .thenReturn(Optional.empty());

        // When / Then
        mockMvc.perform(get(URL)
                        .param("domain", DOMAIN)
                        .param("value", VALUE))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));

        verify(referenceService)
                .getReferencesByDomainAndValue(DOMAIN, VALUE);

        verifyNoInteractions(referenceDtoMapper);
    }

    @Test
    void shouldReturnBadRequestWhenDomainIsMissing()
            throws Exception {

        mockMvc.perform(get(URL)
                        .param("value", VALUE))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(referenceService);
        verifyNoInteractions(referenceDtoMapper);
    }

    @Test
    void shouldReturnBadRequestWhenValueIsMissing()
            throws Exception {

        mockMvc.perform(get(URL)
                        .param("domain", DOMAIN))
                .andExpect(status().isBadRequest());

        verifyNoInteractions(referenceService);
        verifyNoInteractions(referenceDtoMapper);
    }
}
