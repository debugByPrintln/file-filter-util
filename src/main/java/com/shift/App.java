package com.shift;

import com.shift.cli.CommandLineOptions;
import com.shift.processor.FileProcessor;
import picocli.CommandLine;

import java.io.IOException;
import java.nio.file.Paths;

/**
 * Главный класс приложения, отвечающий за запуск утилиты для фильтрации содержимого файлов.
 * Приложение принимает на вход несколько файлов, содержащих в перемешку целые числа, строки и вещественные числа,
 * и разделяет их на три категории: целые числа, вещественные числа и строки. Результаты записываются в отдельные файлы,
 * а также выводится статистика по каждой категории.
 *
 * @author Мельников Никита
 * @version 1.0
 */

public class App {
    /**
     * Точка входа в приложение.
     *
     * @param args Аргументы командной строки, переданные приложению.
     *             Поддерживаются следующие опции:
     *             -s: Вывод краткой статистики.
     *             -f: Вывод полной статистики.
     *             -a: Добавление данных в существующие файлы.
     *             -o: Путь для сохранения результатов.
     *             -p: Префикс для имен выходных файлов.
     *             Файлы для обработки указываются в качестве позиционных аргументов.
     */
    public static void main(String[] args) {
        // Создаем объект для обработки командной строки
        CommandLineOptions options = new CommandLineOptions();
        CommandLine commandLine = new CommandLine(options);

        try {
            // Парсим аргументы командной строки
            commandLine.parseArgs(args);
        }
        catch (CommandLine.ParameterException e) {
            // Обрабатываем ошибки парсинга
            System.err.println("Ошибка: " + e.getMessage());
            commandLine.usage(System.err);
            return;
        }

        // Если запрошена помощь, выводим справку и завершаем работу
        if (commandLine.isUsageHelpRequested()) {
            commandLine.usage(System.out);
            return;
        }

        // Если запрошена версия утилиты, выводим версию и завершаем работу
        if (commandLine.isVersionHelpRequested()){
            commandLine.printVersionHelp(System.out);
            return;
        }

        // Создаем объект для обработки файлов
        FileProcessor processor = new FileProcessor(options);
        try {
            // Обрабатываем каждый файл, указанный в аргументах
            for (String filePath : options.getInputFiles()) {
                processor.processFile(Paths.get(filePath));
            }
            // Записываем результаты в файлы
            processor.writeResults();
            // Выводим статистику
            processor.printStatistics();
        }
        catch (NullPointerException e){
            // Обрабатываем случай, когда не указаны файлы для обработки
            System.err.println("Укажите хоть один файл");
            commandLine.usage(System.out);
        }
        catch (IOException e) {
            // Обрабатываем ошибки при обработке файлов
            System.err.println("Ошибка при обработке файлов: " + e.getMessage());
        }
    }
}