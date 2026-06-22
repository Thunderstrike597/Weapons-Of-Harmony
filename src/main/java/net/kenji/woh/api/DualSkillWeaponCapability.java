package net.kenji.woh.api;

import net.kenji.woh.api.interfaces.IHybridSkill;
import net.kenji.woh.gameasset.skills.WohSkillSlot;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.server.SPChangeSkill;
import yesman.epicfight.network.server.SPSetRemotePlayerSkill;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlot;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.ServerPlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;
import yesman.epicfight.world.capabilities.item.WeaponCapability;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

public class DualSkillWeaponCapability extends WeaponCapability {

    @Nullable
    private final Function<ItemStack, Skill> secondarySkillProvider;
    protected final Map<Style, Boolean> canUseShieldMap;

    protected DualSkillWeaponCapability(Builder builder) {
        super(builder);
        this.secondarySkillProvider = builder.secondarySkillProvider;
        this.canUseShieldMap = builder.canUseShieldMapProvider;
    }

    @Nullable
    public Skill getSecondarySkill(ItemStack itemStack) {
        return secondarySkillProvider != null ? secondarySkillProvider.apply(itemStack) : null;
    }

    public boolean canUseShield(PlayerPatch<?> patch) {
        Style style = this.getStyle(patch);
        return canUseShieldMap.getOrDefault(style, true);
    }


    public SkillSlot getSecondarySkillSlot(){
        return WohSkillSlot.WEAPON_SECONDARY_SKILL;
    }
    @Override
    public void changeWeaponInnateSkill(PlayerPatch<?> playerpatch, ItemStack itemstack) {
        SkillContainer passiveContainer = playerpatch.getSkill(this.getSecondarySkillSlot());
        Skill secondary = getSecondarySkill(itemstack);
        boolean wasActivated = passiveContainer != null
                && passiveContainer.getSkill() == secondary
                && passiveContainer.isActivated();
        int savedDuration = wasActivated ? passiveContainer.getRemainDuration() : 0;
        float savedResource = passiveContainer != null ?passiveContainer.getResource() : 0;
        int savedStack = passiveContainer != null ? passiveContainer.getStack() : 0;

        super.changeWeaponInnateSkill(playerpatch, itemstack); // ← only once

        EpicFightNetworkManager.PayloadBundleBuilder toLocal = EpicFightNetworkManager.PayloadBundleBuilder.create();
        EpicFightNetworkManager.PayloadBundleBuilder toRemote = EpicFightNetworkManager.PayloadBundleBuilder.create();

        SkillContainer passiveSkillContainer = playerpatch.getSkill(this.getSecondarySkillSlot());
        if (secondary != null && passiveSkillContainer != null) {
            if (passiveSkillContainer.getSkill() != secondary) {
                passiveSkillContainer.setSkill(secondary);
                toLocal.and(new SPChangeSkill(this.getSecondarySkillSlot(), ((Player)playerpatch.getOriginal()).getId(), secondary));
                toRemote.and(new SPSetRemotePlayerSkill(((Player)playerpatch.getOriginal()).getId(), this.getSecondarySkillSlot(), secondary));
            }
            if (savedDuration > 0) {
                passiveSkillContainer.setDuration(savedDuration);
            }
            if(savedResource > 0){
                passiveSkillContainer.setResource(savedResource);
            }
            if(savedStack > 0){
                passiveSkillContainer.setStack(savedStack);
            }
            if(wasActivated){
                passiveSkillContainer.activate();
                if(passiveSkillContainer.getSkill() instanceof IHybridSkill iHybridSkill){
                   if(playerpatch instanceof ServerPlayerPatch serverPlayerPatch)
                       iHybridSkill.sendSkillActivateToClient(true, serverPlayerPatch.getOriginal());
                }
            }
        } else if (passiveSkillContainer != null) {
            passiveSkillContainer.setSkill(null);
            toLocal.and(new SPChangeSkill(this.getSecondarySkillSlot(), ((Player)playerpatch.getOriginal()).getId(), (Skill)null));
            toRemote.and(new SPSetRemotePlayerSkill(((Player)playerpatch.getOriginal()).getId(), this.getSecondarySkillSlot(), (Skill)null));
        }

        toLocal.send((first, others) -> EpicFightNetworkManager.sendToPlayer(first, (ServerPlayer)playerpatch.getOriginal(), others));
        toRemote.send((first, others) -> EpicFightNetworkManager.sendToAllPlayerTrackingThisEntity(first, (ServerPlayer)playerpatch.getOriginal(), others));
    }


    public static Builder builder() {
        return new Builder();
    }

    public static class Builder extends WeaponCapability.Builder {

        @Nullable
        Function<ItemStack, Skill> secondarySkillProvider;
         Map<Style, Boolean> canUseShieldMapProvider = new HashMap<>();

        protected Builder() {
            this.constructor(b -> new DualSkillWeaponCapability((DualSkillWeaponCapability.Builder) b));

        }

        public Builder secondarySkill(Function<ItemStack, Skill> secondarySkill) {
            this.secondarySkillProvider = secondarySkill;
            return this;
        }
        public Builder canUseShield(Style style, boolean canUseShield){
            this.canUseShieldMapProvider.put(style, canUseShield);
            return this;
        }

        // Re-declare all parent builder methods to return Builder (fluent chain)
        @Override public Builder category(yesman.epicfight.world.capabilities.item.WeaponCategory category) { super.category(category); return this; }
        @Override public Builder swingSound(net.minecraft.sounds.SoundEvent s) { super.swingSound(s); return this; }
        @Override public Builder hitSound(net.minecraft.sounds.SoundEvent s) { super.hitSound(s); return this; }
        @Override public Builder hitParticle(yesman.epicfight.particle.HitParticleType p) { super.hitParticle(p); return this; }
        @Override public Builder collider(yesman.epicfight.api.collider.Collider c) { super.collider(c); return this; }
        @Override public Builder canBePlacedOffhand(boolean b) { super.canBePlacedOffhand(b); return this; }
        @Override public Builder reach(float r) { super.reach(r); return this; }
        @Override public Builder styleProvider(java.util.function.Function<yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch<?>, Style> s) { super.styleProvider(s); return this; }
        @Override public Builder innateSkill(Style style, java.util.function.Function<ItemStack, Skill> skill) { super.innateSkill(style, skill); return this; }
        @Override public Builder livingMotionModifier(Style s, yesman.epicfight.api.animation.LivingMotion m, yesman.epicfight.api.animation.AnimationManager.AnimationAccessor<? extends yesman.epicfight.api.animation.types.StaticAnimation> a) { super.livingMotionModifier(s, m, a); return this; }
        @Override public Builder comboCounterHandler(yesman.epicfight.world.entity.eventlistener.ComboCounterHandleEvent.ComboCounterHandler h) { super.comboCounterHandler(h); return this; }
        @Override public Builder zoomInType(yesman.epicfight.world.capabilities.item.CapabilityItem.ZoomInType z) { super.zoomInType(z); return this; }
        @Override
        public Builder weaponCombinationPredicator(java.util.function.Function<yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch<?>, Boolean> predicator) { super.weaponCombinationPredicator(predicator); return this; }

   }
}