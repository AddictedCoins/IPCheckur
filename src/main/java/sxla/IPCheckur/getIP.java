package sxla.IPCheckur;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class getIP implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§cUsage: /ip <PlayerName>");
            return true;
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null) {
            sender.sendMessage("§cPlayer " + args[0] + " isnot online！");
            return true;
        }

        String ip = target.getAddress().getAddress().getHostAddress();
        sender.sendMessage("§a" + target.getName() + ": §e" + ip);
        return true;
    }
}
