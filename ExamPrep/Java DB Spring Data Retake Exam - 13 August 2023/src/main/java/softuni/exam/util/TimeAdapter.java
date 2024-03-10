package softuni.exam.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.sql.Time;
import java.time.format.DateTimeFormatter;

public class TimeAdapter extends XmlAdapter<String, Time> {

    private final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    @Override
    public String marshal(Time time) throws Exception {
        return time.toLocalTime().format(timeFormatter);
    }

    @Override
    public Time unmarshal(String s) throws Exception {
        return Time.valueOf(s);
    }
}
