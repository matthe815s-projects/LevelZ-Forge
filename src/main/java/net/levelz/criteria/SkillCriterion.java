package net.levelz.criteria;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;

import net.minecraft.advancements.ICriterionTrigger;
import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.advancements.critereon.AbstractCriterionInstance;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ResourceLocation;

public class SkillCriterion implements ICriterionTrigger<SkillCriterion.Conditions> {
    static final ResourceLocation ID = new ResourceLocation("levelz:skill");

    public SkillCriterion(ResourceLocation criterionIn) {
        super();
    }

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
        SkillPredicate skillPredicate = SkillPredicate.fromJson(json.get("skill_name"));
        NumberPredicate skillLevelPredicate = NumberPredicate.fromJson(json.get("skill_level"));
        return new Conditions(skillPredicate, skillLevelPredicate);
    }

    public void trigger(EntityPlayerMP player, String skillName, int skillLevel) {
        this.trigger(player, skillName, skillLevel);
    }

    class Conditions extends AbstractCriterionInstance {
        private final SkillPredicate skillPredicate;
        private final NumberPredicate skillLevelPredicate;

        public Conditions(SkillPredicate skillPredicate, NumberPredicate skillLevelPredicate) {
            super(SkillCriterion.ID);
            this.skillPredicate = skillPredicate;
            this.skillLevelPredicate = skillLevelPredicate;
        }

        public boolean matches(EntityPlayerMP player, String skillName, int skillLevel) {
            return this.skillPredicate.test(skillName) && skillLevelPredicate.test(skillLevel);
        }
    }

}
