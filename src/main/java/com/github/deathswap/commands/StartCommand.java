package com.github.deathswap.commands;

import com.github.deathswap.DeathSwap;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class StartCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DeathSwap.getInstance().start();
        return true;
    }
}
