package net.levelz.screen.widget;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.TranslateTransition;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.resources.Language;
import net.minecraft.client.resources.LanguageManager;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.apache.commons.codec.language.bm.Languages;
import org.apache.commons.codec.language.bm.NameType;
import org.apache.commons.lang3.StringUtils;
import net.levelz.data.LevelLists;
import net.levelz.init.ConfigInit;
import net.levelz.screen.SkillInfoScreen;
import net.minecraft.util.math.MathHelper;

import javax.annotation.Nullable;

@SideOnly(Side.CLIENT)
public class SkillScrollableWidget extends GuiScrollingList {

    private final List<TextComponentTranslation> textList;
    private final String title;
    private final FontRenderer textRenderer;

    private final ArrayList<Object> sortedUnlockSkillList = new ArrayList<Object>();

    private int totalYSpace = 0;
    private int ySpace = 0;

    private boolean scrollbarDragged;

    public SkillScrollableWidget(int x, int y, int width, int height, List<TextComponentTranslation> textList, String title, FontRenderer textRenderer) {
        super(Minecraft.getMinecraft(), x, y, width, height, 0, 0);
        this.title = title;
        this.textRenderer = textRenderer;
        this.textList = textList;
        // Special Item
        // 0: material, 1: skill, 2: level, 3: info, 4: boolean
        // Other
        // 0: skill, 1: level, 2: info, 3: boolean
        ArrayList<Object> unlockSkillList = new ArrayList<Object>();

        // Fill skill list
        for (int o = 0; o < LevelLists.listOfAllLists.size(); o++) {
            if (!LevelLists.listOfAllLists.get(o).isEmpty()) {
                if (LevelLists.listOfAllLists.get(o).get(0).toString().equals(this.title)) {
                    unlockSkillList.add(LevelLists.listOfAllLists.get(o).get(1));
                    unlockSkillList.add(LevelLists.listOfAllLists.get(o).get(2));
                    unlockSkillList.add(null);
                } else
                    for (int k = 0; k < LevelLists.listOfAllLists.get(o).size(); k += 5)
                        if (LevelLists.listOfAllLists.get(o).get(k + 1).toString().equals(this.title)) {
                            unlockSkillList.add(LevelLists.listOfAllLists.get(o).get(2 + k));
                            unlockSkillList.add(LevelLists.listOfAllLists.get(o).get(3 + k));
                            unlockSkillList.add(LevelLists.listOfAllLists.get(o).get(0 + k));
                        }
            }
        }
        // Sort list
        // If I set j = 0 it will include 0 level unlocks!
        for (int k = 1; k <= ConfigInit.CONFIG.maxLevel; k++) {
            for (int o = 0; o < unlockSkillList.size(); o += 3) {
                if (unlockSkillList.get(o).equals(k)) {
                    if (!sortedUnlockSkillList.contains(unlockSkillList.get(o))) {
                        sortedUnlockSkillList.add(unlockSkillList.get(o));
                    }
                    sortedUnlockSkillList.add(unlockSkillList.get(o + 1));
                    sortedUnlockSkillList.add(unlockSkillList.get(o + 2));
                }
            }
        }
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        this.ySpace = this.getSize();

        if (textList.size() > 0 && translatableTextIsNotBlank(textList.get(0))) {
            this.textRenderer.drawString(textList.get(0).getFormattedText(), 0, this.ySpace, 0x3F3F3F);
            this.ySpace += 14;
        }
        if (textList.size() > 1 && translatableTextIsNotBlank(textList.get(1))) {
            this.textRenderer.drawString(textList.get(1).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
            this.ySpace += 14;
        }
        if (textList.size() > 2 && translatableTextIsNotBlank(textList.get(2))) {
            this.textRenderer.drawString(textList.get(2).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
            if (textList.size() > 3 && translatableTextIsNotBlank(textList.get(3))) {
                this.ySpace += 10;
                this.textRenderer.drawString(textList.get(3).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
            }
            this.ySpace += 14;
        }
        if (textList.size() > 4 && translatableTextIsNotBlank(textList.get(4))) {
            this.textRenderer.drawString(textList.get(4).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
            if (textList.size() > 5 && translatableTextIsNotBlank(textList.get(5))) {
                this.ySpace += 10;
                this.textRenderer.drawString(textList.get(5).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
            }
            this.ySpace += 14;
        }

        boolean hasLvlMaxUnlock = false;
        this.ySpace += 10;
        this.textRenderer.drawString(new TextComponentTranslation("text.levelz.general_info").getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
        this.ySpace += 16;
        // level, object, info, object, info,..., level,...
        for (int u = 0; u < sortedUnlockSkillList.size(); u++) {
            if (sortedUnlockSkillList.get(u) != null && sortedUnlockSkillList.get(u).getClass() == Integer.class) {
                // Add level category info
                this.textRenderer.drawString(new TextComponentTranslation("text.levelz.level", sortedUnlockSkillList.get(u)).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
                this.ySpace += 16;
                for (int g = 1; g < sortedUnlockSkillList.size() - u; g += 2) {
                    if (sortedUnlockSkillList.get(u + g).getClass() == Integer.class) {
                        break;
                    }
                    String string = sortedUnlockSkillList.get(u + g).toString();
                    if (string.contains("minecraft:custom_")) {
                        string = sortedUnlockSkillList.get(u + g + 1).toString();
                    }
                    ResourceLocation identifier = new ResourceLocation(string);
                    boolean hit = true;

                    if (!ForgeRegistries.BLOCKS.getValue(identifier).equals(Blocks.AIR)) {
                        string = ForgeRegistries.BLOCKS.getValue(identifier).getUnlocalizedName();
                    } else if (!ForgeRegistries.ITEMS.getValue(identifier).equals(Items.AIR)) {
                        string = ForgeRegistries.ITEMS.getValue(identifier).getUnlocalizedName();
                    } else {
                        hit = false;
                    }
                    Languages language = Languages.getInstance(NameType.GENERIC);
//
//                    if (hit)
//                        string = "";
//                    else {
//                        String translationKey = String.format("text.levelz.object_info.%s", identifier.getPath());
//                        if (language.hasTranslation(translationKey)) {
//                            string = language.get(translationKey);
//                        } else {
//                            string = StringUtils.capitalize(string.replace("minecraft:", "").replaceAll("_", " ").replace(':', ' '));
//                        }
//                    }
//
//                    if (sortedUnlockSkillList.get(u + g + 1) != null && !sortedUnlockSkillList.get(u + g).toString().contains("minecraft:custom_")) {
//                        String otherString = sortedUnlockSkillList.get(u + g + 1).toString();
//                        String translationKey = String.format("text.levelz.object_prefix.%s", otherString);
//                        if (language.hasTranslation(translationKey)) {
//                            otherString = language.get(translationKey);
//                        } else {
//                            otherString = otherString.replace('_', ' ');
//                        }
//                        context.drawText(this.textRenderer, Text.translatable("text.levelz.object_info_2", StringUtils.capitalize(otherString), string), 0 + 10, this.ySpace, 0x3F3F3F,
//                                false);
//                    } else {
//                        context.drawText(this.textRenderer, Text.translatable("text.levelz.object_info_1", string), 0 + 10, this.ySpace, 0x3F3F3F, false);
//                    }
                    this.ySpace += 16;
                }
                if (sortedUnlockSkillList.get(u).equals(ConfigInit.CONFIG.maxLevel)) {
                    hasLvlMaxUnlock = true;
                } else {
                    this.ySpace += 4;
                }
            }

        }

        if (!hasLvlMaxUnlock) {
            this.textRenderer.drawString(new TextComponentTranslation("text.levelz.level", ConfigInit.CONFIG.maxLevel).getFormattedText(), 0, this.ySpace, 0x3F3F3F);
        } else {
            this.ySpace -= 16;
        }
        if (textList.size() > 6 && translatableTextIsNotBlank(textList.get(6))) {
            this.ySpace += 16;
            this.textRenderer.drawString(textList.get(6).getFormattedText(), 0 + 10, this.ySpace, 0x3F3F3F, false);
            if (textList.size() > 7 && translatableTextIsNotBlank(textList.get(7))) {
                this.ySpace += 10;
                this.textRenderer.drawString(textList.get(7).getFormattedText(), 0 + 10, this.ySpace, 0x3F3F3F, false);
            }
        }
        if (this.totalYSpace == 0) {
            this.totalYSpace = this.ySpace - this.getSize();
        }
    }

    @Override
    public void actionPerformed(GuiButton button) {
        boolean bl2 = mouseX >= (double) (0 + this.listWidth - 5) && mouseX <= (double) (0 + this.listWidth + 1) && mouseY >= (double) this.getSize()
                && mouseY < (double) (this.getSize() + this.listHeight);
        if (bl2) {
            this.scrollbarDragged = true;
        }
    }

    private int getContentsHeightWithPadding() {
        return this.getContentHeight() + 4;
    }

    private int getScrollbarThumbHeight() {
        return MathHelper.clamp((int) ((float) (this.listHeight * this.listHeight) / (float) this.getContentsHeightWithPadding()), 32, this.listHeight);
    }

    private boolean translatableTextIsNotBlank(@Nullable TextComponentTranslation text) {
        if (text == null) {
            return false;
        }
        return true;
    }

    @Override
    protected int getSize() {
        return 0;
    }

    @Override
    protected void elementClicked(int index, boolean doubleClick) {

    }

    @Override
    protected boolean isSelected(int index) {
        return false;
    }

    @Override
    protected void drawBackground() {

    }

    @Override
    protected void drawSlot(int slotIdx, int entryRight, int slotTop, int slotBuffer, Tessellator tess) {

    }
}
