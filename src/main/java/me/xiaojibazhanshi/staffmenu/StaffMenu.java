package me.xiaojibazhanshi.staffmenu;

import me.xiaojibazhanshi.staffmenu.commands.StaffCMDTabCompleter;
import me.xiaojibazhanshi.staffmenu.commands.StaffCommand;
import me.xiaojibazhanshi.staffmenu.config.ConfigManager;
import me.xiaojibazhanshi.staffmenu.guis.GameModeGUI;
import me.xiaojibazhanshi.staffmenu.guis.MainGUI;
import me.xiaojibazhanshi.staffmenu.guis.PlayerTPGUI;
import me.xiaojibazhanshi.staffmenu.listeners.AlertSendListener;
import me.xiaojibazhanshi.staffmenu.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import javax.annotation.Nullable;

public final class StaffMenu extends JavaPlugin {

    /*  Configurable GUI upon /staff command
    *   1) Vanish,
    *   2) Teleport to a random player,
    *   3) Player teleport GUI,
    *   4) Game mode change GUI,
    *   5) Alert sender
    */

    private static StaffMenu instance;
    private ConfigManager configManager;
    private MainGUI mainGUI;
    private GameModeGUI gameModeGUI;
    private PlayerTPGUI playerTPGUI;

    @Override
    public void onEnable() {
        instance = this;

        saveDefaultConfig();
        getConfig().options().copyDefaults(true);

        reloadConfiguration(null);

        playerTPGUI = new PlayerTPGUI();
        gameModeGUI = new GameModeGUI();

        mainGUI = new MainGUI(playerTPGUI, gameModeGUI);

        playerTPGUI.setMainGUI(mainGUI);
        gameModeGUI.setMainGUI(mainGUI);

        Bukkit.getPluginManager().registerEvents(new AlertSendListener(), this);

        getCommand("staff").setExecutor(new StaffCommand());
        getCommand("staff").setTabCompleter(new StaffCMDTabCompleter());
    }

    @Override
    public void onDisable() {
        Util.flushCacheStuff();
    }

    public static StaffMenu getInstance() {
        return instance;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public GameModeGUI getGameModeGUI() {
        return gameModeGUI;
    }

    public MainGUI getMainGUI() {
        return mainGUI;
    }

    public PlayerTPGUI getPlayerTPGUI() {
        return playerTPGUI;
    }



    public void reloadConfiguration(@Nullable Player player) {
        reloadConfig();

        try {
            configManager = new ConfigManager();

            if (player != null)
                player.sendMessage(Util.translateColors("&aConfiguration reloaded successfully."));

        } catch (NullPointerException npe) {

            if (player != null)
                player.sendMessage(Util.translateColors("&cAn error has occured while loading the config! " +
                        "\n&cCheck the console for details."));

            printConfigFail();
        }
    }

    private void printConfigFail() {
        Bukkit.getLogger().warning("""
                    [StaffMenu] Loading the configuration failed. \
                    Please fix the configuration setup or if
                    all else fails, contact szym402pl on discord.""");
    }
}
