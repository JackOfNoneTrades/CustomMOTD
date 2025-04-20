package org.fentanylsolutions.cmotd.mixins.early.minecraft;

import net.minecraft.network.ServerStatusResponse;
import net.minecraft.server.MinecraftServer;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import com.mojang.authlib.GameProfile;

@Mixin(MinecraftServer.class)
public class MixinMinecraftServer {

    @SuppressWarnings("unused")
    @Redirect(
        method = "tick",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/network/ServerStatusResponse$PlayerCountData;func_151330_a([Lcom/mojang/authlib/GameProfile;)V"))
    private void redirectSetGameProfileArray(ServerStatusResponse.PlayerCountData instance, GameProfile[] profiles) {
        /* pass */
    }
}
