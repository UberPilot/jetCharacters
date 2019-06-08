package org.projpi.jetCharacters.events;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.projpi.jetCharacters.JetCharacters;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class RightClickEvent implements Listener
{
    @org.bukkit.event.EventHandler
    private void onEvent(PlayerInteractEntityEvent event)
    {
        if(event.getRightClicked() instanceof Player)
        {
            if(Bukkit.getOnlinePlayers().contains(event.getRightClicked()))
            {
                if (event.getPlayer().hasPermission("jetCharacters.view.click"))
                {
                    if (JetCharacters.getInstance().getCharacters().containsKey(event.getRightClicked().getUniqueId()))
                    {
                        JetCharacters.getInstance().getCharacter((Player) event.getRightClicked()).show(event.getPlayer());
                    }
                }
            }
        }
    }
}
