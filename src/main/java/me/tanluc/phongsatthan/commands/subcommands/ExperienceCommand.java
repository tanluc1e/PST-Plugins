package me.tanluc.phongsatthan.commands.subcommands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.commands.ChildCommand;
import me.tanluc.phongsatthan.database.DatabaseManager;
import me.tanluc.phongsatthan.utils.GenericUseMethods;
import me.tanluc.phongsatthan.utils.PlayerObject;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

public class ExperienceCommand extends ChildCommand {

    private final GenericUseMethods genericUseMethods;
    private final MobContracts plugin;
    private final DatabaseManager databaseManager;

    public ExperienceCommand(String command, GenericUseMethods genericUseMethods, MobContracts plugin, DatabaseManager databaseManager) {
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
        return plugin.getConfig().getString("messages.command-usage.giveXP.desc");
    }

    @Override
    public String getSyntax() {
        return plugin.getConfig().getString("messages.command-usage.giveXP.usage");
    }

    @Override
    public Boolean consoleUse() {
        return true;
    }


    @Override
    public void perform(CommandSender sender, String[] args) {
        Map<UUID, PlayerObject> map = databaseManager.getPlayerMap();

        if (!(args.length >= 4)) {
            genericUseMethods.sendVariedSenderMessage(sender, "&e" + this.getSyntax());
            return;
        }

        int amount = Integer.parseInt(args[2]);
        Player targetPlayer = plugin.getServer().getPlayer(args[3]);

        if (targetPlayer == null || !targetPlayer.isOnline()) {
            genericUseMethods.sendVariedSenderMessage(sender, "&cError: Player is not online!");
            return;
        }

        UUID targetUUID = targetPlayer.getUniqueId();
        PlayerObject targetPlayerObject = map.get(targetUUID);

        if (args[1].equalsIgnoreCase("add")) {
            int currentLevel = targetPlayerObject.getCurrentLevel();
            int currentXP = targetPlayerObject.getCurrentXp();
            int totalXP = targetPlayerObject.getTotalXp();
            int levelXp = currentLevel * plugin.getConfig().getInt("settings.levels.xp-multi");

            int newTotalXP = totalXP + amount;
            targetPlayerObject.setTotalXp(newTotalXP);

            if (currentXP + amount >= levelXp) {
                int level = currentLevel;
                while (currentXP + amount >= levelXp) {
                    level++;
                    levelXp = level * plugin.getConfig().getInt("settings.levels.xp-multi");
                }

                int newAmount = (currentXP + amount) - ((level - 1) * plugin.getConfig().getInt("settings.levels.xp-multi"));
                targetPlayerObject.setCurrentXp(newAmount);
                targetPlayerObject.setCurrentLevel(level);

                genericUseMethods.sendMessageWithPrefix(targetPlayer, plugin.getConfig().getString("messages.command.experience-added")
                        .replace("%player%", sender.getName()).replace("%amount%", String.valueOf(amount)));

                genericUseMethods.sendMessageWithPrefix(targetPlayer, plugin.getConfig().getString("messages.command.experience-levelup")
                        .replace("%level%", String.valueOf(level)).replace("%currentxp", String.valueOf(newAmount)));
            } else {
                targetPlayerObject.setCurrentXp(currentXP + amount);

                genericUseMethods.sendMessageWithPrefix(targetPlayer, plugin.getConfig().getString("messages.command.experience-added")
                        .replace("%player%", sender.getName()).replace("%amount%", String.valueOf(amount)));
            }
        } else if (args[1].equalsIgnoreCase("remove")) {
            int currentXP = targetPlayerObject.getCurrentXp();

            if (currentXP - amount < 0) {
                genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.experience-remove-error"));
                return;
            }

            targetPlayerObject.setCurrentXp(currentXP - amount);
            genericUseMethods.sendMessageWithPrefix(targetPlayer, plugin.getConfig().getString("messages.command.experience-remove")
                    .replace("%player%", sender.getName()).replace("%amount%", String.valueOf(amount)));
        }
    }


    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        if (args.length == 2)
            return Arrays.asList("add", "remove");

        if (args.length == 3)
            return Arrays.asList("1", "2", "3", "4", "5");

        if (args.length == 4)
            return Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList());

        return null;
    }
}
