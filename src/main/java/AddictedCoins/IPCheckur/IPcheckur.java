package jja8.iplog;

import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.JarFile;
import cn.jja8.patronSaint_2022_3_2_1244.allUsed.file.YamlConfig;
import cn.jja8.patronSaint_2022_3_2_1244.bukkit.command.CommandImplement;
import cn.jja8.patronSaint_2022_3_2_1244.bukkit.command.CommandManger;
import com.maxmind.geoip2.DatabaseReader;
import com.maxmind.geoip2.exception.GeoIp2Exception;
import com.maxmind.geoip2.model.CityResponse;
import com.maxmind.geoip2.model.CountryResponse;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public final class IPcheckur extends JavaPlugin  implements Listener {


    /**
     * [uesName] 城市 省 国家 xxx.xxx.xxx.xxx hh:mm:ss yyyy/MM/ss
     *
     * */

    SimpleDateFormat timeToString = new SimpleDateFormat("hh:mm:ss yyyy/MM/dd");
    PrintWriter printWriter;
    DatabaseReader databaseReaderCity,databaseReaderCountry;


    @Override
    public void onEnable() {

        try {
            Database database = YamlConfig.loadFromFile(new File(getDataFolder(), "database.yml"), new Database());

            JarFile.unzipFile(new File(database.city),this.getClass(),"GeoLite2-City.mmdb",false);
            JarFile.unzipFile(new File(database.country),this.getClass(),"GeoLite2-Country.mmdb",false);

            databaseReaderCity = new DatabaseReader.Builder(new File(database.city)).build();
            databaseReaderCountry = new DatabaseReader.Builder(new File(database.country)).build();


            File filef =new File(getDataFolder(),"log");
            filef.mkdirs();
            File file = new File(filef,new SimpleDateFormat("hh.mm.ss.yyyy.MM.dd").format(new Date())+".log");
            printWriter = new PrintWriter(new FileWriter(file));
            Bukkit.getPluginManager().registerEvents(this,this);

            CommandManger commandManger1 = new CommandManger(this,"ip","IPcheckur.admin");
            commandManger1.setDefaulCommand(new CommandImplement() {
                @Override
                public void command(CommandSender commandSender, String[] strings) {
                    if (strings.length<1){
                        commandSender.sendMessage("Player not online");
                        return;
                    }
                    Player player = Bukkit.getPlayer(strings[0]);
                    if (player==null){
                        commandSender.sendMessage("Player not online");
                        return;
                    }
                    InetAddress address = player.getAddress().getAddress();
                    commandSender.sendMessage("("+player.getName()+")'s IP is ["+address.getHostAddress()+"]");
                }
                @Override
                public List<String> TabCompletion(CommandSender commandSender, String[] strings) {
                    List<String> list = new ArrayList<>();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        list.add(onlinePlayer.getName());
                    }
                    return list;
                }
            });

            CommandManger commandManger = new CommandManger(this,"IPcheckur","IPcheckur.admin");
            commandManger.setDefaulCommand(new CommandImplement() {
                @Override
                public void command(CommandSender commandSender, String[] strings) {
                    if (strings.length<1){
                        commandSender.sendMessage("Player not online");
                        return;
                    }
                    Player player = Bukkit.getPlayer(strings[0]);
                    if (player==null){
                        commandSender.sendMessage("Player not online");
                        return;
                    }
                    Date date = new Date();
                    InetAddress address = player.getAddress().getAddress();
                    String city = "unknown";
                    String province = "unknown";
                    String country = "unknown";
                    try {
                        CountryResponse countryResponse = null;
                        try {
                            countryResponse = databaseReaderCountry.country(address);
                        } catch (GeoIp2Exception ignored) {

                        }
                        if (countryResponse!=null){
                            country = countryResponse.getCountry().getName();
                        }
                        CityResponse cityResponse = null;
                        try {
                            cityResponse = databaseReaderCity.city(address);
                        } catch (GeoIp2Exception ignored) {

                        }
                        if (cityResponse!=null){
                            city = cityResponse.getCity().getName();
                            province = cityResponse.getSubdivisions().get(0).getName();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    commandSender.sendMessage("["+player.getName()+"] "+city+" "+province+" "+country+" "+address.getHostAddress()+" "+timeToString.format(date));
                }
                @Override
                public List<String> TabCompletion(CommandSender commandSender, String[] strings) {
                    List<String> list = new ArrayList<>();
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        list.add(onlinePlayer.getName());
                    }
                    return list;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler
    public void PlayerJoinEvent(PlayerJoinEvent event){
        Bukkit.getScheduler().runTaskAsynchronously(this, () -> {
            Player player = event.getPlayer();
            Date date = new Date();
            InetAddress address = player.getAddress().getAddress();
            String city = "unknown";
            String province = "unknown";
            String country = "unknown";
            try {
                CountryResponse countryResponse = null;
                try {
                    countryResponse = databaseReaderCountry.country(address);
                } catch (GeoIp2Exception ignored) {

                }
                if (countryResponse!=null){
                    country = countryResponse.getCountry().getName();
                }
                CityResponse cityResponse = null;
                try {
                    cityResponse = databaseReaderCity.city(address);
                } catch (GeoIp2Exception ignored) {

                }
                if (cityResponse!=null){
                    city = cityResponse.getCity().getName();
                    province = cityResponse.getSubdivisions().get(0).getName();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            String finalCity = city;
            String finalProvince = province;
            String finalCountry = country;
            printWriter.println("["+player.getName()+"] "+ finalCity +" "+ finalProvince +" "+ finalCountry +" "+address.getHostAddress()+" "+timeToString.format(date));
            printWriter.flush();
        });
    }
}
