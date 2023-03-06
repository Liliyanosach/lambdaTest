package edu.predicateTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.*;

public class PersonTest {
    public static void main(String[] args) {
        Person person1 = new Person("Ivan", 25);
        Person person2 = new Person("Liliya", 30);
        Person person3 = new Person("Masha", 8);
        Person person4 = new Person("Zlata", 17);
        Person person5 = new Person("Alice", 35);

        List<Person> persons = new ArrayList<>();
        persons.add(person1);
        persons.add(person2);
        persons.add(person3);
        persons.add(person4);
        persons.add(person5);

        Set<Person> personSet = new TreeSet<>(persons);

        for (Person person : personSet) {
            System.out.println(person);
        }

        // Вызываем у нашего списка метод sort
        // И в параметр передаем объект Comparator
        persons.sort(new PersonSortingByAge());

        for (Person person : persons) {
            System.out.println(person);
        }

        // Создаем объект, реализующий функциональный интерфейс Predicate,
        // и переопределяем его метод test() так, чтобы он возвращал true,
        // если возраст меньше 18
        // И передаем созданный объект предиката в параметр метода removeIf
        persons.removeIf(new Predicate<Person>() {
            @Override
            public boolean test(Person person) {
                return person.getAge() < 18;
            }
        });

        // Теперь проверяем результат и видим, что из списка были
        // удалены объекты, возраст которых меньше 18
        for (Person people : persons) {
            System.out.println(people);
        }

        // Создаем объект анонимного класса,
        // реализующего функциональный интерфейс Function
        // И сразу переопределяем его метод apply таким образом,
        // чтобы он принимал объект класса Person, а возвращал его возраст
        Function<Person, Integer> function = new Function<Person, Integer>() {
            @Override
            public Integer apply(Person person) {
                return person.getAge();
            }
        };
        // Далее просто передаем вызов переопределенного метода
        // в параметр при проходе списка

        for (Person age : persons) {
            System.out.println(new Function<Person, Integer>() {
                @Override
                public Integer apply(Person person) {
                    return person.getAge();
                }
            }.apply(age));
        }


        for (Person age : persons) {
            System.out.println(new UnaryOperator<Person>() {
                @Override
                public Person apply(Person person) {
                    person.setAge(person.getAge() + 10);
                    return person;
                }
            }.apply(age));
        }

        BinaryOperator<Person> binaryOperator = new BinaryOperator<Person>() {
            @Override
            public Person apply(Person person, Person person2) {
                if (person.getAge() > person2.getAge()) {
                    return person;
                }
                return person2;
            }
        };

        System.out.println(binaryOperator.apply(person3, person5));


        Consumer<Person> consumer = new Consumer<Person>() {
            @Override
            public void accept(Person person) {
                System.out.println("Hi! " + person.getName());
            }
        };

        for (Person p : persons) {
            consumer.accept(p);
        }

        Supplier<Person> supplier = new Supplier<Person>() {
            @Override
            public Person get() {
                Person personNew = null;
                for (Person person : persons) {
                    if (person.getAge() > 18) {
                        personNew = person;
                    }
                }
                return personNew;
            }
        };

        System.out.println(supplier.get());

    }
}
