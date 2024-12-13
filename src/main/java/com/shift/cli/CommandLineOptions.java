package com.shift.cli;

import lombok.Getter;
import lombok.Setter;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.List;

/**
 * Класс для обработки аргументов командной строки утилиты фильтрации содержимого файлов.
 * Предоставляет возможность настройки поведения утилиты через параметры командной строки.
 *
 * @author Мельников Никита
 * @version 1.0
 */
@Getter
@Setter
@Command(name = "file-filter-util.jar", mixinStandardHelpOptions = true, version = "1.0",
        description = "Утилита для фильтрации содержимого файлов")
public class CommandLineOptions {

    /**
     * Путь для сохранения результатов обработки файлов.
     * По умолчанию результаты сохраняются в текущей директории.
     */
    @Option(names = {"-o", "--output"}, description = "Путь для результатов")
    private String outputPath = ".";

    /**
     * Префикс для имен выходных файлов.
     * По умолчанию префикс отсутствует.
     */
    @Option(names = {"-p", "--prefix"}, description = "Префикс имен выходных файлов")
    private String prefix = "";

    /**
     * Флаг, указывающий на режим добавления данных в существующие файлы.
     * Если флаг установлен, данные будут добавлены в конец файлов, иначе файлы будут перезаписаны.
     */
    @Option(names = {"-a", "--append"}, description = "Режим добавления в существующие файлы")
    private boolean append = false;

    /**
     * Флаг, указывающий на вывод краткой статистики.
     * Если флаг установлен, будет выведена только информация о количестве элементов в каждой категории.
     */
    @Option(names = {"-s", "--short-stats"}, description = "Вывод краткой статистики")
    private boolean shortStats = false;

    /**
     * Флаг, указывающий на вывод полной статистики.
     * Если флаг установлен, будет выведена подробная информация о каждой категории, включая минимальные, максимальные значения, сумму и среднее.
     */
    @Option(names = {"-f", "--full-stats"}, description = "Вывод полной статистики")
    private boolean fullStats = false;

    /**
     * Список входных файлов, которые будут обработаны утилитой.
     * Файлы указываются в качестве позиционных аргументов.
     */
    @Parameters(paramLabel = "FILES", description = "Входные файлы")
    private List<String> inputFiles;

    /**
     * Возвращает режим записи данных в файлы.
     * Если флаг {@code append} установлен, возвращается режим {@link OutputMode#APPEND}, иначе {@link OutputMode#OVERWRITE}.
     *
     * @return Режим записи данных в файлы.
     */
    public OutputMode getOutputMode() {
        return append ? OutputMode.APPEND : OutputMode.OVERWRITE;
    }

    /**
     * Перечисление режимов записи данных в файлы.
     */
    public enum OutputMode {
        /**
         * Режим добавления данных в конец существующих файлов.
         */
        APPEND,
        /**
         * Режим перезаписи существующих файлов.
         */
        OVERWRITE
    }
}
