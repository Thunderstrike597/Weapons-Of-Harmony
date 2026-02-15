package net.kenji.woh.gameasset;

import com.mojang.datafixers.util.Pair;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.registry.WohColliderPreset;
import net.kenji.woh.registry.animation.*;
import net.kenji.woh.render.ShotogatanaRender;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ShieldItem;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import reascer.wom.world.capabilities.item.WOMWeaponCapabilityPresets;
import reascer.wom.world.item.SatsujinItem;
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
                            boolean isSheathed = ShotogatanaRender.renderSheathMap.getOrDefault(playerPatch.getOriginal().getUUID(), true);
                            if(playerPatch instanceof PlayerPatch<?> patch){
                                if(patch.getSkill(WohSkills.SHEATH_STANCE) != null && patch.getSkill(WohSkills.SHEATH_STANCE).isActivated()){
                                    return CapabilityItem.Styles.TWO_HAND;
                                }
                            }
                            if(isSheathed)
                                return CapabilityItem.Styles.SHEATH;
                            return WohStyles.UNSHEATHED;
                        }
                )
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.TACHI)
                .newStyleCombo(CapabilityItem.Styles.SHEATH,
                        ShotogatanaAnimations.SHOTOGATANA_AUTO_1,
                        CorruptAnimations.KATANA_AUTO2,
                        CorruptAnimations.SWORD_ONEHAND_AUTO1,
                        AnimsSatsujin.SATSUJIN_AUTO_3,
                        AnimsHerrscher.HERRSCHER_AUTO_1,
                        ShotogatanaAnimations.SHOTOGATANA_DASH, ShotogatanaAnimations.SHOTOGATANA_AIRSLASH)
                .newStyleCombo(WohStyles.UNSHEATHED,
                        CorruptAnimations.KATANA_AUTO1,
                        CorruptAnimations.KATANA_AUTO2,
                        CorruptAnimations.SWORD_ONEHAND_AUTO1,
                        AnimsSatsujin.SATSUJIN_AUTO_3,
                        AnimsHerrscher.HERRSCHER_AUTO_1,
                        CorruptAnimations.SWORD_SLASH, AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        CorruptAnimations.LETHAL_SLICING_START,
                        CorruptAnimations.LETHAL_SLICING_ONCE,
                        CorruptAnimations.LETHAL_SLICING_TWICE,
                        CorruptAnimations.LONGSWORD_OLD_AUTO2,
                        CorruptAnimations.LONGSWORD_OLD_AUTO3,
                        CorruptAnimations.LETHAL_SLICING_TWICE,
                        CorruptAnimations.LONGSWORD_OLD_AUTO2,
                        CorruptAnimations.LONGSWORD_OLD_AUTO3,
                        AnimsRuine.RUINE_CHATIMENT,
                        CorruptAnimations.LONGSWORD_OLD_DASH, AnimsSatsujin.SATSUJIN_TSUKUYOMI)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_WALK)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.RUN, CorruptAnimations.YAMATO_RUN)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD)
                .livingMotionModifier(WohStyles.UNSHEATHED, LivingMotions.IDLE, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_IDLE)
                .livingMotionModifier(WohStyles.UNSHEATHED, LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_WALK)
                .livingMotionModifier(WohStyles.UNSHEATHED, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(WohStyles.UNSHEATHED, LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD)

                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)

                .innateSkill(CapabilityItem.Styles.SHEATH, (itemstack) -> WohSkills.SHEATH_STANCE)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.SHEATH_STANCE);

        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> TESSEN = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.TESSEN)
                .styleProvider((playerPatch) -> {
                            if (playerPatch instanceof PlayerPatch<?> patch) {
                                if (patch.getHoldingItemCapability(InteractionHand.OFF_HAND).getWeaponCategory() == WohWeaponCategories.TESSEN) {
                                    if (AimManager.isAiming(patch)) {
                                        return WohStyles.THROWN_TWO_HAND;
                                    }
                                    return CapabilityItem.Styles.TWO_HAND;
                                }
                                if (AimManager.isAiming(patch))
                                    return WohStyles.THROWN_ONE_HAND;
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
                        TessenAnimations.TESSEN_SKILL_DASH, Animations.DAGGER_AIR_SLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TessenAnimations.TESSEN_DUAL_AUTO_1,
                        TessenAnimations.TESSEN_DUAL_AUTO_2,
                        TessenAnimations.TESSEN_DUAL_AUTO_3,
                        TessenAnimations.TESSEN_DUAL_AUTO_4,
                        TessenAnimations.TESSEN_DUAL_AUTO_5,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_DUAL_AIRSLASH)
                .newStyleCombo(WohStyles.THROWN_TWO_HAND,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_1,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_2,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_3,
                        TessenAnimations.TESSEN_SKILL_DUAL_AUTO_4,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_SKILL_AIRSLASH)
                .newStyleCombo(WohStyles.THROWN_ONE_HAND,
                        TessenAnimations.TESSEN_SKILL_AUTO_1,
                        TessenAnimations.TESSEN_SKILL_AUTO_2,
                        TessenAnimations.TESSEN_SKILL_AUTO_3,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_SKILL_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_SKILL_WALK)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_SKILL_WALK)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)

                .passiveSkill(WohSkills.FAN_STANCE);
        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> TSUME = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.TSUME)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        if (patch.getSkill(SkillSlots.WEAPON_INNATE).isActivated())
                            return WohStyles.ENRAGED_CLAWS;

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
                        CorruptAnimations.DUAL_TACHI_AUTO1,
                        CorruptAnimations.DUAL_TACHI_AUTO2,
                        CorruptAnimations.DUAL_TACHI_AUTO3,
                        CorruptAnimations.DUAL_TACHI_AUTO4,
                        CorruptAnimations.BLADE_RUSH1, TsumeAnimations.TSUME_AIRSLASH)
                .newStyleCombo(WohStyles.ENRAGED_CLAWS,
                        AnimsEnderblaster.ENDERBLASTER_ONEHAND_AUTO_3,
                        AnimsAgony.AGONY_AUTO_1,
                        WOMAnimations.ANTITHEUS_AUTO_2,
                        WOMAnimations.TORMENT_BERSERK_AUTO_1,
                        WOMAnimations.TORMENT_BERSERK_AUTO_2,
                        AnimsHerrscher.HERRSCHER_TRANE,
                        WOMAnimations.TORMENT_CHARGED_ATTACK_2,
                        WOMAnimations.TORMENT_BERSERK_DASH, TsumeAnimations.TSUME_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TsumeAnimations.TSUME_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TsumeAnimations.TSUME_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TsumeAnimations.TSUME_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TsumeAnimations.TSUME_GUARD)
                .livingMotionModifier(WohStyles.ENRAGED_CLAWS, LivingMotions.IDLE, TsumeAnimations.TSUME_SKILL_HOLD)
                .livingMotionModifier(WohStyles.ENRAGED_CLAWS, LivingMotions.WALK, TsumeAnimations.TSUME_SKILL_HOLD)
                .livingMotionModifier(WohStyles.ENRAGED_CLAWS, LivingMotions.RUN, TsumeAnimations.TSUME_RUN)
                .livingMotionModifier(WohStyles.ENRAGED_CLAWS, LivingMotions.BLOCK, TsumeAnimations.TSUME_GUARD)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.ENRAGED_CLAWS)
                .innateSkill(WohStyles.ENRAGED_CLAWS, (itemstack) -> WohSkills.ENRAGED_CLAWS)
                .swingSound(EpicFightSounds.WHOOSH_SMALL.get());
        //  .addStyleAttibutes(WohStyles.ENRAGED_CLAWS, new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("tsume_speed", -4, AttributeModifier.Operation.ADDITION)))
        // .addStyleAttibutes(CapabilityItem.Styles.TWO_HAND, new Pair<>(Attributes.ATTACK_SPEED, new AttributeModifier("tsume_speed", -4, AttributeModifier.Operation.ADDITION)));

        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> ARBITERS_BLADE = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.ARBITERS_BLADE)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        if(patch.getSkill(WohSkills.ARBITERS_SLASH) != null && patch.getSkill(WohSkills.ARBITERS_SLASH).isActivated()) {
                            if (AimManager.isAiming(patch)) {
                                return WohStyles.AIMING;
                            }
                            return CapabilityItem.Styles.COMMON;
                        }
                        if(patch.getOriginal().getOffhandItem().getItem() instanceof ShieldItem)
                            return CapabilityItem.Styles.ONE_HAND;
                    }
                    return CapabilityItem.Styles.TWO_HAND;
                })
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == WohWeaponCategories.ARBITERS_BLADE)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.LONGSWORD)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        AnimsHerrscher.HERRSCHER_AUTO_1,
                        CorruptAnimations.SWORD_ONEHAND_AUTO1,
                        CorruptAnimations.SWORD_ONEHAND_AUTO2,
                        AnimsHerrscher.GESETZ_AUTO_1,
                        AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2,
                        CorruptAnimations.SWORD_ONEHAND_AUTO4,
                        CorruptAnimations.BACKWARD_SLASH,
                        AnimsHerrscher.HERRSCHER_VERDAMMNIS, AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        CorruptAnimations.SWORD_ONEHAND_AUTO1,
                        CorruptAnimations.LONGSWORD_OLD_AUTO2,
                        AnimsRuine.RUINE_AUTO_1,
                        CorruptAnimations.TACHI_TWOHAND_AUTO_4,
                        AnimsRuine.RUINE_AUTO_3,
                        AnimsSolar.SOLAR_AUTO_1,
                        AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2,
                        AnimsHerrscher.HERRSCHER_VERDAMMNIS, AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                .newStyleCombo(CapabilityItem.Styles.COMMON,
                        CorruptAnimations.SWORD_ONEHAND_AUTO1,
                        CorruptAnimations.SWORD_ONEHAND_AUTO2,
                        CorruptAnimations.SWORD_ONEHAND_AUTO3,
                        CorruptAnimations.SWORD_ONEHAND_AUTO4,
                        ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_4, ArbitersBladeAnimations.ARBITERS_BLADE_AIRSLASH)
                .newStyleCombo(WohStyles.AIMING,
                        ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AIM_AUTO_1,
                        CorruptAnimations.SWORD_ONEHAND_AUTO1,
                        CorruptAnimations.SWORD_ONEHAND_AUTO2,
                        CorruptAnimations.SWORD_ONEHAND_AUTO3,
                        CorruptAnimations.SWORD_ONEHAND_AUTO4,
                        ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_4, ArbitersBladeAnimations.ARBITERS_BLADE_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.BIPED_HOLD_KATANA)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, CorruptAnimations.RUN_KATANA)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.IDLE, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.WALK, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)
                .livingMotionModifier(CapabilityItem.Styles.COMMON, LivingMotions.BLOCK_SHIELD, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)

                .livingMotionModifier(WohStyles.AIMING, LivingMotions.IDLE, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.WALK, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.RUN, Animations.BIPED_RUN_LONGSWORD)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.BLOCK_SHIELD, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)

                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> WohSkills.ARBITERS_SLASH)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.ARBITERS_SLASH)

                .innateSkill(WohStyles.AIMING, (itemstack) -> WohSkills.ARBITERS_SLASH);
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
                        CorruptAnimations.KATANA_AUTO1,
                        CorruptAnimations.KATANA_AUTO2,
                        WOMAnimations.TORMENT_AUTO_1,
                        AnimsEnderblaster.ENDERBLASTER_ONEHAND_AUTO_3,
                        WOMAnimations.ANTITHEUS_AUTO_1,
                        AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2,
                        CorruptAnimations.SWORD_SLASH, CorruptAnimations.SWORD_SLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        CorruptAnimations.DUAL_TACHI_AUTO1,
                        CorruptAnimations.DUAL_TACHI_AUTO2,
                        CorruptAnimations.DUAL_TACHI_AUTO3,
                        AnimsEnderblaster.ENDERBLASTER_ONEHAND_AUTO_3,
                        AnimsAgony.AGONY_AUTO_1,
                        CorruptAnimations.DUAL_SLASH, CorruptAnimations.DUAL_SLASH)
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
                        CorruptAnimations.TACHI_TWOHAND_AUTO_1,
                        CorruptAnimations.TACHI_TWOHAND_AUTO_2,
                        CorruptAnimations.GREATSWORD_OLD_AUTO1,
                        CorruptAnimations.GREATSWORD_OLD_AUTO2,
                        AnimsRuine.RUINE_AUTO_3,
                        CorruptAnimations.GREATSWORD_OLD_DASH,
                        CorruptAnimations.GREATSWORD_OLD_DASH,  WOMAnimations.TORMENT_AIRSLAM)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.GREATSWORD_OLD_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, CorruptAnimations.GREATSWORD_OLD_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, CorruptAnimations.GREATSWORD_OLD_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, OdachiAnimations.ODACHI_GUARD);
        return builder;
    };

}
