package me.xiaojibazhanshi.staffmenu.commands;

import me.xiaojibazhanshi.staffmenu.StaffMenu;
import me.xiaojibazhanshi.staffmenu.guis.MainGUI;
import me.xiaojibazhanshi.staffmenu.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class StaffCommand implements CommandExecutor {

    MainGUI mainGUI = StaffMenu.getInstance().getMainGUI();

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] args) {

        if (!(sender instanceof Player player)) {
            Bukkit.getLogger().info("Command only available to players.");
            return true;
        }

        boolean isReloadCmd = args.length == 1 && args[0].equalsIgnoreCase("reload");

        if (isReloadCmd) {
            StaffMenu.getInstance().reloadConfiguration(player);

        } else if (args.length == 0) {
            mainGUI.getMainGUI().open(player);

        } else {
            player.sendMessage(Util.translateColors("&cUsage: " + command.getUsage()));
        }

        return true;
    }
}
