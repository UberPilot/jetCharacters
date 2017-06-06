package org.projpi.jetCharacters.commands;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.characters.CardNode;
import org.projpi.jetCharacters.io.Lang;

import java.io.File;
import java.util.Arrays;
import java.util.Map;
import java.util.UUID;

/**
 * Description here.
 * @author UberPilot
 */
public class Commander implements CommandExecutor
{
    private JetCharacters instance;

    public Commander(JetCharacters instance)
    {
        this.instance = instance;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
    {
        if(args.length < 1)
        {
            showHelp(sender, args);
        }
        else
        {
            //Setting Values on a Card
            if(args[0].equalsIgnoreCase("set")
                    || args[0].equalsIgnoreCase("s"))
            {
                if(args.length < 3)
                {
                    showSetHelp(sender, args, false);
                }
                else
                {
                    simpleSubCommand(sender, args, "JetCharacters.set" +
                            ((instance.getConfiguration().getSplitPerms()) ? "." + args[1].toLowerCase() : ""),
                            true, (sender1, args1) ->
                    {
                        if(instance.getNodes().containsKey(args1[0].toLowerCase()))
                        {
                            StringBuilder builder = new StringBuilder(args1[1]);
                            if(args1.length > 2)
                            {
                                for (String arg : Arrays.copyOfRange(args1, 2, args1.length))
                                {
                                    builder.append(" ").append(arg);
                                }
                            }
                            String value = builder.toString();
                            if(value.equalsIgnoreCase("-clear"))
                            {

                            }
                            else
                            {
                                instance.getCharacter((Player) sender1).set(args1[0].toLowerCase(), value);
                                sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.SET_SUCCESS.toString()
                                        .replaceAll("%node%", args1[0].toLowerCase())
                                        .replaceAll("%value%", value));
                            }
                        }
                        else
                        {
                            sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.NO_NODE.toString());
                        }
                    });
                }
            }
            //Checking/Viewing a card
            else if(args[0].equalsIgnoreCase("check")
                    || args[0].equalsIgnoreCase("c")
                    || args[0].equalsIgnoreCase("view")
                    || args[0].equalsIgnoreCase("v"))
            {
                showCharacter(sender, args);
            }
            else if(args[0].equalsIgnoreCase("adminset")
                    || args[0].equalsIgnoreCase("as"))
            {
                if(args.length < 4)
                {
                    showSetHelp(sender, args, true);
                }
                else
                {
                    simpleSubCommand(sender, args, "JetCharacters.admin.set",
                            true, (sender1, args1) ->
                    {
                        Player target = Bukkit.getPlayer(args1[0]);
                        if(instance.getNodes().containsKey(args1[1].toLowerCase()))
                        {
                            StringBuilder builder = new StringBuilder(args1[2]);
                            if(args1.length > 3)
                            {
                                for (String arg : Arrays.copyOfRange(args1, 3, args1.length))
                                {
                                    builder.append(" ").append(arg);
                                }
                            }
                            String value = builder.toString();

                                instance.getCharacter(target).set(args1[1].toLowerCase(), value);
                                sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.ADMINSET_SUCCESS.toString()
                                        .replaceAll("%player%", target.getName())
                                        .replaceAll("%node%", args1[1].toLowerCase())
                                        .replaceAll("%value%", value));

                        }
                        else
                        {
                            sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.NO_NODE.toString());
                        }
                    });
                }
            }
            else if(args[0].equalsIgnoreCase("clear"))
            {
                if(args.length < 2)
                {
                    showUsage(sender, args, "clear [component]");
                }
                else
                {
                    simpleSubCommand(sender, args, "JetCharacters.clear", false, (sender1, args1) ->
                    {
                        if(instance.getNodes().containsKey(args1[0].toLowerCase()))
                        {
                        instance.getCharacter((Player) sender1).remove(args1[0].toLowerCase());
                        sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.CLEAR_SUCCESS.toString()
                                .replaceAll("%node%", args1[0].toLowerCase()));
                        }
                        else
                        {
                            sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.NO_NODE.toString());
                        }
                    });
                }
            }
            else if(args[0].equalsIgnoreCase("adminclear") || args[0].equalsIgnoreCase("ac"))
            {
                if(args.length < 3)
                {
                    showUsage(sender, args, "adminclear [player] [component]");
                }
                else
                {
                    simpleSubCommand(sender, args, "JetCharacters.admin.clear", false, (sender1, args1) -> {

                        Player target = Bukkit.getPlayer(args1[0]);
                        if(instance.getNodes().containsKey(args1[1].toLowerCase()))
                        {
                            instance.getCharacter(target).remove(args1[1].toLowerCase());
                            sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.ADMIN_CLEAR_SUCCESS.toString()
                                    .replaceAll("%player%", target.getName())
                                    .replaceAll("%node%", args1[1].toLowerCase()));
                        }
                        else
                        {
                            sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.NO_NODE.toString());
                        }
                    });
                }
            }
            else if(args[0].equalsIgnoreCase("info")
                    || args[0].equalsIgnoreCase("i"))
            {
                simpleSubCommand(sender, args, "JetCharacters.info", false, (sender1, args1) ->
                {
                    sender1.sendMessage(ChatColor.GOLD + "" + ChatColor.ITALIC + "jet" + ChatColor.DARK_GREEN + "Characters"
                            + ChatColor.GRAY + "" + ChatColor.ITALIC + " by UberPilot");
                    sender1.sendMessage(ChatColor.WHITE + "  \u2192 " + ChatColor.GRAY.toString() + ChatColor.ITALIC + "A jetSuite Plugin.");
                    sender1.sendMessage(ChatColor.GRAY + "Version: " + ChatColor.GREEN + instance.getDescription().getVersion());
                    sender1.sendMessage(ChatColor.GRAY + "Author(s): " + ChatColor.GREEN + instance.getDescription().getAuthors());
                    sender1.sendMessage(ChatColor.GRAY + "Website: " + ChatColor.GREEN + instance.getDescription().getWebsite());
                });
            }
            else if(args[0].equalsIgnoreCase("reload"))
            {
                if(sender.hasPermission("JetCharacters.admin.reload"))
                {
                    instance.onDisable();
                    instance.onEnable();
                }
            }
            else
            {
                sender.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.COMMAND_NOT_FOUND.toString());
            }
        }
        return true;
    }

    private void simpleSubCommand(CommandSender sender, String[] args,
                                  String permission, boolean playerOnly, SimpleCommandFunction function)
    {
        if(playerOnly)
        {
            if(!(sender instanceof Player))
            {
                sender.sendMessage(Lang.CONSOLE_NOT_ALLOWED.toString());
                return;
            }
        }
        if(permission != null)
        {
            if(!sender.hasPermission(permission))
            {
                sender.sendMessage(Lang.PLUGIN_PREFIX.toString() +
                        Lang.NO_PERMISSION.toString().replaceAll("%permission%", permission));
                return;
            }
        }
        function.execute(sender, (args.length > 0) ? Arrays.copyOfRange(args, 1, args.length) : args);
    }

    private void showSetHelp(CommandSender sender, String[] args, boolean admin)
    {
        simpleSubCommand(sender, args, "JetCharacters.help", false, (sender1, args1) ->
        {
            if(admin)
            {
                sender.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.HELP_HEADER.toString());
                for(Map.Entry<String, CardNode> entry : instance.getNodes().entrySet())
                {
                    sender.sendMessage(Lang.SET_ADMIN_HELP.toString().replaceAll("%node%", entry.getKey()).replaceAll("%usage%", Lang.BASE_COMMAND.toString() + " adminset [player] " + entry.getKey()));
                }
            }
            else
            {
                sender.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.HELP_HEADER.toString());
                for(Map.Entry<String, CardNode> entry : instance.getNodes().entrySet())
                {
                    sender.sendMessage(Lang.SET_HELP.toString().replaceAll("%node%", entry.getKey()).replaceAll("%usage%", Lang.BASE_COMMAND.toString() + " set " + entry.getKey()));
                }
            }
        });
    }


    private void showUsage(CommandSender sender, String[] args, String usage)
    {
        simpleSubCommand(sender, args, "JetCharacters.help", false, (sender1, args1) ->
                sender.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.INVALID_ARGS.toString().replaceAll("%usage%", usage)));
    }

    private void showHelp(CommandSender sender, String[] args)
    {
        simpleSubCommand(sender, args, "JetCharacters.help", false, (sender1, args1) ->
        {
            sender.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.HELP_HEADER.toString());
            sender.sendMessage(Lang.COMMAND.toString()
                    .replaceAll("%cmd%", Lang.BASE_COMMAND + " view <player> ")
                    .replaceAll("%desc%", Lang.VIEW_DESC.toString())
                    .replaceAll("%base%", Lang.BASE_COMMAND.toString()));
            sender.sendMessage(Lang.COMMAND.toString()
                    .replaceAll("%cmd%", Lang.BASE_COMMAND + " clear [component] ")
                    .replaceAll("%desc%", Lang.CLEAR_DESC.toString())
                    .replaceAll("%base%", Lang.BASE_COMMAND.toString()));
            sender.sendMessage(Lang.COMMAND.toString()
                    .replaceAll("%cmd%", Lang.BASE_COMMAND + " set [component] [value]")
                    .replaceAll("%desc%", Lang.SET_DESC.toString())
                    .replaceAll("%base%", Lang.BASE_COMMAND.toString()));
            if(sender.hasPermission("Jetcharacers.admin.set"))
            {
                sender.sendMessage(Lang.COMMAND.toString()
                        .replaceAll("%cmd%", Lang.BASE_COMMAND + " adminset [player] [component] [value]")
                        .replaceAll("%desc%", Lang.ADMINSET_DESC.toString())
                        .replaceAll("%base%", Lang.BASE_COMMAND.toString()));
            }
            if(sender.hasPermission("Jetcharacers.admin.clear"))
            {
                sender.sendMessage(Lang.COMMAND.toString()
                        .replaceAll("%cmd%", Lang.BASE_COMMAND + " adminclear [player] [component]")
                        .replaceAll("%desc%", Lang.ADMINCLEAR_DESC.toString())
                        .replaceAll("%base%", Lang.BASE_COMMAND.toString()));
            }
        });
    }

    private void showCharacter(CommandSender sender, String[] args)
    {
        simpleSubCommand(sender, args, "JetCharacters.view", false, (sender1, args1) -> {
            if(args1.length < 1)
            {
                if(sender1 instanceof Player)
                {
                    JetCharacters.getInstance().getCharacter((Player) sender).show(sender1);
                }
                else
                {
                    sender.sendMessage(Lang.CONSOLE_NO_CHARACTER.toString());
                }
            }
            else
            {
                //If the player is online.
                if(Bukkit.getPlayer(args1[0]) != null)
                {
                    JetCharacters.getInstance().getCharacter(Bukkit.getPlayer(args1[0])).show(sender1);
                }
                //Launches a thread to find an offline character.
                else if(JetCharacters.getInstance().getConfiguration().getOffline() &&
                        (!(sender instanceof Player) || !instance.getSearching().contains(((Player) sender1).getUniqueId())))
                {
                    if(sender instanceof Player)
                    {
                        instance.setSearching(((Player) sender).getUniqueId());
                    }
                    new Thread(() ->
                    {
                        try
                        {
                            for (File f : instance.getPlayerDataFolder().listFiles())
                            {
                                if(f.isFile())
                                {
                                    YamlConfiguration c = YamlConfiguration.loadConfiguration(f);
                                    if(c.getString("last-username").equalsIgnoreCase(args[0]))
                                    {
                                        JetCharacters.getInstance()
                                                .getOfflineCharacter(UUID.fromString(f.getName()
                                                        .substring(0, f.getName().indexOf(".yml")))).show(sender1);
                                        if(sender instanceof Player) instance.doneSearching(((Player) sender).getUniqueId());
                                        return;
                                    }
                                }
                            }
                        }catch (NullPointerException npe)
                        {
                            instance.getLogger().severe("An error has occurred while searching for offline players. Please report this to the developer.");
                            instance.getLogger().severe(npe.getLocalizedMessage());
                        }
                        sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.PLAYER_NOT_FOUND.toString());
                        if(sender instanceof Player) instance.doneSearching(((Player) sender).getUniqueId());
                    });
                }
                else
                {
                    sender1.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.PLAYER_NOT_FOUND.toString());
                }
            }
        });
    }


}
