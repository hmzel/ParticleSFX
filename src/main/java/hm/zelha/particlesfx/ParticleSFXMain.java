package hm.zelha.particlesfx;

import org.bukkit.plugin.Plugin;

/** only used to trick BukkitRunnables into thinking this is a plugin */
public final class ParticleSFXMain {

    private static Plugin plugin = null;

    private ParticleSFXMain() {
    }

    public static void setPlugin(Plugin plugin) {
        ParticleSFXMain.plugin = plugin;
    }

    public static Plugin getPlugin() {
        return plugin;
    }
}