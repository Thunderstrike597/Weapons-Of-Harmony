package net.kenji.woh.gameasset;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;
import java.util.Map;

public class ShotogatanaSkillInnate extends WeaponInnateSkill {

    public ShotogatanaSkillInnate(Skill.Builder<? extends Skill> builder) {
        super(builder);
    }

    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.PARRYING.getSkillTexture();
    }

    private final Map<StaticAnimation, AttackAnimation> comboAnimation = Maps.newHashMap();

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        return true;
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }

    public void onRemoved(SkillContainer container) {
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        DynamicAnimation current =
                executer.getServerAnimator().animationPlayer.getAnimation();
        if (current == null) return;

        if(current instanceof StaticAnimation staticAnimation) {
            AttackAnimation next = this.comboAnimation.get(staticAnimation);
            if (next != null) {
                executer.playAnimationSynchronized(next, 0.0F);
                super.executeOnServer(executer, args);
            }
            else if(!staticAnimation.isBasicAttackAnimation()){
                executer.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_SKILL_INNATE, 0.05F);
                super.executeOnServer(executer, args);
            }
        }
    }

    @Override
    public boolean checkExecuteCondition(PlayerPatch<?> executer) {
        DynamicAnimation current =
                executer.getAnimator().getPlayerFor(null).getAnimation();

        if (current == null) return false;

        StaticAnimation staticAnim = (StaticAnimation) current.getRealAnimation();

        EntityState state = executer.getEntityState();
        return this.comboAnimation.containsKey(staticAnim)
                && state.canUseSkill()
                && state.inaction();
    }
    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE).append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(Component.translatable(traslatableText + ".tooltip", new Object[]{this.maxStackSize}).withStyle(ChatFormatting.DARK_GRAY));
        this.generateTooltipforPhase(list, itemStack, cap, playerCap, (Map)this.properties.get(0), "Each Strike:");
        return list;
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        this.comboAnimation.clear();
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_1, (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_5);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_2, (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_3);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_3, (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_6);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_4, (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_3);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_5, (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_6);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_6, (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_3);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_4, (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_6);

        return this;
    }
}
