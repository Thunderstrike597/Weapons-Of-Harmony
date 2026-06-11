package net.kenji.woh.mixins;

import net.kenji.woh.api.basegameassets.HybridHoldableSkill;
import net.kenji.woh.gameasset.skills.ArbitersSlashSkill;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.player.LocalPlayer;
import org.jetbrains.annotations.NotNull;
import org.jline.utils.Log;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.client.input.InputManager;
import yesman.epicfight.api.client.input.action.EpicFightInputAction;
import yesman.epicfight.api.client.input.action.InputAction;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.config.ClientConfig;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPSkillRequest;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.skill.modules.HoldableSkill;

@Mixin(value = ControlEngine.class, remap = false)
public abstract class ControlEngineMixin {

    @Shadow
    private SkillSlot reservedOrHoldingSkillSlot;
    @Shadow
    private KeyMapping currentHoldingKey;

    @Shadow
    private LocalPlayerPatch playerPatch;

    @Shadow
    protected abstract boolean isCurrentHoldingAction(@NotNull InputAction other);

    @Shadow
    protected abstract void reserveKey(SkillSlot slot, InputAction action);

    @Shadow
    private LocalPlayer player;

    @Shadow
    public abstract void lockHotkeys();
    // existing shadows...

    // New shadows needed
    @Shadow private boolean weaponInnatePressToggle;
    @Shadow private int weaponInnatePressCounter;

    /**
     * When same-key and charge skill not yet activated:
     * redirect the long-press from WEAPON_INNATE to WEAPON_PASSIVE
     */
    @Inject(method = "inputTick", at = @At("HEAD"), remap = false)
    private void woh$redirectLongPressToCharge(CallbackInfo ci) {
        if (!weaponInnatePressToggle) return;

        SkillContainer passiveContainer = playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE);
        if (passiveContainer == null || passiveContainer.isEmpty()) return;
        if (!(passiveContainer.getSkill() instanceof HybridHoldableSkill hybridHoldableSkill)) return;
        if (passiveContainer.isActivated()) return;

        if (!InputManager.isBoundToSamePhysicalInput(
                EpicFightInputAction.WEAPON_INNATE_SKILL, EpicFightInputAction.ATTACK)) return;

        // Key released before long press threshold — suppress the attack
        if (!InputManager.isActionActive(EpicFightInputAction.WEAPON_INNATE_SKILL)) {
            weaponInnatePressToggle = false;
            weaponInnatePressCounter = 0;
            return;
        }

        if (weaponInnatePressCounter > ClientConfig.longPressCounter) {
            EpicFightNetworkManager.sendToServer(
                    new CPSkillRequest(SkillSlots.WEAPON_PASSIVE, CPSkillRequest.WorkType.HOLD_START));
            ControlEngine controlEngine = (ControlEngine)(Object) this;
            controlEngine.setHoldingKey(SkillSlots.WEAPON_PASSIVE, hybridHoldableSkill.getKeyMapping());

            weaponInnatePressToggle = false;
            weaponInnatePressCounter = 0;

            hybridHoldableSkill.wasHoldingSkill = true;

        }
    }

    /**
     * Force the hold to stay alive when we are using WEAPON_INNATE key for WEAPON_PASSIVE slot
     */
    @Inject(method = "isCurrentHoldingActionActive", at = @At("RETURN"), cancellable = true, remap = false)
    private void woh$isCurrentHoldingActionActive(CallbackInfoReturnable<Boolean> cir) {
        if (this.currentHoldingKey == EpicFightKeyMappings.WEAPON_INNATE_SKILL) {

            boolean physicallyDown = EpicFightKeyMappings.WEAPON_INNATE_SKILL.isDown();
            cir.setReturnValue(physicallyDown);
        }
    }

    @Inject(method = "handleSeparateWeaponInnateSkill", at = @At("HEAD"), cancellable = true, remap = false)
    private void woh$handleSeparateWeaponInnateSkill(CallbackInfo ci) {
        ControlEngine self = (ControlEngine)(Object) this;
        SkillContainer passiveContainer = this.playerPatch.getSkill(SkillSlots.WEAPON_PASSIVE);

        if (passiveContainer != null && passiveContainer.getSkill() instanceof HoldableSkill holdableSkill) {
            // If charge skill is already activated, don't intercept — let combo work normally
            if (passiveContainer.isActivated()) return;
            ci.cancel();
            // Charging phase: reserve key for WEAPON_PASSIVE, don't fire combo
            if (this.playerPatch.isEpicFightMode()
                    && !this.isCurrentHoldingAction(EpicFightInputAction.WEAPON_INNATE_SKILL)) {
                if (!InputManager.isBoundToSamePhysicalInput(EpicFightInputAction.ATTACK, EpicFightInputAction.WEAPON_INNATE_SKILL)) {
                    if (!this.player.isSpectator()) {
                        this.reserveKey(SkillSlots.WEAPON_PASSIVE, (InputAction) EpicFightInputAction.WEAPON_INNATE_SKILL);
                    }
                }
            }
        }
    }
}