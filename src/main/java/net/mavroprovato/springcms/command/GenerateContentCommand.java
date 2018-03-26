package net.mavroprovato.springcms.command;

import net.mavroprovato.springcms.entity.Content;
import net.mavroprovato.springcms.repository.ContentRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.*;
import org.springframework.context.annotation.ComponentScan;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.concurrent.ThreadLocalRandom;

/**
 * A command line command that generates test content.
 */
@ComponentScan("net.mavroprovato.springcms")
public class GenerateContentCommand implements ApplicationRunner {

    /** Logger for the class */
    private static Logger logger = LoggerFactory.getLogger(GenerateContentCommand.class);

    /** The default number of content items to generate. */
    private static final int DEFAULT_COUNT = 100;

    /** The default minimum publication date for the content items. */
    private static final LocalDateTime DEFAULT_START_DATE = LocalDateTime.now().minus(1, ChronoUnit.YEARS);

    /** The default maximum publication date for the content items. */
    private static final LocalDateTime DEFAULT_END_DATE = LocalDateTime.now();

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
        // Parse the count argument.
        int count = DEFAULT_COUNT;
        if (args.containsOption("count")) {
            try {
                count = Integer.parseInt(args.getOptionValues("count").get(0));
            } catch (NumberFormatException e) {
                logger.error("Cannot parse the count argument ({}) as an integer.",
                        args.getOptionValues("count").get(0));
                return;
            }
            if (count <= 0) {
                logger.error("Count must be a positive integer.");
                return;
            }
        }

        // Parse the start date argument
        LocalDateTime startDate = DEFAULT_START_DATE;
        if (args.containsOption("start-date")) {
            try {
                String startDateString = args.getOptionValues("start-date").get(0);
                startDate = LocalDate.parse(startDateString, DateTimeFormatter.ISO_DATE).atStartOfDay();
            } catch (DateTimeParseException e) {
                logger.error("Start date ({}) cannot be parsed.", args.getOptionValues("start-date").get(0));
                return;
            }
        }

        // Parse the end date argument
        LocalDateTime endDate = DEFAULT_END_DATE;
        if (args.containsOption("end-date")) {
            try {
                String endDateString = args.getOptionValues("end-date").get(0);
                endDate = LocalDate.parse(endDateString, DateTimeFormatter.ISO_DATE).atStartOfDay();
            } catch (DateTimeParseException e) {
                logger.error("End date ({}) cannot be parsed.", args.getOptionValues("end-date").get(0));
                return;
            }
        }

        // Check if start date is before end date
        if (!startDate.isBefore(endDate)) {
            logger.error("Start date must be before end date.");
            return;
        }

        // Generate the content items
        logger.info("Generating {} content items between {} and {}.", count, startDate, endDate);
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
        logger.info("Content items generated.");
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
