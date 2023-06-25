package net.levelz.criteria;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.util.JsonUtils;

public class NumberPredicate {
    private final int levelZ;

    public NumberPredicate(int levelZ) {
        this.levelZ = levelZ;
    }

    public boolean test(int level) {
        if (this.levelZ == level)
            return true;
        else
            return false;
    }

    public static NumberPredicate fromJson(JsonElement json) {
        int level = JsonUtils.getInt(json, "level");
        return new NumberPredicate(level);
    }

    public JsonElement toJson() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("level", (Number) this.levelZ);
        return jsonObject;
    }

}
