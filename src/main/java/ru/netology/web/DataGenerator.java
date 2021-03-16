package ru.netology.web;

import com.github.javafaker.Faker;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.Random;

public class DataGenerator {
    Faker fakerRu = new Faker(new Locale("RU"));
    Faker fakerUS = new Faker(new Locale("US"));
    private DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private final LocalDate currentDate = LocalDate.now();

    public String getCity (String locale) {
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

    public String getName(String locale) {
        if(locale.equalsIgnoreCase("ru")) {
        return fakerRu.name().fullName();
        }
        else if (locale.equalsIgnoreCase("us")) {
            return fakerUS.name().fullName();
        }
        return null;
    }

    public String getDate(int dateShift) {
        return  formatter.format(currentDate.plusDays(dateShift));
    }

    public String getPhone() {
        return fakerRu.numerify("+7##########");
    }

    public String getRandomNumber () {
        return fakerRu.numerify("######");
    }

}
