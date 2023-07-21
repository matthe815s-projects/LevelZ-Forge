package net.levelz.init;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderInit {

    public static final ResourceLocation GUI_ICONS = new ResourceLocation("levelz:textures/gui/icons.png");
    public static final ResourceLocation SKILL_TAB_ICON = new ResourceLocation("levelz:textures/gui/skill_tab_icon.png");
    public static final ResourceLocation BAG_TAB_ICON = new ResourceLocation("levelz:textures/gui/bag_tab_icon.png");

    public static final ResourceLocation MINEABLE_INFO = new ResourceLocation("levelz", "mineable_info");
    public static final ResourceLocation MINEABLE_LEVEL_INFO = new ResourceLocation("levelz", "mineable_level_info");

    public static final boolean isInventorioLoaded = Loader.instance().isModLoaded("inventorio");

    public static void init() {
//        TabRegistry.registerInventoryTab(new VanillaInventoryTab(Text.translatable("container.crafting"), BAG_TAB_ICON, 0, InventoryScreen.class));
//        TabRegistry.registerInventoryTab(new LevelzTab(Text.translatable("screen.levelz.skill_screen"), SKILL_TAB_ICON, 1, SkillScreen.class, SkillInfoScreen.class, SkillListScreen.class));
//
//        HudRenderCallback.EVENT.register((drawContext, tickDelta) -> {
//            TooltipUtil.renderTooltip(Minecraft.getMinecraft(), drawContext);
//        });
    }
}
