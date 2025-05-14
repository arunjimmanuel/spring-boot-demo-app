package com.arun.immanuel.jobtracker;

import java.util.Properties;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.PropertiesPropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

import io.github.cdimascio.dotenv.Dotenv;

@SpringBootApplication
@EnableScheduling
public class JobtrackerApplication {

	public static void main(String[] args) {
		Dotenv dotenv = Dotenv.configure().filename(".env")
				.directory("/home/arun/Arun/SpringBoot/Jobtracker/jobtracker")
				.ignoreIfMalformed()
				.ignoreIfMissing()
				.load();

		Properties dotenvProperties = new Properties();
		dotenv.entries().forEach(entry -> dotenvProperties.setProperty(entry.getKey(), entry.getValue()));

		SpringApplication application = new SpringApplication(JobtrackerApplication.class);
		application.addInitializers(applicationContext -> {
			ConfigurableEnvironment environment = applicationContext.getEnvironment();
			environment.getPropertySources()
					.addFirst(new PropertiesPropertySource("dotenvProperties", dotenvProperties));
		});

		ConfigurableApplicationContext context = application.run(args);
	}

}
