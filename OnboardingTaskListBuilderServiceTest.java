
@GetMapping(value = "/by-value", produces = MediaType.APPLICATION_JSON_VALUE)
@Operation(
    summary = "Get reference",
    description = "Get a reference by domain and value"
)
@ApiResponses(value = {
    @ApiResponse(responseCode = "200", description = "Reference found"),
    @ApiResponse(responseCode = "404", description = "Reference not found"),
    @ApiResponse(responseCode = "500", description = "Unexpected error")
})
@LogMilestone(action = "Get reference by domain and value")
public ResponseEntity<ReferenceDto> getReferenceByDomainAndValue(
        @Parameter(name = "domain", description = "Domain of the reference", example = "YESNO")
        @LoggableValue @RequestParam String domain,
        @LoggableValue @RequestParam String value) {

    return referenceService.getReferencesByDomainAndValue(domain, value)
            .map(reference ->
                    ResponseEntity.ok(referenceDtoMapper.toDto(reference)))
            .orElseGet(() -> ResponseEntity.notFound().build());
}
