package symptomchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DiseaseLoader {
    public static List<Disease> loadDiseases(String filePath) throws IOException {
        List<Disease> diseases = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] tokens = line.split(",");
                String name = tokens[0];
                List<String> symptoms = new ArrayList<>();
                for (int i = 1; i < tokens.length; i++) {
                    symptoms.add(tokens[i]);
                }
                Disease disease = new Disease(name, symptoms);
                diseases.add(disease);
            }
        }
        return diseases;
    }
}
