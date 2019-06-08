package org.projpi.jetCharacters.characters;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class CharacterNode
{
    private boolean enabled;
    private String format;

    public CharacterNode(boolean enabled, String format)
    {
        this.enabled = enabled;
        this.format = format;
    }

    public boolean isEnabled()
    {
        return enabled;
    }

    public String getFormat()
    {
        return format;
    }
}
