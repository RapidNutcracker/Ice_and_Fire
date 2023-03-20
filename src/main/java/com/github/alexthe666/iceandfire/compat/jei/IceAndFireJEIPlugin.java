package com.github.alexthe666.iceandfire.compat.jei;

import com.github.alexthe666.iceandfire.block.IafBlockRegistry;
import com.github.alexthe666.iceandfire.compat.jei.firedragonforge.FireDragonForgeCategory;
import com.github.alexthe666.iceandfire.compat.jei.icedragonforge.IceDragonForgeCategory;
import com.github.alexthe666.iceandfire.compat.jei.lightningdragonforge.LightningDragonForgeCategory;
import com.github.alexthe666.iceandfire.enums.EnumSkullType;
import com.github.alexthe666.iceandfire.item.IafItemRegistry;
import com.github.alexthe666.iceandfire.recipe.DragonForgeRecipe;
import com.github.alexthe666.iceandfire.recipe.IafRecipeRegistry;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.client.Minecraft;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Collectors;

@JeiPlugin
public class IceAndFireJEIPlugin implements IModPlugin {

    public static RecipeType<DragonForgeRecipe> FIRE_DRAGON_FORGE_TYPE = new RecipeType<>(FireDragonForgeCategory.UID, DragonForgeRecipe.class);
    public static RecipeType<DragonForgeRecipe> ICE_DRAGON_FORGE_TYPE = new RecipeType<>(IceDragonForgeCategory.UID, DragonForgeRecipe.class);
    public static RecipeType<DragonForgeRecipe> LIGHTNING_DRAGON_FORGE_TYPE = new RecipeType<>(LightningDragonForgeCategory.UID, DragonForgeRecipe.class);


    public static final ResourceLocation MOD = new ResourceLocation("iceandfire:iceandfire");
    public static final ResourceLocation FIRE_DRAGON_FORGE_ID = new ResourceLocation("iceandfire:fire_dragon_forge");
    public static final ResourceLocation ICE_DRAGON_FORGE_ID = new ResourceLocation("iceandfire:ice_dragon_forge");
    public static final ResourceLocation LIGHTNING_DRAGON_FORGE_ID = new ResourceLocation("iceandfire:lightning_dragon_forge");

    private void addDescription(IRecipeRegistration registry, ItemStack itemStack) {
        registry.addIngredientInfo(itemStack, VanillaTypes.ITEM_STACK, Component.translatable(itemStack.getDescriptionId() + ".jei_desc"));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        List<DragonForgeRecipe> forgeRecipeList = Minecraft.getInstance().level.getRecipeManager().getAllRecipesFor(DragonForgeRecipe.Type.INSTANCE);

        List<DragonForgeRecipe> fire = forgeRecipeList.stream().filter(item -> item.getDragonType().equals("fire")).collect(Collectors.toList());
        List<DragonForgeRecipe> ice = forgeRecipeList.stream().filter(item -> item.getDragonType().equals("ice")).collect(Collectors.toList());
        List<DragonForgeRecipe> lightning = forgeRecipeList.stream().filter(item -> item.getDragonType().equals("lightning")).collect(Collectors.toList());

        registration.addRecipes(FIRE_DRAGON_FORGE_TYPE, fire);
        registration.addRecipes(ICE_DRAGON_FORGE_TYPE, ice);
        registration.addRecipes(LIGHTNING_DRAGON_FORGE_TYPE, lightning);

        addDescription(registration, new ItemStack(IafItemRegistry.FIRE_DRAGON_BLOOD.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.ICE_DRAGON_BLOOD.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.LIGHTNING_DRAGON_BLOOD.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_RED.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_BRONZE.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_GRAY.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_GREEN.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_BLUE.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_WHITE.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_SAPPHIRE.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_SILVER.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_ELECTRIC.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_AMETHYST.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_COPPER.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGONEGG_BLACK.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGON_SKULL_FIRE.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGON_SKULL_ICE.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.DRAGON_SKULL_LIGHTNING.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.FIRE_STEW.get()));
        addDescription(registration, new ItemStack(IafItemRegistry.FROST_STEW.get()));

        for (EnumSkullType skull : EnumSkullType.values()) {
            addDescription(registration, new ItemStack(skull.skull_item.get()));
        }
        for (ItemStack stack : IafRecipeRegistry.BANNER_ITEMS) {
            registration.addIngredientInfo(stack, VanillaTypes.ITEM_STACK, Component.translatable("item.iceandfire.custom_banner.jei_desc"));
        }
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        registration.addRecipeCategories(new FireDragonForgeCategory());
        registration.addRecipeCategories(new IceDragonForgeCategory());
        registration.addRecipeCategories(new LightningDragonForgeCategory());
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registry) {
        registry.addRecipeCatalyst(new ItemStack(IafBlockRegistry.DRAGONFORGE_FIRE_CORE.get()), FIRE_DRAGON_FORGE_TYPE);
        registry.addRecipeCatalyst(new ItemStack(IafBlockRegistry.DRAGONFORGE_ICE_CORE.get()), ICE_DRAGON_FORGE_TYPE);
        registry.addRecipeCatalyst(new ItemStack(IafBlockRegistry.DRAGONFORGE_LIGHTNING_CORE.get()), LIGHTNING_DRAGON_FORGE_TYPE);
    }

    @Override
    public @NotNull ResourceLocation getPluginUid() {
        return MOD;
    }

}
