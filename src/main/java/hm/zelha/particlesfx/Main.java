package hm.zelha.particlesfx;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.util.Random;

/** only used to trick BukkitRunnables into thinking this is a plugin */
public final class Main {

    private static int arrayIndex = 0;
    private static Plugin plugin = Bukkit.getPluginManager().getPlugins()[0];
    private static final Random rng = new Random();

    private Main() {
    }

    public static Plugin getPlugin() {
        if (!plugin.isEnabled()) {
            arrayIndex++;

            if (Bukkit.getPluginManager().getPlugins().length > arrayIndex) return null;

            plugin = Bukkit.getPluginManager().getPlugins()[arrayIndex];

            return getPlugin();
        }

        return plugin;
    }

    public static Random getRng() {
        return rng;
    }
}
