package org.projpi.jetCharacters.commands.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.commands.SimpleCommandFunction;
import org.projpi.jetCharacters.io.Lang;

import java.io.File;
import java.util.UUID;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class ShowSubCommand implements SimpleCommandFunction
{
    private JetCharacters instance;

    public ShowSubCommand(JetCharacters instance)
    {
        this.instance = instance;
    }

    @Override
    public void execute(CommandSender s, String[] args)
    {
        if(args.length < 1)
        {
            if(s instanceof Player)
            {
                JetCharacters.getInstance().getCharacter((Player) s).show(s);
            }
            else
            {
                s.sendMessage(Lang.CONSOLE_NO_CHARACTER.toString());
            }
        }
        else
        {
            //If the player is online.
            if(Bukkit.getPlayer(args[0]) != null)
            {
                JetCharacters.getInstance().getCharacter(Bukkit.getPlayer(args[0])).show(s);
            }
            else
            {
                s.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.PLAYER_NOT_FOUND.toString());
            }
        }
    }
}
