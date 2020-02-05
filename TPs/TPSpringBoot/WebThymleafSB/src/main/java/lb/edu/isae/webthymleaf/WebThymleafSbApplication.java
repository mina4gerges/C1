package lb.edu.isae.webthymleaf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebThymleafSbApplication {
        private static final Logger LOGGER = LoggerFactory.getLogger(WebThymleafSbApplication.class);
	public static void main(String[] args) {
		SpringApplication.run(WebThymleafSbApplication.class, args);
                LOGGER.info("Nous somme en bonne voie pour aprrendre Spring Boot, Thymleaf, et un peu plus!");
	}

}
