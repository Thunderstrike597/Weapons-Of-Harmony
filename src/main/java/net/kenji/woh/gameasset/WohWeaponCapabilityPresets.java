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
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_1.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_2.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_3.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_4.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_4.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_2.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_3.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_5.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_6.getAccessor(),
                        ShotogatanaAnimations.SHOTOGATANA_DASH.getAccessor(), ShotogatanaAnimations.SHOTOGATANA_AIRSLASH.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_IDLE.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_WALK.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.RUN, ShotogatanaAnimations.SHOTOGATANA_RUN.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD.getAccessor())
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
                        TessenAnimations.TESSEN_AUTO_1.getAccessor(),
                        TessenAnimations.TESSEN_AUTO_2.getAccessor(),
                        TessenAnimations.TESSEN_AUTO_3.getAccessor(),
                        TessenAnimations.TESSEN_AUTO_4.getAccessor(),
                        Animations.DAGGER_DASH, Animations.DAGGER_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TessenAnimations.TESSEN_DUAL_AUTO_1.getAccessor(),
                        TessenAnimations.TESSEN_DUAL_AUTO_2.getAccessor(),
                        TessenAnimations.TESSEN_DUAL_AUTO_3.getAccessor(),
                        TessenAnimations.TESSEN_DUAL_AUTO_4.getAccessor(),
                        TessenAnimations.TESSEN_DUAL_AUTO_5.getAccessor(),
                        Animations.DAGGER_DUAL_DASH, TessenAnimations.TESSEN_DUAL_AIRSLASH.getAccessor())
                .newStyleCombo(CapabilityItem.Styles.RANGED,
                        TessenAnimations.TESSEN_SKILL_AUTO_1.getAccessor(),
                        TessenAnimations.TESSEN_SKILL_AUTO_2.getAccessor(),
                        TessenAnimations.TESSEN_SKILL_AUTO_3.getAccessor(),
                        TessenAnimations.TESSEN_SKILL_AUTO_4.getAccessor(),
                        TessenAnimations.TESSEN_SKILL_DASH.getAccessor(), TessenAnimations.TESSEN_SKILL_AIRSLASH.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_GUARD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_DUAL_GUARD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.IDLE, TessenAnimations.TESSEN_SKILL_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.WALK, TessenAnimations.TESSEN_SKILL_WALK.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.RUN, TessenAnimations.TESSEN_RUN.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.RANGED, LivingMotions.BLOCK, TessenAnimations.TESSEN_DUAL_GUARD.getAccessor())
                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> WohSkills.FAN_STANCE)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.FAN_STANCE)
                .innateSkill(CapabilityItem.Styles.RANGED, (itemstack) -> WohSkills.FAN_STANCE);

        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> TSUME = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.TSUME)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        if (patch.getSkill(SkillSlots.WEAPON_INNATE).isActivated())
                            return CapabilityItem.Styles.COMMON;

                    }
                    return CapabilityItem.Styles.TWO_HAND;
                })
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == WohWeaponCategories.TSUME)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(WohColliderPreset.TSUME_CLAWS)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TsumeAnimations.TSUME_AUTO_1.getAccessor(),
                        TsumeAnimations.TSUME_AUTO_2.getAccessor(),
                        TsumeAnimations.TSUME_AUTO_3.getAccessor(),
                        TsumeAnimations.TSUME_AUTO_4.getAccessor(),
                        TsumeAnimations.TSUME_DASH.getAccessor(), TsumeAnimations.TSUME_AIRSLASH.getAccessor())
                .newStyleCombo(CapabilityItem.Styles.COMMON,
                        TsumeAnimations.TSUME_SKILL_AUTO_1.getAccessor(),
                        TsumeAnimations.TSUME_SKILL_AUTO_2.getAccessor(),
                        TsumeAnimations.TSUME_SKILL_AUTO_3.getAccessor(),
                        TsumeAnimations.TSUME_DASH.getAccessor(), TsumeAnimations.TSUME_AIRSLASH.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TsumeAnimations.TSUME_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TsumeAnimations.TSUME_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TsumeAnimations.TSUME_RUN.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TsumeAnimations.TSUME_GUARD.getAccessor())

                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.IDLE, TsumeAnimations.TSUME_SKILL_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.WALK, TsumeAnimations.TSUME_SKILL_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.RUN, TsumeAnimations.TSUME_RUN.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.BLOCK, TsumeAnimations.TSUME_GUARD.getAccessor())

                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.ENRAGED_CLAWS)
                .innateSkill(CapabilityItem.Styles.COMMON, (itemstack) -> WohSkills.ENRAGED_CLAWS);


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
                        WakizashiAnimations.WAKIZASHI_AUTO_1.getAccessor(),
                        WakizashiAnimations.WAKIZASHI_AUTO_2.getAccessor(),
                        WakizashiAnimations.WAKIZASHI_AUTO_3.getAccessor(),
                        Animations.DAGGER_DASH, Animations.DAGGER_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_1.getAccessor(),
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_2.getAccessor(),
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_3.getAccessor(),
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_4.getAccessor(),
                        WakizashiAnimations.WAKIZASHI_DUAL_AUTO_5.getAccessor(),
                        Animations.DAGGER_DUAL_DASH, TessenAnimations.TESSEN_DUAL_AIRSLASH.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, WakizashiAnimations.WAKIZASHI_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, WakizashiAnimations.WAKIZASHI_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, WakizashiAnimations.WAKIZASHI_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, WakizashiAnimations.WAKIZASHI_GUARD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, WakizashiAnimations.WAKIZASHI_DUAL_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, WakizashiAnimations.WAKIZASHI_DUAL_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, WakizashiAnimations.WAKIZASHI_DUAL_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, WakizashiAnimations.WAKIZASHI_DUAL_GUARD.getAccessor());
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
                        OdachiAnimations.ODACHI_AUTO_1.getAccessor(),
                        OdachiAnimations.ODACHI_AUTO_2.getAccessor(),
                        OdachiAnimations.ODACHI_AUTO_3.getAccessor(),
                        OdachiAnimations.ODACHI_AUTO_4.getAccessor(),
                        OdachiAnimations.ODACHI_AUTO_5.getAccessor(),
                        OdachiAnimations.ODACHI_DASH.getAccessor(), OdachiAnimations.ODACHI_AIRSLASH.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, OdachiAnimations.ODACHI_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, OdachiAnimations.ODACHI_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, OdachiAnimations.ODACHI_HOLD.getAccessor())
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, OdachiAnimations.ODACHI_GUARD.getAccessor());
        return builder;
    };
}
