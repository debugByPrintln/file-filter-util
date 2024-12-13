package com.shift.processor;

import com.shift.data.Statistics;
import com.shift.cli.CommandLineOptions;
import lombok.Getter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс, отвечающий за обработку файлов и разделение их содержимого на категории: целые числа, вещественные числа и строки.
 * Обработанные данные записываются в отдельные файлы, а также собирается статистика по каждой категории.
 *
 * @author Мельников Никита
 * @version 1.0
 */
@Getter
public class FileProcessor {
    /**
     * Настройки командной строки, переданные приложению.
     */
    private final CommandLineOptions options;

    /**
     * Списки строковых представлений целых чисел, вещественных чисел и строк, обработанных из файлов.
     */
    private final List<String> integers = new ArrayList<>();
    private final List<String> floats = new ArrayList<>();
    private final List<String> strings = new ArrayList<>();

    /**
     * Объект для сбора статистики по обработанным данным.
     */
    private final Statistics statistics = new Statistics();

    /**
     * Конструктор класса.
     *
     * @param options Настройки командной строки, переданные приложению.
     */
    public FileProcessor(CommandLineOptions options) {
        this.options = options;
    }

    /**
     * Обрабатывает файл, разделяя его содержимое на целые числа, вещественные числа и строки.
     * Обновляет статистику для каждой категории.
     *
     * @param filePath Путь к файлу, который нужно обработать.
     * @throws IOException Если произошла ошибка при чтении файла.
     */
    public void processFile(Path filePath) throws IOException {
        if (!Files.exists(filePath)) {
            System.err.println("Файл не найден: " + filePath);
            return;
        }

        List<String> lines = Files.readAllLines(filePath);
        for (String line : lines) {
            if (line.matches("-?\\d+")) {
                integers.add(line);
                statistics.updateIntegerStatistics(new BigInteger(line));
            }
            else if (line.matches("-?\\d+(\\.\\d+)?([eE][-+]?\\d+)?")) {
                floats.add(line);
                statistics.updateFloatStatistics(new BigDecimal(line));
            }
            else {
                strings.add(line);
                statistics.updateStringStatistics(line);
            }
        }
    }

    /**
     * Записывает результаты обработки в файлы.
     * Каждая категория данных записывается в отдельный файл: целые числа, вещественные числа и строки.
     *
     * @throws IOException Если произошла ошибка при записи в файл.
     */
    public void writeResults() throws IOException {
        writeListToFile(integers, "integers.txt");
        writeListToFile(floats, "floats.txt");
        writeListToFile(strings, "strings.txt");
    }

    /**
     * Записывает список строк в файл.
     * Если директория для файла не существует, она будет создана.
     *
     * @param list     Список строк для записи в файл.
     * @param fileName Имя файла, в который будут записаны данные.
     * @throws IOException Если произошла ошибка при записи в файл.
     */
    private void writeListToFile(List<String> list, String fileName) throws IOException {
        if (list.isEmpty()) return;

        Path outputPath = Path.of(options.getOutputPath(), options.getPrefix() + fileName);
        Path outputDir = outputPath.getParent();

        // Создаем директорию, если она не существует
        if (!Files.exists(outputDir)) {
            Files.createDirectories(outputDir);
        }

        try (BufferedWriter writer = Files.newBufferedWriter(outputPath, StandardOpenOption.CREATE,
                options.getOutputMode() == CommandLineOptions.OutputMode.APPEND ? StandardOpenOption.APPEND : StandardOpenOption.TRUNCATE_EXISTING)) {
            for (String item : list) {
                writer.write(item);
                writer.newLine();
            }
        }
    }

    /**
     * Выводит статистику по обработанным данным.
     * Если указан флаг краткой статистики, выводится краткая информация.
     * Если указан флаг полной статистики, выводится подробная информация.
     */
    public void printStatistics() {
        if (options.isShortStats()){
            statistics.printShortStatistics();
        }
        if (options.isFullStats()) {
            statistics.printFullStatistics();
        }
    }
}