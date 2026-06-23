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
import net.kenji.woh.api.basegameassets.condition.StyleCondition;
import net.kenji.woh.api.basegameassets.skills.BaseComboBuilder;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.gameasset.WohStyles;
import net.kenji.woh.registry.animation.ArbitersBladeAnimations;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.WOMAnimations;
import reascer.wom.gameasset.animations.weapons.AnimsHerrscher;
import reascer.wom.gameasset.animations.weapons.AnimsMoonless;
import reascer.wom.gameasset.animations.weapons.AnimsRuine;
import reascer.wom.gameasset.animations.weapons.AnimsSolar;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.damagesource.StunType;

import java.lang.reflect.Array;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ArbitersBladeCombos extends BaseComboBuilder {

    public static Skill buildSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {

        ComboNode root = ComboNode.create();


        Condition<?>[] oneHandNonActivated = new Condition[]{new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true), new StyleCondition(CapabilityItem.Styles.ONE_HAND)};
        Condition<?>[] twoHandNonActivated = new Condition[]{new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, true), new StyleCondition(CapabilityItem.Styles.TWO_HAND)};
        Condition<?>[] oneHandActivated = new Condition[]{new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false), new StyleCondition(CapabilityItem.Styles.ONE_HAND, WohStyles.ABILITY_ACTIVE_ONE_HAND, WohStyles.AIMING)};
        Condition<?>[] twoHandActivated = new Condition[]{new SkillActivatedCondition(WohSkills.ARBITERS_SLASH, false), new StyleCondition(CapabilityItem.Styles.TWO_HAND, WohStyles.ABILITY_ACTIVE_TWO_HAND, WohStyles.AIMING)};

        ComboNode basicOneHandAttack = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_1, oneHandNonActivated);

        ComboNode basicOneHand1 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_2, oneHandNonActivated);
        ComboNode basicOneHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, oneHandNonActivated);
        ComboNode basicOneHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, oneHandNonActivated);
        ComboNode basicOneHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, oneHandNonActivated);

        ComboNode basicLeftOneHand = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SPIN_ATTACK_LEFT, oneHandNonActivated);
        ComboNode basicLeftOneHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, new LeftCondition(), oneHandNonActivated);
        ComboNode basicLeftOneHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, new LeftCondition(), oneHandNonActivated);
        ComboNode basicLeftOneHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, new LeftCondition(), oneHandNonActivated);

        ComboNode basicRightOneHand = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SPIN_ATTACK_RIGHT, oneHandNonActivated);
        ComboNode basicRightOneHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, new RightCondition(), oneHandNonActivated);
        ComboNode basicRightOneHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, new RightCondition(), oneHandNonActivated);
        ComboNode basicRightOneHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, new RightCondition(), oneHandNonActivated);

        ComboNode basicTwoHandAttack = createArbitersBladeComboNode(CorruptAnimations.LONGSWORD_OLD_AUTO1, twoHandNonActivated);



        ComboNode basicTwoHand1 = createArbitersBladeComboNode(CorruptAnimations.LONGSWORD_OLD_AUTO2, twoHandNonActivated);
        ComboNode basicTwoHand2 = createArbitersBladeComboNode(AnimsRuine.RUINE_AUTO_1, twoHandNonActivated);
        ComboNode basicTwoHand3 = createArbitersBladeComboNode(CorruptAnimations.TACHI_TWOHAND_AUTO_4, twoHandNonActivated);
        ComboNode basicTwoHand4 = createArbitersBladeComboNode(AnimsRuine.RUINE_AUTO_3, twoHandNonActivated);
        ComboNode basicTwoHand5 = createArbitersBladeComboNode(AnimsSolar.SOLAR_AUTO_1, twoHandNonActivated);
        ComboNode basicTwoHand6 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, twoHandNonActivated);

        ComboNode basicLeftTwoHand = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SPIN_ATTACK_LEFT, twoHandNonActivated);
        ComboNode basicLeftTwoHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, new LeftCondition(), twoHandNonActivated);
        ComboNode basicLeftTwoHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, new LeftCondition(), twoHandNonActivated);
        ComboNode basicLeftTwoHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, new LeftCondition(), twoHandNonActivated);

        ComboNode basicRightTwoHand = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SPIN_ATTACK_RIGHT, twoHandNonActivated);
        ComboNode basicRightTwoHand2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_3, new RightCondition(), twoHandNonActivated);
        ComboNode basicRightTwoHand3 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_AUTO_4, new RightCondition(), twoHandNonActivated);
        ComboNode basicRightTwoHand4 = createArbitersBladeComboNode(AnimsSolar.SOLAR_OBSCURIDAD_AUTO_2, new RightCondition(), twoHandNonActivated);


        ComboNode skillOneHandBasicAttack = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_1, oneHandActivated);


        ComboNode basicOneHandSkill1 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_2, oneHandActivated);
        ComboNode basicOneHandSkill2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_3, oneHandActivated);

        ComboNode basicLeftOneHandSkill = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_2, new LeftCondition(), oneHandActivated);
        ComboNode basicLeftOneHandSkill2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_3, new LeftCondition(), oneHandActivated);

        ComboNode basicRightOneHandSkill = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_2, new RightCondition(), oneHandActivated);
        ComboNode basicRightOneHandSkill2 = createArbitersBladeComboNode(ArbitersBladeAnimations.ARBITERS_BLADE_SKILL_AUTO_3, new RightCondition(), oneHandActivated);

        ComboNode skillTwoHandBasicAttack = createArbitersBladeComboNode(CorruptAnimations.LONGSWORD_OLD_AUTO1, twoHandActivated);

        ComboNode basicTwoHandSkill1 = createArbitersBladeComboNode(CorruptAnimations.LONGSWORD_OLD_AUTO2, twoHandActivated);
        ComboNode basicTwoHandSkill2 = createArbitersBladeComboNode(AnimsRuine.RUINE_AUTO_1, twoHandActivated);
        ComboNode basicTwoHandSkill3 = createArbitersBladeComboNode(CorruptAnimations.TACHI_TWOHAND_AUTO_4, twoHandActivated);

        ComboNode jumpAttack1 = createShotogatanaAirComboNode(AnimsHerrscher.HERRSCHER_AUSROTTUNG, true).addCondition(new InAirCondition());

        ComboNode dashCombo = createArbitersBladeComboNode(AnimsHerrscher.HERRSCHER_VERDAMMNIS).addCondition(new SprintingCondition());

        ComboNode basicLeftDodge = createArbitersBladeDodgeComboNode(CorruptAnimations.WOLFDODGE_LEFT, basicLeftOneHand,1.8F, 0.45F).addCondition(new LeftCondition());

        ComboNode basicRightDodge = createArbitersBladeDodgeComboNode(CorruptAnimations.WOLFDODGE_RIGHT, basicRightOneHand,1.8F, 0.45F).addCondition(new RightCondition());


        /// root
        createMovementCombo(root, basicOneHandAttack, new ComboNodeWrapper(skillOneHandBasicAttack, skillTwoHandBasicAttack, skillOneHandBasicAttack, basicTwoHandAttack, basicLeftDodge, basicRightDodge, null, null, jumpAttack1, dashCombo));

        /// root decision
        createMovementCombo(basicOneHandAttack, basicOneHand1, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// basic > basic+
        createMovementCombo(basicOneHand1, basicOneHand2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicOneHand2, basicOneHand3, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicOneHand3, basicOneHand4, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// left > left+

        createMovementCombo(basicLeftOneHand, basicLeftOneHand2, new ComboNodeWrapper(null, null, basicLeftOneHand2, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftOneHand2, basicLeftOneHand3, new ComboNodeWrapper(null, null, basicLeftOneHand3, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftOneHand3, basicLeftOneHand4, new ComboNodeWrapper(null, null, basicLeftOneHand4, basicRightDodge, jumpAttack1, dashCombo));
        /// right > right+

        createMovementCombo(basicRightOneHand, basicRightOneHand2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightOneHand2, jumpAttack1, dashCombo));
        createMovementCombo(basicRightOneHand2, basicRightOneHand3, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightOneHand3, jumpAttack1, dashCombo));
        createMovementCombo(basicRightOneHand3, basicRightOneHand4, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightOneHand4, jumpAttack1, dashCombo));
        /// jump > jump+

        /// root decision
        createMovementCombo(basicTwoHandAttack, basicTwoHand1, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// basic > basic+
        createMovementCombo(basicTwoHand1, basicTwoHand2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicTwoHand2, basicTwoHand3, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicTwoHand3, basicTwoHand4, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicTwoHand4, basicTwoHand5, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicTwoHand5, basicTwoHand6, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// left > left+

        createMovementCombo(basicLeftTwoHand, basicLeftTwoHand2, new ComboNodeWrapper(null, null, basicLeftOneHand2, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftTwoHand2, basicLeftTwoHand3, new ComboNodeWrapper(null, null, basicLeftOneHand3, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftTwoHand3, basicLeftTwoHand4, new ComboNodeWrapper(null, null, basicLeftOneHand4, basicRightDodge, jumpAttack1, dashCombo));
        /// right > right+

        createMovementCombo(basicRightTwoHand, basicRightTwoHand2, new ComboNodeWrapper(null, null, basicLeftOneHand2, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicRightTwoHand2, basicRightTwoHand3, new ComboNodeWrapper(null, null, basicLeftOneHand3, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicRightTwoHand3, basicRightTwoHand4, new ComboNodeWrapper(null, null, basicLeftOneHand4, basicRightDodge, jumpAttack1, dashCombo));
        /// root decision
        ComboNode rootDecisionSkill = createMovementCombo(skillOneHandBasicAttack, basicOneHandSkill1, new ComboNodeWrapper(basicLeftOneHandSkill, basicRightOneHandSkill, null, null, jumpAttack1, dashCombo));

        /// basic > basic+
        createMovementCombo(basicOneHandSkill1, basicOneHandSkill2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// left > left+
        createMovementCombo(basicLeftOneHandSkill, basicLeftOneHandSkill2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        /// right > right+
        createMovementCombo(basicRightOneHandSkill, basicRightOneHandSkill2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        ComboNode rootDecisionTwoHandSkill = createMovementCombo(skillTwoHandBasicAttack, basicTwoHandSkill1, new ComboNodeWrapper(basicLeftOneHandSkill, basicRightOneHandSkill, null, null, jumpAttack1, dashCombo));

        createMovementCombo(basicTwoHandSkill1, basicTwoHandSkill2, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicTwoHandSkill2, basicTwoHandSkill3, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashCombo));

        /// left > left+

        createMovementCombo(basicLeftTwoHand, basicLeftTwoHand2, new ComboNodeWrapper(null, null, basicLeftOneHand2, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftTwoHand2, basicLeftTwoHand3, new ComboNodeWrapper(null, null, basicLeftOneHand3, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicLeftTwoHand3, basicLeftTwoHand4, new ComboNodeWrapper(null, null, basicLeftOneHand4, basicRightDodge, jumpAttack1, dashCombo));
        /// right > right+

        createMovementCombo(basicRightTwoHand, basicRightTwoHand2, new ComboNodeWrapper(null, null, basicLeftOneHand2, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicRightTwoHand2, basicRightTwoHand3, new ComboNodeWrapper(null, null, basicLeftOneHand3, basicRightDodge, jumpAttack1, dashCombo));
        createMovementCombo(basicRightTwoHand3, basicRightTwoHand4, new ComboNodeWrapper(null, null, basicLeftOneHand4, basicRightDodge, jumpAttack1, dashCombo));




        return registryWorker.build("arbiters_combo_skill", (builder) -> new ExtendedComboBasicAttack(builder, root), ExtendedComboBasicAttack
                .createComboBasicAttack()
                .setCombo(root)
                .setMaxProtectTime(22)
                .setMaxPressTime(20)
                .setReserveTime(16)
                .setShouldDrawGui(false).setSkillTextureLocation(ResourceLocation.fromNamespaceAndPath(EpicFightMod.MODID, String.format("textures/gui/skills/weapon_innate/relentless_combo.png"))));
    }


    private static ComboNode createArbitersBladeComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, Condition<?>... conditions){
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
            }
            return null;
        });
        for(Condition condition1 : conditions){
            node.addCondition(condition1);
        }
        return node;
    }
    private static ComboNode createArbitersBladeComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, Condition condition, Condition<?>... conditions){
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
            }
            return null;
        });

        node.addCondition(condition);
        for(Condition condition1 : conditions){
            node.addCondition(condition1);
        }

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
