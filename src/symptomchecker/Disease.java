package symptomchecker;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Disease implements Comparable<Disease> {
    private String name;
    private List<String> symptoms;

    public Disease(String name, List<String> symptoms) {
        this.name = name;
        this.symptoms = symptoms;
    }

    public String getName() {
        return name;
    }

    public List<String> getSymptoms() {
        return symptoms;
    }

    public List<String> setMatchingSymptoms(List<String> userSymptoms) {
        List<String> matchingSymptoms = new ArrayList<>();
        for (String symptom : this.symptoms) {
            if (userSymptoms.contains(symptom)) {
                matchingSymptoms.add(symptom);
            }
        }
        return matchingSymptoms;
    }

    @Override
    public int compareTo(Disease other) {
        return Integer.compare(this.setMatchingSymptoms(Collections.emptyList()).size(), other.setMatchingSymptoms(Collections.emptyList()).size());
    }
}
