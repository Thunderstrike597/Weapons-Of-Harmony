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
import net.kenji.woh.api.basegameassets.ExtendedComboBasicAttack;
import net.kenji.woh.api.basegameassets.condition.CooldownCounterCondition;
import net.kenji.woh.api.basegameassets.condition.InAirCondition;
import net.kenji.woh.api.basegameassets.condition.SkillActivatedCondition;
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
                .addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, true));
        ComboNode basicSkillAttack = ComboNode.createNode(TenraiAnimations.TENRAI_SKILL_AUTO_1)
                .setStunTypeModifier(StunType.HOLD)
                .setDamageMultiplier(ValueModifier.multiplier(0.5F))
                .setCanBeInterrupt(false)
                .addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, false));




        ComboNode basic1 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_2).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, true)).setPlaySpeed(0.8F);
        ComboNode basic2 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_3).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, true)).setPlaySpeed(0.9F);
        ComboNode basic3 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_4).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, true));
        ComboNode basic4 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_5).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, true));

        ComboNode basicSkill1 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_AUTO_2).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, false)).setPlaySpeed(0.8F);
        ComboNode basicSkill2 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_AUTO_3).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, false)).setPlaySpeed(0.8F);
        ComboNode basicSkill3 = createTenraiComboNode(TenraiAnimations.TENRAI_SKILL_AUTO_4).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, false)).setPlaySpeed(0.8F);


        ComboNode leftDodge = createTenraiDodgeComboNode(CorruptAnimations.WOLFDODGE_LEFT, basic1).addCondition(new LeftCondition());
        ComboNode rightDodge = createTenraiDodgeComboNode(CorruptAnimations.WOLFDODGE_RIGHT, basic1).addCondition(new RightCondition());
        ComboNode backAttack = createTenraiDodgeComboNode(TenraiAnimations.TENRAI_AUTO_3, basic1).addCondition(new DownCondition());
        backAttack.addCondition(new CooldownCounterCondition(backAttack, 6.75F));

        ComboNode dash1 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_1).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, true)).addCondition(new SprintingCondition()).setPlaySpeed(0.8F);
        ComboNode dash2 = createTenraiComboNode(TenraiAnimations.TENRAI_AUTO_5).addCondition(new SkillActivatedCondition(WohSkills.SPLIT_TENRAI, true)).addCondition(new SprintingCondition()).setPlaySpeed(0.8F);


        /// root
        createMovementCombo(root, basicAttack, new ComboNodeWrapper(leftDodge, rightDodge, basicSkillAttack, dash1));
        /// root decision
        ComboNode rootDecision = createMovementCombo(basicAttack, basic1, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));

        /// basic > basic+
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        createMovementCombo(basic2, basic3, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        /// left > left+
        createMovementCombo(leftDodge, basic1, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        ///right > right+
        createMovementCombo(rightDodge, basic1, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        createMovementCombo(basic1, basic2, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        createMovementCombo(basic3, basic4, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, basicSkillAttack, dash1));
        ///jump > jump+

        createMovementCombo(basicSkillAttack, basicSkill1, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, dash1));
        createMovementCombo(basicSkill1, basicSkill2, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, dash1));
        createMovementCombo(basicSkill2, basicSkill3, new ComboNodeWrapper(leftDodge, rightDodge, backAttack, dash1));

        /// dash > dash++
        createMovementCombo(dash1, dash2, new ComboNodeWrapper(leftDodge, rightDodge, backAttack));


        return registryWorker.build("tenrai_combo_skill", (builder) -> new ExtendedComboBasicAttack(builder, root), ExtendedComboBasicAttack
                .createComboBasicAttack()
                .setCombo(root)
                .setMaxProtectTime(22)
                .setMaxPressTime(5)
                .setReserveTime(16)
                .setShouldDrawGui(false).setSkillTextureLocation(ResourceLocation.fromNamespaceAndPath(EpicFightMod.MODID, String.format("textures/gui/skills/weapon_innate/relentless_combo.png"))));
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
