package com.github.x3r.synchroma.common.recipe;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.minecraft.util.GsonHelper;
import org.junit.Test;

import java.io.FileNotFoundException;
import java.io.FileReader;

public class SynchromaRecipeSerializerTest {

    @Test
    public void fromJson() throws FileNotFoundException {

        JsonObject json = JsonParser.parseReader(new FileReader(System.getProperty("user.dir") + "\\src\\test\\java\\com\\github\\x3r\\synchroma\\common\\recipe\\centrifuge_recipe.json")).getAsJsonObject();
        if (json.has("input_items")) {
            json.getAsJsonArray("input_items").asList().stream().map(element -> {
                System.out.println(GsonHelper.getAsJsonObject(element.getAsJsonObject(), "ingredient"));
                return element.isJsonArray();
            }).toArray();
        }
        if(json.has("input_fluids")) {
            json.getAsJsonArray("input_fluids").asList().stream().map(element -> {
                JsonObject obj = GsonHelper.getAsJsonObject(element.getAsJsonObject(), "fluid_stack");
                System.out.println(obj.get("fluid"));
                return element.isJsonArray();
            }).toArray();
        }
//        System.out.println(json);
    }
}