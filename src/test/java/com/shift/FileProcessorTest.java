package com.shift;

import com.shift.cli.CommandLineOptions;
import com.shift.data.Statistics;
import com.shift.processor.FileProcessor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Класс для тестирования функциональности класса {@link FileProcessor}.
 * Включает тесты для обработки файлов, записи результатов и проверки статистики.
 *
 * @author Мельников Никита
 * @version 1.0
 */
public class FileProcessorTest {

    /**
     * Объект для обработки файлов.
     */
    private FileProcessor processor;
    /**
     * Настройки командной строки для тестов.
     */
    private CommandLineOptions options;

    /**
     * Инициализация тестового окружения перед каждым тестом.
     * Создается объект {@link FileProcessor} с настройками для тестов.
     */
    @BeforeEach
    public void setUp() {
        options = new CommandLineOptions();
        options.setOutputPath("src/test/resources/out");
        options.setPrefix("tests-");
        options.setAppend(false);
        options.setShortStats(true);
        options.setFullStats(false);
        processor = new FileProcessor(options);
    }

    /**
     * Тест для проверки обработки файлов.
     * Проверяет, что содержимое файлов корректно разделяется на целые числа, вещественные числа и строки.
     * Результаты записываются в соответствующие файлы, и их содержимое проверяется.
     *
     * @throws IOException Если произошла ошибка при чтении или записи файлов.
     */
    @Test
    public void testProcessFile() throws IOException {
        Path in1Path = Paths.get("src/test/resources/in/in1.txt");
        Path in2Path = Paths.get("src/test/resources/in/in2.txt");

        processor.processFile(in1Path);
        processor.processFile(in2Path);
        processor.writeResults();

        Path integersPath = Paths.get("src/test/resources/out/tests-integers.txt");
        Path floatsPath = Paths.get("src/test/resources/out/tests-floats.txt");
        Path stringsPath = Paths.get("src/test/resources/out/tests-strings.txt");

        List<String> integers = Files.readAllLines(integersPath);
        List<String> floats = Files.readAllLines(floatsPath);
        List<String> strings = Files.readAllLines(stringsPath);

        // Проверяем содержимое файлов
        assertEquals(3, integers.size());
        assertEquals(3, floats.size());
        assertEquals(6, strings.size());

        // Проверяем содержимое файла с целыми числами
        assertEquals("45", integers.get(0));
        assertEquals("100500", integers.get(1));
        assertEquals("1234567890123456789", integers.get(2));

        // Проверяем содержимое файла с вещественными числами
        assertEquals("3.1415", floats.get(0));
        assertEquals("-0.001", floats.get(1));
        assertEquals("1.528535047E-25", floats.get(2));

        // Проверяем содержимое файла со строками
        assertEquals("Lorem ipsum dolor sit amet", strings.get(0));
        assertEquals("Пример", strings.get(1));
        assertEquals("consectetur adipiscing", strings.get(2));
        assertEquals("тестовое задание", strings.get(3));
        assertEquals("Нормальная форма числа с плавающей запятой", strings.get(4));
        assertEquals("Long", strings.get(5));
    }

    /**
     * Тест для проверки статистики.
     * Проверяет, что статистика по обработанным данным корректно собирается и вычисляется.
     *
     * @throws IOException Если произошла ошибка при чтении файлов.
     */
    @Test
    public void testStatistics() throws IOException {
        Path in1Path = Paths.get("src/test/resources/in/in1.txt");
        Path in2Path = Paths.get("src/test/resources/in/in2.txt");

        processor.processFile(in1Path);
        processor.processFile(in2Path);

        Statistics statistics = processor.getStatistics();

        // Проверяем статистику для целых чисел
        assertEquals(3, statistics.getIntegerCount());
        assertEquals(new BigInteger("45"), statistics.getMinInteger());
        assertEquals(new BigInteger("1234567890123456789"), statistics.getMaxInteger());
        assertEquals(new BigInteger("1234567890123557334"), statistics.getSumInteger());
        assertEquals(new BigDecimal("411522630041185778"), statistics.getAvgInteger());

        // Проверяем статистику для вещественных чисел
        assertEquals(3, statistics.getFloatCount());
        assertEquals(new BigDecimal("3.1415"), statistics.getMaxFloat());
        assertEquals(new BigDecimal("-0.001"), statistics.getMinFloat());
        assertEquals(new BigDecimal("3.1405000000000000000000001528535047"), statistics.getSumFloat());
        assertEquals(new BigDecimal("1.0468333333333333333333333842845016"), statistics.getAvgFloat());

        // Проверяем статистику для строк
        assertEquals(6, statistics.getStringCount());
        assertEquals(4, statistics.getMinStringLength());
        assertEquals(42, statistics.getMaxStringLength());
    }

    /**
     * Тест для проверки обработки несуществующего файла.
     * Проверяет, что при попытке обработать несуществующий файл, ошибка не возникает,
     * и файл не создается.
     *
     * @throws IOException Если произошла ошибка при проверке существования файла.
     */
    @Test
    public void testNonExistentFile() throws IOException {
        Path nonExistentPath = Paths.get("src/test/resources/in/non-existent.txt");

        processor.processFile(nonExistentPath);

        // Проверяем, что файл не существует
        assertFalse(Files.exists(nonExistentPath));
    }
}
