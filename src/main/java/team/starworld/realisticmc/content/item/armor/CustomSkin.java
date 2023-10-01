package team.starworld.realisticmc.content.item.armor;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import team.starworld.realisticmc.api.item.ComponentItem;
import team.starworld.realisticmc.util.ArmorUtils;
import team.starworld.realisticmc.util.ItemStackUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static team.starworld.realisticmc.registry.RMCRegistries.rl;

public class CustomSkin extends ArmorItem implements ComponentItem {

    public static final ResourceLocation DEFAULT_SKIN = rl("textures/models/armor/diving_gear");

    public static final List <ResourceLocation> SKINS = new ArrayList <> (
        List.of(
            DEFAULT_SKIN,
            rl("textures/models/armor/hazmat_gear"),
            rl("textures/models/armor/custom_skin/space_suit")
        )
    );


    public CustomSkin (Type type, Properties properties) {
        super(ArmorMaterials.LEATHER, type, properties.durability(Integer.MAX_VALUE));
    }

    @Override
    public int getMaxDamage (ItemStack stack) { return Integer.MAX_VALUE; }

    @Override
    public int getDamage (ItemStack stack) { return 0; }

    @Override
    public void setDamage (ItemStack stack, int damage) { super.setDamage(stack, 0); }

    @OnlyIn(Dist.CLIENT)
    @Override
    public void initializeClient (@NotNull Consumer <IClientItemExtensions> consumer) {
        consumer.accept(DivingGear.Rendering.INSTANCE);
    }

    public ResourceLocation getSkin (ItemStack stack) {
        var skin = DEFAULT_SKIN;
        var key = rl("skin").toString();
        if (stack.getOrCreateTag().contains(key) && !stack.getOrCreateTag().getString(key).trim().equals(""))
            skin = new ResourceLocation(stack.getOrCreateTag().getString(key));
        return skin;
    }

    @Override
    public void onArmorTick (ItemStack stack, Level level, Player player) {
        setDamage(stack, 0);
    }

    @Override
    public void inventoryTick (@NotNull ItemStack stack, @NotNull Level level, @NotNull Entity entity, int slot, boolean selected) {
        ItemStackUtils.setUnbreakable(stack, true);
    }

    @Override
    public @Nullable String getArmorTexture (ItemStack stack, Entity entity, EquipmentSlot slot, String type) {
        var skin = getSkin(stack);
        return ArmorUtils.getArmorTexture(skin.getNamespace(), skin.getPath() + ".png");
    }

    @Override
    public void appendHoverText (@NotNull ItemStack stack, @Nullable Level level, @NotNull List <Component> list, @NotNull TooltipFlag flag) {
        list.add(Component.literal(this.getSkin(stack).toString()).withStyle(Style.EMPTY.withColor(ChatFormatting.GRAY)));
    }

    @Override
    public void fillItemCategory (@NotNull CreativeModeTab tab, List <ItemStack> items) {
        if (tab == CreativeModeTabs.searchTab()) for (var skin : SKINS) {
            if (skin == DEFAULT_SKIN) continue;
            var stack = new ItemStack(this);
            stack.getOrCreateTag().putString(rl("skin").toString(), skin.toString());
            items.add(stack);
        }
    }

}
