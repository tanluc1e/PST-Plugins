package me.tanluc.phongsatthan.commands.subcommands;

import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.commands.ChildCommand;
import me.tanluc.phongsatthan.utils.GenericUseMethods;
import org.bukkit.command.CommandSender;

import java.util.List;

public class ReloadCommand extends ChildCommand {
    GenericUseMethods genericUseMethods;
    private final MobContracts plugin;


    public ReloadCommand(String command, GenericUseMethods genericUseMethods, MobContracts plugin) {
        super(command);
        this.genericUseMethods = genericUseMethods;
        this.plugin = plugin;
    }

    @Override
    public String getPermission() {
        return "mobcontracts.admin";
    }
    @Override
    public String getDescription() {
        return plugin.getConfig().getString("messages.command-usage.reload.desc");
    }
    @Override
    public String getSyntax() {
        return plugin.getConfig().getString("messages.command-usage.reload.usage");
    }
    @Override
    public Boolean consoleUse() {
        return true;
    }
    @Override
    public void perform(CommandSender sender, String[] args) {
        if (plugin != null) {
            plugin.reloadConfig();
            genericUseMethods.sendVariedSenderMessage(sender, plugin.getConfig().getString("messages.command.reload"));
        }
    }
    @Override
    public List<String> onTab(CommandSender sender, String... args) {
        return null;
    }
}
