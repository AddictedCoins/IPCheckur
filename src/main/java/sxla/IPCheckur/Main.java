package sxla.IPCheckur;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getLogger().info("§a[§bIPCheckur§a] Plugin Loaded!");

        // 注册命令
        PluginCommand ipCommand = getCommand("ip");
        if (ipCommand != null) {
            ipCommand.setExecutor(new getIP());
        }

        PluginCommand geoIpCommand = getCommand("geoip");
        if (geoIpCommand != null) {
            geoIpCommand.setExecutor(new getGeoIP());
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("§a[§bIPCheckur§a] Plugin Unloaded!");
    }
}
