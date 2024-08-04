package me.xiaojibazhanshi.staffmenu.guis;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import dev.triumphteam.gui.guis.PaginatedGui;
import me.xiaojibazhanshi.staffmenu.StaffMenu;
import me.xiaojibazhanshi.staffmenu.utils.Util;
import me.xiaojibazhanshi.staffmenu.config.ConfigManager;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class PlayerTPGUI {

    private MainGUI mainGUI;
    ConfigManager configManager = StaffMenu.getInstance().getConfigManager();

    public void setMainGUI(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
    }

    public PaginatedGui getPlayerTPGUI() {
        PaginatedGui gui = Gui.paginated()
                .title(Component.text(configManager.getGuiTpTitle()))
                .rows(4)
                .pageSize(27)
                .create();

        List<Player> onlinePlayers = new ArrayList<>(Bukkit.getOnlinePlayers());
        ItemStack skullFormat = configManager.getGuiTpFormat();

        for (Player target : onlinePlayers) {
            ItemStack updatedSkull = Util.updatePlaceholders(skullFormat, target, null);

            GuiItem playerItem = ItemBuilder.from(updatedSkull).asGuiItem(event -> {
                Player player = (Player) event.getWhoClicked();

                player.teleport(target);
                player.sendMessage(Util.replacePlaceholders(configManager.getMsgTeleported(), player, target));
            });

            gui.addItem(playerItem);
        }

        GuiItem back = ItemBuilder.from(Util.createBackButton()).asGuiItem(event -> {
            Player player = (Player) event.getWhoClicked();
            mainGUI.getMainGUI().open(player);
        });

        GuiItem filler = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).asGuiItem();
        int[] fillerSpots = {2, 4, 5, 6, 8};

        for (int i : fillerSpots) {
            gui.setItem(4, i, filler);
        }

        gui.setItem(4, 1, back);
        gui.setItem(4, 3, ItemBuilder.from(Material.PAPER).setName(Util.translateColors("&cPrevious"))
                .asGuiItem(event -> gui.previous()));
        gui.setItem(4, 7, ItemBuilder.from(Material.PAPER).setName(Util.translateColors("&cNext"))
                .asGuiItem(event -> gui.next()));

        gui.setDefaultClickAction(event -> event.setCancelled(true));

        return gui;
    }

}
