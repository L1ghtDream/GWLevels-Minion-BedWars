package me.lightdream.gwlevelsminionbedwars.database;

import me.lightdream.gwlevelsminionbedwars.GwlevelsMinionBedwars;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DatabaseConnector {

    //Connection
    public static Connection con;
    private static Statement st;

    //Auth
    private static String host;
    private static String database;
    private static String user;
    private static String pass;
    private static int port;

    //public static String levels;
    //public static String tags;
    public static String points;

    public static void sqlSetup() {
        try {
            synchronized (GwlevelsMinionBedwars.getPlugin()) {
                if (con != null && !con.isClosed()) {
                    return;
                }
                loadStuff();
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?autoReconnect=true&useSSL=false", host, port, database), user, pass);
                GwlevelsMinionBedwars.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[SQL] " + ChatColor.GREEN + "DATABASE CONNECTED");
            }
        } catch (SQLException var3) {
            GwlevelsMinionBedwars.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[SQL] " + ChatColor.RED + "AN ERROR OCURED WHILE TRYING TO CONNECT TO DATABASE");
            GwlevelsMinionBedwars.getPlugin().getServer().getPluginManager().disablePlugin(GwlevelsMinionBedwars.getPlugin());
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
            GwlevelsMinionBedwars.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[SQL] " + ChatColor.RED + "JBDC DRIVER NOT FOUND! PLEASE INSTALL IT TO USE THIS PLUGIN");
            GwlevelsMinionBedwars.getPlugin().getServer().getPluginManager().disablePlugin(GwlevelsMinionBedwars.getPlugin());
        }

    }

    private static void loadStuff() {
        FileConfiguration config = GwlevelsMinionBedwars.getPlugin().getConfig();
        host = config.getString("host");
        database = config.getString("database");
        user = config.getString("user");
        pass = config.getString("password");
        port = config.getInt("port");

        //levels = config.getString("levels");
        //tags = config.getString("tags");
        points = config.getString("points");
    }

}
