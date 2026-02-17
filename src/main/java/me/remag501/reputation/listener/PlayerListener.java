package me.remag501.reputation.listener;

import me.remag501.bgscore.api.event.EventService;
import me.remag501.reputation.manager.PermissionManager;
import me.remag501.reputation.manager.ReputationManager;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener {

    public PlayerListener(EventService eventService, PermissionManager permissionManager, ReputationManager reputationManager) {

        // Handle Join: apply permissions
        eventService.subscribe(PlayerJoinEvent.class)
                .namespace("reputation")
                .handler(event -> {
                    Player player = event.getPlayer();
                    permissionManager.applyAllPermissions(player, reputationManager.getReputationMap(player));
                });

        // Handle Quit: cleanup attachments
        eventService.subscribe(PlayerQuitEvent.class)
                .namespace("reputation")
                .handler(event -> {
                    permissionManager.removeAttachment(event.getPlayer());
                });
    }
}