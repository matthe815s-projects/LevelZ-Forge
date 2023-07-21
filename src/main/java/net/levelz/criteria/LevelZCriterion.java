package net.levelz.criteria;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootContext;

public class LevelZCriterion implements ICriterionTrigger<LevelZCriterion.Conditions> {
    private static final ResourceLocation ID = new ResourceLocation("levelz:level");

    @Override
    public ResourceLocation getId() {
        return ID;
    }

    @Override
    public void addListener(PlayerAdvancements playerAdvancementsIn, Listener<Conditions> listener) {

    }

    @Override
    public void removeListener(PlayerAdvancements playerAdvancementsIn, Listener<Conditions> listener) {

    }

    @Override
    public void removeAllListeners(PlayerAdvancements playerAdvancementsIn) {

    }

    @Override
    public Conditions deserializeInstance(JsonObject json, JsonDeserializationContext context) {
        NumberPredicate numberPredicate = NumberPredicate.fromJson(json.get("level"));
        return new Conditions(numberPredicate);
    }

    public void trigger(EntityPlayerMP player, int level) {
        this.trigger(player, 0);
    }

    class Conditions extends AbstractCriterionInstance {

        private final NumberPredicate numberPredicate;

        public Conditions(NumberPredicate numberPredicate) {
            super(LevelZCriterion.ID);
            this.numberPredicate = numberPredicate;
        }

        public boolean matches(EntityPlayerMP player, int level) {
            return this.numberPredicate.test(level);
        }
    }

}
