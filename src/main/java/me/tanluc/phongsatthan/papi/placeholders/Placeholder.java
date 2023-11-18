package me.tanluc.phongsatthan.papi.placeholders;

import org.bukkit.entity.Player;

public interface Placeholder {

    String process(Player player, String id);

    String getName();
}
