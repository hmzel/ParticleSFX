package hm.zelha.particlesfx;

import org.bukkit.plugin.Plugin;

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