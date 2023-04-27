package symptomchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymptomLoader {

    public SymptomLoader() {
    }

    public List<String> loadSymptoms(String filePath) throws IOException {
        List<String> symptoms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                symptoms.add(line);
            }
        }
        return symptoms;
    }

    public static SymptomChecker loadSymptomChecker(String symptomFilePath) {
        try {
            List<String> lines = Files.readAllLines(Path.of(symptomFilePath));
            Map<String, List<String>> symptomMap = new HashMap<>();
            for (String line : lines) {
                String[] parts = line.split("=");
                if (parts.length != 2) {
                    System.out.println("Invalid line format: " + line);
                    continue;
                }
                String condition = parts[0];
                String[] symptoms = parts[1].split(",");
                for (String symptom : symptoms) {
                    if (!symptomMap.containsKey(symptom)) {
                        symptomMap.put(symptom, new ArrayList<>());
                    }
                    symptomMap.get(symptom).add(condition);
                }
            }
            return new SymptomChecker(new ArrayList<>(symptomMap.keySet()));
        } catch (IOException e) {
            System.out.println("Failed to load symptoms from file: " + symptomFilePath);
            return null;
        }
    }
}
