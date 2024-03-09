package com.example.football.util;

import javax.xml.bind.annotation.adapters.XmlAdapter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class LocalDateAdapter extends XmlAdapter<String, LocalDate> {

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yy");
    @Override
    public LocalDate unmarshal(String localDate) throws Exception {
        return LocalDate.parse(localDate, formatter);
    }

    @Override
    public String marshal(LocalDate localDate) throws Exception {
        return localDate.format(formatter);
    }
}
