package me.xiaojibazhanshi.staffmenu.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.staffmenu.StaffMenu;
import me.xiaojibazhanshi.staffmenu.config.ConfigManager;
import me.xiaojibazhanshi.staffmenu.utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class MainGUI {

    ConfigManager configManager = StaffMenu.getInstance().getConfigManager();

    private final PlayerTPGUI playerTPGUI;
    private final GameModeGUI gameModeGUI;

    public MainGUI(PlayerTPGUI playerTPGUI, GameModeGUI gameModeGUI) {
        this.playerTPGUI = playerTPGUI;
        this.gameModeGUI = gameModeGUI;
    }


    public Gui getMainGUI() {
        Gui gui = Gui.gui()
                .title(Component.text(configManager.getGuiMainTitle()))
                .rows(3)
                .create();

        GuiItem vanish = ItemBuilder.from(configManager.getGuiMainVanish()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            gui.close(player);
            Util.handleVanish(player);

            String message = Util.isVanished(player) ? configManager.getMsgVanishOn() : configManager.getMsgVanishOff();
            player.sendMessage(message);
        });

        GuiItem rtp = ItemBuilder.from(configManager.getGuiMainRtp()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            gui.close(player);
            Player target = Util.getRandomPlayer();

            player.teleport(target);
            player.sendMessage(Util.replacePlaceholders(configManager.getMsgTeleported(), player, target));
        });

        GuiItem tp = ItemBuilder.from(configManager.getGuiMainTp()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            playerTPGUI.getPlayerTPGUI().open(player);
        });

        GuiItem gm = ItemBuilder.from(configManager.getGuiMainGm()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            gameModeGUI.getGameModeGUI().open(player);
        });

        GuiItem alert = ItemBuilder.from(configManager.getGuiMainAlert()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            Util.startAlertSend(player);
            player.sendMessage(configManager.getMsgAlertPrompt());
        });

        gui.setItem(2, 3, vanish);
        gui.setItem(2, 4, rtp);
        gui.setItem(2, 5, tp);
        gui.setItem(2, 6, gm);
        gui.setItem(2, 7, alert);

        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem());
        gui.setDefaultClickAction(event -> event.setCancelled(true));

        return gui;
    }

}
