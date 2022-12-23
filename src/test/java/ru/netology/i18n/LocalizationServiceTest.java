package ru.netology.i18n;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.entity.Country;
import ru.netology.i18n.LocalizationServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

import java.util.stream.Stream;

public class LocalizationServiceTest {

    @ParameterizedTest
    @MethodSource("sourse")
    public void localeTest(Country country, String str) {

        assertEquals(str, new LocalizationServiceImpl().locale(country));

    }

    private static Stream<Arguments> sourse() {
        return Stream.of(Arguments.of(Country.RUSSIA, "Добро пожаловать"),
                Arguments.of(Country.USA, "Welcome"),
                Arguments.of(Country.BRAZIL, "Welcome"),
                Arguments.of(Country.GERMANY, "Welcome"));
    }

}