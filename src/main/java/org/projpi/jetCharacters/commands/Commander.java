package org.projpi.jetCharacters.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.projpi.jetCharacters.JetCharacters;
import org.projpi.jetCharacters.characters.CardNode;
import org.projpi.jetCharacters.commands.commands.*;
import org.projpi.jetCharacters.io.Lang;

import java.util.Arrays;
import java.util.Map;

/**
 * Description here.
 * @author UberPilot
 */
public class Commander implements CommandExecutor
{
    private JetCharacters instance;

    private final SetSubCommand setSubCommand;
    private final ShowSubCommand showSubCommand;
    private final AdminClearSubCommand adminClearSubCommand;
    private final AdminSetSubCommand adminSetSubCommand;
    private final ClearSubCommand clearSubCommand;
    private final InfoSubCommand infoSubCommand;

    public Commander(JetCharacters instance)
    {
        this.instance = instance;
        this.setSubCommand = new SetSubCommand(instance);
        this.showSubCommand = new ShowSubCommand(instance);
        this.adminClearSubCommand = new AdminClearSubCommand(instance);
        this.adminSetSubCommand = new AdminSetSubCommand(instance);
        this.clearSubCommand = new ClearSubCommand(instance);
        this.infoSubCommand = new InfoSubCommand(instance);
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
                            ((instance.getConfiguration().getSplitPerms()) ? "" + args[1].toLowerCase() : ""),
                            true, setSubCommand);
                }
            }
            //Checking/Viewing a card
            else if(args[0].equalsIgnoreCase("check")
                    || args[0].equalsIgnoreCase("c")
                    || args[0].equalsIgnoreCase("view")
                    || args[0].equalsIgnoreCase("v"))
            {
                simpleSubCommand(sender, args, "JetCharacters.view", false, showSubCommand);
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
                            false, adminSetSubCommand);
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
                    simpleSubCommand(sender, args, "JetCharacters.clear", false, clearSubCommand);
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
                    simpleSubCommand(sender, args, "JetCharacters.admin.clear", false, adminClearSubCommand);
                }
            }
            else if(args[0].equalsIgnoreCase("info")
                    || args[0].equalsIgnoreCase("i"))
            {
                simpleSubCommand(sender, args, "JetCharacters.info", false, infoSubCommand);
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
                sender.sendMessage(Lang.PLUGIN_PREFIX.toString() + Lang.BASE_COMMAND.toString() + " " + Lang.INVALID_ARGS.toString().replaceAll("%usage%", usage)));
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


}
