package net.mavroprovato.springcms.command;

import net.mavroprovato.springcms.entity.Content;
import net.mavroprovato.springcms.repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A command line command that generates test content.
 */
@Component
@ComponentScan("net.mavroprovato.springcms")
public class GenerateContentCommand implements ApplicationRunner {

    /** The content repository */
    private final ContentRepository contentRepository;

    /**
     * Create the generate content command.
     *
     * @param contentRepository The content repository.
     */
    @Autowired
    public GenerateContentCommand(ContentRepository contentRepository) {
        this.contentRepository = contentRepository;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void run(ApplicationArguments args) {
        int count = 100;
        LocalDateTime startDate = LocalDateTime.now().minus(1, ChronoUnit.YEARS);
        LocalDateTime endDate = LocalDateTime.now();

        for (int i = 0; i < count; i++) {
            Content content = new Content();
            content.setTitle("Test Title");
            content.setContent("Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor " +
                    "incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation " +
                    "ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit " +
                    "in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat " +
                    "cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.");
            content.setPublishedAt(randomDateTime(startDate, endDate));

            contentRepository.save(content);
        }
    }

    /**
     * Return a random date between two dates.
     *
     * @param startDate The minimum date.
     * @param endDate The maximum date.
     * @return The random date time.
     */
    private LocalDateTime randomDateTime(LocalDateTime startDate, LocalDateTime endDate) {
        long min = startDate.toEpochSecond(ZoneOffset.UTC);
        long max = endDate.toEpochSecond(ZoneOffset.UTC);
        long random = ThreadLocalRandom.current().nextLong(min, max);

        return LocalDateTime.ofEpochSecond(random, 0, ZoneOffset.UTC);
    }

    /**
     * The entry point of the command.
     *
     * @param args The command line arguments.
     */
    public static void main(String... args) {
        SpringApplication command = new SpringApplication(GenerateContentCommand.class);
        command.setWebApplicationType(WebApplicationType.NONE);
        command.run(args);
    }
}
