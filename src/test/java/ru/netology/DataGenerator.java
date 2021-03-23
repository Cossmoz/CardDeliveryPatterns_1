package ru.netology;

import com.github.javafaker.Faker;
import lombok.Data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import java.util.Arrays;
import java.util.List;
import java.util.Random;

@Data
public class DataGenerator {

    private DataGenerator() {
    }

    public static <T> T getRandom(List<T> list) {
        Random random = new Random();
        return list.get(random.nextInt(list.size()));
    }

    public static String getRandomCity() {
        List<String> list = Arrays.asList("Самара", "Москва", "Астрахань", "Ярославль");
        return getRandom(list);
    }

    public static String getDate(int day) {
        return LocalDate.now().plusDays(day).format(DateTimeFormatter.ofPattern("dd.MM.yyyy"));
    }

    public static String getName() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.name().firstName() + " " + faker.name().lastName();
    }

    public static String getPhone() {
        Faker faker = new Faker(new Locale("ru"));
        return faker.phoneNumber().phoneNumber();
    }

}