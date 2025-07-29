package me.remag501.reputation.util;

import javafx.beans.property.ReadOnlyBooleanProperty;
import me.remag501.reputation.Reputation;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.List;

public class DealerUtil {

    private final List<String> dealers;
    private FileConfiguration config;
    private Reputation plugin;

    public DealerUtil(Reputation plugin) {
        this.plugin = plugin;
        this.config = plugin.getConfig();
        // Load and lowercase dealer names for consistency
        if (config.isList("traders")) {
            this.dealers = config.getStringList("traders").stream()
                    .map(String::toLowerCase)
                    .toList();
        } else {
            this.dealers = Collections.emptyList();
        }
    }

    /**
     * Get all dealers as a list.
     */
    public List<String> getDealers() {
        return dealers;
    }

    /**
     * Check if a dealer exists in the list.
     */
    public boolean isDealer(String name) {
        return dealers.contains(name.toLowerCase());
    }
}
