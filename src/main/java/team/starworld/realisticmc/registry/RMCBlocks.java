package team.starworld.realisticmc.registry;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.gregtechceu.gtceu.api.data.chemical.material.stack.ItemMaterialInfo;
import com.gregtechceu.gtceu.api.data.tag.TagPrefix;
import com.gregtechceu.gtceu.api.item.tool.GTToolType;
import com.gregtechceu.gtceu.api.registry.GTRegistries;
import com.gregtechceu.gtceu.common.data.GTItems;
import com.simibubi.create.content.kinetics.BlockStressDefaults;
import com.simibubi.create.content.kinetics.simpleRelays.BracketedKineticBlockModel;
import com.simibubi.create.foundation.data.BlockStateGen;
import com.simibubi.create.foundation.data.CreateRegistrate;
import com.simibubi.create.foundation.data.SharedProperties;
import com.tterrag.registrate.util.entry.BlockEntry;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.registries.ForgeRegistries;
import team.starworld.realisticmc.content.block.ShaftMaterialBlock;
import team.starworld.realisticmc.data.resources.ResourceGenerator;

import java.util.*;

import static com.gregtechceu.gtceu.common.data.GTBlocks.unificationBlock;

public class RMCBlocks {


    public static Map <String, List <BlockEntry <? extends Block>>> MATERIAL_BLOCKS = new HashMap <> ();

    public static void generateShafts () {
        if (!MATERIAL_BLOCKS.containsKey("shaft")) MATERIAL_BLOCKS.put("shaft", new ArrayList <> ());
        var shafts = MATERIAL_BLOCKS.get("shaft");
        for (var i : GTRegistries.MATERIALS.values()) {
            if (!i.hasFlag(RMCMaterials.GTMaterialFlags.GENERATE_SHAFTS)) continue;
            var entry = RMCRegistries.REGISTRATE
                .block("%s_shaft".formatted(i.getName()), properties -> new ShaftMaterialBlock(i, properties))
                .initialProperties(SharedProperties::stone)
                .properties(properties -> properties.mapColor(MapColor.METAL).forceSolidOn())
                .transform(BlockStressDefaults.setNoImpact())
                .blockstate(BlockStateGen.axisBlockProvider(false))
                .transform(unificationBlock(TagPrefix.block, i))
                .onRegister(CreateRegistrate.blockModel(() -> BracketedKineticBlockModel::new))
                .item(ShaftMaterialBlock.BlockItem::new)
                .onRegister(GTItems.materialInfo(new ItemMaterialInfo(i.getMaterialComponents())))
                .tag(Objects.requireNonNull(ForgeRegistries.ITEMS.tags()).createTagKey(RMCRegistries.rl("shafts/" + i.getName())))
                .build()
                .tag(BlockTags.MINEABLE_WITH_PICKAXE).tag(GTToolType.WRENCH.harvestTag)
                .register();
            shafts.add(entry);
            ResourceGenerator.INSTANCE.getGenerators().add(
                (generator, manager, pack) -> {
                    var jsonBlockState = JsonParser.parseString(ShaftMaterialBlock.BLOCK_STATE_MODEL);
                    pack.addBlockState(RMCRegistries.rl("%s_shaft".formatted(i.getName())), jsonBlockState);
                    var jsonItem = new JsonObject();
                    jsonItem.addProperty("parent", "create:block/shaft");
                    pack.addItemModel(RMCRegistries.rl("%s_shaft".formatted(i.getName())), jsonItem);
                }
            );
        }
    }

}
