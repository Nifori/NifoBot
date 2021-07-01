package nifori.me.nifobot;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import discord4j.common.util.Snowflake;
import discord4j.core.DiscordClient;
import discord4j.core.GatewayDiscordClient;
import discord4j.core.event.domain.message.MessageCreateEvent;
import discord4j.core.object.entity.channel.Channel;
import nifori.me.nifobot.commands.CommandMap;
import nifori.me.nifobot.orm.entities.Lifechannel;
import nifori.me.nifobot.orm.entities.ServerInfo;
import nifori.me.nifobot.orm.repositories.LifechannelRepository;
import nifori.me.nifobot.orm.repositories.ServerInfoRepository;
import reactor.core.publisher.Mono;

@SpringBootApplication
public class SpringMain {

	public static void main(final String[] args) {
		SpringApplication.run(SpringMain.class, args);
	}

	@Value("${discordclient.id}")
	private String id;

	@Value("${db.init}")
	private boolean initdb;

	@Autowired
	private LifechannelRepository lifechannelRepository;

	@Autowired
	private ServerInfoRepository serverInfoRepository;

	@Autowired
	private CommandMap commandMap;

	public void runInitDB() {
		serverInfoRepository.saveAndFlush(new ServerInfo(305743481991593985L, "§$", false));
		serverInfoRepository.saveAndFlush(new ServerInfo(736316283720695888L, "§$", false));

		lifechannelRepository.saveAndFlush(new Lifechannel(305743481991593985L, 736330400103661648L));
		lifechannelRepository.saveAndFlush(new Lifechannel(736316283720695892L, 736316317614866533L));
		lifechannelRepository.saveAndFlush(new Lifechannel(736325231425224806L, 736316283720695892L));
	}

	public String getTrigger(String messageContent, String prefix) {
		var beginIndex = prefix.length();
		var endIndex = messageContent.indexOf(" ");
		var trigger = messageContent.substring(beginIndex, endIndex != -1 ? endIndex : messageContent.length());
		return trigger;
	}

	@Bean
	public CommandLineRunner run() {
		return (args) -> {
			if (initdb)
				runInitDB();

			if (!id.equals("abc")) {
				final DiscordClient client = DiscordClient.create(id);
				final GatewayDiscordClient gateway = client.login().block();

				gateway.on(MessageCreateEvent.class).subscribe(event -> {

					if (event.getMember().get().isBot())
						return;

					ServerInfo info = null;

					if (event.getGuildId().isPresent()) {
						var guildId = event.getGuildId().get().asLong();
						info = serverInfoRepository.findById(guildId).orElse(null);
					}

					if (info != null) {
						var content = event.getMessage().getContent();
						if (content != null && content.startsWith(info.getPrefix())) {
							var trigger = getTrigger(event.getMessage().getContent(), info.getPrefix());
							commandMap.get(trigger).run(event);
						} else {
							var livechannel = lifechannelRepository.findById(event.getMessage().getChannelId().asLong())
									.orElse(null);
							if (livechannel != null) {
								Mono<Channel> channelById = gateway
										.getChannelById(Snowflake.of(livechannel.getOutputid()));
								if (content != null && !content.isEmpty()) {
									channelById.block().getRestChannel().createMessage(event.getMessage().getContent())
											.block();
								}
								event.getMessage().getAttachments().forEach(attachment -> {
									channelById.block().getRestChannel().createMessage(attachment.getUrl()).block();
								});
							}
						}
					}
				});
				gateway.onDisconnect().block();
			}
		};
	}
}
