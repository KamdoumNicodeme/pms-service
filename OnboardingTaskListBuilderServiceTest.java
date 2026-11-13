
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReferenceControllerTest {

    @Mock
    private ReferenceService referenceService;

    @Mock
    private ReferenceDtoMapper referenceDtoMapper;

    @InjectMocks
    private ReferenceController referenceController;

    private static final String DOMAIN = "YESNO";
    private static final String VALUE = "YES";

    @Test
    void shouldReturnReferenceWhenDomainAndValueExist() {
        // Given
        Reference reference = mock(Reference.class);
        ReferenceDto referenceDto = mock(ReferenceDto.class);

        when(referenceService.getReferencesByDomainAndValue(DOMAIN, VALUE))
                .thenReturn(Optional.of(reference));

        when(referenceDtoMapper.toDto(reference))
                .thenReturn(referenceDto);

        // When
        ResponseEntity<ReferenceDto> response =
                referenceController.getReferenceByDomainAndValue(
                        DOMAIN, VALUE
                );

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertSame(referenceDto, response.getBody());

        verify(referenceService)
                .getReferencesByDomainAndValue(DOMAIN, VALUE);

        verify(referenceDtoMapper).toDto(reference);
    }

    @Test
    void shouldReturnNotFoundWhenReferenceDoesNotExist() {
        // Given
        when(referenceService.getReferencesByDomainAndValue(DOMAIN, VALUE))
                .thenReturn(Optional.empty());

        // When
        ResponseEntity<ReferenceDto> response =
                referenceController.getReferenceByDomainAndValue(
                        DOMAIN, VALUE
                );

        // Then
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());

        verify(referenceService)
                .getReferencesByDomainAndValue(DOMAIN, VALUE);

        verifyNoInteractions(referenceDtoMapper);
    }

    @Test
    void shouldPropagateExceptionWhenServiceFails() {
        // Given
        when(referenceService.getReferencesByDomainAndValue(DOMAIN, VALUE))
                .thenThrow(new RuntimeException("Unexpected error"));

        // When / Then
        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> referenceController.getReferenceByDomainAndValue(
                        DOMAIN, VALUE
                )
        );

        assertEquals("Unexpected error", exception.getMessage());

        verify(referenceService)
                .getReferencesByDomainAndValue(DOMAIN, VALUE);

        verifyNoInteractions(referenceDtoMapper);
    }
}
