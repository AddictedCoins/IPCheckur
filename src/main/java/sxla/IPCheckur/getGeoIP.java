package sxla.IPCheckur;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class getGeoIP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /geoip <PlayerName>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer " + args[0] + " isnot online!");
            return true;
        }

        String ip = target.getAddress().getAddress().getHostAddress();
        sender.sendMessage("§aFetching geo-location data for: §e" + ip + " ...");

        Bukkit.getScheduler().runTaskAsynchronously(Main.getPlugin(Main.class), () -> {
            try {
                String apiUrl = "http://ip-api.com/json/" + ip + "?fields=status,message,query,isp,city,regionName,country,timezone";
                HttpURLConnection connection = (HttpURLConnection) new URL(apiUrl).openConnection();
                connection.setRequestMethod("GET");
                connection.setConnectTimeout(5000);
                connection.setReadTimeout(5000);

                BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                JsonObject json = JsonParser.parseString(response.toString()).getAsJsonObject();
                if (!json.get("status").getAsString().equals("success")) {
                    sender.sendMessage("§cFailed to retrieve geo-location data.");
                    return;
                }

                String isp = json.get("isp").getAsString();
                String city = json.get("city").getAsString();
                String region = json.get("regionName").getAsString();
                String country = json.get("country").getAsString();
                String timezone = json.get("timezone").getAsString();

                // time
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss yyyy/MM/dd");
                sdf.setTimeZone(TimeZone.getTimeZone(timezone));
                String currentTime = sdf.format(new Date());

                sender.sendMessage("§aGeoIP Information:");
                sender.sendMessage("§eIP: §f" + ip);
                sender.sendMessage("§eISP: §f" + isp);
                sender.sendMessage("§eLocation: §f" + city + ", " + region + ", " + country);
                sender.sendMessage("§eTime: §f" + currentTime);

            } catch (Exception e) {
                sender.sendMessage("§cError retrieving geo-location data.");
                e.printStackTrace();
            }
        });

        return true;
    }
}
