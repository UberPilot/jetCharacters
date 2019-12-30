package org.projpi.jetCharacters.characters;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.io.Lang;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class JetCharacter
{
    String username;
    LinkedHashMap<String, String> nodes;

    public JetCharacter(LinkedHashMap<String, String> nodes, String username)
    {
        this.username = username;
        this.nodes = nodes;
    }

    public JetCharacter(String username)
    {
        this.username = username;
        this.nodes = new LinkedHashMap<>();
    }

    public String get(String key)
    {
        return nodes.get(key);
    }

    public void set(String key, String value)
    {
        if(JetCharacters.getInstance().getNodes().containsKey(key))
        {
            JetCharacters.getInstance().getLogger().info("Putting " + key + "::" + value + " into " + username + "'s character.");
            nodes.put(key, value);
        }
        else
        {
            JetCharacters.getInstance().getLogger().info("Attempted putting " + key + "::" + value + " into " + username + "'s character, using an invalid key.");
        }
    }

    public void remove(String key)
    {
        nodes.remove(key);
    }

    public Map<String, String> getNodes()
    {
        return Collections.unmodifiableMap(nodes);
    }

    public JetCharacter copy()
    {
        return new JetCharacter(nodes, username);
    }

    public void show(CommandSender viewer)
    {
        if(!Lang.CARD_HEADER.toString().equals(""))
        {
            String header = Lang.CARD_HEADER.toString().replaceAll("%username%", username);
            for (Map.Entry<String, CardNode> entry : JetCharacters.getInstance().getNodes().entrySet())
            {
                if (this.get(entry.getKey()) != null && this.nodes.containsKey(entry.getKey()))
                {
                    header = header.replaceAll("%" + entry.getKey() + "%", this.get(entry.getKey()));
                }
            }
            viewer.sendMessage(header);
        }
        for(Map.Entry<String, CardNode> entry : JetCharacters.getInstance().getNodes().entrySet())
        {
            for(String s : entry.getValue().getFormat().split("\n"))
            {
                if(this.get(entry.getKey()) != null && this.nodes.containsKey(entry.getKey()))
                {
                    viewer.sendMessage(ChatColor.translateAlternateColorCodes('&', s.replaceAll("%" + entry.getKey() + "%", this.get(entry.getKey()))));
                }
            }
        }
        if(!Lang.CARD_FOOTER.toString().equals(""))
        {
            String footer = Lang.CARD_FOOTER.toString().replaceAll("%username%", username);
            for (Map.Entry<String, CardNode> entry : JetCharacters.getInstance().getNodes().entrySet())
            {
                footer = footer.replaceAll("%" + entry.getKey() + "%", this.get(entry.getKey()));
            }
            viewer.sendMessage(footer);
        }
    }
}
