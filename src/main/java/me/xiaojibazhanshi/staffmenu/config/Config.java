package me.xiaojibazhanshi.staffmenu.config;

import me.xiaojibazhanshi.staffmenu.StaffMenu;
import me.xiaojibazhanshi.staffmenu.utils.Util;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public enum Config {

    MSG_VANISH_ON("messages.vanish-on"),
    MSG_VANISH_OFF("messages.vanish-off"),
    MSG_ALERT_PROMPT("messages.alert-prompt"),
    MSG_ALERT_EXPIRY("messages.alert-expiry"),
    MSG_ALERT_FORMAT("messages.alert-format"),
    MSG_ALERT_CANCELLED("messages.alert-cancelled"),
    MSG_TELEPORTED("messages.teleported"),
    MSG_GM_CHANGE("messages.gm-change"),

    GUI_MAIN_TITLE("main-gui.title"),
    GUI_MAIN_VANISH("main-gui.items.vanish-item"),
    GUI_MAIN_RTP("main-gui.items.rtp-item"),
    GUI_MAIN_TP("main-gui.items.tp-item"),
    GUI_MAIN_GM("main-gui.items.gm-item"),
    GUI_MAIN_ALERT("main-gui.items.alert-item"),

    GUI_TP_TITLE("player-teleport-gui.title"),
    GUI_TP_FORMAT("player-teleport-gui.player-format"),

    GUI_GM_TITLE("game-mode-change-gui.title"),
    GUI_GM_SURVIVAL("game-mode-change-gui.items.survival"),
    GUI_GM_CREATIVE("game-mode-change-gui.items.creative"),
    GUI_GM_SPECTATOR("game-mode-change-gui.items.spectator");

    private final String path;

    Config(String path) {
        this.path = path;
    }

    public <T> T get(Class<T> clazz) {
        return clazz.cast(StaffMenu.getInstance().getConfig().get(path));
    }

    public ItemStack getItem() {
        ConfigurationSection section = StaffMenu.getInstance().getConfig().getConfigurationSection(path);

        if (section == null) {
            throw new IllegalArgumentException("Configuration section " + path + " not found.");
        }

        String material = section.getString("material");
        String name = section.getString("name");
        List<String> lore = section.getStringList("lore");

        return Util.createItemStack(material, name, lore);
    }

}
