package me.lightdream.gwlevelsminionbedwars.managers;

import me.lightdream.gwlevelsminionbedwars.GwlevelsMinionBedwars;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class CommandManager implements CommandExecutor {

    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, String label, String[] args) {
        if(label.equalsIgnoreCase("dev"))
        {
            Player player = (Player) sender;
            sender.sendMessage("MainPlugin: " + GwlevelsMinionBedwars.PLUGIN_VERSION + "\nMinionPlugin: " + GwlevelsMinionBedwars.MINION_VERSION);
        }
        return true;
    }

}
