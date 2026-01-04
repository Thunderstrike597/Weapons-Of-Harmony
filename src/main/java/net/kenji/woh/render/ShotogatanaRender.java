package net.kenji.woh.render;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.gameasset.animations.BasisAirAttackAnimation;
import net.kenji.woh.gameasset.animations.BasisAttackAnimation;
import net.kenji.woh.gameasset.animations.WohSheathAnimation;
import net.kenji.woh.item.custom.weapon.Shotogatana;
import net.kenji.woh.registry.WohSkills;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.LivingMotions;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.asset.AssetAccessor;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.gameasset.Animations;
import yesman.epicfight.model.armature.HumanoidArmature;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShotogatanaRender extends RenderItemBase {
    public static final Map<UUID, Boolean> sheathWeapon = new HashMap<>();
    public static final Map<UUID, Boolean> isHoldingSheathed = new HashMap<>();

    public static final Map<UUID, Boolean> hasSetupWeapon = new HashMap<>();


    private final ItemStack katana;
    private final ItemStack sheathStack;
    private final ItemStack sheathedWeaponStack;

    private AnimationManager.AnimationAccessor<StaticAnimation> idleSheathAnim = ShotogatanaAnimations.SHOTOGATANA_IDLE;
    private AnimationManager.AnimationAccessor<StaticAnimation> walkSheathAnim = ShotogatanaAnimations.SHOTOGATANA_WALK;

    private AnimationManager.AnimationAccessor<StaticAnimation> sheathAnim = ShotogatanaAnimations.SHOTOGATANA_SHEATH;
    private AnimationManager.AnimationAccessor<StaticAnimation> unsheathAnim = ShotogatanaAnimations.SHOTOGATANA_UNSHEATH;

    public ShotogatanaRender(JsonElement jsonElement) {
        super(jsonElement);
        this.katana = new ItemStack((ItemLike) WohItems.SHOTOGATANA.get());
        this.sheathStack = new ItemStack((ItemLike) WohItems.SHOTOGATANA_SHEATH.get());
        this.sheathedWeaponStack = new ItemStack((ItemLike) WohItems.SHOTOGATANA_IN_SHEATH.get());
    }

    @SubscribeEvent
    public static void onPlayerLeave(PlayerEvent.PlayerLoggedOutEvent event) {
        UUID playerId = event.getEntity().getUUID();
        sheathWeapon.remove(playerId);
        hasSetupWeapon.remove(playerId);
    }

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        UUID playerId = event.player.getUUID();
        Player player = event.player;
        boolean hasSetup = hasSetupWeapon.getOrDefault(playerId, false);
        if (event.player.getMainHandItem().getItem() == WohItems.SHOTOGATANA.get()) {
            if (!hasSetup) {
                sheathWeapon.put(playerId, true);
                hasSetupWeapon.put(playerId, true);
            }
        }
        if (player.getMainHandItem().getItem() instanceof Shotogatana) {
            player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
                if (cap instanceof PlayerPatch<?> patch) {
                    boolean sheathed = sheathWeapon.getOrDefault(playerId, false);
                    if (sheathed)
                        patch.getAnimator().addLivingAnimation(LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_GUARD);
                    else
                        patch.getAnimator().addLivingAnimation(LivingMotions.BLOCK, ShotogatanaAnimations.SHOTOGATANA_UNSHEATHED_GUARD);
                }
            });
        }
    }

    @SubscribeEvent
    public static void onDeath(PlayerEvent.PlayerRespawnEvent event) {
        UUID playerId = event.getEntity().getUUID();
        hasSetupWeapon.replace(playerId, false);

    }

    private ItemStack getStack(LivingEntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (sheathAnim == null || unsheathAnim == null)
                return sheathStack;
            UUID playerID = playerPatch.getOriginal().getUUID();
            boolean isSheathed = sheathWeapon.getOrDefault(playerID, false);

            AnimationPlayer animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.LOWEST).animationPlayer;
            AnimationPlayer highAnimPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer;

            if (animPlayer.getAnimation() != idleSheathAnim && animPlayer.getAnimation() != walkSheathAnim) {
                animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.MIDDLE).animationPlayer;
            }
            if (animPlayer.getAnimation() != idleSheathAnim && animPlayer.getAnimation() != walkSheathAnim) {
                animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer;
            }


            if (!animPlayer.getAnimation().get().isBasicAttackAnimation() && !(highAnimPlayer.getAnimation() instanceof WohSheathAnimation)) {
                if (playerPatch.getSkill(WohSkills.SHEATH_STANCE) != null) {
                    if (!isSheathed && !playerPatch.getSkill(WohSkills.SHEATH_STANCE).isActivated()) {
                        boolean isAttacking = BasisAttackAnimation.isAttacking.getOrDefault(playerID, false);
                        boolean isAirAttacking = BasisAirAttackAnimation.isAttacking.getOrDefault(playerID, false);
                        if (!isAttacking && !isAirAttacking) {
                            if (WohSheathAnimation.shouldAnimReplay.getOrDefault(playerID, true)) {
                                playerPatch.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_SHEATH, 0.2F);
                            }
                        }
                    }
                }
            }

            if (isSheathed) {
                return sheathedWeaponStack;
            }
        }
        // Skill inactive → katana in hand
        return sheathStack;
    }

    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {

        OpenMatrix4f modelMatrixMainHand = this.getCorrectionMatrix(entitypatch, InteractionHand.MAIN_HAND, poses);
        OpenMatrix4f modelMatrixOffHand = this.getCorrectionMatrix(entitypatch, InteractionHand.OFF_HAND, poses);

        ItemStack sheathItem = getStack(entitypatch);
        boolean sheathed = (sheathItem == sheathedWeaponStack);

        if (!sheathed || !(entitypatch instanceof PlayerPatch<?>)) {
            poseStack.pushPose();
            MathUtils.mulStack(poseStack, modelMatrixMainHand);
            itemRenderer.renderStatic(katana, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level) null, 0);
            poseStack.popPose();
        }

        poseStack.pushPose();
        MathUtils.mulStack(poseStack, modelMatrixOffHand);
        itemRenderer.renderStatic(sheathItem, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level) null, 0);
        poseStack.popPose();

    }
}
