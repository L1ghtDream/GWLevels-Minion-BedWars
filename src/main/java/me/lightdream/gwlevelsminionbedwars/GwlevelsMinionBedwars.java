package me.lightdream.gwlevelsminionbedwars;

import me.clip.placeholderapi.PlaceholderAPI;
import me.lightdream.gwlevelsminionbedwars.database.DatabaseConnector;
import me.lightdream.gwlevelsminionbedwars.managers.CommandManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Objects;

public final class GwlevelsMinionBedwars extends JavaPlugin {

    private static GwlevelsMinionBedwars plugin;
    public static final String PLUGIN_VERSION = "1.0";
    public static final String MINION_VERSION = "1.0";

    @Override
    public void onEnable() {
        plugin = this;

        getServer().getConsoleSender().sendMessage("\n  _____       _             __         _     ______                                           \n |_   _|     (_)           [  |       / |_  |_   _ `.                                         \n   | |       __     .--./)  | |--.   `| |-'   | | `. \\  _ .--.   .---.   ,--.    _ .--..--.   \n   | |   _  [  |   / /'`\\;  | .-. |   | |     | |  | | [ `/'`\\] / /__\\\\ `'_\\ :  [ `.-. .-. |  \n  _| |__/ |  | |   \\ \\._//  | | | |   | |,   _| |_.' /  | |     | \\__., // | |,  | | | | | |  \n |________| [___]  .',__`  [___]|__]  \\__/  |______.'  [___]     '.__.' \\'-;__/ [___||__||__] \n                  ( ( __))                                                                    ");
        DatabaseConnector.sqlSetup();

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        // ------------------------------ Updater ------------------------------

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            for(Player player : Bukkit.getServer().getOnlinePlayers())
            {
                System.out.println("Updated database");
                setToDatabase(player.getName(), calculatePoints(player));
            }
        }, 0L, 6000L);


        Objects.requireNonNull(this.getCommand("dev")).setExecutor(new CommandManager());

    }

    @Override
    public void onDisable() {
        this.saveDefaultConfig();
    }

    // ------------------------------ Database OPS ------------------------------
    public void setToDatabase(String player, double points)
    {
        try {
            PreparedStatement st = DatabaseConnector.con.prepareStatement("SELECT COUNT(*) FROM " + DatabaseConnector.points + " WHERE NAME = '" + player + "'");
            ResultSet rs = st.executeQuery();
            if (rs.next())
            {
                if (rs.getInt("COUNT(*)") == 1)
                    st = DatabaseConnector.con.prepareStatement("UPDATE " + DatabaseConnector.points + " SET BEDWARS=" + points + " WHERE NAME='" + player + "'");
                else
                    st = DatabaseConnector.con.prepareStatement("INSERT INTO " + DatabaseConnector.points + " VALUES('" + player + "', " + points + ", 0)");
                st.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // ------------------------------ Calculus ------------------------------
    private double calculatePoints(Player player)
    {
        double kills =         Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_kills%"))         * this.getConfig().getDouble("kills");
        double wins =          Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_wins%"))          * this.getConfig().getDouble("wins");
        double finalKills =    Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_finalkills%"))    * this.getConfig().getDouble("finalKills");
        double deaths =        Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_deaths%"))        * this.getConfig().getDouble("deaths");
        double loses =         Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_losses%"))        * this.getConfig().getDouble("loses");
        double finalDeaths =   Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_finaldeaths%"))   * this.getConfig().getDouble("finalDeaths");
        double bedsDestroyed = Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_bedsdestroyed%")) * this.getConfig().getDouble("bedsDestroyed");
        double gamesPlayed =   Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_stats_gamesplayed%"))   * this.getConfig().getDouble("gamesPlayed");
        double level =         Double.parseDouble(PlaceholderAPI.setPlaceholders(player, "%bw1058_player_level_raw%"))    * this.getConfig().getDouble("level");

        //System.out.println(kills + " " + wins  + " " + finalKills + " " + deaths + " " + loses + " " + finalDeaths + " " + bedsDestroyed + " " + gamesPlayed + " " + level);

        return kills + wins + finalKills + deaths + loses + finalDeaths + bedsDestroyed + gamesPlayed + level;
    }

    // ------------------------------ Getters ------------------------------
    public static GwlevelsMinionBedwars getPlugin()
    {
        return plugin;
    }

}
