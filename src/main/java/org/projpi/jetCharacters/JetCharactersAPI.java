package org.projpi.jetCharacters;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.projpi.jetCharacters.characters.CardNode;
import org.projpi.jetCharacters.characters.JetCharacter;

import java.util.Map;
import java.util.UUID;

/**
 * These are API-safe methods that can be used to interact with the plugin, properly. Any other methods are volatile, and may change at any time.
 * @since 1.0
 * @author UberPilot
 */
public interface JetCharactersAPI
{
    /**
     * Used to set a node on a character.
     * @param player The player that the character is linked to.
     * @param key The key of the node.
     * @param value The new value of the node.
     * @return Returns true if successful, false if that node doesn't exist.
     */
    boolean setNode(Player player, String key, String value);

    /**
     * Used to add a node to character sheets.
     * @param node The new node.
     * @return Returns true if added, false if it already exists.
     */
    boolean addNode(CardNode node);

    /**
     * @return An unmodifiable copy of all of the currently active nodes.
     */
    Map<String, CardNode> getNodes();

    /**
     * @return An unmodifiable copy of all of the characters.
     */
    Map<UUID, JetCharacter> getCharacters();

    /**
     * Used to get the character of an offline player. <br>More resource intensive than finding an online character.
     * @param uuid The UUID of the player to be retrieved.
     * @return The character of the player, or null if it's not found.
     */
    JetCharacter getOfflineCharacter(UUID uuid);

    /**
     * Used to get the character of an online player. <br>Note that any changes done to this character do not apply to the linked character.
     * @param player The player whose character should be retrieved.
     * @return The character of the player, or a blank character if one hasn't been made.
     */
    JetCharacter getCharacter(Player player);


    PluginDescriptionFile getDescription();
}
