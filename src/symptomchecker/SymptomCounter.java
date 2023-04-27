package symptomchecker;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SymptomCounter {
    private Map<String, Integer> symptomCounts;

    public SymptomCounter(List<String> symptoms) {
        symptomCounts = new HashMap<>();
        for (String symptom : symptoms) {
            symptomCounts.put(symptom, 0);
        }
    }

    public void countSymptoms(List<String> symptoms) {
        for (String symptom : symptoms) {
            if (symptomCounts.containsKey(symptom)) {
                int currentCount = symptomCounts.get(symptom);
                symptomCounts.put(symptom, currentCount + 1);
            }
        }
    }

    public int getCount(String symptom) {
        return symptomCounts.get(symptom);
    }
}
