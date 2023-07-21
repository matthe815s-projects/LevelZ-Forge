package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.init.ConfigInit;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientConfigSync implements IMessage, IMessageHandler<PacketClientConfigSync, IMessage> {
    @Override
    public void fromBytes(ByteBuf buf) {

    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketClientConfigSync message, MessageContext ctx) {
        int maxLevel = buf.readInt();
        int overallMaxLevel = buf.readInt();
        boolean allowHigherSkillLevel = buf.readBoolean();

        double healthBase = buf.readDouble();
        double healthBonus = buf.readDouble();
        float healthAbsorptionBonus = buf.readFloat();
        double movementBase = buf.readDouble();
        double movementBonus = buf.readDouble();
        float movementMissChance = buf.readFloat();
        float movementFallBonus = buf.readFloat();
        double attackBase = buf.readDouble();
        double attackBonus = buf.readDouble();
        float attackDoubleDamageChance = buf.readFloat();
        float attackCritDmgBonus = buf.readFloat();
        double defenseBase = buf.readDouble();
        double defenseBonus = buf.readDouble();
        float defenseReflectChance = buf.readFloat();
        double luckBase = buf.readDouble();
        double luckBonus = buf.readDouble();

        float luckCritBonus = buf.readFloat();
        float luckSurviveChance = buf.readFloat();
        float staminaBase = buf.readFloat();
        float staminaBonus = buf.readFloat();

        float staminaHealthBonus = buf.readFloat();
        float staminaFoodBonus = buf.readFloat();
        double tradeBonus = buf.readDouble();
        float tradeXPBonus = buf.readFloat();
        boolean tradeReputation = buf.readBoolean();
        float smithingCostBonus = buf.readFloat();
        float smithingToolChance = buf.readFloat();
        float smithingAnvilChance = buf.readFloat();
        int farmingBase = buf.readInt();
        float farmingChanceBonus = buf.readFloat();
        float farmingTwinChance = buf.readFloat();
        float alchemyEnchantmentChance = buf.readFloat();
        float alchemyPotionChance = buf.readFloat();
        float archeryInaccuracyBonus = buf.readFloat();
        float archeryBowExtraDamage = buf.readFloat();
        float archeryCrossbowExtraDamage = buf.readFloat();
        float archeryDoubleDamageChance = buf.readFloat();
        float miningOreChance = buf.readFloat();
        float miningTntBonus = buf.readFloat();
        boolean bindAxeDamageToSwordRestriction = buf.readBoolean();

        boolean useIndependentExp = buf.readBoolean();
        float xpCostMultiplicator = buf.readFloat();
        int xpExponent = buf.readInt();
        int xpBaseCost = buf.readInt();
        int xpMaxCost = buf.readInt();

        boolean miningProgression = buf.readBoolean();
        boolean itemProgression = buf.readBoolean();
        boolean blockProgression = buf.readBoolean();
        boolean entityProgression = buf.readBoolean();
        boolean brewingProgression = buf.readBoolean();
        boolean smithingProgression = buf.readBoolean();

        client.execute(() -> {
            ConfigInit.CONFIG.useIndependentExp = useIndependentExp;
            ConfigInit.CONFIG.maxLevel = maxLevel;
            ConfigInit.CONFIG.overallMaxLevel = overallMaxLevel;
            ConfigInit.CONFIG.allowHigherSkillLevel = allowHigherSkillLevel;
            ConfigInit.CONFIG.xpBaseCost = xpBaseCost;
            ConfigInit.CONFIG.xpMaxCost = xpMaxCost;
            ConfigInit.CONFIG.xpCostMultiplicator = xpCostMultiplicator;
            ConfigInit.CONFIG.xpExponent = xpExponent;

            ConfigInit.CONFIG.healthBase = healthBase;
            ConfigInit.CONFIG.healthBonus = healthBonus;
            ConfigInit.CONFIG.healthAbsorptionBonus = healthAbsorptionBonus;
            ConfigInit.CONFIG.movementBase = movementBase;
            ConfigInit.CONFIG.movementBonus = movementBonus;
            ConfigInit.CONFIG.movementMissChance = movementMissChance;
            ConfigInit.CONFIG.movementFallBonus = movementFallBonus;
            ConfigInit.CONFIG.attackBase = attackBase;
            ConfigInit.CONFIG.attackBonus = attackBonus;
            ConfigInit.CONFIG.attackDoubleDamageChance = attackDoubleDamageChance;
            ConfigInit.CONFIG.attackCritDmgBonus = attackCritDmgBonus;
            ConfigInit.CONFIG.defenseBase = defenseBase;
            ConfigInit.CONFIG.defenseBonus = defenseBonus;
            ConfigInit.CONFIG.defenseReflectChance = defenseReflectChance;
            ConfigInit.CONFIG.luckBase = luckBase;
            ConfigInit.CONFIG.luckBonus = luckBonus;
            ConfigInit.CONFIG.luckCritBonus = luckCritBonus;
            ConfigInit.CONFIG.luckSurviveChance = luckSurviveChance;
            ConfigInit.CONFIG.staminaBase = staminaBase;
            ConfigInit.CONFIG.staminaBonus = staminaBonus;
            ConfigInit.CONFIG.staminaHealthBonus = staminaHealthBonus;
            ConfigInit.CONFIG.staminaFoodBonus = staminaFoodBonus;
            ConfigInit.CONFIG.tradeBonus = tradeBonus;
            ConfigInit.CONFIG.tradeXPBonus = tradeXPBonus;
            ConfigInit.CONFIG.tradeReputation = tradeReputation;
            ConfigInit.CONFIG.smithingCostBonus = smithingCostBonus;
            ConfigInit.CONFIG.smithingToolChance = smithingToolChance;
            ConfigInit.CONFIG.smithingAnvilChance = smithingAnvilChance;
            ConfigInit.CONFIG.farmingBase = farmingBase;
            ConfigInit.CONFIG.farmingChanceBonus = farmingChanceBonus;
            ConfigInit.CONFIG.farmingTwinChance = farmingTwinChance;
            ConfigInit.CONFIG.alchemyEnchantmentChance = alchemyEnchantmentChance;
            ConfigInit.CONFIG.alchemyPotionChance = alchemyPotionChance;
            ConfigInit.CONFIG.archeryInaccuracyBonus = archeryInaccuracyBonus;
            ConfigInit.CONFIG.archeryBowExtraDamage = archeryBowExtraDamage;
            ConfigInit.CONFIG.archeryCrossbowExtraDamage = archeryCrossbowExtraDamage;
            ConfigInit.CONFIG.archeryDoubleDamageChance = archeryDoubleDamageChance;
            ConfigInit.CONFIG.miningOreChance = miningOreChance;
            ConfigInit.CONFIG.miningTntBonus = miningTntBonus;
            ConfigInit.CONFIG.bindAxeDamageToSwordRestriction = bindAxeDamageToSwordRestriction;

            ConfigInit.CONFIG.miningProgression = miningProgression;
            ConfigInit.CONFIG.itemProgression = itemProgression;
            ConfigInit.CONFIG.blockProgression = blockProgression;
            ConfigInit.CONFIG.entityProgression = entityProgression;
            ConfigInit.CONFIG.brewingProgression = brewingProgression;
            ConfigInit.CONFIG.smithingProgression = smithingProgression;
        });
    }
}
