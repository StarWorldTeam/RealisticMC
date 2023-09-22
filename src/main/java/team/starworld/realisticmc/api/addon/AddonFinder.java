package team.starworld.realisticmc.api.addon;

import net.minecraftforge.fml.ModList;
import net.minecraftforge.forgespi.language.ModFileScanData;
import org.objectweb.asm.Type;

import java.lang.reflect.Constructor;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
public class AddonFinder {

    public static List <IRMCAddon> getAddons () {
        return getInstances(RMCAddon.class, IRMCAddon.class);
    }

    public static <T> List <T> getInstances (Class <?> annotationClass, Class <T> instanceClass) {
        var type = Type.getType(annotationClass);
        var instances = new ArrayList <T> ();
        for (ModFileScanData scanData : ModList.get().getAllScanData())
            for (ModFileScanData.AnnotationData annotationData : scanData.getAnnotations()) {
                if (Objects.equals(annotationData.annotationType(), type)) {
                    try {
                        Class <?> clazz = Class.forName(annotationData.memberName());
                        Class <? extends T> instanceClazz = clazz.asSubclass(instanceClass);
                        Constructor <? extends T> constructor = instanceClazz.getDeclaredConstructor();
                        T instance = constructor.newInstance();
                        instances.add(instance);
                    } catch (Throwable ignored) {}
                }
            }
        return instances;
    }

}
