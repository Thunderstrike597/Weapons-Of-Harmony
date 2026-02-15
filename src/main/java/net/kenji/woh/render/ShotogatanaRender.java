package net.kenji.woh.render;

import com.google.gson.JsonElement;
import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.manager.ShotogatanaManager;
import net.kenji.woh.gameasset.animation_types.BasisAirAttackAnimation;
import net.kenji.woh.gameasset.animation_types.BasisAttackAnimation;
import net.kenji.woh.gameasset.animation_types.WohSheathAnimation;
import net.kenji.woh.gameasset.WohSkills;
import net.kenji.woh.registry.animation.ShotogatanaAnimations;
import net.kenji.woh.registry.WohItems;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.DynamicAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.api.utils.math.MathUtils;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.renderer.patched.item.RenderItemBase;
import yesman.epicfight.world.capabilities.entitypatch.LivingEntityPatch;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class ShotogatanaRender extends RenderItemBase {


    private final ItemStack katana;
    private final ItemStack sheathStack;
    private final ItemStack sheathedWeaponStack;

    private AnimationManager.AnimationAccessor<StaticAnimation> idleSheathAnim = ShotogatanaAnimations.SHOTOGATANA_IDLE;
    private AnimationManager.AnimationAccessor<StaticAnimation> walkSheathAnim = ShotogatanaAnimations.SHOTOGATANA_WALK;

    private AnimationManager.AnimationAccessor<StaticAnimation> sheathAnim = ShotogatanaAnimations.SHOTOGATANA_SHEATH;
    private AnimationManager.AnimationAccessor<StaticAnimation> unsheathAnim = ShotogatanaAnimations.SHOTOGATANA_UNSHEATH;

    public static Map<UUID, Boolean> renderSheathMap = new HashMap<>();

    public ShotogatanaRender(JsonElement jsonElement) {
        super(jsonElement);
        if (jsonElement.getAsJsonObject().has("shotogatana_sheath")) {
            this.sheathStack = new ItemStack((ItemLike)Objects.requireNonNull((Item)ForgeRegistries.ITEMS.getValue(ResourceLocation.parse(jsonElement.getAsJsonObject().get("shotogatana_sheath").getAsString()))));
        } else {
            this.sheathStack = new ItemStack((ItemLike) WohItems.SHOTOGATANA_SHEATH.get());
        }
        this.katana = new ItemStack((ItemLike) WohItems.SHOTOGATANA.get());
        this.sheathedWeaponStack = new ItemStack((ItemLike) WohItems.SHOTOGATANA_IN_SHEATH.get());
    }


    private ItemStack getStack(LivingEntityPatch<?> entitypatch) {
        if (entitypatch instanceof PlayerPatch<?> playerPatch) {
            if (sheathAnim == null || unsheathAnim == null)
                return sheathStack;
            UUID playerID = playerPatch.getOriginal().getUUID();
            boolean isSheathed = ShotogatanaManager.sheathWeapon.getOrDefault(playerID, false);

            AnimationPlayer animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.LOWEST).animationPlayer;
            AnimationPlayer highAnimPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer;

            if(animPlayer.getAnimation() != idleSheathAnim && animPlayer.getAnimation() != walkSheathAnim) {
                animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.MIDDLE).animationPlayer;
            }
            if(animPlayer.getAnimation() != idleSheathAnim && animPlayer.getAnimation() != walkSheathAnim) {
                animPlayer = entitypatch.getClientAnimator().getCompositeLayer(Layer.Priority.HIGHEST).animationPlayer;
            }


            if(!animPlayer.getAnimation().get().isBasicAttackAnimation() && !(highAnimPlayer.getAnimation() instanceof WohSheathAnimation)) {
                if(playerPatch.getSkill(WohSkills.SHOTOGATANA_SKILL) != null) {
                    if (!isSheathed && !playerPatch.getSkill(WohSkills.SHOTOGATANA_SKILL).isActivated()) {
                        boolean isAttacking = BasisAttackAnimation.isAttacking.getOrDefault(playerID, false);
                        boolean isAirAttacking = BasisAirAttackAnimation.isAttacking.getOrDefault(playerID, false);
                        if (!isAttacking && !isAirAttacking) {
                            if (WohSheathAnimation.shouldAnimReplay.getOrDefault(playerID, true)) {
                                // playerPatch.playAnimationSynchronized(ShotogatanaAnimations.SHOTOGATANA_SHEATH, 0.2F);
                            }
                        }
                    }
                }
            }

            if(isSheathed){
                return sheathedWeaponStack;
            }
        }
        // Skill inactive → katana in hand
        return sheathStack;
    }

    @Override
    public void renderItemInHand(ItemStack stack, LivingEntityPatch<?> entitypatch, InteractionHand hand, OpenMatrix4f[] poses, MultiBufferSource buffer, PoseStack poseStack, int packedLight, float partialTicks) {
        OpenMatrix4f modelMatrix = this.getCorrectionMatrix(entitypatch, InteractionHand.MAIN_HAND, poses);

        ItemStack sheathItem = getStack(entitypatch);

        // Compare the actual items, not ItemStack references
        boolean isSheathed = ItemStack.isSameItem(sheathItem, sheathedWeaponStack);

        // Only render the katana if it's NOT sheathed AND it's a player
        if (!isSheathed && entitypatch instanceof PlayerPatch<?>) {
            poseStack.pushPose();
            MathUtils.mulStack(poseStack, modelMatrix);
            itemRenderer.renderStatic(katana, ItemDisplayContext.THIRD_PERSON_RIGHT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level) null, 0);
            poseStack.popPose();
        }

        modelMatrix = this.getCorrectionMatrix(entitypatch, InteractionHand.OFF_HAND, poses);

        // Always render the sheath/sheathed weapon in off-hand
        poseStack.pushPose();
        MathUtils.mulStack(poseStack, modelMatrix);
        itemRenderer.renderStatic(sheathItem, ItemDisplayContext.THIRD_PERSON_LEFT_HAND, packedLight, OverlayTexture.NO_OVERLAY, poseStack, buffer, (Level) null, 0);
        poseStack.popPose();

    }
}
