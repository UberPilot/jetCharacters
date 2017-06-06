package org.projpi.jetCharacters.papi;

import me.clip.placeholderapi.PlaceholderHook;
import me.clip.placeholderapi.external.EZPlaceholderHook;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.characters.JetCharacter;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class Placeholders extends EZPlaceholderHook
{
    private JetCharacters instance;

    public Placeholders(JetCharacters instance)
    {
        super(instance, "jetCharacters");

        this.instance = instance;
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier)
    {
        if(player == null)
        {
            return "";
        }
        if(instance.getNodes().containsKey(identifier))
        {
            return instance.getCharacter(player).get(identifier);
        }
        else
        {
            return null;
        }
    }
}