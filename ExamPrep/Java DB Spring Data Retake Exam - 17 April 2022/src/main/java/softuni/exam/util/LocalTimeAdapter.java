package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class LocalTimeAdapter extends XmlAdapter<String, LocalTime> {
    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Override
    public String marshal(LocalTime localTime) throws Exception {
        return localTime.format(timeFormatter);
    }

    @Override
    public LocalTime unmarshal(String localTime) throws Exception {
        return LocalTime.parse(localTime,timeFormatter);
    }
}
