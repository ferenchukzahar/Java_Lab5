package com.lab5.task3;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class Task3CipherDemo {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        String fileName = "encrypted_data.txt";

        System.out.print("Введіть текст для шифрування: ");
        String originalText = scanner.nextLine();

        System.out.print("Введіть ключовий символ (наприклад, K): ");
        String keyInput = scanner.nextLine();
        char keyChar = keyInput.isEmpty() ? 'K' : keyInput.charAt(0);

        try (CipherWriter writer = new CipherWriter(
                new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8), keyChar)) {
            writer.write(originalText);
            System.out.println("\nТекст успішно зашифровано ключем '" + keyChar + "' (код " + (int) keyChar + ") у файл " + fileName);
        } catch (IOException e) {
            System.err.println("Помилка запису: " + e.getMessage());
        }

        try (BufferedReader rawReader = new BufferedReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8))) {
            System.out.println("Зашифрований вміст у файлі: " + rawReader.readLine());
        } catch (IOException e) {
            System.err.println("Помилка читання сирого файлу: " + e.getMessage());
        }

        try (CipherReader reader = new CipherReader(
                new InputStreamReader(new FileInputStream(fileName), StandardCharsets.UTF_8), keyChar);
             BufferedReader bufferedDecrypted = new BufferedReader(reader)) {
            String decryptedText = bufferedDecrypted.readLine();
            System.out.println("Дешифрований текст: " + decryptedText);
        } catch (IOException e) {
            System.err.println("Помилка дешифрування: " + e.getMessage());
        }
    }
}
