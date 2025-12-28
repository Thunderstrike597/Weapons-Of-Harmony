package net.kenji.woh.gameasset;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.registry.WOHItems;
import net.kenji.woh.registry.WOHSkills;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.animation.TessenAnimations;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TieredItem;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import java.util.function.Function;

public class WohWeaponCapabilityPresets {

    public static final Function<Item, CapabilityItem.Builder> SHOTOGATANA = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.TACHI)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch){
                        if(patch.getSkill(SkillSlots.WEAPON_INNATE).isActivated())
                            return CapabilityItem.Styles.ONE_HAND;
                        }
                        return CapabilityItem.Styles.SHEATH;
                    }
                )
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.TACHI)
                .newStyleCombo(CapabilityItem.Styles.SHEATH,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_1,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_2,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_3,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_4,
                        ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_4,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_2,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_3,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_5,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_6,
                        ShotogatanaAnimations.SHOTOGATANA_DASH, ShotogatanaAnimations.SHOTOGATANA_AIRSLASH)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_1,
                        ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_2,
                        ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_3,
                        ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_4,
                        ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_5,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_5,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_6,
                        ShotogatanaAnimations.SHOTOGATANA_DASH, ShotogatanaAnimations.SHOTOGATANA_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_WALK)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.RUN, CorruptAnimations.YAMATO_RUN)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_WALK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_GUARD)
                .innateSkill(CapabilityItem.Styles.SHEATH, (itemstack) -> WOHSkills.SHEATH_STANCE)
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> WOHSkills.SHEATH_STANCE);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> TESSEN = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(CapabilityItem.WeaponCategories.SWORD)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        if (patch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.SWORD) {
                            if (patch.getSkill(SkillSlots.WEAPON_INNATE).isActivated())
                                return CapabilityItem.Styles.RANGED;
                            return CapabilityItem.Styles.TWO_HAND;
                        }
                    }
                    return CapabilityItem.Styles.ONE_HAND;
                    }
                )
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == CapabilityItem.WeaponCategories.SWORD)

                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.DAGGER)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        TessenAnimations.TESSEN_AUTO_1,
                        TessenAnimations.TESSEN_AUTO_2,
                        TessenAnimations.TESSEN_AUTO_3,
                        TessenAnimations.TESSEN_AUTO_4,
                        Animations.DAGGER_DUAL_DASH, Animations.DAGGER_DUAL_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TessenAnimations.TESSEN_DUAL_AUTO_1,
                        TessenAnimations.TESSEN_DUAL_AUTO_2,
                        TessenAnimations.TESSEN_DUAL_AUTO_3,
                        TessenAnimations.TESSEN_DUAL_AUTO_4,
                        TessenAnimations.TESSEN_DUAL_AUTO_5,
                        Animations.DAGGER_DUAL_DASH, Animations.DAGGER_DUAL_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.RANGED,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_1,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_2,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_3,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_4,
                        Animations.DAGGER_DUAL_DASH, Animations.DAGGER_DUAL_AIR_SLASH)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_DUAL_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.BLOCK, TessenAnimations.TESSEN_DUAL_GUARD)
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> WOHSkills.FAN_STANCE)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WOHSkills.FAN_STANCE)
                .innateSkill(CapabilityItem.Styles.RANGED, (itemstack) -> WOHSkills.FAN_STANCE);

        return builder;
    };
}
