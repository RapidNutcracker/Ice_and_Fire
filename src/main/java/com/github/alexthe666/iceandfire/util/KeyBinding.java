package com.github.alexthe666.iceandfire.util;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import org.lwjgl.glfw.GLFW;

public class KeyBinding {

    public static final String KEY_CATEGORIES_ICEANDFIRE = "key.categories.iceandfire";

    public static final String KEY_DRAGON_BREATH = "key.iceandfire.dragon_breath";
    public static final String KEY_DRAGON_STRIKE = "key.iceandfire.dragon_strike";
    public static final String KEY_DRAGON_DESCEND = "key.iceandfire.dragon_descend";
    public static final String KEY_DRAGON_CHANGE_THIRD_PERSON_VIEW = "key.iceandfire.dragon_change_third_person_view";

    public static final KeyMapping DRAGON_BREATH_KEY = new KeyMapping(KEY_DRAGON_BREATH, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_R, KEY_CATEGORIES_ICEANDFIRE);
    public static final KeyMapping DRAGON_STRIKE_KEY = new KeyMapping(KEY_DRAGON_STRIKE, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_G, KEY_CATEGORIES_ICEANDFIRE);
    public static final KeyMapping DRAGON_DESCEND_KEY = new KeyMapping(KEY_DRAGON_DESCEND, KeyConflictContext.IN_GAME, InputConstants.Type.KEYSYM, GLFW.GLFW_KEY_X, KEY_CATEGORIES_ICEANDFIRE);;
    public static KeyMapping DRAGON_CHANGE_THIRD_PERSON_VIEW_KEY = new KeyMapping(KEY_DRAGON_CHANGE_THIRD_PERSON_VIEW, GLFW.GLFW_KEY_F7, KEY_CATEGORIES_ICEANDFIRE);;
}
