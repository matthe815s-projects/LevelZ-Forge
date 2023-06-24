package net.levelz.config;

import java.util.ArrayList;
import java.util.Arrays;

import net.levelz.LevelzClient;
import net.levelz.init.ConfigInit;
import net.minecraftforge.common.config.Config;

@Config(name = "levelz", modid = LevelzClient.MODID)
public class LevelzConfig {

    // Level settings
    @Config.Comment("Maximum level per skills")
    public int maxLevel = 20;

    @Config.Comment("Maximum level: 0 = disabled")
    public int overallMaxLevel = 0;

    @Config.Comment("In combination with overallMaxLevel, only when all skills maxed")
    public boolean allowHigherSkillLevel = false;

    @Config.Comment("Applies if bonus chest world setting is enabled")
    public int startPoints = 5;

    @Config.Comment("Enables starter points for SERVER only")
    public boolean enableStartPoints = false;

    public int pointsPerLevel = 1;

    @Config.Comment("If true will reset stats on death")
    public boolean hardMode = false;

    public boolean disableMobFarms = true;

    @Config.Comment("Amount of allowed mob kills in a chunk")
    public int mobKillCount = 5;

    @Config.Comment("Strange potion resets all stats instead of one")
    public boolean opStrangePotion = false;

    @Config.Comment("restrict hand usage when item not unlocked")
    public boolean lockedHandUsage = false; // Client only

    @Config.Comment("Only for Devs")
    public boolean devMode = false;

    // Skill settings
    @Config.Comment("Attribute values - Bonus for each lvl")
    public double healthBase = ConfigInit.isOriginsLoaded ? 20D : 6D;

    public double healthBonus = 1D;

    @Config.Comment("Absorption Bonus at max lvl")
    public float healthAbsorptionBonus = 6F;

    @Config.Comment("Levelz Screen Multiplies it by 10")
    public double movementBase = 0.09D;

    public double movementBonus = 0.001D;

    @Config.Comment("Chance of damage misses player at max lvl")
    public float movementMissChance = 0.05F;

    @Config.Comment("Reduces fall damage")
    public float movementFallBonus = 0.25F;

    public double attackBase = 1D;

    public double attackBonus = 0.2D;

    @Config.Comment("Chance of double meele damage at max lvl")
    public float attackDoubleDamageChance = 0.03F;

    public float attackCritDmgBonus = 0.2F;

    public double defenseBase = 0D;

    public double defenseBonus = 0.2D;

    @Config.Comment("Chance of damage reflection at max lvl")
    public float defenseReflectChance = 0.05F;

    public double luckBase = 0D;

    public double luckBonus = 0.05D;

    public float luckCritBonus = 0.01F;

    @Config.Comment("Chance of not dying at max lvl")
    public float luckSurviveChance = 0.5F;

    public float staminaBase = 1.1F;

    public float staminaBonus = 0.02F;

    public float staminaHealthBonus = 0.05F;

    @Config.Comment("Food is more nutritious at max lvl")
    public float staminaFoodBonus = 0.3F;

    @Config.Comment("Price reduction in %")
    public double tradeBonus = 1.0D;

    public float tradeXPBonus = 0.5F;

    @Config.Comment("Disables bad reputation possibility at max lvl")
    public boolean tradeReputation = true;

    public float smithingCostBonus = 0.015F;

    @Config.Comment("Chance of no tool damage")
    public float smithingToolChance = 0.01F;

    @Config.Comment("Chance of no xp usage on anvil at max lvl")
    public float smithingAnvilChance = 0.1F;

    @Config.Comment("Min level to get chance of more crops drop")
    public int farmingBase = 10;

    @Config.Comment("Chance of more crops drop")
    public float farmingChanceBonus = 0.01F;

    @Config.Comment("Breeding twin chance at max lvl")
    public float farmingTwinChance = 0.2F;

    @Config.Comment("Chance of increased enchantment strength")
    public float alchemyEnchantmentChance = 0.005F;

    @Config.Comment("Chance of drinking potion with double value at max lvl")
    public float alchemyPotionChance = 0.05F;

    public float archeryInaccuracyBonus = 0.015F;

    public float archeryBowExtraDamage = 0.2F;

