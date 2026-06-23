package net.kenji.woh.gameasset;

import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.corruptdog.cdm.world.CDWeaponCapabilityPresets;
import net.kenji.woh.api.DualSkillWeaponCapability;
import net.kenji.woh.api.manager.AimManager;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.skills.combos.ShotogatanaCombos;
import net.kenji.woh.gameasset.skills.combos.TenraiCombos;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.animation.*;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.*;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.ColliderPreset;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.WeaponCapability;
import yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent;

import java.util.function.Function;

public class WohWeaponCapabilityPresets {

    public static final Function<Item, CapabilityItem.Builder> SHOTOGATANA = (item) -> {
        DualSkillWeaponCapability.Builder builder = (DualSkillWeaponCapability.Builder)DualSkillWeaponCapability.builder()                .category(WohWeaponCategories.SHOTOGATANA)
                .styleProvider((playerPatch) -> {
                    ItemStack stack = playerPatch.getOriginal().getMainHandItem();
                            boolean isSheathed = stack.getItem() instanceof Shotogatana && ShotogatanaManager.getWeaponSheathed(playerPatch.getOriginal());
                            if(playerPatch instanceof PlayerPatch<?> patch){
                                if(!isSheathed && patch.getSkill(WohSkills.SHOTOGATANA_SKILL) != null && patch.getSkill(WohSkills.SHOTOGATANA_SKILL).isActivated()){
                                    return CapabilityItem.Styles.TWO_HAND;
                                }
                            }
                            return CapabilityItem.Styles.SHEATH;

                        }
                )
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.TACHI)
                .newStyleCombo(CapabilityItem.Styles.SHEATH,
                        Animations.SWORD_AUTO1,
                        Animations.SWORD_AUTO2,
                        Animations.SWORD_AUTO3,
                        CorruptAnimations.YAMATO_DASH, CorruptAnimations.YAMATO_AIRSLASH)
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
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.WALK, ShotogatanaAnimations.SHOTOGATANA_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.RUN, CorruptAnimations.YAMATO_RUN)
                .livingMotionModifier(CapabilityItem.Styles.SHEATH, LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_HOLD_TACHI)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)
                .innateSkill(CapabilityItem.Styles.SHEATH, (itemstack) -> WohSkills.SHOTOGATANA_COMBO)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.SHOTOGATANA_COMBO);
        builder.secondarySkill((itemStack) -> WohSkills.SHOTOGATANA_SKILL);
        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> TESSEN = (item) -> {
        DualSkillWeaponCapability.Builder builder = (DualSkillWeaponCapability.Builder)DualSkillWeaponCapability.builder()
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
                        TessenAnimations.TESSEN_NEW_AUTO_1,
                        TessenAnimations.TESSEN_NEW_AUTO_2,
                        TessenAnimations.TESSEN_NEW_AUTO_3,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_NEW_AIRSLASH)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TessenAnimations.TESSEN_NEW_DUAL_AUTO_1,
                        TessenAnimations.TESSEN_NEW_DUAL_AUTO_2,
                        TessenAnimations.TESSEN_NEW_DUAL_AUTO_3,
                        TessenAnimations.TESSEN_NEW_DUAL_AUTO_4,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_SKILL_AIRSLASH)
                .newStyleCombo(WohStyles.THROWN_TWO_HAND,
                        TessenAnimations.TESSEN_NEW_SKILL_AUTO_1,
                        TessenAnimations.TESSEN_NEW_SKILL_DUAL_AUTO_2,
                        TessenAnimations.TESSEN_NEW_SKILL_DUAL_AUTO_3,
                        TessenAnimations.TESSEN_NEW_SKILL_DUAL_AUTO_4,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_SKILL_AIRSLASH)
                .newStyleCombo(WohStyles.THROWN_ONE_HAND,
                        TessenAnimations.TESSEN_NEW_SKILL_AUTO_1,
                        TessenAnimations.TESSEN_NEW_SKILL_AUTO_2,
                        TessenAnimations.TESSEN_NEW_SKILL_AUTO_3,
                        TessenAnimations.TESSEN_NEW_SKILL_AUTO_4,
                        TessenAnimations.TESSEN_SKILL_DASH, TessenAnimations.TESSEN_NEW_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_NEW_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_NEW_WALK)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK_SHIELD, TessenAnimations.TESSEN_SKILL_HOLD)

                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_NEW_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_NEW_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_DUAL_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK_SHIELD, TessenAnimations.TESSEN_SKILL_HOLD)

                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_SKILL_WALK)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_DUAL_RUN)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_TWO_HAND, LivingMotions.BLOCK_SHIELD, TessenAnimations.TESSEN_SKILL_HOLD)

                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.IDLE, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.WALK, TessenAnimations.TESSEN_SKILL_WALK)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.RUN, TessenAnimations.TESSEN_RUN)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.BLOCK, TessenAnimations.TESSEN_SKILL_HOLD)
                .livingMotionModifier(WohStyles.THROWN_ONE_HAND, LivingMotions.BLOCK_SHIELD, TessenAnimations.TESSEN_SKILL_HOLD)

                .passiveSkill(WohSkills.FAN_STANCE);
        builder.canUseShield(CapabilityItem.Styles.ONE_HAND, false);
        builder.canUseShield(CapabilityItem.Styles.TWO_HAND, false);
        builder.canUseShield(WohStyles.THROWN_TWO_HAND, false);
        builder.canUseShield(WohStyles.THROWN_ONE_HAND, false);


        return builder;
    };

    public static final Function<Item, CapabilityItem.Builder> TSUME = (item) -> {
        WeaponCapability.Builder builder = WeaponCapability.builder()
                .category(WohWeaponCategories.TSUME)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        if (patch.getSkill(SkillSlots.WEAPON_INNATE).isActivated())
                            return WohStyles.ABILITY_ACTIVE;

                    }
                    return CapabilityItem.Styles.TWO_HAND;
                })
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == WohWeaponCategories.TSUME)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.SWORD)
                .canBePlacedOffhand(false)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TsumeAnimations.TSUME_AUTO_1,
                        TsumeAnimations.TSUME_AUTO_2,
                        TsumeAnimations.TSUME_AUTO_3,
                        TsumeAnimations.TSUME_AUTO_4,
                        CorruptAnimations.BLADE_RUSH1, TsumeAnimations.TSUME_NEW_AIRSLASH)
                .newStyleCombo(WohStyles.ABILITY_ACTIVE,
                        TsumeAnimations.TSUME_AUTO_1,
                        TsumeAnimations.TSUME_AUTO_2,
                        TsumeAnimations.TSUME_AUTO_3,
                        TsumeAnimations.TSUME_AUTO_4,
                        CorruptAnimations.BLADE_RUSH1, TsumeAnimations.TSUME_NEW_AIRSLASH)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TsumeAnimations.TSUME_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, TsumeAnimations.TSUME_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, TsumeAnimations.TSUME_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, TsumeAnimations.TSUME_GUARD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE, LivingMotions.IDLE, TsumeAnimations.TSUME_SKILL_HOLD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE, LivingMotions.WALK, TsumeAnimations.TSUME_SKILL_HOLD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE, LivingMotions.RUN, TsumeAnimations.TSUME_RUN)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE, LivingMotions.BLOCK, TsumeAnimations.TSUME_GUARD)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.ENRAGED_CLAWS)
                .innateSkill(WohStyles.ABILITY_ACTIVE, (itemstack) -> WohSkills.ENRAGED_CLAWS)
                .swingSound(EpicFightSounds.WHOOSH_SMALL.get());
        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> ARBITERS_BLADE = (item) -> {
        DualSkillWeaponCapability.Builder builder = (DualSkillWeaponCapability.Builder)DualSkillWeaponCapability.builder()
                .category(WohWeaponCategories.ARBITERS_BLADE)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        boolean isAbilityActive = patch.getSkill(WohSkills.ARBITERS_SLASH) != null && patch.getSkill(WohSkills.ARBITERS_SLASH).isActivated();
                        boolean isOffhandValid = patch.getOriginal().getOffhandItem().getItem() instanceof ShieldItem;
                        if(isAbilityActive) {
                            if (AimManager.isAiming(patch)) {
                                return WohStyles.AIMING;
                            }
                            if(isOffhandValid)
                                return WohStyles.ABILITY_ACTIVE_ONE_HAND;
                            return WohStyles.ABILITY_ACTIVE_TWO_HAND;
                        }
                        if(isOffhandValid)
                            return CapabilityItem.Styles.ONE_HAND;
                    }

                    return CapabilityItem.Styles.TWO_HAND;
                })
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == WohWeaponCategories.ARBITERS_BLADE)
                .comboCancel(style -> false)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.LONGSWORD)
                .newStyleCombo(CapabilityItem.Styles.ONE_HAND,
                        ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_1,
                        ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_2,
                        ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3,
                        ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4,
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
                .newStyleCombo(WohStyles.ABILITY_ACTIVE_TWO_HAND,
                        CorruptAnimations.LONGSWORD_OLD_AUTO1,
                        CorruptAnimations.LONGSWORD_OLD_AUTO2,
                        CorruptAnimations.LONGSWORD_OLD_AUTO3,
                        CorruptAnimations.LONGSWORD_OLD_AUTO4,
                        AnimsHerrscher.HERRSCHER_VERDAMMNIS, AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                .newStyleCombo(WohStyles.ABILITY_ACTIVE_ONE_HAND,
                        Animations.LONGSWORD_AUTO1,
                        Animations.LONGSWORD_AUTO2,
                        Animations.LONGSWORD_AUTO3,
                        AnimsHerrscher.HERRSCHER_VERDAMMNIS, AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                .newStyleCombo(WohStyles.AIMING,
                        ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AIM_ATTACK,
                        Animations.LONGSWORD_AUTO2,
                        Animations.LONGSWORD_AUTO3,
                        AnimsHerrscher.HERRSCHER_VERDAMMNIS, AnimsHerrscher.HERRSCHER_AUSROTTUNG)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.IDLE, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.WALK, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.RUN, ArbitersBladeAnimations.ARBITERS_BLADE_RUN)
                .livingMotionModifier(CapabilityItem.Styles.ONE_HAND, LivingMotions.BLOCK, GenericAnimations.ARBITERS_SHIELD_BLOCK)

                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.BIPED_HOLD_KATANA)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_LONGSWORD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, CorruptAnimations.RUN_KATANA)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, Animations.LONGSWORD_GUARD)

                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_TWO_HAND, LivingMotions.IDLE, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_TWO_HAND, LivingMotions.WALK, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_TWO_HAND, LivingMotions.RUN, ArbitersBladeAnimations.ARBITERS_BLADE_RUN)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_TWO_HAND, LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_TWO_HAND, LivingMotions.BLOCK_SHIELD, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)

                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_ONE_HAND, LivingMotions.IDLE, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_ONE_HAND, LivingMotions.WALK, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_ONE_HAND, LivingMotions.RUN, ArbitersBladeAnimations.ARBITERS_BLADE_RUN)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_ONE_HAND, LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE_ONE_HAND, LivingMotions.BLOCK_SHIELD, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)

                .livingMotionModifier(WohStyles.AIMING, LivingMotions.IDLE, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.WALK, ArbitersBladeAnimations.ARBITERS_BLADE_HOLD)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.RUN, ArbitersBladeAnimations.ARBITERS_BLADE_RUN)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.BLOCK, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)
                .livingMotionModifier(WohStyles.AIMING, LivingMotions.BLOCK_SHIELD, ArbitersBladeAnimations.ARBITERS_BLADE_AIM)

                .innateSkill(CapabilityItem.Styles.ONE_HAND, (itemstack) -> WohSkills.ARBITERS_SLASH_COMBO)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.ARBITERS_SLASH_COMBO)
                .innateSkill(WohStyles.ABILITY_ACTIVE_TWO_HAND, (itemstack) -> WohSkills.ARBITERS_SLASH_COMBO)
                .innateSkill(WohStyles.ABILITY_ACTIVE_ONE_HAND, (itemstack) -> WohSkills.ARBITERS_SLASH_COMBO)
                .innateSkill(WohStyles.AIMING, (itemstack) -> WohSkills.ARBITERS_SLASH_COMBO);
                builder.secondarySkill((itemstack) -> WohSkills.ARBITERS_SLASH);


        return builder;
    };
    public static final Function<Item, CapabilityItem.Builder> TENRAI = (item) -> {
        DualSkillWeaponCapability.Builder builder = (DualSkillWeaponCapability.Builder)DualSkillWeaponCapability.builder()
                .category(WohWeaponCategories.TENRAI)
                .styleProvider((playerPatch) -> {
                    if(playerPatch instanceof PlayerPatch<?> patch) {
                        SkillSlot skillSlot = EpicFightCapabilities.getItemStackCapability(patch.getOriginal().getMainHandItem()) instanceof DualSkillWeaponCapability weaponCap ? weaponCap.getSecondarySkillSlot() : SkillSlots.WEAPON_INNATE;
                        if (patch.getSkill(skillSlot).isActivated())
                            return WohStyles.ABILITY_ACTIVE;
                    }
                    return CapabilityItem.Styles.TWO_HAND;
                })
                .weaponCombinationPredicator(
                        (entitypatch) ->
                                EpicFightCapabilities.getItemStackCapability(entitypatch.getOriginal().getOffhandItem()).getWeaponCategory()
                                        == WohWeaponCategories.TENRAI)
                .hitSound(EpicFightSounds.BLADE_HIT.get())
                .collider(ColliderPreset.LONGSWORD)
                .newStyleCombo(CapabilityItem.Styles.TWO_HAND,
                        TenraiAnimations.TENRAI_AUTO_1,
                        TenraiAnimations.TENRAI_AUTO_2,
                        TenraiAnimations.TENRAI_AUTO_3,
                        TenraiAnimations.TENRAI_AUTO_4,
                        TenraiAnimations.TENRAI_AUTO_5,
                        TenraiAnimations.TENRAI_DASH,  TenraiAnimations.TENRAI_AIRSLASH)
                .newStyleCombo(WohStyles.ABILITY_ACTIVE,
                        TenraiAnimations.TENRAI_SKILL_AUTO_1,
                        TenraiAnimations.TENRAI_SKILL_AUTO_2,
                        TenraiAnimations.TENRAI_SKILL_AUTO_3,
                        TenraiAnimations.TENRAI_SKILL_AUTO_4,
                        TenraiAnimations.TENRAI_SKILL_DASH,  AnimsAgony.AGONY_AIR_ATTACK_1)

                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, TenraiAnimations.TENRAI_HOLD)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, Animations.BIPED_WALK_SPEAR)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, Animations.BIPED_RUN_SPEAR)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE, LivingMotions.IDLE, TenraiAnimations.TENRAI_SKILL_HOLD)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE, LivingMotions.WALK, TenraiAnimations.TENRAI_SKILL_WALK)
                .livingMotionModifier(WohStyles.ABILITY_ACTIVE, LivingMotions.RUN, TenraiAnimations.TENRAI_SKILL_RUN)
                .innateSkill(CapabilityItem.Styles.TWO_HAND, (itemstack) -> WohSkills.TENRAI_COMBO)
                .innateSkill(WohStyles.ABILITY_ACTIVE, (itemstack) -> WohSkills.TENRAI_COMBO);
                builder.secondarySkill((itemStack) -> WohSkills.SPLIT_TENRAI);
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
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.IDLE, CorruptAnimations.DUAL_GREATSWORD_IDLE)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.WALK, CorruptAnimations.DUAL_GREATSWORD_WALK)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.RUN, CorruptAnimations.DUAL_GREATSWORD_RUN)
                .livingMotionModifier(CapabilityItem.Styles.TWO_HAND, LivingMotions.BLOCK, OdachiAnimations.ODACHI_GUARD);
        return builder;
    };

}
