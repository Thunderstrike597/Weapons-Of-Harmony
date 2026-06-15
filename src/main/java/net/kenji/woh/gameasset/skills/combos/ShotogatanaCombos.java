package net.kenji.woh.gameasset.skills.combos;

import com.p1nero.invincible.api.events.BaseEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.api.skill.ComboNode;
import com.p1nero.invincible.client.InputManager;
import com.p1nero.invincible.conditions.*;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.WOHAnimationUtils;

import net.kenji.woh.api.animation_types.ShotogatanaAttackAnimation;
import net.kenji.woh.api.basegameassets.ExtendedComboBasicAttack;
import net.kenji.woh.api.basegameassets.condition.CooldownCounterCondition;
import net.kenji.woh.api.basegameassets.condition.InAirCondition;
import net.kenji.woh.api.basegameassets.skills.BaseComboBuilder;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import reascer.wom.gameasset.animations.weapons.AnimsMoonless;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationProperty;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.forgeevent.SkillBuildEvent;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.main.EpicFightMod;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)

public class ShotogatanaCombos extends BaseComboBuilder {


    public static Skill buildSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {

        ComboNode root = ComboNode.create();
        ComboNode basicAttack = ComboNode.createNode(ShotogatanaAnimations.SHOTOGATANA_AUTO_1)
                .setStunTypeModifier(StunType.HOLD)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaInEvent(entityPatch))));


        ComboNode basic1 = createShotogatanaComboNode(ShotogatanaAnimations.SHOTOGATANA_AUTO_2, false).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaInEvent(entityPatch))));
        ComboNode basic2 = createShotogatanaComboNode(ShotogatanaAnimations.SHOTOGATANA_AUTO_3, false);
        ComboNode basic3 = createShotogatanaComboNode(ShotogatanaAnimations.SHOTOGATANA_AUTO_4_SPIN, false);
        ComboNode basic4 = createShotogatanaComboNode(ShotogatanaAnimations.SHOTOGATANA_AUTO_5, false);
        ComboNode basic5 = createShotogatanaComboNode(ShotogatanaAnimations.SHOTOGATANA_AUTO_6, false);



        ComboNode basicLeft = createShotogatanaComboNode(CorruptAnimations.YAMATO_AUTO2, false);
        ComboNode basicLeft2 = createShotogatanaComboNode(CorruptAnimations.YAMATO_AUTO3, false).addTimeEvent(new TimeStampedEvent(0.05F, ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).addCondition(new LeftCondition());
        ComboNode basicLeft3 = createShotogatanaComboNode(CorruptAnimations.YAMATO_RISING_SLASH, false).addTimeEvent(new TimeStampedEvent(0.05F, ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).addCondition(new LeftCondition());
        ComboNode basicLeft4 = createShotogatanaComboNode(CorruptAnimations.YAMATO_DAWN, false).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).addCondition(new LeftCondition());


        ComboNode basicRight = createShotogatanaComboNode(CorruptAnimations.EX_YAMATO_AUTO3, false);
        ComboNode basicRight2 = createShotogatanaComboNode(CorruptAnimations.YAMATO_AUTO4, false).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).addCondition(new RightCondition());
        ComboNode basicRight3 = createShotogatanaComboNode(CorruptAnimations.YAMATO_TWIN_SLASH, false).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).addCondition(new RightCondition());
        ComboNode basicRight4 = createShotogatanaComboNode(CorruptAnimations.YAMATO_POWER3, false).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).addCondition(new RightCondition());


        ComboNode leftCombo1 = createShotogatanaComboNode(CorruptAnimations.YAMATO_COUNTER1, false)
                .addCondition(new LeftCondition());
        ComboNode rightCombo1 = createShotogatanaComboNode(CorruptAnimations.YAMATO_COUNTER2, false)
                .addCondition(new RightCondition());
        ComboNode downCombo1 = createShotogatanaComboNode(CorruptAnimations.YAMATO_POWER0_2, false)
                .addCondition(new DownCondition());


        downCombo1.addCondition(new CooldownCounterCondition(downCombo1, 185));

        ComboNode jumpAttack1 = createShotogatanaAirComboNode(CorruptAnimations.YAMATO_AIR1, true).addCondition(new InAirCondition()).addTimeEvent(new TimeStampedEvent(0.1F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));
        ComboNode jumpAttack2 = createShotogatanaAirComboNode(CorruptAnimations.YAMATO_AIR2, false).addCondition(new InAirCondition()).addTimeEvent(new TimeStampedEvent(0.1F, ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));
        ComboNode jumpAttack3 = createShotogatanaAirComboNode(CorruptAnimations.YAMATO_AIR3, false).addCondition(new InAirCondition()).addTimeEvent(new TimeStampedEvent(0.1F, ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));

        ComboNode upCombo1= createShotogatanaComboNode(CorruptAnimations.YAMATO_DAWN, false).addCondition(new UpCondition()).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));
        ComboNode upCombo2 = createShotogatanaComboNode(CorruptAnimations.YAMATO_POWER3, false).addCondition(new UpCondition()).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));


        ComboNode dashCombo = createShotogatanaComboNode(AnimsMoonless.MOONLESS_AUTO_3_VERSO, true).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));

        ComboNode basicLeftDodge = createShotogatanaDodgeComboNode(CorruptAnimations.YAMATO_STEP_LEFT, basicLeft,false).addCondition(new LeftCondition());

        ComboNode basicRightDodge = createShotogatanaDodgeComboNode(CorruptAnimations.YAMATO_STEP_RIGHT, basicRight,false).addCondition(new RightCondition());

        ComboNode dashComboDodge = createShotogatanaDodgeComboNode(CorruptAnimations.YAMATO_STEP_FORWARD, dashCombo,true).addCondition(new SprintingCondition()).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));

        /// root
        createMovementCombo(root, basicAttack, new ComboNodeWrapper(null, null, null, null, jumpAttack1, dashComboDodge));

        /// root decision
        ComboNode rootDecision = createMovementCombo(basicAttack, basic1, new ComboNodeWrapper(null, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashComboDodge));
        /// basic > basic+
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(null, downCombo1, basicLeftDodge, basicRightDodge, jumpAttack1, dashComboDodge));
        createMovementCombo(basic2, basic3, new ComboNodeWrapper(upCombo1, null, basicLeftDodge, basicRightDodge, jumpAttack1, dashComboDodge));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(upCombo2, downCombo1, basicLeftDodge, basicRightDodge, jumpAttack1, dashComboDodge));
        createMovementCombo(basic4, basic5, new ComboNodeWrapper(upCombo2, downCombo1, basicLeftDodge, basicRightDodge, jumpAttack1, dashComboDodge));

        /// left > left+

        createMovementCombo(basicLeft, basic2, new ComboNodeWrapper(null, downCombo1, basicLeft2, basicRightDodge, jumpAttack1, dashComboDodge));
        createMovementCombo(basicLeft2, basic2, new ComboNodeWrapper(null, downCombo1, basicLeft3, basicRightDodge, jumpAttack1, dashComboDodge));
        createMovementCombo(basicLeft3, basic2, new ComboNodeWrapper(null, downCombo1, basicLeft4, basicRightDodge, jumpAttack1, dashComboDodge));
        /// right > right+

        createMovementCombo(basicRight, basic2, new ComboNodeWrapper(null, downCombo1, basicLeftDodge, basicRight2, jumpAttack1, dashComboDodge));
        createMovementCombo(basicRight2, basic2, new ComboNodeWrapper(null, downCombo1, basicLeftDodge, basicRight3, jumpAttack1, dashComboDodge));
        createMovementCombo(basicRight3, basic2, new ComboNodeWrapper(null, downCombo1, basicLeftDodge, basicRight4, jumpAttack1, dashComboDodge));
        /// jump > jump+
        createMovementCombo(jumpAttack1, jumpAttack2, new ComboNodeWrapper(null, null, null, null, null, dashCombo));
        createMovementCombo(jumpAttack2, jumpAttack3, new ComboNodeWrapper(null, null, null, null, null, dashCombo));

        return registryWorker.build("shotogatana_combo_skill", (builder) -> new ExtendedComboBasicAttack(builder, root), ExtendedComboBasicAttack
                .createComboBasicAttack()
                .setCombo(root)
                .setMaxProtectTime(22)
                .setMaxPressTime(20)
                .setReserveTime(16)
                .setShouldDrawGui(false).setSkillTextureLocation(ResourceLocation.fromNamespaceAndPath(EpicFightMod.MODID, String.format("textures/gui/skills/weapon_innate/relentless_combo.png"))));
    }


    private static ComboNode createShotogatanaComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, boolean addSheathEvent){
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
                if (anim instanceof ShotogatanaAttackAnimation shotogatanaAttackAnimation) {
                    if(shotogatanaAttackAnimation.unsheatheTime != -1) {

                        node.addTimeEvent(new TimeStampedEvent(shotogatanaAttackAnimation.unsheatheTime,
                                ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));
                    }
                    if(shotogatanaAttackAnimation.sheathTime != -1) {

                        node.addTimeEvent(new TimeStampedEvent(shotogatanaAttackAnimation.sheathTime,
                                ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaInEvent(entityPatch))));
                    }
                }
            }
            return null;
        });

        return node;
    }
    private static ComboNode createShotogatanaDodgeComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, ComboNode followUpCombo, boolean addSheathEvent){
        ComboNode node = ComboNode.createNode(animation)
                .addBeginEvent(BaseEvent.create((playerPatch, entity, invinciblePlayer) -> {
                    WOHAnimationUtils.regainMovementEvent(playerPatch);
                }));

        // Defer the addTimeEvent call until animation.get() is non-null
        DEFERRED_SETUP.add(() -> {
            StaticAnimation anim = animation.get();
            if(anim instanceof AttackAnimation attackAnimation) {
                float time = attackAnimation.phases[attackAnimation.phases.length - 1].contact;
                float recovery = attackAnimation.phases[attackAnimation.phases.length - 1].recovery;
                float end = attackAnimation.phases[attackAnimation.phases.length - 1].end;

                node.addTimeEvent(new TimeStampedEvent(time,
                        ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.stopMovementEvent(entityPatch))));
                if (addSheathEvent) {

                    float sheatheTime = recovery + (end - recovery) * 0.75F;
                    node.addTimeEvent(new TimeStampedEvent(recovery,
                            ((entityPatch, target, invinciblePlayer) -> entityPatch.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_NEW_SHEATH, 0.1F))));
                }
            }
            float end = anim.getTotalTime() - 0.1F;
            node.addTimeEvent(new TimeStampedEvent(end,
                    ((entityPatch, target, invinciblePlayer) -> {
                        ComboBasicAttack comboAttack = InputManager.getComboBasicSkill();

                        if(comboAttack != null){
                            SkillContainer container = entityPatch.getSkill(WohSkills.SHOTOGATANA_COMBO);

                            if(container != null){
                                comboAttack.executeNodeOnServer(container, followUpCombo, 1, 1);
                            }
                        }
                    })));
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
            if(isSharp){
                ((AttackAnimation)anim).addProperty(
                        AnimationProperty.AttackPhaseProperty.SWING_SOUND,
                        EpicFightSounds.WHOOSH_SHARP.get());
            }
            return null;
        });

        return node;
    }
}
