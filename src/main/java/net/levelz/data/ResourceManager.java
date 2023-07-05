package net.levelz.data;

import net.minecraft.util.ResourceLocation;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResourceManager {
    Map<String, File> findResources(String folder, ResourcePredicate filter) {
        Map<String, File> files = new HashMap<>();
        File dir = new File(folder);
        File[] directoryListing = dir.listFiles();
        if (directoryListing != null) {
            for (File child : directoryListing) {
                if (filter.Loop(new ResourceLocation(child.getName())))
                    files.put(child.getName(), child);
            }
        }

        return files;
    }
}
