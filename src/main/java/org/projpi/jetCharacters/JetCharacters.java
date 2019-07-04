package org.projpi.jetCharacters;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import org.projpi.jetCharacters.characters.CardNode;
import org.projpi.jetCharacters.characters.JetCharacter;
import org.projpi.jetCharacters.commands.Commander;
import org.projpi.jetCharacters.events.ChatListener;
import org.projpi.jetCharacters.events.JoinLeaveHandler;
import org.projpi.jetCharacters.events.RightClickEvent;
import org.projpi.jetCharacters.io.CharacterIO;
import org.projpi.jetCharacters.io.Config;
import org.projpi.jetCharacters.io.Load;
import org.projpi.jetCharacters.papi.Placeholders;

import java.io.File;
import java.util.*;

/**
 * Description here.
 *
 * @author UberPilot
 */
public class JetCharacters extends JavaPlugin implements JetCharactersAPI
{
    private Map<UUID, JetCharacter> characters;
    private List<UUID> searching;
    private Map<UUID, String> longMessage;
    private Config config;
    private Load load;
    private CharacterIO cio;
    private Map<String, CardNode> nodes;
    private File langFile;
    private File playerDataFolder;
    private YamlConfiguration lang;

    public File getLangFile()
    {
        return langFile;
    }

    public YamlConfiguration getLang()
    {
        return lang;
    }

    public File getPlayerDataFolder()
    {
        return new File(this.getDataFolder() + File.separator + "data" + File.separator);
    }

    public Config getConfiguration()
    {
        return config;
    }

    @Override
    public void onDisable()
    {
        for(Player p : Bukkit.getOnlinePlayers())
        {
            cio.save(p);
        }
    }

    public void onEnable()
    {
        getPlayerDataFolder().mkdirs();
        cio = new CharacterIO(this);
        load = new Load(this);
        load.load();
        config = new Config(this);
        config.load();
        nodes = config.getNodes();
        characters = new LinkedHashMap<>();
        searching = new LinkedList<>();
        longMessage = new LinkedHashMap<>();

        if(Bukkit.getPluginManager().isPluginEnabled("PlaceholderAPI"))
        {
            new Placeholders(this).register();
            getLogger().info("Placeholders hooked to PlaceholderAPI.");
        }

        getServer().getPluginManager().registerEvents(new JoinLeaveHandler(this), this);
        if(config.getRightClick()) getServer().getPluginManager().registerEvents(new RightClickEvent(), this);
        if(config.getLongMessages()) getServer().getPluginManager().registerEvents(new ChatListener(this), this);

        getCommand("jetcharacter").setExecutor(new Commander(this));

        Metrics metrics = new Metrics(this);

        for(Player p : Bukkit.getOnlinePlayers())
        {
            characters.put(p.getUniqueId(), cio.load(p));
        }
    }

    public void loadCharacter(Player player)
    {
        JetCharacter c = cio.load(player);
//        getLogger().info("Loaded Character with name " + c.get("name"));
        characters.put(player.getUniqueId(), c);
    }

    public void saveAndRemoveCharacter(Player player)
    {
        cio.save(player);
        characters.remove(player.getUniqueId());
    }

    public static JetCharacters getInstance()
    {
        return JavaPlugin.getPlugin(JetCharacters.class);
    }

    public void setLangFile(File langFile)
    {
        this.langFile = langFile;
    }

    public void setLang(YamlConfiguration lang)
    {
        this.lang = lang;
    }

    public List<UUID> getSearching()
    {
        return Collections.unmodifiableList(searching);
    }

    public void setSearching(UUID uuid)
    {
        searching.add(uuid);
    }

    public void doneSearching(UUID uuid)
    {
        searching.remove(uuid);
    }

    public void setLongMessage(UUID uuid, String message)
    {
        longMessage.put(uuid, message);
    }

    public String getLongMessage(UUID uuid)
    {
        return longMessage.get(uuid);
    }

    public boolean longMessageContains(UUID uuid)
    {
        return longMessage.containsKey(uuid);
    }

    public void doneLongMessage(UUID uuid)
    {
        longMessage.remove(uuid);
    }

    //API Methods
    public Map<UUID, JetCharacter> getCharacters()
    {
        return Collections.unmodifiableMap(characters);
    }

    public Map<String, CardNode> getNodes()
    {
        return Collections.unmodifiableMap(nodes);
    }

    @Override
    public boolean setNode(Player player, String key, String value)
    {
        if(characters.get(player.getUniqueId()).getNodes().containsKey(key))
        {
            characters.get(player.getUniqueId()).set(key, value);
            return true;
        }
        else
        {
            return false;
        }
    }

    @Override
    public boolean addNode(CardNode node)
    {
        if(!nodes.containsKey("node"))
        {
            nodes.put(node.getName(), node);
            return true;
        }
        return false;
    }

    @Override
    public JetCharacter getOfflineCharacter(UUID uuid)
    {
        return null;
    }

    @Override
    public JetCharacter getCharacter(Player player)
    {
        return characters.get(player.getUniqueId());
    }

}