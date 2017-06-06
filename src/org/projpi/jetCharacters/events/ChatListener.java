package org.projpi.jetCharacters.events;

import org.bukkit.event.*;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.projpi.jetCharacters.JetCharacters;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class ChatListener implements Listener
{
    JetCharacters instance;

    public ChatListener(JetCharacters instance)
    {
        this.instance = instance;
    }

    @org.bukkit.event.EventHandler(priority = EventPriority.HIGHEST)
    private void onChatEvent(AsyncPlayerChatEvent event)
    {
        if(event.getMessage().endsWith("&") && event.getPlayer().hasPermission("JetCharacters.longmessage"))
        {
            instance.setLongMessage(event.getPlayer().getUniqueId(),
                    (instance.longMessageContains(event.getPlayer().getUniqueId()) ?
                            instance.getLongMessage(event.getPlayer().getUniqueId()) + event.getMessage().substring(0, event.getMessage().length()-1) : event.getMessage().substring(0, event.getMessage().length()-1)));
            if(instance.getLongMessage(event.getPlayer().getUniqueId()).length() + event.getMessage().length() >= instance.getConfiguration().getLongMessageLength())
            {
                event.setMessage(instance.getLongMessage(event.getPlayer().getUniqueId()) + event.getMessage());
            }
            else
            {
                event.setCancelled(true);
            }
        }
        else if(instance.longMessageContains(event.getPlayer().getUniqueId()))
        {
            event.setMessage(instance.getLongMessage(event.getPlayer().getUniqueId()) + event.getMessage());
            instance.doneLongMessage(event.getPlayer().getUniqueId());
        }
    }

}
