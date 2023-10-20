package team.starworld.realisticmc.content.block;

import com.gregtechceu.gtceu.api.data.chemical.material.Material;
import com.simibubi.create.content.kinetics.simpleRelays.ShaftBlock;
import lombok.Getter;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

public class ShaftMaterialBlock extends ShaftBlock {

    public static final String BLOCK_STATE_MODEL = """
        {
          "variants": {
            "axis=x": {
              "model": "create:block/shaft",
              "x": 90,
              "y": 90
            },
            "axis=y": {
              "model": "create:block/shaft"
            },
            "axis=z": {
              "model": "create:block/shaft",
              "x": 90,
              "y": 180
            }
          }
        }
        """;

    @Getter
    private final Material material;

    public ShaftMaterialBlock (Material material, Properties properties) {
        super(properties);
        this.material = material;
    }

    @Override
    public @NotNull MutableComponent getName () {
        return Component.translatable("tagprefix.shaft", material.getLocalizedName());
    }

    public static class BlockItem extends net.minecraft.world.item.BlockItem {

        public BlockItem (Block block, Properties properties) {
            super(block, properties);
        }

        @Override
        public @NotNull Component getName (@NotNull ItemStack stack) {
            return getBlock().getName();
        }
    }

}
