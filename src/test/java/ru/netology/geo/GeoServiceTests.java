package ru.netology.geo;

import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import ru.netology.GeoServiceImpl;
import ru.netology.entity.Country;
import ru.netology.entity.Location;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class GeoServiceTests {

    @ParameterizedTest
    @MethodSource("sourse")
    public void locationByIpTest(String ip, Location location) {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location result = geoService.byIp(ip);

        assertEquals(location.getCountry(), result.getCountry());
        assertEquals(location.getCity(), result.getCity());
        assertEquals(location.getBuiling(), result.getBuiling());
        assertEquals(location.getStreet(), result.getStreet());
    }

    private static Stream<Arguments> sourse() {
        return Stream.of(Arguments.of("127.0.0.1", new Location(null, null, null, 0)),
                Arguments.of("172.0.32.11", new Location("Moscow", Country.RUSSIA, "Lenina", 15)),
                Arguments.of("96.44.183.149", new Location("New York", Country.USA, " 10th Avenue", 32)),
                Arguments.of("172.1.1.11", new Location("Moscow", Country.RUSSIA, null, 0)),
                Arguments.of("96.4.4.149", new Location("New York", Country.USA, null, 0)));
    }

    @Test
    public void locationByIpTest() {
        GeoServiceImpl geoService = new GeoServiceImpl();
        Location result = geoService.byIp("0.0.0.0");

        assertNull(result);
    }

    @Test
    public void locationByCoordinatesTest() {
        var expected = RuntimeException.class;

        assertThrowsExactly(expected, () -> new GeoServiceImpl().byCoordinates(22.22, 33.33));
    }


}
