package sxla.IPCheckur;

import org.bukkit.command.PluginCommand;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {

        // Register Command
        PluginCommand ipCommand = getCommand("ip");
        if (getIP != null) {
            getIP.setExecutor(new IpCommand());
        }

        PluginCommand getGeoIP = getCommand("geoip");
        if (getGeoIP != null) {
            getGeoIP.setExecutor(new getGeoIP());
        }
    }
}
