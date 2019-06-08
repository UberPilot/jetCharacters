package org.projpi.jetCharacters.commands.commands;

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
public class ClearSubCommand implements SimpleCommandFunction
{
    private JetCharacters instance;

    public ClearSubCommand(JetCharacters instance)
    {
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender s, String[] args)
    {
        if(instance.getNodes().containsKey(args[0].toLowerCase()))
        {
            instance.getCharacter((Player) s).remove(args[0].toLowerCase());
            s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.CLEAR_SUCCESS.toString()
                    .replaceAll("%node%", args[0].toLowerCase()));
        }
        else
        {
            s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.NO_NODE.toString());
        }
    }
}
