package com.leo.project;

import com.leo.project.decide.Discussion;
import com.leo.project.io.SystemOutput;
import com.leo.project.util.OutputMessages;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication
public class Application implements CommandLineRunner {

    private SystemOutput output = new SystemOutput();

    public static void main(String[] args) {
        new SpringApplicationBuilder(Application.class)
                .build()
                .run(args);
    }

    /**
     * Entry point of the application process. <br>
     * @param args
     */
    @Override
    public void run(String... args) {
        output.write(OutputMessages.WELCOME_MESSAGE);

        new Discussion().initiateUntilOver();

        output.write(OutputMessages.BYBY_MESSAGE);
    }

}
