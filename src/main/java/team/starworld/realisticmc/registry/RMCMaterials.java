package team.starworld.realisticmc.registry;

import com.gregtechceu.gtceu.api.data.chemical.material.info.MaterialFlag;

import java.util.List;

import static com.gregtechceu.gtceu.common.data.GTMaterials.*;

public class RMCMaterials {

    public static class GTMaterials {

        public static void init () {
            List.of(
                Iron, Steel, Aluminium
            ).forEach(material -> material.addFlags(GTMaterialFlags.GENERATE_SHAFTS));
        }

    }

    public static class GTMaterialFlags {

        public static final MaterialFlag GENERATE_SHAFTS = new MaterialFlag.Builder("rmc_shafts").build();

    }

}
