package org.projpi.jetCharacters.commands.commands;

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
public class SetSubCommand implements SimpleCommandFunction
{
    private JetCharacters instance;
    
    public SetSubCommand(JetCharacters instance)
    {
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender s, String[] args)
    {
        if(instance.getNodes().containsKey(args[0].toLowerCase()))
        {
            //Get the node. Used to test length and get names.
            CardNode node = instance.getNodes().get(args[0].toLowerCase());
            StringBuilder builder = new StringBuilder(args[1]);
            //Build a string out of the remaining arguments.
            if(args.length > 2)
            {
                for (String arg : Arrays.copyOfRange(args, 2, args.length))
                {
                    builder.append(" ").append(arg);
                }
            }
            String value = builder.toString();
            //Test if the value fits in the length.
            if(value.length() <= node.getLimit())
            {
                instance.getCharacter((Player) s).set(args[0].toLowerCase(), value);
                s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.SET_SUCCESS.toString()
                        .replaceAll("%node%", node.getName())
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
