package net.levelz.screen;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.google.common.collect.Multimap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.event.ClickEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.levelz.access.PlayerStatsManagerAccess;
import net.levelz.data.LevelLists;
import net.levelz.init.ConfigInit;
import net.levelz.network.PlayerStatsClientPacket;
import net.levelz.stats.PlayerStatsManager;
import net.levelz.stats.Skill;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class SkillScreen extends GuiScreen {

    public static final ResourceLocation BACKGROUND_TEXTURE = new ResourceLocation("levelz:textures/gui/skill_background.png");
    public static final ResourceLocation ICON_TEXTURES = new ResourceLocation("levelz:textures/gui/icons.png");

    private final WidgetButtonPage[] skillButtons = new WidgetButtonPage[12];
    private final WidgetButtonPage[] levelButtons = new WidgetButtonPage[12];

    private EntityPlayer playerEntity;
    private PlayerStatsManager playerStatsManager;

    private int backgroundWidth = 200;
    private int backgroundHeight = 215;
    private int x;
    private int y;

    public SkillScreen() {
        super();
    }

    @Override
    public void initGui() {
        super.initGui();
        this.playerEntity = Minecraft.getMinecraft().player;
        this.playerStatsManager = ((PlayerStatsManagerAccess) playerEntity).getPlayerStatsManager();
        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;

        for (int i = 0; i < this.skillButtons.length; i++) {
            final int skillInt = i;
            this.skillButtons[i] = this.addButton(new WidgetButtonPage(this.x + 15 + (i > 5 ? 90 : 0), this.y + 90 + i * 20 - (i > 5 ? 120 : 0), 16, 16, i * 16, 16, false, true,
                    new TextComponentTranslation("spritetip.levelz." + Skill.values()[i].name().toLowerCase() + "_skill"), button -> {
                        Minecraft.getMinecraft().displayGuiScreen(new SkillInfoScreen(Skill.values()[skillInt].name().toLowerCase()));
                    }));
            for (int o = 1; o < 10; o++) {
                String translatable = "spritetip.levelz." + Skill.values()[i].name().toLowerCase() + "_skill_info_" + o;
                TextComponentTranslation tooltip = new TextComponentTranslation(translatable);
                if (!tooltip.getFormattedText().equals(translatable)) {
                    this.skillButtons[i].addTooltip(tooltip);
                }
            }
            this.levelButtons[i] = this.addButton(new WidgetButtonPage(this.x + 83 + (i > 5 ? 90 : 0), this.y + 92 + i * 20 - (i > 5 ? 120 : 0), 13, 13, 33, 42, true, true, null, (button) -> {
                int level = 1;
                PlayerStatsClientPacket.writeC2SIncreaseLevelPacket(this.playerStatsManager, Skill.values()[skillInt], level);
            }));
            //this.levelButtons[i].enabled = playerStatsManager.getSkillPoints() > 0 && this.playerStatsManager.getSkillLevel(Skill.values()[i]) < ConfigInit.CONFIG.maxLevel;
        }

        WidgetButtonPage infoButton = this.addButton(new WidgetButtonPage(this.x + 178, this.y + 73, 11, 13, 0, 42, true, false, new TextComponentTranslation("text.levelz.more_info"), null));
        String[] infoTooltip = new TextComponentTranslation("text.levelz.gui.level_up_skill.tooltip").getFormattedText().split("\n");
        for (int i = 0; i < infoTooltip.length; i++) {
            infoButton.addTooltip(new TextComponentTranslation(infoTooltip[i]));
        }

        if (!LevelLists.craftingItemList.isEmpty()) {
            this.addButton(new WidgetButtonPage(this.x + 180, this.y + 5, 15, 13, 0, 80, true, true, new TextComponentTranslation("text.levelz.crafting_info"), button -> {
                Minecraft.getMinecraft().displayGuiScreen(new SkillListScreen("crafting"));
            }));
        }
        if (!ConfigInit.CONFIG.useIndependentExp) {
            WidgetButtonPage levelUpButton = this.addButton(new WidgetButtonPage(this.x + 177, this.y + 49, 13, 13, 33, 42, true, true, new TextComponentTranslation("text.levelz.level_up"), button -> {
                int level = 1;
                if (((WidgetButtonPage) button).wasRightButtonClicked()) {
                    level = 5;
                } else if (((WidgetButtonPage) button).wasMiddleButtonClicked()) {
                    level = 10;
                }
                PlayerStatsClientPacket.writeC2SLevelUpPacket(level);
            }));
            String[] levelUpTooltip = new TextComponentTranslation("text.levelz.gui.level_up.tooltip").getFormattedText().split("\n");
            for (int i = 0; i < levelUpTooltip.length; i++) {
                levelUpButton.addTooltip(new TextComponentTranslation(levelUpTooltip[i]));
            }
            //levelUpButton.active = !playerStatsManager.isMaxLevel() && (this.playerStatsManager.getNonIndependentExperience() / this.playerStatsManager.getNextLevelExperience()) >= 1;
        }
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

        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);

        if (Minecraft.getMinecraft().player != null) {
            ScaledResolution scaledResolution = new ScaledResolution(Minecraft.getMinecraft());

            // Top label
            TextComponentTranslation playerName = new TextComponentTranslation("text.levelz.gui.title", playerEntity.getName());
            Minecraft.getMinecraft().fontRenderer.drawString(playerName.getFormattedText(), this.x - this.fontRenderer.getStringWidth(playerName.getFormattedText()) / 2 + 120, this.y + 7, 0x3F3F3F, false);

            // Small icon labels
            Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(Math.round(playerEntity.getHealth())), this.x + 74, this.y + 22, 0x3F3F3F, false); // lifeLabel
            Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(BigDecimal.valueOf(playerEntity.getEntityAttribute(SharedMonsterAttributes.ARMOR).getAttributeValue()).setScale(2, RoundingMode.HALF_DOWN).floatValue()),
                    this.x + 74, this.y + 36, 0x3F3F3F, false); // protectionLabel
            Minecraft.getMinecraft().fontRenderer.drawString(
                    String.valueOf(BigDecimal.valueOf(playerEntity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() * 10D).setScale(2, RoundingMode.HALF_DOWN).floatValue()), this.x + 124,
                    this.y + 22, 0x3F3F3F, false); // speedLabel
            Minecraft.getMinecraft().fontRenderer.drawString(this.getDamageLabel(), this.x + 124, this.y + 36, 0x3F3F3F, false); // damageLabel
            Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(Math.round(playerEntity.getFoodStats().getFoodLevel())), this.x + 171, this.y + 22, 0x3F3F3F, false); // foodLabel
            Minecraft.getMinecraft().fontRenderer.drawString(String.valueOf(BigDecimal.valueOf(playerEntity.getEntityAttribute(SharedMonsterAttributes.LUCK).getAttributeValue()).setScale(2, RoundingMode.HALF_DOWN).floatValue()),
                    this.x + 171, this.y + 36, 0x3F3F3F, false); // fortuneLabel

            // Level label
            TextComponentTranslation skillLevelText = new TextComponentTranslation("text.levelz.gui.level", playerStatsManager.getOverallLevel());
            Minecraft.getMinecraft().fontRenderer.drawString(skillLevelText.getFormattedText(), this.x - this.fontRenderer.getStringWidth(skillLevelText.getFormattedText()) / 2 + 91, this.y + 52, 0x3F3F3F, false);
            // Point label
            TextComponentTranslation skillPointText = new TextComponentTranslation("text.levelz.gui.points", playerStatsManager.getSkillPoints());
            Minecraft.getMinecraft().fontRenderer.drawString(skillPointText.getFormattedText(), this.x - this.fontRenderer.getStringWidth(skillPointText.getFormattedText()) / 2 + 156, this.y + 52, 0x3F3F3F, false);

            // Experience bar
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 58, this.y + 64, 0, 100, 131, 5);

            int nextLevelExperience = playerStatsManager.getNextLevelExperience();
            float levelProgress = 0.0f;
            long experience = 0;
            if (!ConfigInit.CONFIG.useIndependentExp) {
                experience = this.playerStatsManager.getNonIndependentExperience();
                levelProgress = Math.min((float) experience / nextLevelExperience, 1);
            } else {
                levelProgress = this.playerStatsManager.getLevelProgress();
                experience = (int) (nextLevelExperience * levelProgress);
            }
            Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 58, this.y + 64, 0, 105, (int) (130.0f * levelProgress), 5);
            // current xp label
            TextComponentTranslation currentXpText = new TextComponentTranslation("text.levelz.gui.current_xp", experience, nextLevelExperience);
            Minecraft.getMinecraft().fontRenderer.drawString(currentXpText.getFormattedText(), this.x - this.fontRenderer.getStringWidth(currentXpText.getFormattedText()) / 2 + 123, this.y + 74, 0x3F3F3F, false);

            boolean skillsAllMaxed = true;
            for (int o = 0; o < this.levelButtons.length; o++) {
                this.levelButtons[o].enabled = playerStatsManager.getSkillPoints() > 0 && this.playerStatsManager.getSkillLevel(Skill.values()[o]) < ConfigInit.CONFIG.maxLevel;
                if (skillsAllMaxed) {
                    skillsAllMaxed = this.playerStatsManager.getSkillLevel(Skill.values()[o]) >= ConfigInit.CONFIG.maxLevel;
                }
            }
            if (skillsAllMaxed && ConfigInit.CONFIG.allowHigherSkillLevel) {
                for (int o = 0; o < this.levelButtons.length; o++) {
                    //this.levelButtons[o].active = true;
                }
            }

            // Small icons text
            for (int o = 0; o < 12; o++) {
                TextComponentTranslation currentLevelText = new TextComponentTranslation("text.levelz.gui.current_level", playerStatsManager.getSkillLevel(Skill.values()[o]), ConfigInit.CONFIG.maxLevel);
                this.fontRenderer.drawString(currentLevelText.getFormattedText(), this.x - this.fontRenderer.getStringWidth(currentLevelText.getFormattedText()) / 2 + 57 + (o > 5 ? 90 : 0), this.y + 95 + o * 20 - (o > 5 ? 120 : 0),
                        0x3F3F3F, false);
            }
        }
        // Small icons
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 58, this.y + 21, 0, 0, 9, 9); // lifeIcon
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 58, this.y + 34, 9, 0, 9, 9); // protectionIcon
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 108, this.y + 21, 18, 0, 9, 9); // speedIcon
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 108, this.y + 34, 27, 0, 9, 9); // damageIcon
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 155, this.y + 21, 36, 0, 9, 9); // foodIcon
        Minecraft.getMinecraft().ingameGUI.drawTexturedModalRect(this.x + 155, this.y + 34, 45, 0, 9, 9); // fortuneIcon

        //DrawTabHelper.drawTab(client, context, this, x, y, mouseX, mouseY);
        super.drawScreen(mouseX, mouseY, delta);
    }

    @Override
    public void handleKeyboardInput() throws IOException {
//        if (KeyInit.screenKey.matchesKey(keyCode, scanCode) || Objects.requireNonNull(client).options.inventoryKey.matchesKey(keyCode, scanCode)) {
//            this.close();
//            return true;
//        } else {
//            return super.keyPressed(keyCode, scanCode, modifiers);
//        }
        super.handleKeyboardInput();
    }

    @Override
    public void handleMouseInput() throws IOException {
//        DrawTabHelper.onTabButtonClick(client, this, this.x, this.y, mouseX, mouseY, this.getFocused() != null);
        super.handleMouseInput();
    }

    public int getWidth() {
        return this.backgroundWidth;
    }

    public int getHeight() {
        return this.backgroundHeight;
    }

    private String getDamageLabel() {
        float damage = 0.0F;
        Item item = playerEntity.getHeldItemMainhand().getItem();
        ArrayList<Object> levelList = LevelLists.customItemList;
        if (!levelList.isEmpty() && PlayerStatsManager.playerLevelisHighEnough(playerEntity, levelList, ForgeRegistries.ITEMS.getKey(item).toString(), false)) {
            if (item instanceof ItemSword) {
                damage = ((ItemSword) item).getAttackDamage();
            } else if (item instanceof ItemTool) {
                damage = 1;
            } else if (!item.getAttributeModifiers(EntityEquipmentSlot.MAINHAND, playerEntity.getHeldItemMainhand()).isEmpty() && item.getAttributeModifiers(EntityEquipmentSlot.MAINHAND, playerEntity.getHeldItemMainhand()).containsKey(SharedMonsterAttributes.ATTACK_DAMAGE)) {
                Multimap<String, AttributeModifier> multimap = item.getAttributeModifiers(EntityEquipmentSlot.MAINHAND, playerEntity.getHeldItemMainhand());
                for (Map.Entry<String, AttributeModifier> entry : multimap.entries()) {
                    if (entry.getKey().equals(SharedMonsterAttributes.ATTACK_DAMAGE)) {
                        damage = (float) entry.getValue().getAmount();
                        break;
                    }
                }
            }
        } else if (item instanceof ItemTool) {
            levelList = null;
            if (item instanceof ItemSword) {
                levelList = LevelLists.swordList;
            } else if (item instanceof ItemAxe) {
                if (ConfigInit.CONFIG.bindAxeDamageToSwordRestriction) {
                    levelList = LevelLists.swordList;
                } else {
                    levelList = LevelLists.axeList;
                }
            } else if (item instanceof ItemHoe) {
                levelList = LevelLists.hoeList;
            } else if (item instanceof ItemPickaxe || item instanceof ItemSpade) {
                levelList = LevelLists.toolList;
            }
            if (levelList != null) {
                if (PlayerStatsManager.playerLevelisHighEnough(playerEntity, levelList, ((ItemTool) item).toString().toLowerCase(), false)) {
                    if (item instanceof ItemSword) {
                        damage = ((ItemSword) item).getAttackDamage();
                    } else if (item instanceof ItemPickaxe) {
                        damage = 2;
                    }
                }
            }
        }

        damage += playerEntity.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue();
        return String.valueOf(BigDecimal.valueOf(damage).setScale(2, RoundingMode.HALF_DOWN).floatValue());
    }

    public interface OnPress {
        public void Action(GuiButton button);
    }

    public static class WidgetButtonPage extends GuiButton {

        private final boolean hoverOutline;
        private final boolean clickable;
        private final int textureX;
        private final int textureY;
        private List<TextComponentTranslation> tooltip = new ArrayList<>();
        private int clickedKey = -1;

        public WidgetButtonPage(int x, int y, int sizeX, int sizeY, int textureX, int textureY, boolean hoverOutline, boolean clickable, @Nullable TextComponentTranslation tooltip, OnPress action) {
            super(1, x, y, sizeX, sizeY, "");
            this.hoverOutline = hoverOutline;
            this.clickable = clickable;
            this.textureX = textureX;
            this.textureY = textureY;
            this.width = sizeX;
            this.height = sizeY;
            if (tooltip != null) {
                this.tooltip.add(tooltip);
            }
        }

        @Override
        public void drawButton(Minecraft mc, int mouseX, int mouseY, float delta) {
            Minecraft minecraftClient = Minecraft.getMinecraft();
            int i = hoverOutline ? this.getTextureY() : 0;
            //context.drawTexture(ICON_TEXTURES, this.getX(), this.getY(), this.textureX + i * this.width, this.textureY, this.width, this.height);
            if (this.hovered) {
                //context.drawTooltip(minecraftClient.textRenderer, this.tooltip, mouseX, mouseY);
            }
        }

        @Override
        public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
            if (!this.clickable) {
                return false;
            }
            return super.mousePressed(mc, mouseX, mouseY);
        }

        public void addTooltip(TextComponentTranslation text) {
            this.tooltip.add(text);
        }

        public boolean wasMiddleButtonClicked() {
            return clickedKey == 2;
        }

        public boolean wasRightButtonClicked() {
            return clickedKey == 1;
        }

        private int getTextureY() {
            int i = 1;
            if (!this.enabled) {
                i = 0;
            }
            return i;
        }
    }

}
