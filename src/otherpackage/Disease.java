package otherpackage;

import java.util.Set;

public class Disease {
    private String name;
    private Set<String> symptoms;

    public Disease(String name, Set<String> symptoms) {
        this.name = name;
        this.symptoms = symptoms;
    }

    public String getName() {
        return name;
    }

    public Set<String> getSymptoms() {
        return symptoms;
    }
}
