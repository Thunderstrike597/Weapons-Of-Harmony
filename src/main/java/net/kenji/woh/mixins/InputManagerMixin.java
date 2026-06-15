package net.kenji.woh.mixins;

import com.mojang.blaze3d.platform.InputConstants;
import com.p1nero.invincible.client.InputManager;
import net.kenji.woh.api.DualSkillWeaponCapability;
import net.kenji.woh.api.basegameassets.HybridHoldableSkill;
import net.kenji.woh.api.interfaces.IHybridSkill;
import net.kenji.woh.api.manager.AttackManager;
import net.kenji.woh.gameasset.skills.WohSkillSlot;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillDataKey;
import yesman.epicfight.skill.SkillDataManager;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mixin(value = InputManager.class, remap = false)
public abstract class InputManagerMixin {



    @Inject(method = "handlePressing", at = @At("HEAD"), cancellable = true, remap = false)
    private static void woh$shouldHandleInput(CallbackInfo ci) {

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        LocalPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);
        if (playerPatch == null) return;

        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(mc.player.getMainHandItem());
        if (!(cap instanceof DualSkillWeaponCapability weaponCapability)) return;

        SkillContainer passiveContainer = playerPatch.getSkill(weaponCapability.getSecondarySkillSlot());
        if (passiveContainer == null || passiveContainer.isEmpty()) return;
        if (!(passiveContainer.getSkill() instanceof IHybridSkill holdableSkill)) return;
        ControlEngineAccessor accessor = (ControlEngineAccessor) ClientEngine.getInstance().controlEngine;

        if(holdableSkill.getKeyMapping().isDown()){
            if(holdableSkill.getWasHoldingSkill()) {
                InputManager.clearKeyCache();
                holdableSkill.setWasHoldingSkill(false);
                CapabilityItem capabilityItem = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);
            }
            ci.cancel();
        }
    }
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