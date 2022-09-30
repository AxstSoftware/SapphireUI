package io.github.sdxqw.sapphire.mixins;

import io.github.sdxqw.sapphire.ISapphire;
import net.minecraft.client.Minecraft;
import org.reflections.Reflections;
import org.reflections.util.ClasspathHelper;
import org.reflections.util.ConfigurationBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixins {

    @Inject(method = "startGame", at = @At("RETURN"))
    public void startEngine(CallbackInfo ci) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
        for (Class<? extends ISapphire> t : reflections.getSubTypesOf(ISapphire.class)) {
            try {
                t.newInstance().startEngine();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("ERROR: " + e);
            }
        }
    }

    @Inject(method = "shutdown", at = @At(value = "RETURN"))
    public void stopEngine(CallbackInfo ci) {
        Reflections reflections = new Reflections(new ConfigurationBuilder().setUrls(ClasspathHelper.forJavaClassPath()));
        for (Class<? extends ISapphire> t : reflections.getSubTypesOf(ISapphire.class)) {
            try {
                t.newInstance().stopEngine();
            } catch (InstantiationException | IllegalAccessException e) {
                System.out.println("ERROR: " + e);
            }
        }
    }

}
