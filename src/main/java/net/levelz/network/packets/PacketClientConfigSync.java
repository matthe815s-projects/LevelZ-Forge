package net.levelz.network.packets;

import io.netty.buffer.ByteBuf;
import net.levelz.init.ConfigInit;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class PacketClientConfigSync implements IMessage, IMessageHandler<PacketClientConfigSync, IMessage> {

    int maxLevel = 0;
    int overallMaxLevel = 0;
    boolean allowHigherSkillLevel = false;

    double healthBase = 0;
    double healthBonus = 0;
    float healthAbsorptionBonus = 0;
    double movementBase = 0;
    double movementBonus = 0;
    float movementMissChance = 0;
    float movementFallBonus = 0;
    double attackBase = 0;
    double attackBonus = 0;
    float attackDoubleDamageChance = 0;
    float attackCritDmgBonus = 0;
    double defenseBase = 0;
    double defenseBonus = 0;
    float defenseReflectChance = 0;
    double luckBase = 0;
    double luckBonus = 0;

    float luckCritBonus = 0;
    float luckSurviveChance = 0;
    float staminaBase = 0;
    float staminaBonus = 0;

    float staminaHealthBonus = 0;
    float staminaFoodBonus = 0;
    double tradeBonus = 0;
    float tradeXPBonus = 0;
    boolean tradeReputation = false;
    float smithingCostBonus = 0;
    float smithingToolChance = 0;
    float smithingAnvilChance = 0;
    int farmingBase = 0;
    float farmingChanceBonus = 0;
    float farmingTwinChance = 0;
    float alchemyEnchantmentChance = 0;
    float alchemyPotionChance = 0;
    float archeryInaccuracyBonus = 0;
    float archeryBowExtraDamage = 0;
    float archeryCrossbowExtraDamage = 0;
    float archeryDoubleDamageChance = 0;
    float miningOreChance = 0;
    float miningTntBonus = 0;
    boolean bindAxeDamageToSwordRestriction = false;

    boolean useIndependentExp = false;
    float xpCostMultiplicator = 0;
    int xpExponent = 0;
    int xpBaseCost = 0;
    int xpMaxCost = 0;

    boolean miningProgression = false;
    boolean itemProgression = false;
    boolean blockProgression = false;
    boolean entityProgression = false;
    boolean brewingProgression = false;
    boolean smithingProgression = false;

    @Override
    public void fromBytes(ByteBuf buf) {
        maxLevel = buf.readInt();
        overallMaxLevel = buf.readInt();
        allowHigherSkillLevel = buf.readBoolean();

        healthBase = buf.readDouble();
        healthBonus = buf.readDouble();
        healthAbsorptionBonus = buf.readFloat();
        movementBase = buf.readDouble();
        movementBonus = buf.readDouble();
        movementMissChance = buf.readFloat();
        movementFallBonus = buf.readFloat();
        attackBase = buf.readDouble();
        attackBonus = buf.readDouble();
        attackDoubleDamageChance = buf.readFloat();
        attackCritDmgBonus = buf.readFloat();
        defenseBase = buf.readDouble();
        defenseBonus = buf.readDouble();
        defenseReflectChance = buf.readFloat();
        luckBase = buf.readDouble();
        luckBonus = buf.readDouble();

        luckCritBonus = buf.readFloat();
        luckSurviveChance = buf.readFloat();
        staminaBase = buf.readFloat();
        staminaBonus = buf.readFloat();

        staminaHealthBonus = buf.readFloat();
        staminaFoodBonus = buf.readFloat();
        tradeBonus = buf.readDouble();
        tradeXPBonus = buf.readFloat();
        tradeReputation = buf.readBoolean();
        smithingCostBonus = buf.readFloat();
        smithingToolChance = buf.readFloat();
        smithingAnvilChance = buf.readFloat();
        farmingBase = buf.readInt();
        farmingChanceBonus = buf.readFloat();
        farmingTwinChance = buf.readFloat();
        alchemyEnchantmentChance = buf.readFloat();
        alchemyPotionChance = buf.readFloat();
        archeryInaccuracyBonus = buf.readFloat();
        archeryBowExtraDamage = buf.readFloat();
        archeryCrossbowExtraDamage = buf.readFloat();

        archeryDoubleDamageChance = buf.readFloat();
        miningOreChance = buf.readFloat();
        miningTntBonus = buf.readFloat();

        bindAxeDamageToSwordRestriction = buf.readBoolean();

        useIndependentExp = buf.readBoolean();
        xpCostMultiplicator = buf.readFloat();
        xpExponent = buf.readInt();
        xpBaseCost = buf.readInt();
        xpMaxCost = buf.readInt();

        miningProgression = buf.readBoolean();
        itemProgression = buf.readBoolean();
        blockProgression = buf.readBoolean();
        entityProgression = buf.readBoolean();
        brewingProgression = buf.readBoolean();
        smithingProgression = buf.readBoolean();
    }

    @Override
    public void toBytes(ByteBuf buf) {

    }

    @Override
    public IMessage onMessage(PacketClientConfigSync message, MessageContext ctx) {
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

        return null;
    }
}
