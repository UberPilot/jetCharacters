package org.projpi.jetCharacters.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.projpi.jetCharacters.JetCharacters;

public class CommandListener implements Listener {

    private final JetCharacters instance;

    public CommandListener(JetCharacters instance) {
        this.instance = instance;
    }


    @EventHandler(priority = EventPriority.HIGHEST)
    public void  onCommand(PlayerCommandPreprocessEvent event) {
        if(event.getMessage().endsWith("&") && event.getPlayer().hasPermission("JetCharacters.longmessage"))
        {
            instance.setLongCommand(event.getPlayer().getUniqueId(),
                (instance.longCommandContains(event.getPlayer().getUniqueId()) ?
                    instance.getLongCommand(event.getPlayer().getUniqueId()) + event.getMessage().substring(1, event.getMessage().length()-1) : event.getMessage().substring(1, event.getMessage().length()-1)));
            if(instance.getLongCommand(event.getPlayer().getUniqueId()).length() + event.getMessage().length() >= instance.getConfiguration().getLongMessageLength())
            {
                event.setMessage(instance.getLongCommand(event.getPlayer().getUniqueId()) + event.getMessage());
            }
            else
            {
                event.setCancelled(true);
            }
        }
    }
}
