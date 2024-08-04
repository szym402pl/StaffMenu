package me.xiaojibazhanshi.staffmenu.config;

import me.xiaojibazhanshi.staffmenu.utils.Util;
import org.bukkit.inventory.ItemStack;

public class ConfigManager {

    private final String msgVanishOn;
    private final String msgVanishOff;
    private final String msgAlertPrompt;
    private final String msgAlertExpiry;
    private final String msgAlertFormat;
    private final String msgTeleported;
    private final String msgGmChange;

    private final String guiMainTitle;
    private final ItemStack guiMainVanish;
    private final ItemStack guiMainRtp;
    private final ItemStack guiMainTp;
    private final ItemStack guiMainGm;
    private final ItemStack guiMainAlert;

    private final String guiTpTitle;
    private final ItemStack guiTpFormat;

    private final String guiGmTitle;
    private final ItemStack guiGmSurvival;
    private final ItemStack guiGmCreative;
    private final ItemStack guiGmSpectator;

    public ConfigManager() throws NullPointerException {
        msgVanishOn = Util.translateColors(Config.MSG_VANISH_ON.get(String.class));
        msgVanishOff = Util.translateColors(Config.MSG_VANISH_OFF.get(String.class));
        msgAlertPrompt = Util.translateColors(Config.MSG_ALERT_PROMPT.get(String.class));
        msgAlertExpiry = Util.translateColors(Config.MSG_ALERT_EXPIRY.get(String.class));
        msgAlertFormat = Util.translateColors(Config.MSG_ALERT_FORMAT.get(String.class));
        msgTeleported = Util.translateColors(Config.MSG_TELEPORTED.get(String.class));
        msgGmChange = Util.translateColors(Config.MSG_GM_CHANGE.get(String.class));

        guiMainTitle = Util.translateColors(Config.GUI_MAIN_TITLE.get(String.class));
        guiMainVanish = Config.GUI_MAIN_VANISH.getItem();
        guiMainRtp = Config.GUI_MAIN_RTP.getItem();
        guiMainTp = Config.GUI_MAIN_TP.getItem();
        guiMainGm = Config.GUI_MAIN_GM.getItem();
        guiMainAlert = Config.GUI_MAIN_ALERT.getItem();

        guiTpTitle = Util.translateColors(Config.GUI_TP_TITLE.get(String.class));
        guiTpFormat = Config.GUI_TP_FORMAT.getItem();

        guiGmTitle = Util.translateColors(Config.GUI_GM_TITLE.get(String.class));
        guiGmSurvival = Config.GUI_GM_SURVIVAL.getItem();
        guiGmCreative = Config.GUI_GM_CREATIVE.getItem();
        guiGmSpectator = Config.GUI_GM_SPECTATOR.getItem();
    }

    public String getMsgVanishOn() {
        return msgVanishOn;
    }

    public String getMsgVanishOff() {
        return msgVanishOff;
    }

    public String getMsgAlertPrompt() {
        return msgAlertPrompt;
    }

    public String getMsgAlertExpiry() {
        return msgAlertExpiry;
    }

    public String getMsgAlertFormat() {
        return msgAlertFormat;
    }

    public String getMsgTeleported() {
        return msgTeleported;
    }

    public String getMsgGmChange() {
        return msgGmChange;
    }

    public String getGuiMainTitle() {
        return guiMainTitle;
    }

    public ItemStack getGuiMainVanish() {
        return guiMainVanish;
    }

    public ItemStack getGuiMainRtp() {
        return guiMainRtp;
    }

    public ItemStack getGuiMainTp() {
        return guiMainTp;
    }

    public ItemStack getGuiMainGm() {
        return guiMainGm;
    }

    public ItemStack getGuiMainAlert() {
        return guiMainAlert;
    }

    public String getGuiTpTitle() {
        return guiTpTitle;
    }

    public ItemStack getGuiTpFormat() {
        return guiTpFormat;
    }

    public String getGuiGmTitle() {
        return guiGmTitle;
    }

    public ItemStack getGuiGmSurvival() {
        return guiGmSurvival;
    }

    public ItemStack getGuiGmCreative() {
        return guiGmCreative;
    }

    public ItemStack getGuiGmSpectator() {
        return guiGmSpectator;
    }

}
