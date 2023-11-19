package me.tanluc.phongsatthan.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.commands.ChildCommand;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.utils.GenericUseMethods;
import me.tanluc.phongsatthan.utils.PlayerObject;

import java.util.*;
import java.util.stream.Collectors;

public class GiveCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final MobContracts plugin;
    private final DatabaseManager databaseManager;

    public GiveCommand(String command, GenericUseMethods genericUseMethods, MobContracts plugin, DatabaseManager databaseManager) {
        super(command);
        this.genericUseMethods = genericUseMethods;
        this.plugin = plugin;
        this.databaseManager = databaseManager;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }

    @Override
    public String getDescription() {
        return plugin.getConfig().getString("messages.command-usage.give.desc");
    }

    @Override
    public String getSyntax() {
        return plugin.getConfig().getString("messages.command-usage.give.usage");
    }

    @Override
    public Boolean consoleUse() {
        return true;
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();

        if (args.length < 3) {
            genericUseMethods.sendVariedSenderMessage(sender, "&e" + this.getSyntax());
            return;
        }

        int amount = 1; // Default amount if not specified
        if (args.length >= 4) {
            try {
                amount = Integer.parseInt(args[3]);
            } catch (NumberFormatException e) {
                genericUseMethods.sendVariedSenderMessage(sender, "&cError: Amount should be a number!");
                return;
            }
        }

        Player targetPlayer = Bukkit.getPlayer(args[1]);
        if (targetPlayer == null) {
            genericUseMethods.sendVariedSenderMessage(sender, "&cError: Player is not online!");
            return;
        }

        PlayerObject playerObject = map.get(targetPlayer.getUniqueId());
        if (playerObject == null) {
            genericUseMethods.sendVariedSenderMessage(sender, "&cError: Player data not found!");
            return;
        }

        String contractType = args[2].toLowerCase();
        switch (contractType) {
            case "legendary":
                playerObject.setLegendaryOwned(playerObject.getLegendaryOwned() + amount);
                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.give-legendary")
                        .replace("%player%", targetPlayer.getName())
                        .replace("%amount%", String.valueOf(amount)));
                genericUseMethods.sendMessageWithPrefix(targetPlayer, plugin.getConfig().getString("messages.command.received-legendary")
                        .replace("%amount%", String.valueOf(amount)));
                break;
            case "epic":
                playerObject.setEpicOwned(playerObject.getEpicOwned() + amount);
                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.give-epic")
                        .replace("%player%", targetPlayer.getName())
                        .replace("%amount%", String.valueOf(amount)));
                genericUseMethods.sendMessageWithPrefix(targetPlayer, plugin.getConfig().getString("messages.command.received-epic")
                        .replace("%amount%", String.valueOf(amount)));
                break;
            case "common":
                playerObject.setCommonOwned(playerObject.getCommonOwned() + amount);
                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.give-common")
                        .replace("%player%", targetPlayer.getName())
                        .replace("%amount%", String.valueOf(amount)));
                genericUseMethods.sendMessageWithPrefix(targetPlayer, plugin.getConfig().getString("messages.command.received-common")
                        .replace("%amount%", String.valueOf(amount)));
                break;
            default:
                genericUseMethods.sendVariedSenderMessage(sender, "&cError: Invalid contract type!");
                return;
        }
    }

    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 2) {
            List<String> players = new ArrayList<>();
            players.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            return players;
        } else if (args.length == 3) {
            return Arrays.asList("Common", "Epic", "Legendary");
        } else if (args.length == 4) {
            return Arrays.asList("1", "2", "3", "4", "5");
        }
        return null;
    }
}
