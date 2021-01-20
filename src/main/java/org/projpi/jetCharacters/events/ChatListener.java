package org.projpi.jetCharacters.events;

import org.bukkit.Bukkit;
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

    @org.bukkit.event.EventHandler(priority = EventPriority.MONITOR)
    private void onChatEvent(AsyncPlayerChatEvent event)
    {
        if (!event.getPlayer().hasPermission("JetCharacters.longmessage")) {
            return;
        }
        if(event.getMessage().endsWith("&"))
        {
            if (!instance.longMessageContains(event.getPlayer().getUniqueId())
                && !instance.longCommandContains(event.getPlayer().getUniqueId())) {
                 instance.setLongMessage(event.getPlayer().getUniqueId(), event.getMessage().substring(0, event.getMessage().length()-1));
            }
            else if(instance.longMessageContains(event.getPlayer().getUniqueId())) {
                instance.setLongMessage(event.getPlayer().getUniqueId(),
                    instance.getLongMessage(event.getPlayer().getUniqueId())
                        + event.getMessage().substring(0, event.getMessage().length()-1));
                if(instance.getLongMessage(event.getPlayer().getUniqueId()).length() + event.getMessage().length() >= instance.getConfiguration().getLongMessageLength())
                {
                    event.setMessage(instance.getLongMessage(event.getPlayer().getUniqueId()) + event.getMessage());
                }
            }
            else {
                instance.setLongCommand(event.getPlayer().getUniqueId(),
                    instance.getLongCommand(event.getPlayer().getUniqueId())
                        + event.getMessage().substring(0, event.getMessage().length()-1));
            }
            event.setCancelled(true);
        }
        else if(instance.longMessageContains(event.getPlayer().getUniqueId()))
        {
            event.setMessage(instance.getLongMessage(event.getPlayer().getUniqueId()) + event.getMessage());
            instance.doneLongMessage(event.getPlayer().getUniqueId());
        }
        else if(instance.longCommandContains(event.getPlayer().getUniqueId()))
        {
            Bukkit.getScheduler().runTask(instance, () -> {
                Bukkit.dispatchCommand(event.getPlayer(), instance.getLongCommand(event.getPlayer().getUniqueId()) + event.getMessage());
                instance.doneLongCommand(event.getPlayer().getUniqueId());
            });
        }
    }

}
