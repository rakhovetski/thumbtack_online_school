package net.thumbtack.school.functional;

import java.util.*;
import java.util.function.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class FunctionalTasks {

    // 1
    Function<String, List<String>> split =  s -> Arrays.asList(s.split(" "));
    Function<List<?>, Integer> count = list -> list.size();
    String str = "Hello world";
    List<String> result = split.apply(str);
    int a = count.apply(result);

    // 2
    // Это возможно, потому что компилятор может сам определить тип на основе контекста.

    // 3
    // Мы можем заменить лямбда-выражение на method reference, если
    // лямбда-выражение вызывает метод без дополнительной логики.
    Function<String, List<String>> split2 = ThirdTask::func;
    Function<List<?>, Integer> count2 = List::size;


    // 4.a
    Function<String, Integer> splitAndCount = split.andThen(count);
    // 4.b
    Function<String, Integer> compose = count.compose(split);
    // Благодаря andThen и compose можно явно объеденять функции.
    // А в count.apply(split.apply(str)) мы явно передаем
    //результаты один функций (split.apply()) и передаем в другие (count.apply()).


    // 5
    Function<String, Person> create1 = a -> new Person(a);
    Function<String, Person> create2 = Person::new;


    // 6
    // Здесь подойдет BinaryOperator, потому что он принимает два аргумента одинаковых типов
    // и возвращает зачение того же типа.
    BinaryOperator<Integer> max = Math::max;


    // 7
    // Можно использовать Supplier, потому что у него есть метод get,
    // который не принимает параметров и возвращает тип T.
    Supplier<Date> getCurrentDate = Date::new;
    Date currentDate = getCurrentDate.get();


    // 8
    // Используем Predicate, потому что он принимает параметр T
    // и возвращает булево значение.
    Predicate<Integer> isEven = a -> a % 2 == 0;


    //9
    // Можно использовать BiFunction, так как его метод apply,
    // который принимает два параметра
    // T и U и возвращает третий параметр R
    BiFunction<Integer, Integer, Boolean> areEquals = Integer::equals;


    // 10
    MyFunction<Integer, Integer> lastNumber = a -> a % 10;


    // 11
    // Если мы добавим  K apply(T arg1, T arg2), то у нас возникнет ошибка,
    // потому что функциональный интерфейс может иметь только один абстрактный метод

    // 12.а - класс PersonTaskA
    // 12.b - класс PersonTaskB


    // 13
    static IntStream transform(IntStream stream, IntUnaryOperator op){
        return stream.map(op);
    }
    // 14
    // 13 и 14 задания внизу, в методе main


    //15
    List<PersonNameAndAge> massive = Arrays.asList(
            new PersonNameAndAge("Andrey", 31),
            new PersonNameAndAge("Ivan", 5),
            new PersonNameAndAge("Anita", 22),
            new PersonNameAndAge("Ivan", 35),
            new PersonNameAndAge("Alex", 20),
            new PersonNameAndAge("Viktor", 45)
    );


    List<String> resultNames = massive
            .stream()
            .filter(person -> person.getAge() > 30)
            .map(PersonNameAndAge::getName)
            .sorted(Comparator.comparing(String::length).reversed())
            .distinct()
            .collect(Collectors.toList());


    //16
    List<String> resultNames2 = massive
            .stream()
            .filter(person -> person.getAge() > 30)
            .collect(Collectors.groupingBy(PersonNameAndAge::getName,
                    Collectors.counting()))
            .entrySet()
            .stream()
            .sorted(Map.Entry.comparingByValue())
            .map(Map.Entry::getKey)
            .collect(Collectors.toList());


    //17
    List<Integer> help1 = Arrays.asList(1, 2, 3, 4);

    int sum = help1
            .stream()
            .reduce(0, Integer::sum);

    int product = help1
            .stream()
            .reduce(1, (a, b) -> a * b);



    public static void main(String[] args) {
        //13
        System.out.println("13 Task");
        int[] help = {1, -2, 3, -4};
        IntStream stream = Arrays.stream(help);
        IntUnaryOperator op = Math::abs;
        IntStream res1 = transform(stream, op);
        res1.forEach(System.out::println);

        System.out.println();
        System.out.println("14 Task");
        //14
        IntStream stream1 = Arrays.stream(help);
        IntUnaryOperator op1 = Math::abs;
        IntStream res2 = transform(stream1, op1).parallel();
        res2.forEach(System.out::println);
    }
}
