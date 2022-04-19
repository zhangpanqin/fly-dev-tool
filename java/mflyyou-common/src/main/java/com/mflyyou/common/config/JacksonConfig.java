package com.mflyyou.common.config;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;

import static com.mflyyou.common.TimeFormatterConstant.DATE_TIME_FORMATTER;
import static com.mflyyou.common.TimeFormatterConstant.FULL_DATE_FORMATTER;

public class JacksonConfig {
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer jackson2ObjectMapperBuilderCustomizer() {
        return builder -> {
            builder.serializerByType(LocalDateTime.class, new LocalDateTimeSerializer(DATE_TIME_FORMATTER));
            builder.serializerByType(LocalDate.class, new LocalDateSerializer(FULL_DATE_FORMATTER));
            builder.serializerByType(Instant.class, InstantSerializer.INSTANCE);
            builder.serializerByType(Long.class, ToStringSerializer.instance);

            builder.deserializerByType(LocalDateTime.class, new LocalDateTimeDeserializer(DATE_TIME_FORMATTER));
            builder.deserializerByType(LocalDate.class, new LocalDateDeserializer(FULL_DATE_FORMATTER));
            builder.deserializerByType(Instant.class, InstantDeserializer.INSTANT);

            builder.postConfigurer(objectMapper -> {
                // instant deserializer to timestamps
                objectMapper.configure(SerializationFeature.WRITE_DATE_TIMESTAMPS_AS_NANOSECONDS, false);
                objectMapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, true);
            });
        };
    }
}
