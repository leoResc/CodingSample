package com.leo.project;

import com.leo.project.decide.Discussion;
import com.leo.project.io.SystemOutput;
import com.leo.project.util.OutputMessages;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.logging.Level;
import java.util.logging.Logger;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private static final Logger LOGGER = Logger.getLogger(Application.class.getSimpleName());

    private SystemOutput output = new SystemOutput();

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .build()
                .run(args);
    }

    /**
     * Entry point of the application process
     */
    @Override
    public void run(String... args) {
        output.write(OutputMessages.WELCOME_MESSAGE);

        try {
            new Discussion().initiateUntilOver();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Application encountered a serious problem and will shut down.", e);
        } finally {
            output.write(OutputMessages.BYBY_MESSAGE);
        }
    }

}
