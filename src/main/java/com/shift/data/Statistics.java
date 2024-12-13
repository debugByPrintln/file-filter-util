package com.shift.data;

import lombok.Getter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Класс для сбора и хранения статистики по данным, разделенным на категории: целые числа, вещественные числа и строки.
 * Предоставляет методы для обновления статистики и вывода краткой и полной информации о собранных данных.
 *
 * @author Мельников Никита
 * @version 1.0
 */
@Getter
public class Statistics {

    /**
     * Количества целых чисел, вещественных чисел и строк, обработанных утилитой.
     */
    private int integerCount = 0;
    private int floatCount = 0;
    private int stringCount = 0;

    /**
     * Статистические данные для целых чисел.
     */
    private BigInteger minInteger = null;
    private BigInteger maxInteger = null;
    private BigInteger sumInteger = BigInteger.ZERO;
    private BigDecimal avgInteger = BigDecimal.ZERO;

    /**
     * Статистические данные для вещественных чисел.
     */
    private BigDecimal minFloat = null;
    private BigDecimal maxFloat = null;
    private BigDecimal sumFloat = BigDecimal.ZERO;
    private BigDecimal avgFloat = BigDecimal.ZERO;

    /**
     * Статистические данные для строк.
     */
    private int minStringLength = Integer.MAX_VALUE;
    private int maxStringLength = Integer.MIN_VALUE;

    /**
     * Обновляет статистику для целых чисел.
     *
     * @param value Новое значение целого числа для добавления в статистику.
     */
    public void updateIntegerStatistics(BigInteger value) {
        integerCount++;
        if (minInteger == null || value.compareTo(minInteger) < 0) minInteger = value;
        if (maxInteger == null || value.compareTo(maxInteger) > 0) maxInteger = value;
        sumInteger = sumInteger.add(value);
        avgInteger = new BigDecimal(sumInteger).divide(new BigDecimal(integerCount), BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Обновляет статистику для вещественных чисел.
     *
     * @param value Новое значение вещественного числа для добавления в статистику.
     */
    public void updateFloatStatistics(BigDecimal value) {
        floatCount++;
        if (minFloat == null || value.compareTo(minFloat) < 0) minFloat = value;
        if (maxFloat == null || value.compareTo(maxFloat) > 0) maxFloat = value;
        sumFloat = sumFloat.add(value);
        avgFloat = sumFloat.divide(new BigDecimal(floatCount), BigDecimal.ROUND_HALF_UP);
    }

    /**
     * Обновляет статистику для строк.
     *
     * @param value Новая строка для добавления в статистику.
     */
    public void updateStringStatistics(String value) {
        stringCount++;
        int length = value.length();
        if (length < minStringLength) minStringLength = length;
        if (length > maxStringLength) maxStringLength = length;
    }

    /**
     * Выводит краткую статистику по обработанным данным.
     * Вывод включает количество целых чисел, вещественных чисел и строк.
     */
    public void printShortStatistics(){
        System.out.println("Short statistics:");
        System.out.println("Integers: " + integerCount);
        System.out.println("Floats: " + floatCount);
        System.out.println("Strings: " + stringCount);
    }

    /**
     * Выводит полную статистику по обработанным данным.
     * Вывод включает количество, минимальное, максимальное, сумму и среднее значение для целых и вещественных чисел,
     * а также минимальную и максимальную длину строк.
     */
    public void printFullStatistics() {
        System.out.println("Full statistics:");
        System.out.println("Integers: " + integerCount +
                " (Min: " + minInteger + ", Max: " + maxInteger + ", Sum: " + sumInteger +
                ", Avg: " + avgInteger + ")");

        System.out.println("Floats: " + floatCount +
                " (Min: " + minFloat + ", Max: " + maxFloat + ", Sum: " + sumFloat +
                ", Avg: " + avgFloat + ")");

        System.out.println("Strings: " + stringCount +
                " (Min Length: " + minStringLength + ", Max Length: " + maxStringLength + ")");
    }
}