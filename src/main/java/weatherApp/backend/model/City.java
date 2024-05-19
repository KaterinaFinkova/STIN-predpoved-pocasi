package weatherApp.backend.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class City {
    private String name;
    private double latitude;
    private double longitude;
    private String country;
    private List<String> admins;
}