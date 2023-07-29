package net.levelz.screen;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import net.levelz.data.LevelLists;
import net.levelz.init.ConfigInit;
import net.levelz.init.KeyInit;
import net.levelz.screen.widget.SkillListScrollableWidget;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkillListScreen extends GuiScreen {

    private int backgroundWidth = 200;
    private int backgroundHeight = 215;
    private int x;
    private int y;

    private final String title;
    private final boolean crafing;

    public SkillListScreen(String title) {
        super();
        this.title = title;
        this.crafing = this.title.equals("crafting");
    }

    @Override
    public void initGui() {
        super.initGui();

        this.x = (this.width - this.backgroundWidth) / 2;
        this.y = (this.height - this.backgroundHeight) / 2;

        this.addButton(new SkillScreen.WidgetButtonPage(this.x + 180, this.y + (this.crafing ? 5 : 7), 15 - (this.crafing ? 0 : 3), 13 - (this.crafing ? 0 : 4), this.crafing ? 0 : 57, 80,
                this.crafing, true, null, button -> {
                    if (this.crafing) {
                        this.mc.displayGuiScreen(new SkillScreen());
                    } else {
                        this.mc.displayGuiScreen(new SkillInfoScreen(this.title));
                    }
                }));

        List<Integer> levelList = new ArrayList<>();
        List<List<Integer>> objectList = new ArrayList<>();
        List<String> skillList = new ArrayList<>();
        if (Objects.equals(this.title, "mining")) {
            levelList = LevelLists.miningLevelList;
            objectList = LevelLists.miningBlockList;
        } else if (Objects.equals(this.title, "alchemy")) {
            levelList = LevelLists.brewingLevelList;
            objectList = LevelLists.brewingItemList;
        } else if (Objects.equals(this.title, "smithing")) {
            levelList = LevelLists.smithingLevelList;
            objectList = LevelLists.smithingItemList;
        } else if (this.crafing) {
            levelList = LevelLists.craftingLevelList;
            objectList = LevelLists.craftingItemList;
            skillList = LevelLists.craftingSkillList;
            if (ConfigInit.CONFIG.sortCraftingRecipesBySkill) {
                //SortList.concurrentSort(skillList, skillList, levelList, objectList);
            }
        }

        //this.addButton(new SkillListScrollableWidget(this.x + 10, this.y + 22, 183, 185, levelList, objectList, skillList, this.title, this.fontRenderer));
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
        this.mc.ingameGUI.drawTexturedModalRect(i, j, 0, 0, this.backgroundWidth, this.backgroundHeight);
        this.mc.ingameGUI.drawString(this.fontRenderer, new TextComponentTranslation("text.levelz.locked_list", new TextComponentTranslation(String.format("spritetip.levelz.%s_skill", this.title))).getFormattedText(), this.x + 6, this.y + 7, 0x3F3F3F);
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
