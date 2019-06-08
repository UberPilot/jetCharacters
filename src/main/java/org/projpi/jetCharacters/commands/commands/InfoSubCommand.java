package org.projpi.jetCharacters.commands.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.commands.SimpleCommandFunction;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class InfoSubCommand implements SimpleCommandFunction
{
    private JetCharacters instance;

    public InfoSubCommand(JetCharacters instance)
    {
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender s, String[] args)
    {
        s.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "jet" + ChatColor.DARK_GREEN + "Characters"
                + ChatColor.GRAY + "" + ChatColor.ITALIC + " by UberPilot");
        s.sendMessage(ChatColor.WHITE + "  \u2192 " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "A jetSuite Plugin.");
        s.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.GREEN + instance.getDescription().getVersion());
        s.sendMessage(ChatColor.GRAY + "Author(s): " + ChatColor.GREEN + instance.getDescription().getAuthors());
        s.sendMessage(ChatColor.GRAY + "Website: " + ChatColor.GREEN + instance.getDescription().getWebsite());
    }
}
