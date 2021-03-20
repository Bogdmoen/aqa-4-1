package ru.netology.generator;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    private DataGenerator () {}
    private static Faker fakerRu = new Faker(new Locale("RU"));
    private static Faker fakerUS = new Faker(new Locale("US"));
    private static DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final static LocalDate currentDate = LocalDate.now();

    public static String getCity (String locale) {
        if (locale.equalsIgnoreCase("ru")) {
            String[] city = {"Москва", "Санкт-Петербург", "Новосибирск", "Нижний Новгород", "Екатеринбург", "Краснодар", "Псков", "Владивосток"};
            int index = new Random().nextInt(city.length);
            return city[index];
        }
        else if(locale.equalsIgnoreCase("us")) {
            return fakerUS.address().city();
        }
        return null;
    }

    public static String getName(String locale) {
        if(locale.equalsIgnoreCase("ru")) {
        return fakerRu.name().fullName();
        }
        else if (locale.equalsIgnoreCase("us")) {
            return fakerUS.name().fullName();
        }
        return null;
    }

    public static String getDate(int dateShift) {
        return  formatter.format(currentDate.plusDays(dateShift));
    }

    public static String getPhone() {
        return fakerRu.numerify("+7##########");
    }

    public static String getRandomNumber () {
        return fakerRu.numerify("######");
    }

}
