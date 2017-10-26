package com.demo.converter;

import org.junit.Before;
import org.junit.Test;

import javax.persistence.AttributeConverter;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.Month;
import java.util.Calendar;

import static org.assertj.core.api.Assertions.assertThat;

public class LocalDateTimeConverterTest {
    private AttributeConverter<LocalDateTime, Timestamp> localDateTimeConverter;

    @Before
    public void setup() {
        localDateTimeConverter = new LocalDateTimeConverter();
    }

    @Test
    public void convertToDatabaseColumn_whenValidLocalDateTime_thenReturnTimestamp() {
        LocalDateTime localDateTime = LocalDateTime.of(2017, Month.JANUARY, 1, 1, 2, 3);

        Timestamp actual = localDateTimeConverter.convertToDatabaseColumn(localDateTime);

        assertThat(actual).isEqualTo(Timestamp.valueOf(localDateTime));
    }

    @Test
    public void convertToDatabaseColumn_whenNullLocalDateTime_thenReturnNull() {
        Timestamp actual = localDateTimeConverter.convertToDatabaseColumn(null);

        assertThat(actual).isNull();
    }

    @Test
    public void convertToEntityAttribute_whenValidTimestamp_thenReturnLocalDateTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, 2017);
        calendar.set(Calendar.MONTH, calendar.JANUARY);
        calendar.set(Calendar.DATE, 1);
        calendar.set(Calendar.HOUR_OF_DAY, 1);
        calendar.set(Calendar.MINUTE, 2);
        calendar.set(Calendar.SECOND, 3);
        calendar.set(Calendar.MILLISECOND, 0);
        Timestamp timestamp = new Timestamp(calendar.getTime().getTime());

        LocalDateTime actual = localDateTimeConverter.convertToEntityAttribute(timestamp);

        assertThat(actual).isEqualByComparingTo(LocalDateTime.of(2017, Month.JANUARY, 1, 1, 2, 3));
    }

    @Test
    public void convertToEntityAttribute_whenNullTimestamp_thenReturnNull() {
        LocalDateTime actual = localDateTimeConverter.convertToEntityAttribute(null);

        assertThat(actual).isNull();
    }
}
