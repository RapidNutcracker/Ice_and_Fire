package com.github.alexthe666.iceandfire.compat.jei.icedragonforge;

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


public class IceDragonForgeCategory implements IRecipeCategory<DragonForgeRecipe> {

    public final static ResourceLocation UID = new ResourceLocation(IceAndFire.MODID, "ice_dragon_forge");
    public IceDragonForgeDrawable drawable;
    

    public IceDragonForgeCategory() {
        drawable = new IceDragonForgeDrawable();
    } 

    @Override
    public RecipeType<DragonForgeRecipe> getRecipeType() {
        return IceAndFireJEIPlugin.ICE_DRAGON_FORGE_TYPE;
    }

    @Override
    public @NotNull Component getTitle() {
        return Component.translatable("iceandfire.ice_dragon_forge");
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
        builder.addSlot(RecipeIngredientRole.INPUT, 64, 29).addIngredients(recipe.getInput());
        builder.addSlot(RecipeIngredientRole.INPUT, 82, 29).addIngredients(recipe.getBlood());
        builder.addSlot(RecipeIngredientRole.OUTPUT, 144, 30).addItemStack(recipe.getResultItem());
    }
}
// public class IceDragonForgeCategory implements IRecipeCategory<DragonForgeRecipe> {

//     public IceDragonForgeDrawable drawable;

//     public IceDragonForgeCategory() {
//         drawable = new IceDragonForgeDrawable();
//     }

//     @Override
//     public @NotNull ResourceLocation getUid() {
//         return IceAndFireJEIPlugin.ICE_DRAGON_FORGE_ID;
//     }

//     @Override
//     public @NotNull Class<? extends DragonForgeRecipe> getRecipeClass() {
//         return DragonForgeRecipe.class;
//     }

//     @Override
//     public @NotNull Component getTitle() {
//         return Component.translatable("iceandfire.ice_dragon_forge");
//     }

//     @Override
//     public @NotNull IDrawable getBackground() {
//         return drawable;
//     }

//     @Override
//     public @NotNull IDrawable getIcon() {
//         return null;
//     }

//     @Override
//     public void setIngredients(DragonForgeRecipe dragonForgeRecipe, IIngredients iIngredients) {
//         List<Ingredient> ingredientsList = new ArrayList<>();
//         ingredientsList.add(dragonForgeRecipe.getInput());
//         ingredientsList.add(dragonForgeRecipe.getBlood());
//         iIngredients.setInputIngredients(ingredientsList);
//         iIngredients.setOutput(VanillaTypes.ITEM, dragonForgeRecipe.getResultItem());
//     }

//     @Override
//     public void setRecipe(IRecipeLayoutBuilder recipeLayoutBuilder, @NotNull DragonForgeRecipe dragonForgeRecipe, @NotNull IIngredients iIngredients) {
//         IGuiItemStackGroup guiItemStacks = recipeLayoutBuilder.getItemStacks();
//         guiItemStacks.init(0, true, 64, 29);
//         guiItemStacks.init(1, true, 82, 29);
//         guiItemStacks.init(2, false, 144, 30);
//         guiItemStacks.set(iIngredients);
//     }
// }
