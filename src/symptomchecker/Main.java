package symptomchecker;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        try {
            String symptomFilePath = "symptoms.txt";
            SymptomChecker symptomChecker = SymptomChecker.loadSymptoms(symptomFilePath);
            List<Symptom> symptomObjects = symptomChecker.getSymptoms();
            List<String> symptoms = symptomObjects.stream()
                    .map(Symptom::getName)
                    .collect(Collectors.toList());
            System.out.println("Các triệu chứng có trong hệ thống:");
            for (String symptom : symptoms) {
                System.out.println(symptom);
            }

            System.out.println();

            String diseaseFilePath = "diseases.txt";
            List<Disease> diseases = DiseaseLoader.loadDiseases(diseaseFilePath);
            System.out.println("Các bệnh có trong hệ thống:");
            for (Disease disease : diseases) {
                System.out.println(disease.getName());
            }

            System.out.println();

            try (Scanner scanner = new Scanner(System.in)) {
                System.out.println("Vui lòng nhập các triệu chứng bạn đang gặp phải (mỗi triệu chứng cách nhau bởi dấu phẩy):");
                String input = scanner.nextLine();
                System.out.println("Bạn đã nhập các triệu chứng sau:");
                System.out.println(input);
                List<String> userSymptoms = Arrays.stream(input.split(","))
                        .map(String::trim)
                        .collect(Collectors.toList());

                // Suggest diseases based on user's symptoms
                List<Disease> suggestedDiseases = suggestDiseases(userSymptoms, diseases);

                // Sort suggested diseases by the number of matching symptoms in descending order
                Collections.sort(suggestedDiseases, Collections.reverseOrder());

                System.out.println("Các bệnh được đề xuất:");
                for (Disease disease : suggestedDiseases) {
                    System.out.println(disease.getName() + " (" + disease.setMatchingSymptoms(userSymptoms).size() + " triệu chứng khớp)");
                }
            }

        } catch (IOException e) {
            System.out.println("Không thể đọc file.");
            e.printStackTrace();
        }
    }

    private static List<Disease> suggestDiseases(List<String> userSymptoms, List<Disease> diseases) {
        List<Disease> suggestedDiseases = new ArrayList<>();
        for (Disease disease : diseases) {
            List<String> matchingSymptoms = disease.setMatchingSymptoms(userSymptoms);
            if (!matchingSymptoms.isEmpty()) {
                suggestedDiseases.add(disease);
            }
        }
        return suggestedDiseases;
    }

}
