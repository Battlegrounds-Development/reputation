package me.remag501.reputation.manager;

import me.remag501.reputation.Reputation;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PermissionManager {
    private final Map<String, Map<String, Integer>> dealerPermissions = new HashMap<>();
    private Reputation plugin;

    public PermissionManager(Reputation plugin) {
        this.plugin = plugin;
        loadConfig();
    }

    public void loadConfig() {
        FileConfiguration config = plugin.getConfig();
        dealerPermissions.clear();

        if (!config.isConfigurationSection("permissions")) return;

        for (String dealer : config.getConfigurationSection("permissions").getKeys(false)) {
            List<Map<?, ?>> permList = config.getMapList("permissions." + dealer);
            Map<String, Integer> permMap = new HashMap<>();

            for (Map<?, ?> entry : permList) {
                String permission = (String) entry.get("permission");
                int min = (int) entry.get("min");
                permMap.put(permission, min);
            }

            dealerPermissions.put(dealer, permMap);
        }
    }

    public void applyPermissions(Player player, String dealer, int playerRep) {
        Map<String, Integer> perms = dealerPermissions.get(dealer.toLowerCase());
        player.sendMessage(dealerPermissions.toString());
        if (perms == null) return;
        for (Map.Entry<String, Integer> entry : perms.entrySet()) {
            String permission = entry.getKey();
            int requiredRep = entry.getValue();

            if (playerRep >= requiredRep) {
                // Grant
                player.sendMessage("You gained permission " + permission);
                PermissionAttachment permissionAttachment = player.addAttachment(Bukkit.getPluginManager().getPlugin("ReputationBGS"));
                permissionAttachment.setPermission(permission, true);
            } else {
                // Revoke
                player.sendMessage("You lost permission " + permission);
                player.addAttachment(Bukkit.getPluginManager().getPlugin("ReputationBGS"), permission, false);
            }
        }
    }

}

