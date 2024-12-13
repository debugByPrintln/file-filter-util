# Тестовое задание для ЦФТ-ШИФТ
Ссылка на ТЗ: https://drive.google.com/file/d/10qhGjn7biURykP8hIcT6NJHoGX-WOHUh/view

## Автор
Мельников Никита Сергеевич

## Описание приложения
file-filter-util - это утилита для фильтрации содержимого файлов. 
Утилита принимает на вход несколько файлов, содержащих в перемешку целые числа, 
строки и вещественные числа, и разделяет их на три категории: целые числа, вещественные числа и строки. 
Результаты записываются в отдельные файлы, а также выводится статистика по каждой категории.
Поддерживаются следующие опции:
     *             -s: Вывод краткой статистики.
     *             -f: Вывод полной статистики.
     *             -a: Добавление данных в существующие файлы.
     *             -o: Путь для сохранения результатов.
     *             -p: Префикс для имен выходных файлов.
     *             Файлы для обработки указываются в качестве позиционных аргументов.

## Версии Java и системы сборки
В проекте использовалась Java 21 и система сборки Мaven 3.9.6

## Библиотеки, используемые в проекте
```
picocli v4.7.0
```
Для парсинга аргументов коммандной строки. [picocli](https://picocli.info/)

```
lombok v1.18.30
```
Для сокращения количества boilerplate-кода. [lombok](https://projectlombok.org/)

```
junit v5.8.1
```
Для написания unit-тестов. [junit](https://junit.org/junit5/)

## Инструкция по использованию
Скопируйте репозиторий командой:
```
git clone https://github.com/debugByPrintln/file-filter-util
```

В репозитории уже есть готовый к использованию jar-файл в директории out/artifacts/file_filter_util_jar с названием file-filter-util.jar

Для отображения помощи в использовании утилиты введите:
```
java -jar file-filter-util.jar -h  
```

## Пример использования утилиты:
Допустим, у нас есть 2 входных файла - in1.txt и in2.txt, расположенных в директории src/test/resources/in.<br>
Мы хотим записать разные типы данных в разные файлы. Целые числа в одинвыходной файл, вещественные в другой, строки в третий.<br>
При этом, мы хотим, чтобы у нас также вывелась полная статистика по этим файлам, файлы-результаты записались в директорию src/test/resources/out и имели префикс "example-".<br>
Тогда, команда, которую нам необходимо ввести будет выглядеть следующим образом:
```
java -jar file-filter-util.jar -f -o src/test/resources/out -p example- src/test/resources/in/in1.txt src/test/resources/in/in2.txt
```

## Дополнительные команды:
Для запуска тестов введите команду:
```
mvn test
```

Если вы хотите не хотите пользоваться уже собранным jar-файлом, а хотите собрать проект самостоятельно, введите следующую команду:
```
mvn package
```

Тогда, в директории /target создастся jar-файл с именем file-filter-util-jar-with-dependencies.jar.

