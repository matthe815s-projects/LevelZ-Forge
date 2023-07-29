package net.levelz.screen;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.levelz.init.ConfigInit;
import net.levelz.init.KeyInit;
import net.levelz.screen.widget.SkillScrollableWidget;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkillInfoScreen extends GuiScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("levelz:textures/gui/skill_info_background.png");

    private int backgroundWidth = 200;
    private int backgroundHeight = 215;
    private int x;
    private int y;

    private final String title;

    private TextComponentTranslation translatableText1A = null;
    private TextComponentTranslation translatableText1B = null;
    private TextComponentTranslation translatableText2A = null;
    private TextComponentTranslation translatableText2B = null;
    private TextComponentTranslation translatableText3A = null;
    private TextComponentTranslation translatableText3B = null;
    private TextComponentTranslation translatableText6A = null;
    private TextComponentTranslation translatableText6B = null;

    public SkillInfoScreen(String title) {
        super();
        this.title = title;
    }

    @Override
    public void initGui() {
        super.initGui();

        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;

        switch (this.title) {
        case "health":
            this.translatableText1A = new TextComponentTranslation("text.levelz.health_info_1", ConfigInit.CONFIG.healthBase);
            this.translatableText2A = new TextComponentTranslation("text.levelz.health_info_2_1", ConfigInit.CONFIG.healthBonus);
            this.translatableText2B = new TextComponentTranslation("text.levelz.health_info_2_2", ConfigInit.CONFIG.healthBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.health_max_lvl_1", ConfigInit.CONFIG.healthAbsorptionBonus);
            this.translatableText6B = new TextComponentTranslation("text.levelz.health_max_lvl_2", ConfigInit.CONFIG.healthAbsorptionBonus);
            break;
        case "strength":
            this.translatableText1A = new TextComponentTranslation("text.levelz.strength_info_1", ConfigInit.CONFIG.attackBase);
            this.translatableText2A = new TextComponentTranslation("text.levelz.strength_info_2_1", ConfigInit.CONFIG.attackBonus);
            this.translatableText2B = new TextComponentTranslation("text.levelz.strength_info_2_2", ConfigInit.CONFIG.attackBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.strength_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.attackDoubleDamageChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.strength_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.attackDoubleDamageChance * 100F));
            break;
        case "agility":
            this.translatableText1A = new TextComponentTranslation("text.levelz.agility_info_1", ConfigInit.CONFIG.movementBase);
            this.translatableText2A = new TextComponentTranslation("text.levelz.agility_info_2_1", ConfigInit.CONFIG.movementBonus);
            this.translatableText2B = new TextComponentTranslation("text.levelz.agility_info_2_2", ConfigInit.CONFIG.movementBonus);
            this.translatableText3A = new TextComponentTranslation("text.levelz.agility_info_3_1", ConfigInit.CONFIG.movementFallBonus);
            this.translatableText3B = new TextComponentTranslation("text.levelz.agility_info_3_2", ConfigInit.CONFIG.movementFallBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.agility_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.movementMissChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.agility_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.movementMissChance * 100F));
            break;
        case "defense":
            this.translatableText1A = new TextComponentTranslation("text.levelz.defense_info_1", ConfigInit.CONFIG.defenseBase);
            this.translatableText2A = new TextComponentTranslation("text.levelz.defense_info_2_1", ConfigInit.CONFIG.defenseBonus);
            this.translatableText2B = new TextComponentTranslation("text.levelz.defense_info_2_2", ConfigInit.CONFIG.defenseBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.defense_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.defenseReflectChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.defense_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.defenseReflectChance * 100F));
            break;
        case "stamina":
            this.translatableText1A = new TextComponentTranslation("text.levelz.stamina_info_1", ConfigInit.CONFIG.staminaBase);
            this.translatableText2A = new TextComponentTranslation("text.levelz.stamina_info_2_1", ConfigInit.CONFIG.staminaBonus);
            this.translatableText2B = new TextComponentTranslation("text.levelz.stamina_info_2_2", ConfigInit.CONFIG.staminaBonus);
            this.translatableText3A = new TextComponentTranslation("text.levelz.stamina_info_3_1", ConfigInit.CONFIG.staminaHealthBonus);
            this.translatableText3B = new TextComponentTranslation("text.levelz.stamina_info_3_2", ConfigInit.CONFIG.staminaHealthBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.stamina_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.staminaFoodBonus * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.stamina_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.staminaFoodBonus * 100F));
            break;
        case "luck":
            this.translatableText1A = new TextComponentTranslation("text.levelz.luck_info_1", ConfigInit.CONFIG.luckBase);
            this.translatableText1B = new TextComponentTranslation("text.levelz.luck_info_1_2");
            this.translatableText2A = new TextComponentTranslation("text.levelz.luck_info_2_1", ConfigInit.CONFIG.luckBonus);
            this.translatableText2B = new TextComponentTranslation("text.levelz.luck_info_2_2", ConfigInit.CONFIG.luckBonus);
            this.translatableText3A = new TextComponentTranslation("text.levelz.luck_info_3_1", ConfigInit.CONFIG.luckCritBonus);
            this.translatableText3B = new TextComponentTranslation("text.levelz.luck_info_3_2", ConfigInit.CONFIG.luckCritBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.luck_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.luckSurviveChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.luck_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.luckSurviveChance * 100F));
            break;
        case "archery":
            this.translatableText2A = new TextComponentTranslation("text.levelz.archery_info_2_1", ConfigInit.CONFIG.archeryBowExtraDamage);
            this.translatableText2B = new TextComponentTranslation("text.levelz.archery_info_2_2", ConfigInit.CONFIG.archeryBowExtraDamage);
            this.translatableText3A = new TextComponentTranslation("text.levelz.archery_info_3_1", ConfigInit.CONFIG.archeryCrossbowExtraDamage);
            this.translatableText3B = new TextComponentTranslation("text.levelz.archery_info_3_2", ConfigInit.CONFIG.archeryCrossbowExtraDamage);
            this.translatableText6A = new TextComponentTranslation("text.levelz.archery_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.archeryDoubleDamageChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.archery_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.archeryDoubleDamageChance * 100F));
            break;
        case "trade":
            this.translatableText2A = new TextComponentTranslation("text.levelz.trade_info_2_1", ConfigInit.CONFIG.tradeXPBonus);
            this.translatableText2B = new TextComponentTranslation("text.levelz.trade_info_2_2", ConfigInit.CONFIG.tradeXPBonus);
            this.translatableText3A = new TextComponentTranslation("text.levelz.trade_info_3_1", ConfigInit.CONFIG.tradeBonus);
            this.translatableText3B = new TextComponentTranslation("text.levelz.trade_info_3_2", ConfigInit.CONFIG.tradeBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.trade_max_lvl_1", ConfigInit.CONFIG.tradeReputation);
            this.translatableText6B = new TextComponentTranslation("text.levelz.trade_max_lvl_2", ConfigInit.CONFIG.tradeReputation);
            break;
        case "smithing":
            this.translatableText2A = new TextComponentTranslation("text.levelz.smithing_info_2_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.smithingToolChance * 100F));
            this.translatableText2B = new TextComponentTranslation("text.levelz.smithing_info_2_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.smithingToolChance * 100F));
            this.translatableText3A = new TextComponentTranslation("text.levelz.smithing_info_3_1", ConfigInit.CONFIG.smithingCostBonus);
            this.translatableText3B = new TextComponentTranslation("text.levelz.smithing_info_3_2", ConfigInit.CONFIG.smithingCostBonus);
            this.translatableText6A = new TextComponentTranslation("text.levelz.smithing_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.smithingAnvilChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.smithing_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.smithingAnvilChance * 100F));

            this.addButton(new SkillScreen.WidgetButtonPage(this.x + 180, this.y + 7, 12, 9, 45, 80, false, true, null, button -> {
                this.mc.displayGuiScreen(new SkillListScreen(this.title));
            }));
            break;
        case "mining":
            this.translatableText1A = new TextComponentTranslation("text.levelz.mining_info_1");
            this.translatableText2A = new TextComponentTranslation("text.levelz.mining_info_2_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.miningOreChance * 100F));
            this.translatableText2B = new TextComponentTranslation("text.levelz.mining_info_2_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.miningOreChance * 100F));
            this.translatableText6A = new TextComponentTranslation("text.levelz.mining_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.miningTntBonus * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.mining_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.miningTntBonus * 100F));

            this.addButton(new SkillScreen.WidgetButtonPage(this.x + 180, this.y + 7, 12, 9, 45, 80, false, true, null, button -> {
                this.mc.displayGuiScreen(new SkillListScreen(this.title));
            }));
            break;
        case "farming":
            this.translatableText2A = new TextComponentTranslation("text.levelz.farming_info_2_1", ConfigInit.CONFIG.farmingBase);
            this.translatableText2B = new TextComponentTranslation("text.levelz.farming_info_2_2", ConfigInit.CONFIG.farmingBase);
            this.translatableText3A = new TextComponentTranslation("text.levelz.farming_info_3_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.farmingChanceBonus * 100F));
            this.translatableText3B = new TextComponentTranslation("text.levelz.farming_info_3_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.farmingChanceBonus * 100F));
            this.translatableText6A = new TextComponentTranslation("text.levelz.farming_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.farmingTwinChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.farming_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.farmingTwinChance * 100F));
            break;
        case "alchemy":
            this.translatableText1A = new TextComponentTranslation("text.levelz.alchemy_info_1");
            this.translatableText1B = new TextComponentTranslation("text.levelz.alchemy_info_1_2");
            this.translatableText2A = new TextComponentTranslation("text.levelz.alchemy_info_2_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.alchemyEnchantmentChance * 100F));
            this.translatableText2B = new TextComponentTranslation("text.levelz.alchemy_info_2_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.alchemyEnchantmentChance * 100F));
            this.translatableText6A = new TextComponentTranslation("text.levelz.alchemy_max_lvl_1", new DecimalFormat("0.0").format(ConfigInit.CONFIG.alchemyPotionChance * 100F));
            this.translatableText6B = new TextComponentTranslation("text.levelz.alchemy_max_lvl_2", new DecimalFormat("0.0").format(ConfigInit.CONFIG.alchemyPotionChance * 100F));

            this.addButton(new SkillScreen.WidgetButtonPage(this.x + 180, this.y + 7, 12, 9, 45, 80, false, true, null, button -> {
                this.mc.displayGuiScreen(new SkillListScreen(this.title));
            }));
            break;
        default:
            break;
        }

        List<ITextComponent> list = new ArrayList<>();
        list.add(translatableText1A);
        list.add(translatableText1B);
        list.add(translatableText2A);
        list.add(translatableText2B);
        list.add(translatableText3A);
        list.add(translatableText3B);
        list.add(translatableText6A);
        list.add(translatableText6B);

        //this.addButton(new SkillScrollableWidget(this.x + 10, this.y + 22, 183, 185, list, this.title, this.fontRenderer));
    }

    @Override
    public boolean doesGuiPauseGame() {
        return false;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        this.drawBackground(0x000000);

        int i = (this.width - this.backgroundWidth) / 2;
        int j = (this.height - this.backgroundHeight) / 2;
        mc.ingameGUI.drawTexturedModalRect(i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        mc.fontRenderer.drawString(new TextComponentTranslation("text.levelz.info", new TextComponentTranslation(String.format("spritetip.levelz.%s_skill", this.title))).getFormattedText(), this.x + 6, this.y + 7, 0x3F3F3F, false);
        //DrawTabHelper.drawTab(client, context, this, x, y, mouseX, mouseY);

        super.drawScreen(mouseX, mouseY, delta);
    }

    @Override
    public void handleMouseInput() throws IOException {
        //DrawTabHelper.onTabButtonClick(client, this, this.x, this.y, mouseX, mouseY, false);
        super.handleMouseInput();
    }

    public int getWidth() {
        return this.backgroundWidth;
    }

    public int getHeight() {
        return this.backgroundHeight;
    }

}
