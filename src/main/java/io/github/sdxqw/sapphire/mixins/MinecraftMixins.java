package io.github.sdxqw.sapphire.mixins;

import io.github.sdxqw.sapphire.Sapphire;
import net.minecraft.client.Minecraft;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class)
public class MinecraftMixins {

    @Inject(method = "startGame", at = @At(value = "HEAD", shift = At.Shift.AFTER))
    public void startEngine(CallbackInfo ci) {
        Sapphire.getInstance().startEngine();
    }

    @Inject(method = "shutdown", at = @At(value = "RETURN"))
    public void stopEngine(CallbackInfo ci) {
        Sapphire.getInstance().stopEngine();
    }

}
