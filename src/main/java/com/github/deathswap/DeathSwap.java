package com.github.deathswap;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

import com.github.deathswap.commands.*;
import com.github.deathswap.listeners.*;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;

public class DeathSwap extends JavaPlugin implements Runnable {
    private boolean running = false;
    private int taskId;
    private int timer;

    @Override
    public void onEnable() {
        // Save default config, fails silently if config already exists
        saveDefaultConfig();

        // Create commands
        getCommand("start").setExecutor(new StartCommand());
        getCommand("stop").setExecutor(new StopCommand());
        getCommand("swap").setExecutor(new SwapCommand());
        getCommand("config").setExecutor(new ConfigCommand());

        // Create listeners
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new GUIClickListener(), this);
    }

    @Override
    public void run() {
        int warningTime = getConfig().getInt("warning-time");

        timer--;

        // If timer reaches swap time, swap
        if (timer <= 0) {
            swap();
        }
        // If timer within warning time, broadcast warnings
        else if (timer <= warningTime) {
            getServer().broadcastMessage(
                (timer <= 10 ? ChatColor.RED : ChatColor.YELLOW) + "" +
                timer + " seconds before swap!"
            );

            // If plugin should give warning sound, play sound to all players
            if (getConfig().getBoolean("warning-sounds")) {
                for (Player player : getServer().getOnlinePlayers()) {
                    player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1, 1);
                }
            }
        }
    }

    public void start() {
        // If already running, give warning
        if (running) {
            getServer().broadcastMessage(ChatColor.YELLOW + "Game already running, restarting");
            stop();
        }

        // Reset timer
        timer = getConfig().getInt("swap-time");

        // Schedule repeating task
        taskId = getServer().getScheduler().scheduleSyncRepeatingTask(this, this, 20, 20);

        // Get world
        World world = getServer().getWorld("world");

        // Create world border around spawn chunks
        world.getWorldBorder().setSize(getConfig().getInt("world-border-size"));

        // Set time to 0
        world.setTime(0);

        // For every player
        for (Player player : getServer().getOnlinePlayers()) {
            // Clear inventory
            player.getInventory().clear();

            // Give players full health and food
            player.setHealth(20);
            player.setFoodLevel(20);

            // Revoke all advancements
            Iterator<Advancement> advancements = getServer().advancementIterator();
            while (advancements.hasNext()) {
                AdvancementProgress advancementProgress = player.getAdvancementProgress(advancements.next());
                for (String criteria : advancementProgress.getAwardedCriteria()){
                    advancementProgress.revokeCriteria(criteria);
                }
            }

            // Clear all potion effects
            for (PotionEffect potionEffect : player.getActivePotionEffects()) {
                player.removePotionEffect(potionEffect.getType());
            }

            // Teleport to world spawn
            player.teleport(world.getSpawnLocation());
        }

        // Set running
        running = true;

        // Broadcast starting game
        getServer().broadcastMessage(ChatColor.GREEN + "Starting game!");
        getServer().broadcastMessage(ChatColor.YELLOW + "First swap in " + getConfig().getInt("swap-time") + " seconds");
    }

    public void stop() {
        // If already not running, give warning
        if (!running) {
            getServer().broadcastMessage(ChatColor.YELLOW + "Game not running");
        }

        // Set not running
        running = false;

        // Cancel repeating task
        getServer().getScheduler().cancelTask(taskId);

        // Reset world border
        getServer().getWorld("world").getWorldBorder().reset();

        // Broadcast stopping game
        getServer().broadcastMessage(ChatColor.RED + "Stopping game");
    }

    public void swap() {
        // Reset timer
        timer = getConfig().getInt("swap-time");

        // Broadcast swapping players
        getServer().broadcastMessage(ChatColor.RED + "Swapping players!");

        // Create array list for players
        ArrayList<Player> players = new ArrayList<>();

        // Add players in survival mode to array list
        for (Player player : getServer().getOnlinePlayers()) {
            if (player.getGameMode() == GameMode.SURVIVAL) {
                players.add(player);
            }
        }

        // If 1 or less players, return
        if (players.size() <= 1) {
            return;
        }

        // Shuffle players
        Collections.shuffle(players);

        // Save location of first player
        Location firstLocation = players.get(0).getLocation();

        for (int i = 0; i < players.size(); i++) {
            // Teleport each player to the next player
            if (i < players.size() - 1) {
                players.get(i).teleport(players.get(i + 1));
            }
            // Teleport the last player to the first player
            else {
                players.get(i).teleport(firstLocation);
            }
        }
    }

    public void setTime(int seconds) {
        this.timer = seconds;
    }

    public boolean isRunning() {
        return running;
    }

    public static DeathSwap getInstance() {
        return (DeathSwap) Bukkit.getPluginManager().getPlugin("DeathSwap");
    }
}