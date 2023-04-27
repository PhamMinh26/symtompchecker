package symptomchecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Arrays;

import otherpackage.Disease; // import class Disease từ package khác

public class DiseaseSuggester {

    private Map<String, List<String>> symptomToDiseases;

    public DiseaseSuggester(List<Disease> diseases) {
        this.symptomToDiseases = new HashMap<>();
        for (Disease disease : diseases) {
            for (String symptom : disease.getSymptoms()) {
                List<String> diseaseList = symptomToDiseases.getOrDefault(symptom, new ArrayList<>());
                diseaseList.add(disease.getName());
                symptomToDiseases.put(symptom, diseaseList);
            }
        }
    }

    public List<String> suggestDiseases(List<String> symptoms) {
        Map<String, Integer> diseaseCount = new HashMap<>();
        for (String symptom : symptoms) {
            List<String> relatedDiseases = symptomToDiseases.get(symptom);
            if (relatedDiseases != null) {
                for (String disease : relatedDiseases) {
                    int count = diseaseCount.getOrDefault(disease, 0);
                    diseaseCount.put(disease, count + 1);
                }
            }
        }
        List<String> suggestedDiseases = new ArrayList<>();
        int maxCount = 0;
        for (Map.Entry<String, Integer> entry : diseaseCount.entrySet()) {
            int count = entry.getValue();
            if (count > maxCount) {
                suggestedDiseases.clear();
                suggestedDiseases.add(entry.getKey());
                maxCount = count;
            } else if (count == maxCount) {
                suggestedDiseases.add(entry.getKey());
            }
        }
        return suggestedDiseases;
    }

    public static void main(String[] args) {
        Disease disease = new Disease("Tên bệnh", new HashSet<>(Arrays.asList("Triệu chứng 1", "Triệu chứng 2")));
        List<Disease> diseases = new ArrayList<>();
        diseases.add(disease);
        DiseaseSuggester diseaseSuggester = new DiseaseSuggester(diseases);
        List<String> suggestedDiseases = diseaseSuggester.suggestDiseases(Arrays.asList("Triệu chứng 1"));
        System.out.println(suggestedDiseases);
    }
}
