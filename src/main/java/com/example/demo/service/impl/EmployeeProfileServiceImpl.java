@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public EmployeeProfile update(Long id, EmployeeProfile updated) {
        EmployeeProfile existing = repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));

        existing.setName(updated.getName());
        existing.setTeamName(updated.getTeamName());
        existing.setActive(updated.isActive());

        return repo.save(existing);
    }

    @Override
    public EmployeeProfile getById(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Employee not found"));
    }

    @Override
    public List<EmployeeProfile> getEmployeesByTeam(String teamName) {
        return repo.findByTeamNameAndActiveTrue(teamName);
    }
}