    public float archeryCrossbowExtraDamage = 0.2F;

    @Config.Comment("Chance of double range damage at max lvl")
    public float archeryDoubleDamageChance = 0.05F;

    @Config.Comment("Chance of more ore drop")
    public float miningOreChance = 0.01F;

    @Config.Comment("Tnt power increase at max lvl")
    public float miningTntBonus = 0.5F;

    @Config.Comment("Locked blocks break slower factor")
    public float miningLockedMultiplicator = 2.0F;

    public boolean bindAxeDamageToSwordRestriction = true;

    // Experience settings
    @Config.Comment("Caution! Level up use independent levelz xp system")
    public boolean useIndependentExp = true;

    @Config.Comment("XP equation: lvl^exponent * multiplicator + base")
    public float xpCostMultiplicator = 0.1F;

    public int xpExponent = 2;

    public int xpBaseCost = 50;

    @Config.Comment("0 = no experience cap")
    public int xpMaxCost = 0;

    public boolean resetCurrentXP = true;

    public boolean dropPlayerXP = true;

    public boolean dropXPbasedOnLvl = false;

    @Config.Comment("0.01 = 1% more xp per lvl")
    public float basedOnMultiplier = 0.01F;

    public float breedingXPMultiplier = 1.0F;

    public float bottleXPMultiplier = 1.0F;

    public float dragonXPMultiplier = 0.5F;

    public float fishingXPMultiplier = 0.8F;

    public float furnaceXPMultiplier = 0.1F;

    public float oreXPMultiplier = 1.0F;

    public float tradingXPMultiplier = 0.3F;

    public float mobXPMultiplier = 1.0F;

    @Config.Comment("Show the skill gui button in the inventory")
    public boolean inventoryButton = true; // Client only

    @Config.Comment("Highlighlocked blocks in red.")
    public boolean highlightLocked = false; // Client only

    public boolean sortCraftingRecipesBySkill = false; // Client only

    public boolean inventorySkillLevel = true; // Client only

    public int inventorySkillLevelPosX = 0; // Client only

    public int inventorySkillLevelPosY = 0; // Client only

    public boolean showLevelList = true; // Client only

    public boolean showLevel = true; // Client only

    @Config.Comment("Switch levelz screen instead of closing with inventory key")
    public boolean switch_screen = false; // Client only

    public boolean showLockedBlockInfo = false; // Client only

    public int lockedBlockInfoPosX = 0; // Client only

    public int lockedBlockInfoPosY = 0; // Client only

    public boolean miningProgression = true;

    public boolean itemProgression = true;

    public boolean blockProgression = true;

    public boolean entityProgression = true;

    public boolean brewingProgression = true;

    public boolean smithingProgression = true;

    // List.of is immutable
    // Arrays.asList returns an ArrayList of the Arrays class which is different to the ArrayList class
    // Returns a list which should be synced to the client
    public ArrayList<Object> getConfigList() {
        return new ArrayList<Object>(Arrays.asList(maxLevel, overallMaxLevel, allowHigherSkillLevel, healthBase, healthBonus, healthAbsorptionBonus, movementBase, movementBonus, movementMissChance,
                movementFallBonus, attackBase, attackBonus, attackDoubleDamageChance, attackCritDmgBonus, defenseBase, defenseBonus, defenseReflectChance, luckBase, luckBonus, luckCritBonus,
                luckSurviveChance, staminaBase, staminaBonus, staminaHealthBonus, staminaFoodBonus, tradeBonus, tradeXPBonus, tradeReputation, smithingCostBonus, smithingToolChance,
                smithingAnvilChance, farmingBase, farmingChanceBonus, farmingTwinChance, alchemyEnchantmentChance, alchemyPotionChance, archeryInaccuracyBonus, archeryBowExtraDamage,
                archeryCrossbowExtraDamage, archeryDoubleDamageChance, miningOreChance, miningTntBonus, bindAxeDamageToSwordRestriction, useIndependentExp, xpCostMultiplicator, xpExponent,
                xpBaseCost, xpMaxCost, miningProgression, itemProgression, blockProgression, entityProgression, brewingProgression, smithingProgression));
    }

}