
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReferenceServiceImplTest {

    @Mock
    private ReferenceRepository referenceRepository;

    @Mock
    private ReferenceRepositoryMapper referenceRepositoryMapper;

    @InjectMocks
    private ReferenceServiceImpl referenceService;

    private Pageable pageable;
    private ReferenceDbo referenceDbo;
    private Reference reference;

    @BeforeEach
    void setUp() {
        pageable = PageRequest.of(0, 20);
        referenceDbo = mock(ReferenceDbo.class);
        reference = mock(Reference.class);
    }

    @Test
    void shouldFindReferencesByDomainWhenSearchIsNull() {
        // Given
        String domain = "YESNO";

        Page<ReferenceDbo> dboPage =
                new PageImpl<>(List.of(referenceDbo));

        when(referenceRepository.findByDomain(domain, pageable))
                .thenReturn(dboPage);

        when(referenceRepositoryMapper.toDomain(referenceDbo))
                .thenReturn(reference);

        // When
        Page<Reference> result =
                referenceService.getReferencesByDomainPaginated(
                        domain, null, pageable
                );

        // Then
        assertEquals(1, result.getTotalElements());
        assertSame(reference, result.getContent().get(0));

        verify(referenceRepository)
                .findByDomain(domain, pageable);

        verify(referenceRepository, never())
                .findByDomainAndKeyStartingWithIgnoreCase(
                        anyString(), anyString(), any(Pageable.class)
                );

        verify(referenceRepositoryMapper)
                .toDomain(referenceDbo);
    }

    @Test
    void shouldFindReferencesByDomainWhenSearchIsBlank() {
        // Given
        String domain = "YESNO";
        String search = "   ";

        when(referenceRepository.findByDomain(domain, pageable))
                .thenReturn(Page.empty(pageable));

        // When
        Page<Reference> result =
                referenceService.getReferencesByDomainPaginated(
                        domain, search, pageable
                );

        // Then
        assertTrue(result.isEmpty());

        verify(referenceRepository)
                .findByDomain(domain, pageable);

        verify(referenceRepository, never())
                .findByDomainAndKeyStartingWithIgnoreCase(
                        anyString(), anyString(), any(Pageable.class)
                );

        verifyNoInteractions(referenceRepositoryMapper);
    }

    @Test
    void shouldSearchReferencesByDomainAndKey() {
        // Given
        String domain = "COUNTRY";
        String search = "LU";

        Page<ReferenceDbo> dboPage =
                new PageImpl<>(List.of(referenceDbo));

        when(referenceRepository
                .findByDomainAndKeyStartingWithIgnoreCase(
                        domain, search, pageable
                ))
                .thenReturn(dboPage);

        when(referenceRepositoryMapper.toDomain(referenceDbo))
                .thenReturn(reference);

        // When
        Page<Reference> result =
                referenceService.getReferencesByDomainPaginated(
                        domain, search, pageable
                );

        // Then
        assertEquals(1, result.getTotalElements());
        assertSame(reference, result.getContent().get(0));

        verify(referenceRepository)
                .findByDomainAndKeyStartingWithIgnoreCase(
                        domain, search, pageable
                );

        verify(referenceRepository, never())
                .findByDomain(anyString(), any(Pageable.class));

        verify(referenceRepositoryMapper)
                .toDomain(referenceDbo);
    }

    @Test
    void shouldReturnEmptyPageWhenNoReferencesMatchSearch() {
        // Given
        String domain = "COUNTRY";
        String search = "UNKNOWN";

        when(referenceRepository
                .findByDomainAndKeyStartingWithIgnoreCase(
                        domain, search, pageable
                ))
                .thenReturn(Page.empty(pageable));

        // When
        Page<Reference> result =
                referenceService.getReferencesByDomainPaginated(
                        domain, search, pageable
                );

        // Then
        assertTrue(result.isEmpty());
        assertEquals(0, result.getTotalElements());

        verifyNoInteractions(referenceRepositoryMapper);
    }

    @Test
    void shouldReturnReferenceWhenDomainAndValueExist() {
        // Given
        String domain = "YESNO";
        String value = "YES";

        when(referenceRepository.findByDomainAndValue(domain, value))
                .thenReturn(Optional.of(referenceDbo));

        when(referenceRepositoryMapper.toDomain(referenceDbo))
                .thenReturn(reference);

        // When
        Optional<Reference> result =
                referenceService.getReferencesByDomainAndValue(
                        domain, value
                );

        // Then
        assertTrue(result.isPresent());
        assertSame(reference, result.get());

        verify(referenceRepository)
                .findByDomainAndValue(domain, value);

        verify(referenceRepositoryMapper)
                .toDomain(referenceDbo);
    }

    @Test
    void shouldReturnEmptyWhenDomainAndValueDoNotExist() {
        // Given
        String domain = "YESNO";
        String value = "UNKNOWN";

        when(referenceRepository.findByDomainAndValue(domain, value))
                .thenReturn(Optional.empty());

        // When
        Optional<Reference> result =
                referenceService.getReferencesByDomainAndValue(
                        domain, value
                );

        // Then
        assertTrue(result.isEmpty());

        verify(referenceRepository)
                .findByDomainAndValue(domain, value);

        verifyNoInteractions(referenceRepositoryMapper);
    }
}
