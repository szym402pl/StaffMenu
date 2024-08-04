package me.xiaojibazhanshi.staffmenu.utils;

import me.xiaojibazhanshi.staffmenu.StaffMenu;
import me.xiaojibazhanshi.staffmenu.config.ConfigManager;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.scheduler.BukkitRunnable;

import javax.annotation.Nullable;
import java.util.*;

public class Util {

    public static Set<UUID> hiddenPlayers = new HashSet<>();
    public static List<UUID> playersSendingAlert = new ArrayList<>();

    // vanish logic

    public static void handleVanish(Player player) {
        UUID playerUUID = player.getUniqueId();

        if (isVanished(player)) {
            hiddenPlayers.remove(playerUUID);
            Bukkit.getOnlinePlayers().forEach(target -> player.showPlayer(StaffMenu.getInstance(), player));
        } else {
            hiddenPlayers.add(playerUUID);
            Bukkit.getOnlinePlayers().forEach(target -> player.hidePlayer(StaffMenu.getInstance(), player));
        }
    }

    public static boolean isVanished(Player player) {
        return hiddenPlayers.contains(player.getUniqueId());
    }

    // Random player teleport logic

    public static Player getRandomPlayer() {
        Collection<? extends Player> immutablePlayerList = Bukkit.getOnlinePlayers();
        List<Player> onlinePlayers = new ArrayList<>(immutablePlayerList);

        Collections.shuffle(onlinePlayers);
        return onlinePlayers.getFirst();
    }

    // Alert sender logic

    public static boolean isSendingAnAlert(Player player) {
        return playersSendingAlert.contains(player.getUniqueId());
    }

    public static void startAlertSend(Player player) {
        playersSendingAlert.add(player.getUniqueId());

        // failsafe for if player doesn't type anything in after 30 sec
        stopAlertSend(player, (long) 20 * 30);
    }

    public static void stopAlertSend(Player player, Long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!isSendingAnAlert(player)) {
                    return;
                }

                playersSendingAlert.remove(player.getUniqueId());

                if (player.isOnline())
                    player.sendMessage(StaffMenu.getInstance().getConfigManager().getMsgAlertExpiry());
            }
        }.runTaskLaterAsynchronously(StaffMenu.getInstance(), delay);
    }

    // Items n stuff

    public static ItemStack createItemStack(@Nullable String material, @Nullable String name, @Nullable List<String> lore) {
        if (material == null) {
            Bukkit.getLogger().info("[StaffMenu] Some item(s) haven't been set up in the config!");
            return new ItemStack(Material.BARRIER, 1);
        }

        ItemStack item = new ItemStack(Material.valueOf(material), 1);

        if (name == null || lore == null) {
            Bukkit.getLogger().info("[StaffMenu] Some item(s) are missing name/lore in the config!");
            return item;
        }

        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setEnchantmentGlintOverride(true);
        meta.setDisplayName(translateColors(name));
        meta.setLore(lore.stream().map(Util::translateColors).toList());

        item.setItemMeta(meta);

        return item;
    }

    public static ItemStack createBackButton() {
        ItemStack item = new ItemStack(Material.BARRIER, 1);
        ItemMeta meta = item.getItemMeta();

        meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
        meta.setEnchantmentGlintOverride(true);
        meta.setDisplayName(translateColors("&c&lGO BACK"));
        meta.setLore(new ArrayList<>());

        item.setItemMeta(meta);

        return item;
    }

    // Updates all placeholders in a given item's name and lore
    public static ItemStack updatePlaceholders(ItemStack original, Player player, @Nullable Player target) {
        ItemStack updatedItem = original;
        ItemMeta updatedMeta;

        if (original.getType() == Material.PLAYER_HEAD) {
            SkullMeta skullMeta = (SkullMeta) original.getItemMeta();
            skullMeta.setOwningPlayer(player);

            updatedMeta = skullMeta;
        } else {
            updatedMeta = original.getItemMeta();
        }

        updatedMeta.setDisplayName(replacePlaceholders(original.getItemMeta().getDisplayName(), player, target));
        updatedMeta.setLore(original.getItemMeta().getLore().stream()
                .map(line -> replacePlaceholders(line, player, target)).toList());

        updatedItem.setItemMeta(updatedMeta);
        return updatedItem;
    }

    // OTHER

    public static String translateColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    public static String replacePlaceholders(String text, @Nullable Player player, @Nullable Player target) {
        String updated = text;

        if (target != null) {
            updated = updated.replace("{target}", target.getName());
        }

        if (player != null) {
            updated = updated
                    .replace("{player}", player.getName())
                    .replace("{game-mode}", player.getGameMode().toString());
        }

        return updated;
    }

    public static void flushCacheStuff() {
        playersSendingAlert.clear();
        hiddenPlayers.clear();
    }
}
