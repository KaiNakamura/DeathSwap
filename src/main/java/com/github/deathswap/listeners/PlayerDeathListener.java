package com.github.deathswap.listeners;

import com.github.deathswap.DeathSwap;

import org.bukkit.GameMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

public class PlayerDeathListener implements Listener {
    @EventHandler
    public void onPlayerRespawn(PlayerDeathEvent event) {
        if (DeathSwap.getInstance().isRunning()) {
            event.getEntity().setGameMode(GameMode.SPECTATOR);
        }
    }
}