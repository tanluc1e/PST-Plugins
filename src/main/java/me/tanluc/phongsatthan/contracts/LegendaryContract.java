package me.tanluc.phongsatthan.contracts;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.attribute.Attribute;
import org.bukkit.entity.*;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import me.tanluc.phongsatthan.MobContracts;
import me.tanluc.phongsatthan.effects.LegendaryEffects;
import me.tanluc.phongsatthan.events.ContractSummonEvent;
import me.tanluc.phongsatthan.mobs.MobFeatures;
import me.tanluc.phongsatthan.utils.ContractType;

import java.util.Random;
import java.util.UUID;

public class LegendaryContract {

    private final MobContracts plugin;
    private final MobFeatures mobFeatures;
    private final LegendaryEffects legendaryEffects;
    private final ContractType contractType;
    private static final Random rnd = new Random();

    public LegendaryContract(MobContracts plugin, MobFeatures mobFeatures, LegendaryEffects legendaryEffects, ContractType contractType) {
        this.plugin = plugin;
        this.mobFeatures = mobFeatures;
        this.legendaryEffects = legendaryEffects;
        this.contractType = contractType;
    }

    public void summonLegendaryContract(Player player) {
        UUID uuid = player.getUniqueId();
        UUID mobUuid = UUID.randomUUID();
        double maxHp = plugin.getConfig().getDouble("settings.legendary.max-health");
        double minHp = plugin.getConfig().getDouble("settings.legendary.min-health");
        double maxDmg = plugin.getConfig().getDouble("settings.legendary.max-damage");
        double minDmg = plugin.getConfig().getDouble("settings.legendary.min-damage");
        double maxSpeed = plugin.getConfig().getDouble("settings.legendary.max-speed");
        double minSpeed = plugin.getConfig().getDouble("settings.legendary.min-speed");
        double health = rnd.nextDouble() * (maxHp - minHp) + minHp;
        double damage = rnd.nextDouble() * (maxDmg - minDmg) + minDmg;
        double armor = rnd.nextDouble() * (20 - 1) + 1;
        double speed = rnd.nextDouble() * (maxSpeed - minSpeed) + minSpeed;
        String name = plugin.getConfig().getStringList("settings.names.name").get(rnd.nextInt(plugin.getConfig().getStringList("settings.names.name").size()));
        String adj = plugin.getConfig().getStringList("settings.names.adjectives").get(rnd.nextInt(plugin.getConfig().getStringList("settings.names.adjectives").size()));
        String fullName = ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("settings.legendary.name-format")
                .replace("%name%", name).replace("%adjective%", adj));

        Entity entity = player.getWorld().spawnEntity(player.getLocation(),
                EntityType.valueOf(plugin.getConfig().getStringList("contract-type").get(rnd.nextInt(plugin.getConfig().getStringList("contract-type").size()))));
        LivingEntity spawned = (LivingEntity) entity;

        // stats
        spawned.getAttribute(Attribute.GENERIC_ARMOR).setBaseValue(armor);
        spawned.getAttribute(Attribute.GENERIC_ARMOR_TOUGHNESS).setBaseValue(armor);
        spawned.getAttribute(Attribute.GENERIC_MAX_HEALTH).setBaseValue(health);
        spawned.getAttribute(Attribute.GENERIC_ATTACK_DAMAGE).setBaseValue(damage);
        spawned.getAttribute(Attribute.GENERIC_MOVEMENT_SPEED).setBaseValue(speed);
        spawned.setHealth(health);
        spawned.addScoreboardTag("LegendaryContract");
        spawned.addScoreboardTag("Contract");
        spawned.setCustomName(fullName);
        spawned.setCustomNameVisible(true);

        if (plugin.getConfig().getBoolean("settings.legendary.allow-weapon"))
            mobFeatures.giveWeapons(spawned);

        if (plugin.getConfig().getBoolean("settings.legendary.allow-targeting"))
            mobFeatures.getTarget((Creature) spawned);

        if(plugin.getConfig().getBoolean("settings.general.enable-glowing-contract"))
            spawned.addPotionEffect(new PotionEffect(PotionEffectType.GLOWING, 100000, 1));

        String effect = "No effect";
        if (plugin.getConfig().getBoolean("settings.legendary.enable-aoe-effects"))
            effect = legendaryEffects.randomLegendaryEffect(spawned);

        contractType.addContract(spawned.getUniqueId(), "Legendary", effect, spawned.getType());
        ContractSummonEvent event = new ContractSummonEvent(spawned, mobUuid, spawned.getCustomName(), damage, speed, health, armor, "Legendary", effect, spawned.getType().name(), player);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }
}
