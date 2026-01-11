package net.kenji.woh.mixins;

import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.gameasset.WohStyles;
import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.registry.animation.*;
import net.kenji.woh.render.ShotogatanaRender;
import net.minecraft.world.InteractionHand;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.LivingMotion;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.Map;
import java.util.function.BiFunction;

@Mixin(value = GuardSkill.class, priority = 5000, remap = false)
public abstract class GuardSkillMixin {

    @Shadow @Final
    protected Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardMotions;

    @Shadow @Final
    protected Map<WeaponCategory, BiFunction<CapabilityItem, PlayerPatch<?>, ?>> guardBreakMotions;

    @Inject(method = "<init>", at = @At("TAIL"))
    private void woh$addCustomCategory(CallbackInfo ci) {
        GuardSkill self = (GuardSkill) (Object) this;

        guardMotions.put(
                WohWeaponCategories.SHOTOGATANA,
                (item, player) -> {
                    boolean isSheathed = ShotogatanaManager.sheathWeapon.getOrDefault(player.getOriginal().getUUID(), false);
                    if (isSheathed) {
                        return ShotogatanaAnimations.SHOTOGATANA_GUARD;
                    } else return ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_GUARD;
                }
        );

        guardMotions.put(
                WohWeaponCategories.TSUME,
                (item, player) -> {
                   return TsumeAnimations.TSUME_GUARD;
                }
        );
        guardMotions.put(
                WohWeaponCategories.WAKIZASHI,
                (item, player) -> {
                    if (item.getStyle(player) == CapabilityItem.Styles.ONE_HAND) {
                        return WakizashiAnimations.WAKIZASHI_GUARD;
                    }
                    return WakizashiAnimations.WAKIZASHI_DUAL_GUARD;
                }
        );
        guardMotions.put(
                WohWeaponCategories.ODACHI,
                (item, player) -> {
                    return OdachiAnimations.ODACHI_GUARD;
                }
        );
    }
    @Inject(method = "getGuardMotion", at = @At("RETURN"), cancellable = true, remap = false)
    private void getCustomLivingMotion(PlayerPatch<?> playerpatch, CapabilityItem itemCapability, GuardSkill.BlockType blockType, CallbackInfoReturnable<StaticAnimation> cir) {
        SkillContainer container = playerpatch.getSkill(WohSkills.ARBITERS_SLASH);
        if(container != null){
            if(!container.isActivated()){
                cir.setReturnValue(Animations.LONGSWORD_GUARD);
            }
        }
    }
}
