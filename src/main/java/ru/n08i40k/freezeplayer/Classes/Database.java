package ru.n08i40k.freezeplayer.Classes;
import java.io.File;
import java.io.IOException;
import java.sql.*;
import java.util.logging.Logger;
import ru.n08i40k.freezeplayer.FreezePlayer;

public class Database {
    private final FreezePlayer plugin;
    private final Logger logger;

    private final String dbFilename = "players.sql";
    private File dbFile;
    private Connection database;

    public Database(FreezePlayer instance) {
        plugin = instance;
        logger = plugin.getLogger();

        openConnection();

        if (database != null)
            prepareDatabase();
    }
    private void openConnection() {
        if (!plugin.getDataFolder().exists())
            plugin.getDataFolder().mkdirs();

        dbFile = new File(plugin.getDataFolder() + File.separator + dbFilename);

        if (!dbFile.exists()) {
            logger.info("(Database) The database file does not exist. A new one will be created.");
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                logger.warning("(Database) An error occurred while trying to create the database file!");
                e.printStackTrace();

                plugin.Stop();
                return;
            }
        } else {
            logger.info("(Database) Database file found.");
        }

        try {
            database = DriverManager.getConnection("jdbc:sqlite:"+dbFile);
        } catch (SQLException e) {
            logger.warning("(Database) An error occurred while trying to open the database file!");
            e.printStackTrace();

            plugin.Stop();
            return;
        }
        logger.info("Database was successfully loaded!");
    }
    private void prepareDatabase() {
        try {
            Statement st = database.createStatement();

            st.executeUpdate("CREATE TABLE IF NOT EXISTS players (name string)");

            ResultSet rs = st.executeQuery("SELECT * FROM players;");

            while (rs.next()) {
                plugin.FreezedPlayers.add(rs.getString("name"));
            }
            st.close();
        } catch (SQLException e) {
            logger.warning("(Database) An error occurred while trying to prepare the database!");
            e.printStackTrace();

            plugin.Stop();
            return;
        }
        logger.info("Database was successfully prepared!");
    }
    public void FreezePlayer(String name) {
        try {
            boolean contains = isPlayerExists(name);
            logger.info ("is player exist: " + contains);

            Statement st = database.createStatement();
            if (!contains) st.executeUpdate("INSERT INTO players VALUES('" + name + "');");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.Stop();
        }
    }

    public void UnFreezePlayer(String name) {
        try {
            Statement st = database.createStatement();
            st.executeUpdate("DELETE FROM players WHERE name='" + name + "';");
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.Stop();
        }
    }
    private boolean isPlayerExists(String name) {
        try {
            Statement st = database.createStatement();
            ResultSet rs = st.executeQuery("SELECT * FROM players WHERE name='"+name+"';");
            st.close();

            int size = 0;
            try {
                rs.last();
                size = rs.getRow();
            }
            catch(Exception ex) {
                return false;
            }
            return (size == 1);
        } catch (SQLException e) {
            e.printStackTrace();
            plugin.Stop();
        }
        return false;
    }
    public void closeConnection() {
        if (database != null)
            try { database.close(); } catch (SQLException e) { e.printStackTrace(); }
    }
}

