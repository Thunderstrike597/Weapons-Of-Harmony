package net.kenji.woh.gameasset.skills.combos;

import com.p1nero.invincible.api.events.BaseEvent;
import com.p1nero.invincible.api.events.TimeStampedEvent;
import com.p1nero.invincible.api.skill.ComboNode;
import com.p1nero.invincible.client.InputManager;
import com.p1nero.invincible.conditions.LeftCondition;
import com.p1nero.invincible.conditions.PressedTimeCondition;
import com.p1nero.invincible.conditions.RightCondition;
import com.p1nero.invincible.conditions.SprintingCondition;
import com.p1nero.invincible.skill.ComboBasicAttack;
import net.corruptdog.cdm.gameasset.CorruptAnimations;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.WOHAnimationUtils;
import net.kenji.woh.api.basegameassets.condition.InAirCondition;
import net.kenji.woh.api.basegameassets.skills.BaseComboBuilder;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.gameasset.skills.TenraiSkillInnate;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.animation.TenraiAnimations;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
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

public class TenraiCombos extends BaseComboBuilder {



    public static Skill buildTenraiSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {

        ComboNode root = ComboNode.create();
        ComboNode basicAttack = ComboNode.createNode(TenraiAnimations.TENRAI_AUTO_1)
                .setStunTypeModifier(StunType.HOLD)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaInEvent(entityPatch))));



        ComboNode basic1 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_2).addTimeEvent(new TimeStampedEvent(0.05F, ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).setPlaySpeed(0.8F);
        ComboNode basic2 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_3).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).setPlaySpeed(0.9F);
        ComboNode basic3 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_4).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));
        ComboNode basic4 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_5).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));
        ComboNode basic5 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_6).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));

        ComboNode skillCombo1 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_COMBO_1).addCondition(new PressedTimeCondition(5));
        ComboNode skillCombo2 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_COMBO_2).addCondition(new PressedTimeCondition(5));
        ComboNode skillCombo3 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_COMBO_3).addCondition(new PressedTimeCondition(5));
        ComboNode skillCombo4 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_COMBO_4).addCondition(new PressedTimeCondition(5));

        ComboNode leftDodge = createTenraiDodgeComboNode(CorruptAnimations.WOLFDODGE_LEFT, basic1).addCondition(new LeftCondition());
        ComboNode rightDodge = createTenraiDodgeComboNode(CorruptAnimations.WOLFDODGE_RIGHT, basic1).addCondition(new RightCondition());


        /// root
        createMovementCombo(root, basicAttack, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo1));
        /// root decision
        ComboNode rootDecision = createMovementCombo(basicAttack, basic1, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo2));

        /// basic > basic+
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo3));
        createMovementCombo(basic2, basic3, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo4));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo3));
        createMovementCombo(basic4, basic5, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo2));
        /// left > left+
        createMovementCombo(leftDodge, basic1, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo3));
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo4));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo2));
        createMovementCombo(basic4, basic5, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo3));
        ///right > right+
        createMovementCombo(rightDodge, basic1, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo3));
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo4));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo3));
        createMovementCombo(basic4, basic5, new ComboNodeWrapper(leftDodge, rightDodge, skillCombo2));
        ///jump > jump+

        basic5.key1(rootDecision);
        skillCombo1.key1(rootDecision);
        skillCombo2.key1(rootDecision);
        skillCombo3.key1(rootDecision);
        skillCombo4.key1(rootDecision);




        return registryWorker.build("tenrai_combo_skill", ComboBasicAttack::new, ComboBasicAttack
                .createComboBasicAttack()
                .setCombo(root)
                .setMaxProtectTime(22)
                .setMaxPressTime(5)
                .setReserveTime(16)
                .setShouldDrawGui(true).setSkillTextureLocation(ResourceLocation.fromNamespaceAndPath(EpicFightMod.MODID, String.format("textures/gui/skills/weapon_innate/relentless_combo.png"))));
    }
    public static Skill buildSplitTenraiSkills(SkillBuildEvent.ModRegistryWorker registryWorker) {

        ComboNode root = ComboNode.create();
        ComboNode basicAttack = ComboNode.createNode(TenraiAnimations.TENRAI_AUTO_1)
                .setStunTypeModifier(StunType.HOLD)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaInEvent(entityPatch))));


        ComboNode basic1 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_AUTO_1).addTimeEvent(new TimeStampedEvent(0.05F, ((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).setPlaySpeed(0.8F);
        ComboNode basic2 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_AUTO_2).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch)))).setPlaySpeed(0.9F);
        ComboNode basic3 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_AUTO_3).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));
        ComboNode basic4 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_AUTO_4).addTimeEvent(new TimeStampedEvent(0.05F,((entityPatch, target, invinciblePlayer) -> WOHAnimationUtils.katanaOutEvent(entityPatch))));

        ComboNode tenraiDash = createTenraiComboNode(TenraiAnimations.TENRAI_DASH).addCondition(new SprintingCondition());
        ComboNode tenraiAirSlash = createTenraiComboNode(TenraiAnimations.TENRAI_AIRSLASH).addCondition(new InAirCondition());

        ComboNode leftDodge = createTenraiDodgeComboNode(CorruptAnimations.WOLFDODGE_LEFT, basic1).addCondition(new LeftCondition());
        ComboNode rightDodge = createTenraiDodgeComboNode(CorruptAnimations.WOLFDODGE_RIGHT, basic1).addCondition(new RightCondition());



        /// root
        createMovementCombo(root, basicAttack, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        /// root decision
        ComboNode rootDecision = createMovementCombo(basicAttack, basic1, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));

        /// basic > basic+
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        createMovementCombo(basic2, basic3, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        /// left > left+
        createMovementCombo(leftDodge, basic1, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        ///right > right+
        createMovementCombo(rightDodge, basic1, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, tenraiDash, tenraiAirSlash));
        ///jump > jump+

        basic4.key1(rootDecision);

        return registryWorker.build("tenrai_split_combo_skill", ComboBasicAttack::new, ComboBasicAttack
                .createComboBasicAttack()
                .setCombo(root)
                .setMaxProtectTime(22)
                .setMaxPressTime(5)
                .setReserveTime(16)
                .setShouldDrawGui(true).setSkillTextureLocation(ResourceLocation.fromNamespaceAndPath(EpicFightMod.MODID, String.format("textures/gui/skills/weapon_innate/relentless_combo.png"))));
    }

    private static ComboNode createTenraiComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation){
        ComboNode node = ComboNode.createNode(animation);

        return node;
    }
    private static ComboNode createTenraiDodgeComboNode(AnimationManager.AnimationAccessor<? extends StaticAnimation> animation, ComboNode followUpCombo){
        ComboNode node = ComboNode.createNode(animation);

        // Defer the addTimeEvent call until animation.get() is non-null
        DEFERRED_SETUP.add(() -> {
            StaticAnimation anim = animation.get();
            float end = anim.getTotalTime() - 0.1F;
            node.addTimeEvent(new TimeStampedEvent(end,
                    ((entityPatch, target, invinciblePlayer) -> {
                        ComboBasicAttack comboAttack = InputManager.getComboBasicSkill();

                        if(comboAttack != null){
                            SkillContainer container = entityPatch.getSkill(WohSkills.TENRAI_COMBO);

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
