package nifori.me.nifobot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nifori.me.nifobot.commands.CommandMap;
import nifori.me.nifobot.orm.repositories.LifechannelRepository;
import nifori.me.nifobot.orm.repositories.ServerInfoRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(LifechannelRepository lifechannelRepository,
			ServerInfoRepository serverInfoRepository, CommandMap commandsAsMap) {
		return (args) -> {
			commandsAsMap.forEach((key, value) -> System.out.println(key + ": " + value));
		};
	}
}
