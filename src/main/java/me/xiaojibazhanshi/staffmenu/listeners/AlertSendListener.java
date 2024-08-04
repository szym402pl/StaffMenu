package me.xiaojibazhanshi.staffmenu.listeners;

import me.xiaojibazhanshi.staffmenu.StaffMenu;
import me.xiaojibazhanshi.staffmenu.utils.Util;
import me.xiaojibazhanshi.staffmenu.config.ConfigManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class AlertSendListener implements Listener {

    private final ConfigManager configManager = StaffMenu.getInstance().getConfigManager();

    @EventHandler
    public void onChatSend(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!Util.isSendingAnAlert(player)) {
            return;
        }

        event.setCancelled(true);
        Util.playersSendingAlert.remove(player.getUniqueId());

        String msg = event.getMessage();
        if (msg.equalsIgnoreCase("cancel")) {
            return;
        }

        String updatedMsg = configManager.getMsgAlertFormat().replace("{message}", Util.translateColors(msg));
        Bukkit.broadcastMessage(updatedMsg);
    }
}
