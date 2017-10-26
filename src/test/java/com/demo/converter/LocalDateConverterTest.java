package com.demo.converter;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.AttributeConverter;
import java.sql.Date;
import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateConverterTest {
    private AttributeConverter<LocalDate, Date> localDateConverter;

    @Before
    public void setup() {
        localDateConverter = new LocalDateConverter();
    }

    @Test
    public void convertToDatabaseColumn_whenValidLocalDate_thenReturnDate() {
        LocalDate localDate = LocalDate.of(2017, Month.JANUARY, 1);

        Date actual = localDateConverter.convertToDatabaseColumn(localDate);

        assertThat(actual).isEqualTo(Date.valueOf(localDate));
    }

    @Test
    public void convertToDatabaseColumn_whenNullLocalDate_thenReturnNull() {
        Date actual = localDateConverter.convertToDatabaseColumn(null);

        assertThat(actual).isNull();
    }

    @Test
    public void convertToEntityAttribute_whenValidDate_thenReturnLocalDate() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(calendar.YEAR, 2017);
        calendar.set(calendar.MONTH, calendar.JANUARY);
        calendar.set(calendar.DATE, 1);
        Date date = new Date(calendar.getTime().getTime());

        LocalDate actual = localDateConverter.convertToEntityAttribute(date);

        assertThat(actual).isEqualByComparingTo(LocalDate.of(2017, Month.JANUARY, 1));
    }

    @Test
    public void convertToEntityAttribute_whenNullDate_thenReturnNull() {
        LocalDate actual = localDateConverter.convertToEntityAttribute(null);

        assertThat(actual).isNull();
    }
}
