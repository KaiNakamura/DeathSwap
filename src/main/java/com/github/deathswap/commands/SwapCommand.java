package com.github.deathswap.commands;

import com.github.deathswap.DeathSwap;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class SwapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        DeathSwap plugin = DeathSwap.getInstance();

        // If plugin not running, give warning
        if (!plugin.isRunning()) {
            sender.sendMessage(ChatColor.YELLOW + "Game not running");
        }
        else {
            // If no arguments, swap immediately
            if (args.length == 0) {
                plugin.swap();
            }
            else {
                // Try to parse int
                try {
                    // Set timer
                    int seconds = Integer.parseInt(args[0]);
                    plugin.setTime(seconds);
                    plugin.getServer().broadcastMessage(
                        ChatColor.YELLOW + "Swapping in " +
                        ChatColor.GREEN + seconds +
                        ChatColor.YELLOW + " seconds"
                    );
                }
                // If invalid, give warning
                catch (NumberFormatException e) {
                    sender.sendMessage(plugin.getCommand("swap").getUsage());
                }
            }
        }

        return true;
    }
}
