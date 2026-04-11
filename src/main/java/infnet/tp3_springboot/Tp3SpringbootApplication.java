package infnet.tp3_springboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableCaching
@EnableScheduling
public class Tp3SpringbootApplication {

	public static void main(String[] args) {
		SpringApplication.run(Tp3SpringbootApplication.class, args);
	}

}
