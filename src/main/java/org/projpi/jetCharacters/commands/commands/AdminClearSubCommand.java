package org.projpi.jetCharacters.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.commands.SimpleCommandFunction;
import org.projpi.jetCharacters.io.Lang;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class AdminClearSubCommand implements SimpleCommandFunction
{
    private JetCharacters instance;

    public AdminClearSubCommand(JetCharacters instance)
    {
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender s, String[] args)
    {
        Player target = Bukkit.getPlayer(args[0]);
        if(instance.getNodes().containsKey(args[1].toLowerCase()))
        {
            instance.getCharacter(target).remove(args[1].toLowerCase());
            s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.ADMIN_CLEAR_SUCCESS.toString()
                    .replaceAll("%player%", target.getName())
                    .replaceAll("%node%", args[1].toLowerCase()));
        }
        else
        {
            s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.NO_NODE.toString());
        }
    }
}
