package symptomchecker;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SymptomChecker {
    private List<Symptom> symptoms;

    public SymptomChecker(List<Symptom> symptoms) {
        this.symptoms = symptoms;
    }

    public List<Symptom> getSymptoms() {
        return symptoms;
    }

    public static SymptomChecker loadSymptoms(String filePath) throws IOException {
        List<Symptom> symptoms = loadSymptomsFromFile(filePath);
        return new SymptomChecker(symptoms);
    }

    private static List<Symptom> loadSymptomsFromFile(String filePath) throws IOException {
        List<Symptom> symptoms = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath))) {
            String line;
            while ((line = br.readLine()) != null) {
                Symptom symptom = new Symptom(line.trim());
                symptoms.add(symptom);
            }
        }
        return symptoms;
    }

    public List<Disease> checkSymptoms(List<String> userSymptoms) throws IOException {
        List<Symptom> userSymptomsList = convertToSymptoms(userSymptoms);
        DiseaseLoader diseaseLoader = new DiseaseLoader();
        List<Disease> diseases = diseaseLoader.loadDiseases("diseases.txt");
        List<Disease> matchingDiseases = new ArrayList<>();
        for (Disease disease : diseases) {
            int matchingSymptoms = 0;
            for (Symptom symptom : userSymptomsList) {
                if (disease.hasSymptom(symptom)) {
                    matchingSymptoms++;
                }
            }
            if (matchingSymptoms > 0) {
                disease.setMatchingSymptomsCount(matchingSymptoms);
                matchingDiseases.add(disease);
            }
        }
        Collections.sort(matchingDiseases, new Comparator<Disease>() {
            public int compare(Disease d1, Disease d2) {
                return Integer.compare(d2.getMatchingSymptomsCount(), d1.getMatchingSymptomsCount());
            }
        });
        return matchingDiseases;
    }

    private List<Symptom> convertToSymptoms(List<String> userSymptoms) {
        List<Symptom> symptomsList = new ArrayList<>();
        for (String symptom : userSymptoms) {
            Symptom s = new Symptom(symptom);
            symptomsList.add(s);
        }
        return symptomsList;
    }
}
