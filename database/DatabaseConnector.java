package me.lightdream.gwlevels.database;

import me.lightdream.gwlevels.Gwlevels;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

import java.sql.*;

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

    public static String levels;
    public static String tags;

    public static void sqlSetup() {
        try {
            synchronized (Gwlevels.getPlugin()) {
                if (con != null && !con.isClosed()) {
                    return;
                }
                loadStuff();
                Class.forName("com.mysql.cj.jdbc.Driver");
                con = DriverManager.getConnection(String.format("jdbc:mysql://%s:%s/%s?autoReconnect=true&useSSL=false", host, port, database), user, pass);
                Gwlevels.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[SQL] " + ChatColor.GREEN + "DATABASE CONNECTED");
            }
        } catch (SQLException var3) {
            Gwlevels.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[SQL] " + ChatColor.RED + "AN ERROR OCURED WHILE TRYING TO CONNECT TO DATABASE");
            Gwlevels.getPlugin().getServer().getPluginManager().disablePlugin(Gwlevels.getPlugin());
        } catch (ClassNotFoundException var4) {
            var4.printStackTrace();
            Gwlevels.getPlugin().getServer().getConsoleSender().sendMessage(ChatColor.YELLOW + "[SQL] " + ChatColor.RED + "JBDC DRIVER NOT FOUND! PLEASE INSTALL IT TO USE THIS PLUGIN");
            Gwlevels.getPlugin().getServer().getPluginManager().disablePlugin(Gwlevels.getPlugin());
        }

    }

    private static void loadStuff() {
        FileConfiguration config = Gwlevels.getPlugin().getConfig();
        host = config.getString("host");
        database = config.getString("database");
        user = config.getString("user");
        pass = config.getString("password");
        port = config.getInt("port");

        levels = config.getString("levels");
        tags = config.getString("tags");
    }

}
