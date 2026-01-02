package net.kenji.woh.gameasset;

import net.kenji.woh.registry.WohSkills;
import net.kenji.woh.registry.WohColliderPreset;
import net.kenji.woh.registry.animation.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
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
                .category(WohWeaponCategories.SHOTOGATANA)
                .styleProvider((playerPatch) -> {
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
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_WALK)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.RUN, ShotogatanaAnimations.SHOTOGATANA_RUN)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD)
                .innateSkill(CapabilityItem.Styles.SHEATH, (itemstack) -> WohSkills.SHEATH_STANCE);
        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> TESSEN = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.TESSEN)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        if (patch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WohWeaponCategories.TESSEN) {
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
                                        == WohWeaponCategories.TESSEN)

                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.DAGGER)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        TessenAnimations.TESSEN_AUTO_1,
                        TessenAnimations.TESSEN_AUTO_2,
                        TessenAnimations.TESSEN_AUTO_3,
                        TessenAnimations.TESSEN_AUTO_4,
                        Animations.DAGGER_DASH, Animations.DAGGER_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TessenAnimations.TESSEN_DUAL_AUTO_1,
                        TessenAnimations.TESSEN_DUAL_AUTO_2,
                        TessenAnimations.TESSEN_DUAL_AUTO_3,
                        TessenAnimations.TESSEN_DUAL_AUTO_4,
                        TessenAnimations.TESSEN_DUAL_AUTO_5,
                        Animations.DAGGER_DUAL_DASH, TessenAnimations.TESSEN_DUAL_AIRSLASH)
                .newStyleCombo(CapabilityItem.Styles.RANGED,
                        TessenAnimations.TESSEN_SKILL_AUTO_1,
                        TessenAnimations.TESSEN_SKILL_AUTO_2,
                        TessenAnimations.TESSEN_SKILL_AUTO_3,
                        TessenAnimations.TESSEN_SKILL_AUTO_4,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_SKILL_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_DUAL_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.IDLE, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.WALK, TessenAnimations.TESSEN_SKILL_WALK)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.BLOCK, TessenAnimations.TESSEN_DUAL_GUARD)
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> WohSkills.FAN_STANCE)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.FAN_STANCE)
                .innateSkill(CapabilityItem.Styles.RANGED, (itemstack) -> WohSkills.FAN_STANCE);

        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> TSUME = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.TSUME)
                .styleProvider((playerPatch) -> {
                            return CapabilityItem.Styles.TWO_HAND;
                        }
                )
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == WohWeaponCategories.TSUME)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(WohColliderPreset.TSUME_CLAWS)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TsumeAnimations.TSUME_AUTO_1,
                        TsumeAnimations.TSUME_AUTO_2,
                        TsumeAnimations.TSUME_AUTO_3,
                        TsumeAnimations.TSUME_AUTO_4,
                        TsumeAnimations.TSUME_DASH, TsumeAnimations.TSUME_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TsumeAnimations.TSUME_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TsumeAnimations.TSUME_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TsumeAnimations.TSUME_RUN);
        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> WAKIZASHI = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.WAKIZASHI)
                .styleProvider((playerPatch) -> {
                            if(playerPatch instanceof PlayerPatch<?> patch) {
                                if (patch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WohWeaponCategories.WAKIZASHI) {
                                    return CapabilityItem.Styles.TWO_HAND;
                                }
                            }
                            return CapabilityItem.Styles.ONE_HAND;
                        }
                )
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == WohWeaponCategories.WAKIZASHI)

                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.DAGGER)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        WakizashiAnimations.WAKIZASHI_AUTO_1,
                        WakizashiAnimations.WAKIZASHI_AUTO_2,
                        WakizashiAnimations.WAKIZASHI_AUTO_3,
                        Animations.DAGGER_DASH, Animations.DAGGER_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_1,
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_2,
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_3,
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_4,
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_5,
                        Animations.DAGGER_DUAL_DASH, TessenAnimations.TESSEN_DUAL_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, WakizashiAnimations.WAKIZASHI_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, WakizashiAnimations.WAKIZASHI_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, WakizashiAnimations.WAKIZASHI_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, WakizashiAnimations.WAKIZASHI_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WakizashiAnimations.WAKIZASHI_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WakizashiAnimations.WAKIZASHI_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WakizashiAnimations.WAKIZASHI_DUAL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WakizashiAnimations.WAKIZASHI_DUAL_GUARD);
        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> ODACHI = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.ODACHI)
                .styleProvider((playerPatch) -> {
                            return CapabilityItem.Styles.TWO_HAND;
                        }
                )
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.GREATSWORD)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        OdachiAnimations.ODACHI_AUTO_1,
                        OdachiAnimations.ODACHI_AUTO_2,
                        OdachiAnimations.ODACHI_AUTO_3,
                        OdachiAnimations.ODACHI_AUTO_4,
                        OdachiAnimations.ODACHI_AUTO_5,
                        OdachiAnimations.ODACHI_DASH, OdachiAnimations.ODACHI_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, OdachiAnimations.ODACHI_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, OdachiAnimations.ODACHI_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, OdachiAnimations.ODACHI_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, OdachiAnimations.ODACHI_GUARD);
        return builder;
    };
}
