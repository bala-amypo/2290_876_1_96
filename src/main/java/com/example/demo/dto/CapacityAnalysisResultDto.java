public class CapacityAnalysisResultDto {

    private String teamName;
    private boolean risky;
    private Map<LocalDate, Integer> capacityByDate;

    // ✅ REQUIRED BY TESTS
    public CapacityAnalysisResultDto(
            String teamName,
            boolean risky,
            Map<LocalDate, Integer> capacityByDate
    ) {
        this.teamName = teamName;
        this.risky = risky;
        this.capacityByDate = capacityByDate;
    }

    public String getTeamName() {
        return teamName;
    }

    // ✅ REQUIRED BY TESTS
    public boolean isRisky() {
        return risky;
    }

    // ✅ REQUIRED BY TESTS
    public Map<LocalDate, Integer> getCapacityByDate() {
        return capacityByDate;
    }
}
