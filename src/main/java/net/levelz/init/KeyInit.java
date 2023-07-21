package net.levelz.init;

import java.io.FileWriter;
import java.io.IOException;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class KeyInit {
    public static KeyBinding screenKey;
    public static KeyBinding devKey;
    private static boolean keyBoolean;

    public static void init() {
        // Keybinds
        screenKey = new KeyBinding("key.levelz.openskillscreen", 51, "category.levelz.keybind");
        devKey = new KeyBinding("key.levelz.dev", 52,  "category.levelz.keybind");
//        // Registering
//        KeyBinding.registerKeyBinding(screenKey);
//        // Callback
//        ClientTickEvents.END_CLIENT_TICK.register(client -> {
//            if (screenKey.wasPressed()) {
//                if (!keyBoolean) {
//                    client.setScreen(new SkillScreen());
//                }
//                keyBoolean = true;
//            } else if (keyBoolean) {
//                keyBoolean = false;
//            }
//        });
    }

    public static void writeId(String string) {
        try (FileWriter idFile = new FileWriter("idlist.json", true)) {
            idFile.append("\"" + string + "\",");
            idFile.append(System.lineSeparator());
        } catch (IOException e) {
        }
    }

}