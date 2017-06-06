package org.projpi.jetCharacters.events;

import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
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
            if(event.getPlayer().hasPermission("jetCharacters.view.click"))
            JetCharacters.getInstance().getCharacter((Player) event.getRightClicked()).show(event.getPlayer());
        }
    }
}
