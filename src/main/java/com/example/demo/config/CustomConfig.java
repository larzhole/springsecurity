package com.example.demo.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.Formatter;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.TimeZone;

@Configuration
public class CustomConfig {

    @Bean
    public ModelMapper modelMapper() {
        return new ModelMapper();
    }

    // Convert strings to LocalDate in Query Params
    @Bean
    public Formatter<LocalDate> localDateFormatter() {
        final String pattern = "yyyy-MM-dd";

        return new Formatter<LocalDate>() {
            @Override
            public LocalDate parse(String s, Locale locale) {
                return LocalDate.parse(s, DateTimeFormatter.ofPattern(pattern));
            }

            @Override
            public String print(LocalDate localDate, Locale locale) {
                return DateTimeFormatter.ofPattern(pattern).format(localDate);
            }
        };
    }

    @Bean
    public void setUTCTimezone() {
        TimeZone.setDefault(TimeZone.getTimeZone("UTC"));
    }
}
