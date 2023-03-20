package com.github.alexthe666.iceandfire.compat.jei.lightningdragonforge;

import com.github.alexthe666.iceandfire.IceAndFire;
import com.github.alexthe666.iceandfire.compat.jei.IceAndFireJEIPlugin;
import com.github.alexthe666.iceandfire.recipe.DragonForgeRecipe;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;

import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;


public class LightningDragonForgeCategory implements IRecipeCategory<DragonForgeRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(IceAndFire.MODID, "lightning_dragon_forge");
    public LightningDragonForgeDrawable drawable;


    public LightningDragonForgeCategory() {
        drawable = new LightningDragonForgeDrawable();
    }

    @Override
    public RecipeType<DragonForgeRecipe> getRecipeType() {
        return IceAndFireJEIPlugin.LIGHTNING_DRAGON_FORGE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("iceandfire.lightning_dragon_forge");
    }

    @Override
    public @NotNull IDrawable getBackground() {
        return drawable;
    }

    @Override
    public @NotNull IDrawable getIcon() {
        return null;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, @NotNull DragonForgeRecipe recipe, @NotNull IFocusGroup focuses) {
        // IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

        if (recipe.getIngredients().size() > 0) {
            builder.addSlot(RecipeIngredientRole.INPUT, 64, 29).addIngredients(recipe.getInput());
        }

        builder.addSlot(RecipeIngredientRole.INPUT, 82, 29).addIngredients(recipe.getBlood());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 30).addItemStack(recipe.getResultItem());
    }
}
