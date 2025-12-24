@Service
public class CapacityAnalysisServiceImpl implements CapacityAnalysisService {

    private final TeamCapacityConfigRepository configRepo;
    private final EmployeeProfileRepository employeeRepo;
    private final LeaveRequestRepository leaveRepo;
    private final CapacityAlertRepository alertRepo;

    // ✅ REQUIRED BY TESTS — DO NOT CHANGE
    public CapacityAnalysisServiceImpl(
            TeamCapacityConfigRepository configRepo,
            EmployeeProfileRepository employeeRepo,
            LeaveRequestRepository leaveRepo,
            CapacityAlertRepository alertRepo
    ) {
        this.configRepo = configRepo;
        this.employeeRepo = employeeRepo;
        this.leaveRepo = leaveRepo;
        this.alertRepo = alertRepo;
    }

    @Override
    public CapacityAnalysisResultDto analyzeTeamCapacity(
            String teamName,
            LocalDate startDate,
            LocalDate endDate
    ) {
        TeamCapacityConfig config = configRepo.findByTeamName(teamName)
                .orElseThrow(() -> new BadRequestException("Capacity config not found"));

        Map<LocalDate, Integer> capacityByDate = new HashMap<>();
        boolean risky = false;

        List<EmployeeProfile> employees =
                employeeRepo.findByTeamNameAndActiveTrue(teamName);

        for (LocalDate d = startDate; !d.isAfter(endDate); d = d.plusDays(1)) {
            int onLeave =
                    leaveRepo.findApprovedOnDate(d).size();

            int available = employees.size() - onLeave;
            capacityByDate.put(d, available);

            if (available < config.getTotalHeadcount()
                    * config.getMinCapacityPercent() / 100) {
                risky = true;
            }
        }

        return new CapacityAnalysisResultDto(teamName, risky, capacityByDate);
    }

    @Override
    public List<LocalDate> getOverlappingDates(
            String teamName,
            LocalDate start,
            LocalDate end
    ) {
        List<LocalDate> dates = new ArrayList<>();
        for (LocalDate d = start; !d.isAfter(end); d = d.plusDays(1)) {
            dates.add(d);
        }
        return dates;
    }
}
