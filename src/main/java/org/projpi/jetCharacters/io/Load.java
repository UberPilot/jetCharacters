package org.projpi.jetCharacters.io;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.projpi.jetCharacters.JetCharacters;

import java.io.*;
import java.util.ArrayList;
import java.util.logging.Level;

/**
 * Manages loading configuration files and extracting default configurations.
 *
 * @author UberPilot
 */
public class Load
{
    private JetCharacters instance;

    public Load(JetCharacters instance)
    {
        this.instance = instance;
    }

    public boolean load()
    {
        extract(instance);
        return loadLang();
    }

    public boolean loadLang() {
        File lang = new File(instance.getDataFolder(), "lang.yml");
        if (!lang.exists()) {
            try {
                instance.getDataFolder().mkdir();
                lang.createNewFile();
                InputStream defConfigStream = instance.getResource("lang.yml");
                if (defConfigStream != null) {
                    YamlConfiguration defConfig = YamlConfiguration.loadConfiguration(new InputStreamReader(defConfigStream));
                    defConfig.save(lang);
                    Lang.setFile(defConfig);
                }
            } catch(IOException e) {
                e.printStackTrace(); // So they notice
                instance.getLogger().severe("Couldn't create language file.");
                instance.getLogger().severe("This is a fatal error. Now disabling.");
                instance.getLogger().severe("Report this stack trace to the issue tracker");
                instance.getLogger().severe("https://github.com/UberPilot/JetCharacters/issues");
                instance.getLogger().severe("Or the discord for assistance");
                instance.getLogger().severe("https://discord.gg/zUbNX9t");
                return false;
            }
        }
        YamlConfiguration conf = YamlConfiguration.loadConfiguration(lang);
        for(Lang item:Lang.values()) {
            if (conf.getString(item.getPath()) == null) {
                conf.set(item.getPath(), item.getDefault());
            }
        }
        Lang.setFile(conf);
        instance.setLang(conf);
        instance.setLangFile(lang);
        try {
            conf.save(instance.getLangFile());
        } catch(IOException e) {
            instance.getLogger().severe("Failed to save lang.yml.");
            instance.getLogger().severe("Report this stack trace to the issue tracker");
            instance.getLogger().severe("https://github.com/UberPilot/JetCharacters/issues");
            instance.getLogger().severe("Or the discord for assistance");
            instance.getLogger().severe("https://discord.gg/zUbNX9t");
            e.printStackTrace();
        }
        return true;
    }



    public boolean extract(JavaPlugin instance) {
        ArrayList<InternalFile> files = new ArrayList<>();
        files.add(new InternalFile("config", "config.yml", new File(instance.getDataFolder(), "config.yml")));
        files.add(new InternalFile("language", "lang.yml", new File(instance.getDataFolder(), "lang.yml")));
        if (!instance.getDataFolder().exists()) {
            instance.getDataFolder().mkdirs();
            FileOutputStream fos = null;

            for(InternalFile file : files)
            {
                File pointer = file.getFile();
                if(!file.getFile().exists())
                {
                    InputStream is = Load.class.getResourceAsStream("/" + file.getFileName());
                    try
                    {
                        fos = new FileOutputStream(pointer);
                        byte[] buf = new byte[256];
                        int i;
                        while ((i = is.read(buf)) != -1)
                        {
                            fos.write(buf, 0, i);
                        }
                    } catch (Exception e)
                    {
                        instance.getServer().getLogger().log(Level.SEVERE, "Failed to load default " + file.getName()
                                + " from jarfile", e);
                    } finally
                    {
                        try
                        {
                            if (is != null)
                            {
                                is.close();
                            }
                            if (fos != null)
                            {
                                fos.close();
                            }
                        }
                        catch (Exception e)
                        {
                            e.printStackTrace();
                        }
                    }
                }

            }
        }
        return true;
    }
}
