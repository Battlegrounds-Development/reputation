package me.remag501.reputation.util;

import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
import java.util.List;

public class DealerUtil {

    private final List<String> dealers;

    public DealerUtil(FileConfiguration config) {
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
