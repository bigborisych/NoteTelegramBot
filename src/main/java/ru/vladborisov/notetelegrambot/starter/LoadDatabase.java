package ru.vladborisov.notetelegrambot.starter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import ru.vladborisov.notetelegrambot.model.Tag;
import ru.vladborisov.notetelegrambot.service.NotesService;


@Configuration
class LoadDatabase {

    private static final Logger log = LoggerFactory.getLogger(LoadDatabase.class);

    @Bean
    CommandLineRunner initDatabase(NotesService service) {
        return args -> {
            log.info("Set tags");
            service.addTag(new Tag("spring"));
        };
    }
}