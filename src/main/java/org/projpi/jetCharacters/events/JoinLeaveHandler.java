package org.projpi.jetCharacters.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.projpi.jetCharacters.JetCharacters;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class JoinLeaveHandler implements Listener
{
    private final JetCharacters instance;

    public JoinLeaveHandler(JetCharacters instance)
    {
        this.instance = instance;
    }

    @EventHandler
    private void onJoin(PlayerJoinEvent event)
    {
        instance.getLogger().info("Got join event for " + event.getPlayer().getName());
        instance.loadCharacter(event.getPlayer());
    }

    @EventHandler
    private void onQuit(PlayerQuitEvent event)
    {
        instance.getLogger().info("Got quit event for " + event.getPlayer().getName());
        instance.saveAndRemoveCharacter(event.getPlayer());
    }
}
