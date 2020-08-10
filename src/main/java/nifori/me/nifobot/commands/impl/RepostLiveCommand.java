package nifori.me.nifobot.commands.impl;

import org.springframework.stereotype.Component;

import nifori.me.nifobot.commands.Command;

@Component
public class RepostLiveCommand extends Command {

    public RepostLiveCommand() {
        super.command = "live";
    }

    @Override
    public void run() {
        System.out.println("Live!");
    }
    
}