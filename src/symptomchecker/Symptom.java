package symptomchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class Symptom {
    private List<String> symptoms; // danh sách các triệu chứng
    private Map<String, List<String>> diseases; // danh sách các bệnh tương ứng với từng triệu chứng
    private Map<String, Integer> diseaseCount; // đếm số triệu chứng của từng bệnh
    private String fileName; // tên file txt chứa dữ liệu về triệu chứng và bệnh
    
    public Symptom(String fileName) throws IOException {
        this.fileName = fileName;
        this.symptoms = new ArrayList<>();
        this.diseases = new HashMap<>();
        this.diseaseCount = new HashMap<>();
        loadSymptomsAndDiseases();
    }

    private void loadSymptomsAndDiseases() throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(this.fileName));
        String line;
        while ((line = reader.readLine()) != null) {
            String[] parts = line.split(":");
            String diseaseName = parts[0].trim();
            List<String> symptomsList = Arrays.asList(parts[1].trim().split(","));
            diseases.put(diseaseName, symptomsList);
            for (String symptom : symptomsList) {
                if (!symptoms.contains(symptom)) {
                    symptoms.add(symptom);
                }
                Integer count = diseaseCount.get(symptom);
                if (count == null) {
                    count = 0;
                }
                diseaseCount.put(symptom, count + 1);
            }
        }
        reader.close();
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public Map<String, List<String>> getDiseases() {
        return diseases;
    }

    public Map<String, Integer> getDiseaseCount() {
        return diseaseCount;
    }

    public List<String> suggestDiseases(List<String> inputSymptoms) {
        Map<String, Integer> diseaseScores = new HashMap<>();
        for (String symptom : inputSymptoms) {
            if (symptoms.contains(symptom)) {
                for (String disease : diseases.keySet()) {
                    List<String> diseaseSymptoms = diseases.get(disease);
                    if (diseaseSymptoms.contains(symptom)) {
                        Integer score = diseaseScores.get(disease);
                        if (score == null) {
                            score = 0;
                        }
                        diseaseScores.put(disease, score + 1);
                    }
                }
            }
        }
        List<String> suggestedDiseases = new ArrayList<>(diseaseScores.keySet());
        suggestedDiseases.sort((d1, d2) -> diseaseScores.get(d2) - diseaseScores.get(d1));
        return suggestedDiseases;
    }
    
    public String getName() {
        return "Symptom Checker";
    }
}
