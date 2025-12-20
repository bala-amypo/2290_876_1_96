import java.time.LocalDate;
import java.util.List;
import com.example.demo.model.CapacityAlert;

public interface CapacityAnalysisService {
    List<CapacityAlert> analyzeCapacity(String team,
                                        LocalDate startDate,
                                        LocalDate endDate);
}
