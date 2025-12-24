@Service
public class EmployeeProfileServiceImpl implements EmployeeProfileService {

    private final EmployeeProfileRepository repository;

    public EmployeeProfileServiceImpl(EmployeeProfileRepository repository) {
        this.repository = repository;
    }

    @Override
    public EmployeeProfileDto create(EmployeeProfileDto dto) {
        EmployeeProfile employee = new EmployeeProfile();
        employee.setEmployeeId(dto.getEmployeeId());
        employee.setFullName(dto.getFullName());   // ✅ FIXED
        employee.setEmail(dto.getEmail());
        employee.setTeamName(dto.getTeamName());
        employee.setRole(dto.getRole());

        repository.save(employee);
        return mapToDto(employee);
    }

    @Override
    public EmployeeProfileDto update(Long id, EmployeeProfileDto dto) {
        EmployeeProfile employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));

        employee.setFullName(dto.getFullName());   // ✅ FIXED
        employee.setEmail(dto.getEmail());
        employee.setTeamName(dto.getTeamName());
        employee.setRole(dto.getRole());

        repository.save(employee);
        return mapToDto(employee);
    }

    @Override
    public EmployeeProfileDto getById(Long id) {
        EmployeeProfile employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        return mapToDto(employee);
    }

    @Override
    public List<EmployeeProfileDto> getByTeam(String teamName) {
        return repository.findByTeamName(teamName)
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public List<EmployeeProfileDto> getAll() {
        return repository.findAll()
                .stream()
                .map(this::mapToDto)
                .toList();
    }

    @Override
    public void deactivate(Long id) {
        EmployeeProfile employee = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
        employee.deactivate();
        repository.save(employee);
    }

    private EmployeeProfileDto mapToDto(EmployeeProfile employee) {
        EmployeeProfileDto dto = new EmployeeProfileDto();
        dto.setId(employee.getId());
        dto.setEmployeeId(employee.getEmployeeId());
        dto.setFullName(employee.getFullName());   // ✅ FIXED
        dto.setEmail(employee.getEmail());
        dto.setTeamName(employee.getTeamName());
        dto.setRole(employee.getRole());
        dto.setActive(employee.getActive());
        return dto;
    }
}
