import java.util.List;
import com.example.demo.model.EmployeeProfile;

public interface EmployeeProfileService {
    List<EmployeeProfile> getActiveEmployeesByTeam(String team);
}
