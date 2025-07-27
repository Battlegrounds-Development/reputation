package me.remag501.reputation;

import me.remag501.reputation.commands.ReputationCommand;
import me.remag501.reputation.core.ReputationCore;
import me.remag501.reputation.util.PermissionUtil;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.List;

public final class Reputation extends JavaPlugin {
    private ReputationCore reputationCore;
    private File reputationFile;
    private FileConfiguration reputationConfig;

    @Override
    public void onEnable() {
        // Load config.yml
        saveDefaultConfig();

        // Setup reputation.yml
        createReputationFile();

        // Example: load NPC list and permission util
        List<String> npcList = List.of("amy", "brick", "jake");

        PermissionUtil permissionUtil = new PermissionUtil();
        permissionUtil.loadFromConfig(getConfig()); // loads from config.yml

        ReputationCore reputationCore = new ReputationCore(reputationConfig, npcList, permissionUtil);

        if (getCommand("reputation") != null) {
            getCommand("reputation").setExecutor(new ReputationCommand(reputationCore));
        }
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


    @Override
    public void onDisable() {
        saveConfig();
        saveReputationFile();
    }

    public void saveReputationFile() {
        try {
            reputationConfig.save(reputationFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public ReputationCore getReputationCore() {
        return reputationCore;
    }
}
