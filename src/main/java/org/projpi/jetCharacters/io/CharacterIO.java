package org.projpi.jetCharacters.io;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.characters.CardNode;
import org.projpi.jetCharacters.characters.JetCharacter;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class CharacterIO
{
    public CharacterIO(JetCharacters instance)
    {
        this.instance = instance;
    }

    private JetCharacters instance;
    
    public JetCharacter load(Player p)
    {
        instance.getLogger().info("Loading Character for " + p.getName());
        File charFile = new File(instance.getPlayerDataFolder(), p.getUniqueId().toString() + ".yml");
        if(charFile.exists())
        {
            return loadFile(charFile, p.getName());
        }
        else
        {
            return new JetCharacter(p.getName());
        }
    }

    public JetCharacter loadOffline(UUID uuid)
    {
        File charFile = new File(instance.getPlayerDataFolder(), uuid.toString() + ".yml");
        return (charFile.exists()) ? loadFile(charFile, null) : null;
    }

    public void save(Player p)
    {
        instance.getLogger().info("Saving Character for " + p.getName());
        JetCharacter c = JetCharacters.getInstance().getCharacters().get(p.getUniqueId());
        File charFile = new File(instance.getPlayerDataFolder(), p.getUniqueId().toString() + ".yml");
        if(!charFile.exists())
        {
            try
            {
                charFile.getParentFile().mkdirs();
                charFile.createNewFile();
                FileConfiguration data = YamlConfiguration.loadConfiguration(charFile);
                data.set("last-username", p.getName());
                for(Map.Entry<String, CardNode> entry : JetCharacters.getInstance().getNodes().entrySet())
                {
                    data.set("card." + entry.getKey(), c.get(entry.getKey()));
                }
                data.save(charFile);
            }
            catch (IOException e)
            {
                e.printStackTrace();
            }
        }
        else
        {
            FileConfiguration data = YamlConfiguration.loadConfiguration(charFile);
            data.set("last-username", p.getName());
            for(Map.Entry<String, CardNode> entry : JetCharacters.getInstance().getNodes().entrySet())
            {
                data.set("card." + entry.getKey(), c.get(entry.getKey()));
            }
            try
            {
                data.save(charFile);
            } catch (IOException e)
            {
                e.printStackTrace();
            }


        }
    }

    private JetCharacter loadFile(File f, String username)
    {
        FileConfiguration data = YamlConfiguration.loadConfiguration(f);
        LinkedHashMap<String, String> nodes = new LinkedHashMap<>();
        for(Map.Entry<String, CardNode> entry : JetCharacters.getInstance().getNodes().entrySet())
        {
            nodes.put(entry.getKey(), data.getString("card." + entry.getKey()));
        }
        if(username == null) username = data.getString("last-username");
        return new JetCharacter(nodes, username);
    }
}
