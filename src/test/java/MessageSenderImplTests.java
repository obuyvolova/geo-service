import com.sun.source.doctree.SeeTree;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.Mockito;
import ru.netology.entity.Country;
import ru.netology.entity.Location;
import ru.netology.geo.GeoService;
import ru.netology.i18n.LocalizationService;
import ru.netology.sender.MessageSenderImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

public class MessageSenderImplTests {
    @ParameterizedTest
    @MethodSource("sourse")
    void sendTest(String ip, Location location, String str, String expected) {
        GeoService geoService = Mockito.mock(GeoService.class);
        Mockito.when(geoService.byIp(ip)).thenReturn(location);

        LocalizationService localizationService = Mockito.mock(LocalizationService.class);
        Mockito.when(localizationService.locale(location.getCountry())).thenReturn(str);

        MessageSenderImpl messageSender = new MessageSenderImpl(geoService, localizationService);
        Map<String, String> headers = new HashMap<>();
        headers.put("x-real-ip", ip);
        String result = messageSender.send(headers);

        Assertions.assertEquals(expected, result);
    }

    private static Stream<Arguments> sourse() {
        return Stream.of(
                Arguments.of("172.0.32.11", new Location("", Country.RUSSIA, "", 15),
                        "Добро пожаловать", "Добро пожаловать"),
                Arguments.of("2.16.196.0", new Location("", Country.BRAZIL, "", 0),
                        "Welcome", "Welcome"),
                Arguments.of("0", new Location("", Country.BRAZIL, "", 0),
                        "Welcome", "Welcome"),
                Arguments.of("96.44.183.149", new Location("", Country.USA, "", 0),
                        "Welcome", "Welcome")
        );
    }
}
