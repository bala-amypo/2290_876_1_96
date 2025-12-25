@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repo;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repo) {
        this.repo = repo;
    }

    @Override
    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile e = new EmployeeProfile();
        e.setEmployeeId(dto.getEmployeeId());
        e.setFullName(dto.getFullName()); // ✅ FIXED
        e.setEmail(dto.getEmail());
        e.setTeamName(dto.getTeamName());
        e.setRole(dto.getRole());
        e.setActive(true);

        return toDto(repo.save(e));
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));

        e.setFullName(dto.getFullName()); // ✅ FIXED
        e.setTeamName(dto.getTeamName());
        e.setRole(dto.getRole());
        e.setActive(dto.getActive());

        return toDto(repo.save(e));
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        return toDto(repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found")));
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String team) {
        return repo.findByTeamNameAndActiveTrue(team)
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return repo.findAll()
                .stream()
                .map(this::toDto)
                .toList();
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile e = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Employee not found"));
        e.deactivate();
        repo.save(e);
    }

    private EmployeeProfileDto toDto(EmployeeProfile e) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(e.getId());
        dto.setEmployeeId(e.getEmployeeId());
        dto.setFullName(e.getFullName()); // ✅ FIXED
        dto.setEmail(e.getEmail());
        dto.setTeamName(e.getTeamName());
        dto.setRole(e.getRole());
        dto.setActive(e.getActive());
        return dto;
    }
}
