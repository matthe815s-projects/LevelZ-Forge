package net.levelz.init;

import net.levelz.entity.LevelExperienceOrbEntity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.registry.EntityEntry;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

public class EntityInit {
    public static final boolean isRedstoneBitsLoaded = Loader.instance().isModLoaded("redstonebits");

    public static void init() {
        ForgeRegistries.ENTITIES.register(new EntityEntry(LevelExperienceOrbEntity.class, "level_experience_orb"));
    }

}
