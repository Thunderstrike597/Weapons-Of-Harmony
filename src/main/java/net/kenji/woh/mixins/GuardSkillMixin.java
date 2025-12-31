package net.kenji.woh.mixins;

import net.kenji.woh.gameasset.WohWeaponCategories;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.animation.TessenAnimations;
import net.kenji.woh.registry.animation.TsumeAnimations;
import net.kenji.woh.render.EnhancedKatanaRender;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.guard.GuardSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCategory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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
                    boolean isSheathed = EnhancedKatanaRender.sheathWeapon.getOrDefault(player.getOriginal().getUUID(), false);
                    if (isSheathed) {
                        return ShotogatanaAnimations.SHOTOGATANA_GUARD;
                    } else return ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_GUARD;
                }
        );
        guardMotions.put(
                WohWeaponCategories.TESSEN,
                (item, player) -> {
                    if (item.getStyle(player) == CapabilityItem.Styles.ONE_HAND) {
                        return TessenAnimations.TESSEN_GUARD;
                    }
                    else return TessenAnimations.TESSEN_DUAL_GUARD;
                }
        );
        guardMotions.put(
                WohWeaponCategories.TSUME,
                (item, player) -> {
                   return TsumeAnimations.TSUME_GUARD;
                }
        );
    }
}
