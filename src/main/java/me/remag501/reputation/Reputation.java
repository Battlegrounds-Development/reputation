package me.remag501.reputation;

import me.remag501.reputation.command.ReputationCommand;
import me.remag501.reputation.manager.ReputationManager;
import me.remag501.reputation.manager.DealerManager;
import me.remag501.reputation.manager.PermissionManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Reputation extends JavaPlugin {
    private ReputationManager reputationManager;
    private File reputationFile;
    private FileConfiguration reputationConfig;
    private DealerManager dealerManager;
    private PermissionManager permissionManager;

    @Override
    public void onEnable() {
        // Load config.yml
        saveDefaultConfig();

        // Setup reputation.yml
        createReputationFile();

        // Example: load NPC list and permission util
        dealerManager = new DealerManager(this);
        permissionManager = new PermissionManager(this);
        reputationManager = new ReputationManager(this);

        if (getCommand("reputation") != null) {
            getCommand("reputation").setExecutor(new ReputationCommand(this));
        }
    }

    public void reload() {
        reloadConfig();
        dealerManager.reload();
        permissionManager.loadConfig();
        reputationManager.reload();
    }

    private void createReputationFile() {
        reputationFile = new File(getDataFolder(), "reputation.yml");

        if (!reputationFile.exists()) {
            try {
                reputationFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        reputationConfig = YamlConfiguration.loadConfiguration(reputationFile);
    }

    public FileConfiguration getReputationConfig() {
        return reputationConfig;
    }

    @Override
    public void onDisable() {
//        saveConfig();
        saveReputationFile();
    }

    public void saveReputationFile() {
        try {
            reputationConfig.save(reputationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ReputationManager getReputationCore() {
        return reputationManager;
    }

    public PermissionManager getPermissionUtil() {
        return permissionManager;
    }

    public DealerManager getDealerManager() {
        return dealerManager;
    }
}
