package com.lab5.task2;

import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Scanner;

public class Task2Main {
    public static void main(String[] args) {
        Shape[] shapes = new Shape[]{
                new Rectangle("Червоний", 4.0, 5.0),
                new Circle("Синій", 3.0),
                new Triangle("Зелений", 6.0, 4.0),
                new Rectangle("Жовтий", 2.5, 8.0),
                new Circle("Червоний", 5.0),
                new Triangle("Синій", 10.0, 3.0),
                new Rectangle("Зелений", 7.0, 3.0),
                new Circle("Жовтий", 2.0),
                new Triangle("Червоний", 5.0, 6.0),
                new Rectangle("Синій", 6.0, 6.0)
        };

        ShapeFileManager fileManager = new ShapeFileManager();
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\n--- МЕНЮ (Лаба 5.2 - Робота з фігурами та файлами) ---");
            System.out.println("1. Показати всі фігури");
            System.out.println("2. Обчислити сумарну площу фігур за кольором (ввід з клавіатури)");
            System.out.println("3. Впорядкувати фігури за збільшенням площі");
            System.out.println("4. Зберегти набір фігур у файл");
            System.out.println("5. Завантажити набір фігур з файлу");
            System.out.println("0. Вихід");
            System.out.print("Оберіть дію: ");

            String choice = scanner.nextLine();

            try {
                switch (choice) {
                    case "1":
                        System.out.println("\n=== Поточний набір фігур ===");
                        for (Shape s : shapes) {
                            System.out.println(s);
                        }
                        break;

                    case "2":
                        System.out.print("Введіть колір для пошуку (наприклад, Червоний, Синій, Зелений, Жовтий): ");
                        String color = scanner.nextLine().trim();
                        double sumArea = 0;
                        int count = 0;
                        for (Shape s : shapes) {
                            if (s.getShapeColor().equalsIgnoreCase(color)) {
                                sumArea += s.calcArea();
                                count++;
                            }
                        }
                        if (count == 0) {
                            System.out.println("Фігур кольору '" + color + "' не знайдено.");
                        } else {
                            System.out.printf("Знайдено фігур: %d. Їх сумарна площа: %.2f\n", count, sumArea);
                        }
                        break;

                    case "3":
                        Shape[] sorted = Arrays.copyOf(shapes, shapes.length);
                        Arrays.sort(sorted, Comparator.comparingDouble(Shape::calcArea));
                        System.out.println("\n=== Фігури за збільшенням площі ===");
                        for (Shape s : sorted) {
                            System.out.println(s);
                        }
                        break;

                    case "4":
                        System.out.print("Введіть шлях та ім'я файлу для збереження (наприклад, shapes.dat): ");
                        String savePath = scanner.nextLine().trim();
                        if (savePath.isEmpty()) {
                            System.out.println("Помилка: ім'я файлу не може бути порожнім.");
                            break;
                        }
                        fileManager.saveShapesToFile(shapes, savePath);
                        System.out.println("Дані успішно збережено у файл: " + savePath);
                        break;

                    case "5":
                        System.out.print("Введіть шлях та ім'я файлу для читання (наприклад, shapes.dat): ");
                        String loadPath = scanner.nextLine().trim();
                        shapes = fileManager.loadShapesFromFile(loadPath);
                        System.out.println("Дані успішно завантажено з файлу (" + shapes.length + " об'єктів).");
                        break;

                    case "0":
                        running = false;
                        System.out.println("Завершення роботи.");
                        break;

                    default:
                        System.out.println("Невірний пункт меню. Спробуйте ще раз.");
                }
            } catch (IOException e) {
                System.err.println("Помилка вводу/виводу при роботі з файлом: " + e.getMessage());
            } catch (ClassNotFoundException e) {
                System.err.println("Помилка: формат даних у файлі не відповідає класу Shape.");
            } catch (Exception e) {
                System.err.println("Непередбачена помилка: " + e.getMessage());
            }
        }
    }
}
