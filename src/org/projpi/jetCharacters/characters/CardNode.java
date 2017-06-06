package org.projpi.jetCharacters.characters;

import org.projpi.jetCharacters.JetCharacters;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class CardNode
{
    private String name;
    private String format;

    public CardNode(String name)
    {
        this.name = name;
        String path = "nodes." + name;
        format = JetCharacters.getInstance().getConfig().getString(path + ".format");
    }

    public String getFormat()
    {
        return format;
    }

    public String getName()
    {
        return name;
    }
}
