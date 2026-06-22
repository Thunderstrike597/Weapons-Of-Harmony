package net.kenji.woh.gameasset.skills.combos;

import com.p1nero.invincible.api.events.BaseEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.api.skill.ComboNode;

import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.animation_types.ShotogatanaAttackAnimation;
import net.kenji.woh.api.basegameassets.ExtendedComboBasicAttack;
import net.kenji.woh.api.basegameassets.condition.InAirCondition;
import net.kenji.woh.api.basegameassets.condition.SkillActivatedCondition;
import net.kenji.woh.api.basegameassets.skills.BaseComboBuilder;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.registry.animation.ArbitersBladeAnimations;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsMoonless;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.damagesource.StunType;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ArbitersBladeCombos extends BaseComboBuilder {

    public static Skill buildSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {

        ComboNode root = ComboNode.create();

        ComboNode basicAttack = ComboNode.createNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_1)
                .setStunTypeModifier(StunType.HOLD)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));


        ComboNode basicOneHand1 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_2, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));
        ComboNode basicOneHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicOneHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicOneHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;

        ComboNode basicLeftOneHand = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SPIN_ATTACK_LEFT, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicLeftOneHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, false).addCondition(new LeftCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicLeftOneHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, false).addCondition(new LeftCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicLeftOneHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, false).addCondition(new LeftCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;

        ComboNode basicRightOneHand = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SPIN_ATTACK_RIGHT, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicRightOneHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, false).addCondition(new RightCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicRightOneHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, false).addCondition(new RightCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;
        ComboNode basicRightOneHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, false).addCondition(new RightCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true));;

        ComboNode skillBasicAttack = ComboNode.createNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_1)
                .setStunTypeModifier(StunType.HOLD)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false));


        ComboNode basicOneHandSkill1 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_2, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false));
        ComboNode basicOneHandSkill2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_3, false).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false));

        ComboNode basicLeftOneHandSkill = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_2, false).addCondition(new LeftCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false));
        ComboNode basicLeftOneHandSkill2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_3, false).addCondition(new LeftCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false));

        ComboNode basicRightOneHandSkill = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_2, false).addCondition(new RightCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false));
        ComboNode basicRightOneHandSkill2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_3, false).addCondition(new RightCondition()).addCondition(new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false));



        ComboNode jumpAttack1 = createShotogatanaAirComboNode(AnimsHerrscher.HERRSCHER_AUSROTTUNG, true).addCondition(new InAirCondition());

        ComboNode dashCombo = createArbitersBladeComboNode(AnimsHerrscher.HERRSCHER_VERDAMMNIS, true).addCondition(new SprintingCondition());

        ComboNode basicLeftDodge = createArbitersBladeDodgeComboNode(CorruptAnimations.WOLFDODGE_LEFT, basicLeftOneHand,1.8F, 0.45F).addCondition(new LeftCondition());

        ComboNode basicRightDodge = createArbitersBladeDodgeComboNode(CorruptAnimations.WOLFDODGE_RIGHT, basicRightOneHand,1.8F, 0.45F).addCondition(new RightCondition());


        /// root
        createMovementCombo(root, basicAttack, new ComboNodeWrapper(skillBasicAttack, basicLeftDodge, basicRightDodge, null, null, jumpAttack1, dashCombo));

        /// root decision
        ComboNode rootDecision = createMovementCombo(basicAttack, basicOneHand1, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// basic > basic+
        createMovementCombo(basicOneHand1, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicOneHand2, basicOneHand3, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicOneHand3, basicOneHand4, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// left > left+

        createMovementCombo(basicLeftOneHand, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftOneHand2, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftOneHand2, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftOneHand3, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftOneHand3, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftOneHand4, basicRightDodge, jumpAttack1, dashCombo));
        /// right > right+

        createMovementCombo(basicRightOneHand, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightOneHand2, jumpAttack1, dashCombo));
        createMovementCombo(basicRightOneHand2, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightOneHand3, jumpAttack1, dashCombo));
        createMovementCombo(basicRightOneHand3, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightOneHand4, jumpAttack1, dashCombo));
        /// jump > jump+

        /// root decision
        ComboNode rootDecisionSkill = createMovementCombo(skillBasicAttack, basicOneHandSkill1, new ComboNodeWrapper(basicLeftOneHandSkill, basicRightOneHandSkill, null, null, jumpAttack1, dashCombo));

        /// basic > basic+
        createMovementCombo(basicOneHandSkill1, basicOneHandSkill2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// left > left+
        createMovementCombo(basicLeftOneHandSkill, basicLeftOneHandSkill2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        /// right > right+
        createMovementCombo(basicRightOneHandSkill, basicRightOneHandSkill2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));


        return registryWorker.build("arbiters_combo_skill", (builder) -> new ExtendedComboBasicAttack(builder, root), ExtendedComboBasicAttack
                .createComboBasicAttack()
                .setCombo(root)
                .setMaxProtectTime(22)
                .setMaxPressTime(20)
                .setReserveTime(16)
                .setShouldDrawGui(false).setSkillTextureLocation(ResourceLocation.fromNamespaceAndPath(EpicFightMod.MODID, String.format("textures/gui/skills/weapon_innate/relentless_combo.png"))));
    }


    private static ComboNode createArbitersBladeComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, boolean addSheathEvent){
        ComboNode node = ComboNode.createNode(animation)
                .addBeginEvent(BaseEvent.create((playerPatch, entity, invinciblePlayer) -> {
                    WOHAnimationUtils.regainMovementEvent(playerPatch);
                }));

        // Defer the addTimeEvent call until animation.get() is non-null
        DEFERRED_SETUP.add(() -> {
            StaticAnimation anim = animation.get();
            if(anim instanceof AttackAnimation attackAnimation) {
                float time = attackAnimation.phases[attackAnimation.phases.length - 1].contact;
                node.addTimeEvent(new TimeStampedEvent(time,
                        ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.stopMovementEvent(entityPatch))));
                if (addSheathEvent) {
                    float recovery = attackAnimation.phases[attackAnimation.phases.length - 1].recovery;
                    float end = attackAnimation.phases[attackAnimation.phases.length - 1].end;
                    node.addTimeEvent(new TimeStampedEvent(recovery,
                            ((entityPatch, target, invinciblePlayer) -> entityPatch.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_NEW_SHEATH, 0.1F))));

                }
            }
            return null;
        });

        return node;
    }
    private static ComboNode createArbitersBladeDodgeComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, ComboNode followUpCombo, float speedMutliplier, float followUpOffset){
        ComboNode node = ComboNode.createNode(animation)
                .addBeginEvent(BaseEvent.create((playerPatch, entity, invinciblePlayer) -> {
                    WOHAnimationUtils.regainMovementEvent(playerPatch);
                }));

        // Defer the addTimeEvent call until animation.get() is non-null
        DEFERRED_SETUP.add(() -> {
            StaticAnimation anim = animation.get();
            if(anim != null) {
                anim.addProperty(AnimationProperty.StaticAnimationProperty.PLAY_SPEED_MODIFIER, (self, entitypatch, speed, prevElapsedTime, elapsedTime) -> {
                    return speed * speedMutliplier;
                });
                float end = anim.getTotalTime() - followUpOffset;

                node.addTimeEvent(new TimeStampedEvent(end,
                        ((entityPatch, target, invinciblePlayer) -> {
                            Skill skill = entityPatch.getSkill(SkillSlots.WEAPON_INNATE).getSkill();
                            if (skill instanceof ComboBasicAttack comboAttack) {
                                SkillContainer container = entityPatch.getSkill(WohSkills.ARBITERS_SLASH_COMBO);

                                if (container != null) {
                                    comboAttack.executeNodeOnServer(container, followUpCombo, 1, 1);
                                }
                            }
                        })));
            }
            return null;
        });

        return node;
    }
    private static ComboNode createShotogatanaAirComboNode(AnimationManager.AnimationAccessor<? extends AttackAnimation> animation, boolean isSharp){
        ComboNode node = ComboNode.createNode(animation)
                .addBeginEvent(BaseEvent.create((playerPatch, entity, invinciblePlayer) -> {
                    WOHAnimationUtils.regainMovementEvent(playerPatch);
                }));

        // Defer the addTimeEvent call until animation.get() is non-null
        DEFERRED_SETUP.add(() -> {
            AttackAnimation anim = animation.get();
            float contact = anim.phases[anim.phases.length - 1].contact;
            float recovery = anim.phases[anim.phases.length - 1].end;

            node.addTimeEvent(new TimeStampedEvent(contact,
                    ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.stopMovementEvent(entityPatch))));
            ((AttackAnimation)anim).addProperty(
                    AnimationProperty.AttackAnimationProperty.NO_GRAVITY_TIME,
                    TimePairList.create(new float[]{0.1F, recovery})
            );
            return null;
        });

        return node;
    }
}
