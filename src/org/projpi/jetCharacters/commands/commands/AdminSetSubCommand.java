package org.projpi.jetCharacters.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.characters.CardNode;
import org.projpi.jetCharacters.commands.SimpleCommandFunction;
import org.projpi.jetCharacters.io.Lang;

import java.util.Arrays;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class AdminSetSubCommand implements SimpleCommandFunction
{
    private JetCharacters instance;

    public AdminSetSubCommand(JetCharacters instance)
    {
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender s, String[] args)
    {
        Player target = Bukkit.getPlayer(args[0]);
        if(instance.getNodes().containsKey(args[1].toLowerCase()))
        {
            CardNode node = instance.getNodes().get(args[1].toLowerCase());
            StringBuilder builder = new StringBuilder(args[2]);
            if(args.length > 3)
            {
                for (String arg : Arrays.copyOfRange(args, 3, args.length))
                {
                    builder.append(" ").append(arg);
                }
            }
            String value = builder.toString();
            if(value.length() <= node.getLimit())
            {
            instance.getCharacter(target).set(args[1].toLowerCase(), value);
            s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.ADMINSET_SUCCESS.toString()
                    .replaceAll("%player%", target.getName())
                    .replaceAll("%node%", args[1].toLowerCase())
                    .replaceAll("%value%", value));
            }
            else
            {
                s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.OVER_LIMIT.toString()
                        .replaceAll("%node%", node.getName())
                        .replaceAll("%chars%", String.valueOf(node.getLimit())));
            }
        }
        else
        {
            s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.NO_NODE.toString());
        }
    }
}
