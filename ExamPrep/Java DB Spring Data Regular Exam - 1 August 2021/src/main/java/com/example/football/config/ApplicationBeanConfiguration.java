package com.example.football.config;

import com.example.football.util.ValidatorUtil;
import com.example.football.util.ValidatorUtilImpl;
import com.example.football.util.XmlParser;
import com.example.football.util.XmlParserImpl;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

//ToDo Create configurations

@Configuration
public class ApplicationBeanConfiguration {

    @Bean
    public Gson gson () {
        return new GsonBuilder().setPrettyPrinting().create();
    }

    @Bean
    public XmlParser xmlParser () {
        return new XmlParserImpl();
    }

    @Bean
    public ModelMapper modelMapper () {
        return new ModelMapper();
    }

    @Bean
    public ValidatorUtil validatorUtil () {
        return new ValidatorUtilImpl();
    }
}
