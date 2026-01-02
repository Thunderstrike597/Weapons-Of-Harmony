package net.kenji.woh.gameasset;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.registry.WohSkills;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.api.animation.types.*;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.*;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ShotogatanaSkillInnate extends WeaponInnateSkill {

    private static final int REQUIRED_NO_DAMAGE_TICKS = 100; // 5 seconds (20 ticks = 1 second)

    private static float resourceTakeAmount = 5;

    // Track last hurt time per player
    private final Map<UUID, Boolean> wasHurt = Maps.newHashMap();

    public ShotogatanaSkillInnate(Skill.Builder<? extends Skill> builder) {
        super(builder);
        this.consumption = 20F;
        this.maxStackSize = 1;
    }


    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.GRASPING_SPIRE.getSkillTexture();
    }

    @Override
    public boolean shouldDraw(SkillContainer container) {
        return super.shouldDraw(container);
    }

    private final Map<StaticAnimation, AttackAnimation> comboAnimation = Maps.newHashMap();

    @Override
    public boolean canExecute(PlayerPatch<?> executer) {
        // Just call super - it will check our checkExecuteCondition() + resources
        return super.canExecute(executer);
    }

    @Override
    public void updateContainer(SkillContainer container) {
        if (container.getExecuter().getOriginal() != null) {
            Player player = container.getExecuter().getOriginal();
            UUID playerId = player.getUUID();
            // Reset cooldown when hurt
            if (player.hurtMarked) {
                if(container.getExecuter() instanceof ServerPlayerPatch serverPlayerPatch){
                    setConsumptionSynchronize(serverPlayerPatch, container.getResource() - resourceTakeAmount);
                    setStackSynchronize(serverPlayerPatch, container.getStack() - 1);

                }
            }
        }


        super.updateContainer(container);
    }

    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
    }

    public void onRemoved(SkillContainer container) {
        if (container.getExecuter().getOriginal() != null) {
            UUID playerId = container.getExecuter().getOriginal().getUUID();
            wasHurt.remove(playerId);
        }
    }

    @Override
    public void executeOnServer(ServerPlayerPatch executer, FriendlyByteBuf args) {
        DynamicAnimation current =
                executer.getServerAnimator().animationPlayer.getAnimation();

        if (current instanceof StaticAnimation staticAnimation) {
            AttackAnimation next = this.comboAnimation.get(staticAnimation);
            if (next != null) {
                executer.playAnimationSynchronized(next, 0.0F);
            } else if (!staticAnimation.isBasicAttackAnimation()) {
                executer.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_SKILL_INNATE, 0.05F);
            }
        }
        super.executeOnServer(executer, args);
    }

    @Override
    public boolean checkExecuteCondition(PlayerPatch<?> executor) {
        // First check the base animation conditions
        DynamicAnimation current = executor.getAnimator().getPlayerFor(null).getAnimation();

        if (current != null) {
            StaticAnimation staticAnim = (StaticAnimation) current.getRealAnimation();
            // Only allow if in combo OR if it's a basic attack animation
            if (!this.comboAnimation.containsKey(staticAnim) && staticAnim.isBasicAttackAnimation()) {
                return false;
            }
        }

        return super.checkExecuteCondition(executor);
    }

    @Override
    public List<Component> getTooltipOnItem(ItemStack itemStack, CapabilityItem cap, PlayerPatch<?> playerCap) {
        List<Component> list = Lists.newArrayList();
        String traslatableText = this.getTranslationKey();
        list.add(Component.translatable(traslatableText).withStyle(ChatFormatting.WHITE)
                .append(Component.literal(String.format("[%.0f]", this.consumption)).withStyle(ChatFormatting.AQUA)));
        list.add(Component.translatable(traslatableText + ".tooltip",
                        REQUIRED_NO_DAMAGE_TICKS / 20) // Convert ticks to seconds
                .withStyle(ChatFormatting.DARK_GRAY));
        this.generateTooltipforPhase(list, itemStack, cap, playerCap,
                (Map)this.properties.get(0), "Each Strike:");
        return list;
    }

    @Override
    public WeaponInnateSkill registerPropertiesToAnimation() {
        this.comboAnimation.clear();
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_1,
                (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_5);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_2,
                (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_3);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_3,
                (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_6);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_4,
                (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_3);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_5,
                (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_6);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_AUTO_6,
                (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_3);
        this.comboAnimation.put(ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_AUTO_4,
                (AttackAnimation)ShotogatanaAnimations.SHOTOGATANA_AUTO_6);

        return this;
    }
}