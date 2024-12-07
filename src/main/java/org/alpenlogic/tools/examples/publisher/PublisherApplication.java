package org.alpenlogic.tools.examples.publisher;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "org.alpenlogic.tools.examples")
public class PublisherApplication {
    private static final Logger log = LogManager.getLogger(PublisherApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(PublisherApplication.class, args);
    }

}
