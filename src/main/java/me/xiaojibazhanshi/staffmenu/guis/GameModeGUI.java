package me.xiaojibazhanshi.staffmenu.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import me.xiaojibazhanshi.staffmenu.StaffMenu;
import me.xiaojibazhanshi.staffmenu.config.ConfigManager;
import me.xiaojibazhanshi.staffmenu.utils.Util;
import net.kyori.adventure.text.Component;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.entity.Player;

public class GameModeGUI {

    private MainGUI mainGUI;
    ConfigManager configManager = StaffMenu.getInstance().getConfigManager();

    public void setMainGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public Gui getGameModeGUI() {
        Gui gui = Gui.gui()
                .title(Component.text(configManager.getGuiGmTitle()))
                .rows(3)
                .create();

        GuiItem back = ItemBuilder.from(Util.createBackButton()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            mainGUI.getMainGUI().open(player);
        });

        GuiItem survival = ItemBuilder.from(configManager.getGuiGmSurvival()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            gui.close(player);

            player.setGameMode(GameMode.SURVIVAL);
            player.sendMessage(Util.replacePlaceholders(configManager.getMsgGmChange(), player, null));
        });

        GuiItem creative = ItemBuilder.from(configManager.getGuiGmCreative()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            gui.close(player);

            player.setGameMode(GameMode.CREATIVE);
            player.sendMessage(Util.replacePlaceholders(configManager.getMsgGmChange(), player, null));
        });

        GuiItem spectator = ItemBuilder.from(configManager.getGuiGmSpectator()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            gui.close(player);

            player.setGameMode(GameMode.SPECTATOR);
            player.sendMessage(Util.replacePlaceholders(configManager.getMsgGmChange(), player, null));
        });

        gui.setItem(2, 3, survival);
        gui.setItem(2, 5, creative);
        gui.setItem(2, 7, spectator);
        gui.setItem(3, 1, back);

        gui.getFiller().fill(ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem());
        gui.setDefaultClickAction(event -> event.setCancelled(true));

        return gui;
    }




}
