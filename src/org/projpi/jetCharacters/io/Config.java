package org.projpi.jetCharacters.io;

import org.bukkit.ChatColor;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.characters.CardNode;

import java.util.LinkedHashMap;
import java.util.LinkedList;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class Config
{

    private JetCharacters instance;

    public Config(JetCharacters instance)
    {
        this.instance = instance;
    }

    //Card Nodes
    private LinkedHashMap<String, CardNode> nodes;
    //Components
    private boolean rightClick;
    private boolean offline;
    private boolean splitPerms;
    private boolean longMessages;
    private int longMessageLength;

    public void load()
    {
        nodes = new LinkedHashMap<>();
        //Nodes
        for(String key : instance.getConfig().getConfigurationSection("nodes").getKeys(false))
        {
            nodes.put(key, new CardNode(key));
        }

        //Components
        rightClick = instance.getConfig().getBoolean("components.rightclick");
        offline = instance.getConfig().getBoolean("components.offline");
        splitPerms = instance.getConfig().getBoolean("components.split-perms");
        longMessages = instance.getConfig().getBoolean("components.long-messages");
        longMessageLength = instance.getConfig().getInt("components.long-message-length");
    }

    public LinkedHashMap<String, CardNode> getNodes()
    {
        return nodes;
    }

    public boolean getRightClick()
    {
        return rightClick;
    }

    public boolean getOffline()
    {
        return offline;
    }

    public boolean getSplitPerms()
    {
        return splitPerms;
    }

    public boolean getLongMessages()
    {
        return splitPerms;
    }

    public int getLongMessageLength()
    {
        return longMessageLength;
    }
}
