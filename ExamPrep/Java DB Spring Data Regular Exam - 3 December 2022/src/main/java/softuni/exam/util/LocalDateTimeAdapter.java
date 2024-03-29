package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class LocalDateTimeAdapter extends XmlAdapter<String, LocalDateTime> {
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    @Override
    public LocalDateTime unmarshal(String s) throws Exception {
        return LocalDateTime.parse(s, formatter);
    }

    @Override
    public String marshal(LocalDateTime localDateTime) throws Exception {
        return localDateTime.format(formatter);
    }
}
