package me.lightdream.gwlevelsminionbedwars;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class GwlevelsMinionBedwars extends JavaPlugin {

    @Override
    public void onEnable() {

        this.saveDefaultConfig();
        this.getConfig().options().copyDefaults(true);

        // ------------------------------ Updater ------------------------------

        Bukkit.getScheduler().runTaskTimer(this, () -> {
            //topLevelInventory = Gwlevels.getTopLevelInventory();
            for(Player player : Bukkit.getServer().getOnlinePlayers())
            {

            }

        }, 0L, 10000L);

    }

    @Override
    public void onDisable() {
        this.saveDefaultConfig();
    }

    // ------------------------------ Database OPS ------------------------------
    public void setToDataBase(String player, double points)
    {

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

        return 0;
    }


}
