package com.lab5.task4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Task4HtmlTags {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введіть URL сторінки (натисніть Enter для https://example.com): ");
        String urlString = scanner.nextLine().trim();
        if (urlString.isEmpty()) {
            urlString = "https://example.com";
        }

        try {
            URL url = URI.create(urlString).toURL();
            StringBuilder htmlContent = new StringBuilder();

            try (BufferedReader reader = new BufferedReader(
                    new InputStreamReader(url.openStream(), StandardCharsets.UTF_8))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    htmlContent.append(line).append("\n");
                }
            }

            Pattern tagPattern = Pattern.compile("<\\s*([a-zA-Z][a-zA-Z0-9]*)\\b[^>]*>");
            Matcher matcher = tagPattern.matcher(htmlContent.toString());

            Map<String, Integer> tagFrequency = new HashMap<>();
            while (matcher.find()) {
                String tagName = matcher.group(1).toLowerCase();
                tagFrequency.put(tagName, tagFrequency.getOrDefault(tagName, 0) + 1);
            }

            if (tagFrequency.isEmpty()) {
                System.out.println("Тегів на сторінці не знайдено.");
                return;
            }

            System.out.println("\n=== а) Теги в лексикографічному порядку ===");
            Map<String, Integer> sortedByName = new TreeMap<>(tagFrequency);
            for (Map.Entry<String, Integer> entry : sortedByName.entrySet()) {
                System.out.printf("<%-12s : %d\n", entry.getKey() + ">", entry.getValue());
            }

            System.out.println("\n=== b) Теги за зростанням частоти появи ===");
            var sortedByFreq = new ArrayList<>(tagFrequency.entrySet());
            sortedByFreq.sort(Map.Entry.<String, Integer>comparingByValue()
                    .thenComparing(Map.Entry.comparingByKey()));

            for (Map.Entry<String, Integer> entry : sortedByFreq) {
                System.out.printf("<%-12s : %d\n", entry.getKey() + ">", entry.getValue());
            }

        } catch (IOException | IllegalArgumentException e) {
            System.err.println("Помилка завантаження сторінки за URL: " + e.getMessage());
        }
    }
}
