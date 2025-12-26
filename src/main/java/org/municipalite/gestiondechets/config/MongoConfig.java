package org.municipalite.gestiondechets.config;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoClientConfiguration;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;

import java.time.LocalTime;
import java.util.Arrays;

@Configuration
public class MongoConfig extends AbstractMongoClientConfiguration {

    @Override
    public MongoCustomConversions customConversions() {
        return new MongoCustomConversions(Arrays.asList(
                new LocalTimeReadConverter(),
                new LocalTimeWriteConverter()
        ));
    }

    static class LocalTimeReadConverter implements org.springframework.core.convert.converter.Converter<String, LocalTime> {
        @Override
        public LocalTime convert(String source) {
            return LocalTime.parse(source);
        }
    }

    static class LocalTimeWriteConverter implements org.springframework.core.convert.converter.Converter<LocalTime, String> {
        @Override
        public String convert(LocalTime source) {
            return source.toString();
        }
    }

    @Override
    protected String getDatabaseName() {
        return "gestion-dechet"; // le nom exact de ta base dans le port 27027
    }

    @Override
    public MongoClient mongoClient() {
        // Forcer le port 27027
        return MongoClients.create("mongodb://localhost:27027/gestion-dechet");
    }
}
