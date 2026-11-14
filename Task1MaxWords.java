package com.lab5.task1;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Task1MaxWords {
    public static void main(String[] args) {
        String defaultFileName = "input_text.txt";
        createSampleFileIfNotExists(defaultFileName);

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть шлях до файлу (або натисніть Enter для '" + defaultFileName + "'): ");
        String filePath = scanner.nextLine().trim();
        if (filePath.isEmpty()) {
            filePath = defaultFileName;
        }

        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(new FileInputStream(filePath), StandardCharsets.UTF_8))) {

            String line;
            String maxWordsLine = "";
            int maxWordsCount = 0;
            int lineNumber = 0;
            int targetLineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                String trimmed = line.trim();
                int wordCount = trimmed.isEmpty() ? 0 : trimmed.split("\\s+").length;

                if (wordCount > maxWordsCount) {
                    maxWordsCount = wordCount;
                    maxWordsLine = line;
                    targetLineNumber = lineNumber;
                }
            }

            if (maxWordsCount > 0) {
                System.out.println("\nРядок №" + targetLineNumber + " містить найбільше слів (" + maxWordsCount + "):");
                System.out.println("\"" + maxWordsLine + "\"");
            } else {
                System.out.println("Файл порожній або не містить слів.");
            }

        } catch (IOException e) {
            System.err.println("Помилка читання файлу: " + e.getMessage());
        }
    }

    private static void createSampleFileIfNotExists(String fileName) {
        File file = new File(fileName);
        if (!file.exists()) {
            try (PrintWriter writer = new PrintWriter(file, StandardCharsets.UTF_8)) {
                writer.println("Перший рядок містить чотири слова.");
                writer.println("Другий рядок трохи довший і має вже дев'ять слів у собі.");
                writer.println("Третій рядок короткий.");
            } catch (IOException ignored) {
            }
        }
    }
}
