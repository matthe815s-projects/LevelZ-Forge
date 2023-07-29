package net.levelz.screen.widget;

import java.util.ArrayList;
import java.util.List;

import net.levelz.data.LevelLists;
import net.levelz.screen.SkillInfoScreen;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fml.client.GuiScrollingList;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class SkillListScrollableWidget extends GuiScrollingList {

    private final String title;
    private final boolean mining;

    private final List<Integer> levelList;
    private final List<List<Integer>> objectList;
    private final List<String> skillList;

    private final FontRenderer textRenderer;

    private int totalYSpace = 0;
    private int ySpace = 0;

    private boolean scrollbarDragged;

    public SkillListScrollableWidget(int x, int y, int width, int height, List<Integer> levelList, List<List<Integer>> objectList, List<String> skillList, String title, FontRenderer textRenderer) {
        super(Minecraft.getMinecraft(), x, y, width, height, 0, 0);
        this.title = title;
        this.textRenderer = textRenderer;
        this.levelList = levelList;
        this.objectList = objectList;
        this.skillList = skillList;
        this.mining = this.title.equals("mining");
    }

    @Override
    protected int getContentHeight() {
        return this.totalYSpace;
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float delta) {
        this.ySpace = this.getSize();
        // 9 objects next to each other
        for (int u = 0; u < levelList.size(); u++) {
            if (objectList.get(u).isEmpty()) {
                continue;
            }

            if (!skillList.isEmpty()) {
                this.textRenderer.drawString(new TextComponentTranslation("item.levelz." + skillList.get(u) + ".tooltip", levelList.get(u)).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
            } else {
                this.textRenderer.drawString(new TextComponentTranslation("text.levelz.level", levelList.get(u)).getFormattedText(), 0, this.ySpace, 0x3F3F3F, false);
            }

            int listSplitter = 0;
            int gridXSpace = 0;
            this.ySpace += 16;

            for (int k = 0; k < objectList.get(u).size(); k++) {
                ItemStack stack = null;
                List<ITextComponent> tooltip = new ArrayList<ITextComponent>();
                if (this.mining) {
                    Block block = ForgeRegistries.BLOCKS.getValues().get(objectList.get(u).get(k));
                    stack = new ItemStack(block);
                    tooltip.add(new TextComponentString(block.getLocalizedName()));
                } else {
                    Item item = ForgeRegistries.ITEMS.getValues().get(objectList.get(u).get(k));
                    tooltip.add(new TextComponentString(item.getUnlocalizedName()));
                    stack = item.getDefaultInstance();

                    if (BrewingRecipeRegistry.isValidIngredient(item.getDefaultInstance()) && LevelLists.potionList.contains(item)) {
                        int index = LevelLists.potionList.indexOf(item);
                        Potion potion = (Potion) LevelLists.potionList.get(index + 1);
                        //tooltip.add(Text.of("Ingredient for " + Text.translatable(((PotionItem) PotionUtil.setPotion(potionStack, potion).getItem()).getTranslationKey(potionStack)).getString()));
                    }
                }
                if (stack != null) {
                    //context.drawItem(stack, gridXSpace, this.ySpace);

//                    if (!tooltip.isEmpty() && this.isPointWithinBounds(gridXSpace - this.getX(), this.ySpace - this.getY() - (int) this.getScrollY(), 16, 16, mouseX, mouseY)) {
//                        context.disableScissor();
//                        context.getMatrices().push();
//                        context.getMatrices().translate(0.0, this.getScrollY(), 0.0);
//                        context.drawTooltip(this.textRenderer, tooltip, mouseX, mouseY);
//                        context.drawText(this.textRenderer, "", 0, 0, 0, false); // need this cause of render bug
//                        context.getMatrices().pop();
//                        context.enableScissor(this.getX(), this.getY(), this.getX() + this.width, this.getY() + this.height);
//                    }
                }
                gridXSpace += 18;
                listSplitter++;
                if (listSplitter % 9 == 0 || k == objectList.get(u).size() - 1) {
                    this.ySpace += 18;
                    gridXSpace = 0;
                }
            }
            this.ySpace += 8;
        }
        if (this.totalYSpace == 0) {
            this.totalYSpace = this.ySpace - this.getSize();
        }
    }

    private int getContentsHeightWithPadding() {
        return this.getContentHeight() + 4;
    }

    private int getScrollbarThumbHeight() {
        return MathHelper.clamp((int) ((float) (this.listHeight * this.listHeight) / (float) this.getContentsHeightWithPadding()), 32, this.listHeight);
    }

    private boolean isPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY) {
        int i = 0;
        int j = this.getSize();
        return (pointX -= (double) i) >= (double) (x - 1) && pointX < (double) (x + width + 1) && (pointY -= (double) j) >= (double) (y - 1) && pointY < (double) (y + height + 1);
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
