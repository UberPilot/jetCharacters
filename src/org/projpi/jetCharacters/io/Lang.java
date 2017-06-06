package org.projpi.jetCharacters.io;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

/**
 * Description here.
 *
 * @author UberPilot
 */
public enum Lang {
    PLUGIN_PREFIX("plugin-prefix", "&8[&2Characters&8] &7&o"),
    CONSOLE_NOT_ALLOWED("console-not-allowed", "That command cannot be used from console!"),
    INVALID_ARGS("invalid-args", "&cImproper usage. The correct usage is %usage%"),
    NO_NODE("no-node", "&cYou can't set that on your character."),
    NO_PERMISSION("no-permission", "&cYou don't have permission to use that."),
    CARD_HEADER("card.header", "&7\u250C----[ &2%username%'s Character Card &7]----\u2510"),
    CARD_FOOTER("card.footer", "&7\u2514----[ &2%username%'s Character Card &7]----\u2518"),
    SET_HELP("help.set", "&a%usage% &f- &7&osets your character's %node%"),
    SET_ADMIN_HELP("help.set-admin", "&a%usage% &f- &7&osets [player]'s %node%"),
    PLAYER_NOT_FOUND("player-not-found", "&cPlayer not found."),
    CONSOLE_NO_CHARACTER("console-no-character", "Console is not allowed to have a character. Sorry console!"),
    BASE_COMMAND("help.base-command", "/char"),
    HELP_HEADER("help.header", "&ajetCharacters Help"),
    HELP_DESC("help.help-desc", "View jetCharacters Help"),
    VIEW_DESC("help.view-desc", "View your card, or <player>'s card."),
    SET_DESC("help.set-desc", "Set a value on your card. Do '%base% set' for more help."),
    ADMINSET_DESC("help.adminset-desc", "Set a value on [player]'s card. Do '%base% adminset' for more help."),
    COMMAND("help.command", "&a%cmd% &f- &7&o%desc%"),
    ADMINCLEAR_DESC("help.adminclear-desc", "Clears a component on [player]'s card."),
    CLEAR_DESC("help.clear-desc", "Clears a component on your card."),
    ADMINSET_SUCCESS("set-admin-success", "Successfully set %player%'s %node% to %value%"),
    SET_SUCCESS("set-success", "Successfully set your %node% to %value%"),
    CLEAR_SUCCESS("clear-success", "Successfully cleared your %node%"),
    ADMIN_CLEAR_SUCCESS("clear-admin-success", "Successfully cleared %player%'s %node%"),
    COMMAND_NOT_FOUND("command-not-found", "&cCommand not found.");


    private String path;
    private String def;
    private static YamlConfiguration LANG;

    /**
     * Lang enum constructor.
     * @param path The string path.
     * @param def The default string.
     */
    Lang(String path, String def) {
        this.path = path;
        this.def = def;
    }

    /**
     * Set the {@code YamlConfiguration} to use.
     * @param config The config to set.
     */
    public static void setFile(YamlConfiguration config) {
        LANG = config;
    }

    @Override
    public String toString() {
        return ChatColor.translateAlternateColorCodes('&', LANG.getString(this.path, def));
    }

    /**
     * Get the default value of the path.
     * @return The default value of the path.
     */
    public String getDefault() {
        return this.def;
    }

    /**
     * Get the path to the string.
     * @return The path to the string.
     */
    public String getPath() {
        return this.path;
    }
}
