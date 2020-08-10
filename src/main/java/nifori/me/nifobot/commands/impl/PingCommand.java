package nifori.me.nifobot.commands.impl;

import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.stereotype.Component;

import nifori.me.nifobot.commands.Command;

@Component
public class PingCommand extends Command {

    public PingCommand() {
        super.command = "ping";
    }

    @Override
    public void run() {
        System.out.println("Ping!");
    }
    
}