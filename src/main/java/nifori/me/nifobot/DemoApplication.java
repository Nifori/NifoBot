package nifori.me.nifobot;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import nifori.me.nifobot.orm.entities.ServerInfo;
import nifori.me.nifobot.orm.repositories.LifechannelRepository;
import nifori.me.nifobot.orm.repositories.ServerInfoRepository;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

	@Bean
	public CommandLineRunner run(LifechannelRepository lifechannelRepository, ServerInfoRepository serverInfoRepository) {
		return (args) -> {
			System.out.println("Hello World");
			ServerInfo tmpInfo = new ServerInfo(1, "§$", false);
			serverInfoRepository.saveAndFlush(tmpInfo);
			var serverInfos = serverInfoRepository.findAll();
			System.out.println(serverInfos.size());
			serverInfos.forEach(info -> System.out.println(info));
		};
	}
}
