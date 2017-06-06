package org.projpi.jetCharacters.events;

import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.projpi.jetCharacters.JetCharacters;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class EventHandler implements Listener
{
    JetCharacters instance;

    public EventHandler(JetCharacters instance)
    {
        this.instance = instance;
    }

    @org.bukkit.event.EventHandler
    private void onJoin(PlayerJoinEvent event)
    {
        instance.getLogger().info("Got join event for " + event.getPlayer().getName());
        instance.loadCharacter(event.getPlayer());
    }

    @org.bukkit.event.EventHandler
    private void onQuit(PlayerQuitEvent event)
    {
        instance.getLogger().info("Got quit event for " + event.getPlayer().getName());
        instance.saveAndRemoveCharacter(event.getPlayer());
    }
}
