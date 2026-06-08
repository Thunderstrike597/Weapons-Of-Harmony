package net.kenji.woh.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import com.p1nero.invincible.client.InputManager;
import net.kenji.woh.api.manager.AttackManager;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.Input;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.phys.Vec2;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.client.input.PlayerInputState;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataManager;

@Mixin(value = InputManager.class, remap = false)
public class InputMixin {

    @Inject(method = "checkDirectionKeyDown", at = @At("HEAD"), cancellable = true)
    private static void cancelMovementImpulse(SkillDataManager manager, SkillDataKey<Boolean> skillDataKey, KeyMapping key, CallbackInfo ci) {
        Minecraft mc = Minecraft.getInstance();
        boolean attacking = AttackManager.isInAttack.getOrDefault(mc.player.getUUID(), false);
        ci.cancel();
        InputConstants.Key input = key.getKey();
        boolean physicallyDown = InputConstants.isKeyDown(mc.getWindow().getWindow(), input.getValue());

        manager.setDataSync(skillDataKey, physicallyDown);
    }
}
