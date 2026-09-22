@Mapper(uses = DateMapper.class)
public interface ClientProfilingCaseRestApiModelMapper {

    ClientProfilingCaseDataDto toDto(
            ClientProfilingCaseData clientProfilingCaseData
    );

    ChangeClientInformationDto toDto(
            ChangeClientInformation changeClientInformation
    );

    ChangeClientInformation toDomain(
            ChangeClientInformationDto changeClientInformationDto
    );


    // =========================================================
    // DOMAIN -> DTO
    // =========================================================

    default ClientProfilingThirdPartyDto toThirdPartyDto(
            ClientProfilingThirdParty clientProfilingThirdParty
    ) {

        if (clientProfilingThirdParty == null) {
            return null;
        }

        return switch (clientProfilingThirdParty.getType()) {

            case MORAL_PERSON ->
                    toMoralPersonDto(clientProfilingThirdParty);

            case PHYSICAL_PERSON ->
                    toPhysicalPersonDto(clientProfilingThirdParty);

            default ->
                    throw new IllegalArgumentException(
                            String.format(
                                    "Third party type '%s' not supported",
                                    clientProfilingThirdParty.getType()
                            )
                    );
        };
    }

    ClientProfilingThirdPartyDto.PhysicalPersonDtoClientProfiling toPhysicalPersonDto(
            ClientProfilingThirdParty clientProfilingThirdParty
    );

    ClientProfilingThirdPartyDto.MoralPersonDtoClientProfiling toMoralPersonDto(
            ClientProfilingThirdParty clientProfilingThirdParty
    );


    // =========================================================
    // DTO -> DOMAIN
    // =========================================================

    default ClientProfilingThirdParty toThirdParty(
            ClientProfilingThirdPartyDto clientProfilingThirdPartyDto
    ) {

        if (clientProfilingThirdPartyDto == null) {
            return null;
        }

        return switch (clientProfilingThirdPartyDto.getType()) {

            case PHYSICAL_PERSON ->
                    toPhysicalPerson(
                            (ClientProfilingThirdPartyDto.PhysicalPersonDtoClientProfiling)
                                    clientProfilingThirdPartyDto
                    );

            case MORAL_PERSON ->
                    toMoralPerson(
                            (ClientProfilingThirdPartyDto.MoralPersonDtoClientProfiling)
                                    clientProfilingThirdPartyDto
                    );

            default ->
                    throw new IllegalArgumentException(
                            String.format(
                                    "Third party type '%s' not supported",
                                    clientProfilingThirdPartyDto.getType()
                            )
                    );
        };
    }

    ClientProfilingThirdParty toPhysicalPerson(
            ClientProfilingThirdPartyDto.PhysicalPersonDtoClientProfiling clientProfilingThirdPartyDto
    );

    ClientProfilingThirdParty toMoralPerson(
            ClientProfilingThirdPartyDto.MoralPersonDtoClientProfiling clientProfilingThirdPartyDto
    );
}
