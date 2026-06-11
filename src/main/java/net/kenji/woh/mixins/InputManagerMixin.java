package net.kenji.woh.mixins;

import com.p1nero.invincible.client.InputManager;
import net.kenji.woh.api.DualSkillWeaponCapability;
import net.kenji.woh.api.basegameassets.HybridHoldableSkill;
import net.kenji.woh.gameasset.skills.ArbitersSlashSkill;
import net.minecraft.client.Minecraft;
import net.minecraft.world.InteractionHand;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.modules.HoldableSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.gamerule.EpicFightGameRules;

@Mixin(value = InputManager.class, remap = false)
public abstract class InputManagerMixin {



    @Inject(method = "handlePressing", at = @At("HEAD"), cancellable = true, remap = false)
    private static void woh$shouldHandleInput(CallbackInfo ci) {

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null) return;

        LocalPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);
        if (playerPatch == null) return;

        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(mc.player.getMainHandItem());
        if (!(cap instanceof DualSkillWeaponCapability)) return;

        SkillContainer passiveContainer = playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE);
        if (passiveContainer == null || passiveContainer.isEmpty()) return;
        if (!(passiveContainer.getSkill() instanceof HybridHoldableSkill holdableSkill)) return;
        ControlEngineAccessor accessor = (ControlEngineAccessor) ClientEngine.getInstance().controlEngine;

        if(holdableSkill.getKeyMapping().isDown()){
            if(holdableSkill.wasHoldingSkill) {
                InputManager.clearKeyCache();
                holdableSkill.wasHoldingSkill = false;
                CapabilityItem capabilityItem = playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND);

            }
            ci.cancel();
        }
    }
}