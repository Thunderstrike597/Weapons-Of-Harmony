package net.kenji.woh.client.layers;

import com.mojang.blaze3d.vertex.PoseStack;
import net.kenji.woh.item.custom.base.HolsterWeaponBase;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.layers.RenderLayer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jline.utils.Log;
import org.joml.Matrix4f;
import org.joml.Quaternionf;
import org.lwjgl.glfw.GLFW;
import yesman.epicfight.api.client.model.Meshes;
import yesman.epicfight.api.utils.math.OpenMatrix4f;
import yesman.epicfight.client.mesh.HumanoidMesh;
import yesman.epicfight.client.renderer.patched.layer.ModelRenderLayer;
import yesman.epicfight.client.world.capabilites.entitypatch.player.AbstractClientPlayerPatch;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

import static net.kenji.woh.WohConfigClient.HOLSTER_HIDDEN_ITEMS;

@OnlyIn(Dist.CLIENT)
public class HolsteredItemLayer extends ModelRenderLayer<
        AbstractClientPlayer,
        AbstractClientPlayerPatch<AbstractClientPlayer>,
        PlayerModel<AbstractClientPlayer>,
        RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>>,
        HumanoidMesh
        > {
    public int itemSlotIndex;
    public int trueSlotIndex;
    public static Map<UUID, Boolean> debugMode = new HashMap<>();

    // Store adjustments per slot index
    public static Map<Integer, RotationAdjustments> rotationAdjustmentsMap = new HashMap<>();
    public static Map<Integer, TranslationAdjustments> translationdjustmentsMap = new HashMap<>();

    public class RotationAdjustments {
        public float xVal;
        public float yVal;
        public float zVal;

        public Quaternionf getRotation(){
            return new Quaternionf()
                    .rotateX(xVal)
                    .rotateY(yVal)
                    .rotateZ(zVal);
        }

        public RotationAdjustments(float x, float y, float z) {
            xVal = x;
            yVal = y;
            zVal = z;
        }

        public void LogCurrentValues() {
            Log.info("Rotation Values For SlotIndex: " + itemSlotIndex + " - X: " + xVal + " Y: " + yVal + " Z: " + zVal);
        }

        public void addXRotValue(float value) { xVal += value; }
        public void addYRotValue(float value) { yVal += value; }
        public void addZRotValue(float value) { zVal += value; }
    }
    public class TranslationAdjustments {
        public float xVal;
        public float yVal;
        public float zVal;

        public Vec3 getTranslation(){
            return new Vec3(xVal, yVal, zVal);
        }

        public TranslationAdjustments(float x, float y, float z) {
            xVal = x;
            yVal = y;
            zVal = z;
        }

        public void LogCurrentValues() {
            Log.info("Translation Values For SlotIndex: " + itemSlotIndex + " - X: " + xVal + " Y: " + yVal + " Z: " + zVal);
        }

        public void addXRotValue(float value) { xVal += value; }
        public void addYRotValue(float value) { yVal += value; }
        public void addZRotValue(float value) { zVal += value; }
    }

    public HolsteredItemLayer(Meshes.MeshAccessor<HumanoidMesh> mesh, int slotIndex) {
        super(mesh);
        if (rotationAdjustmentsMap.isEmpty()) {
            MinecraftForge.EVENT_BUS.register(new SubEventHandler());
        }
        this.itemSlotIndex = slotIndex;
        this.trueSlotIndex = 36 + slotIndex;

    }
    public static boolean showHolsterFor(Item item) {
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(item);
        if (id == null) return false;

        return !HOLSTER_HIDDEN_ITEMS.get().contains(id.toString());
    }


    @Override
    protected void renderLayer(
            AbstractClientPlayerPatch<AbstractClientPlayer> patch,
            AbstractClientPlayer player,
            @Nullable RenderLayer<AbstractClientPlayer, PlayerModel<AbstractClientPlayer>> parentLayer,
            PoseStack poseStack,
            MultiBufferSource buffer,
            int light,
            OpenMatrix4f[] poses,
            float limbSwing,
            float limbSwingAmount,
            float partialTicks,
            float ageInTicks
    ) {
        int currentLightLevel = LevelRenderer.getLightColor(
                player.level(),
                player.blockPosition()
        );
        ItemStack stack = findHolsteredItem(player);
        boolean showItem = showHolsterFor(stack.getItem());

        if (stack.isEmpty() || !showItem) return;

        if (!(stack.getItem() instanceof HolsterWeaponBase holsterItem)) return;


        if(holsterItem.holsterTransform == null) return;

        ItemStack holsterStack = holsterItem.holsterItem != null
                ? holsterItem.holsterItem.getDefaultInstance()
                : ItemStack.EMPTY;
        ItemStack unholsterStack = holsterItem.unholsteredItem != null
                ? holsterItem.unholsteredItem.getDefaultInstance()
                : ItemStack.EMPTY;

        RotationAdjustments rotAdj = rotationAdjustmentsMap.computeIfAbsent(itemSlotIndex, k -> {
            HolsterWeaponBase.QuaternionFPair rotPair = holsterItem.holsterTransform.rotationPair;
            return new RotationAdjustments(rotPair.keyX, rotPair.keyY, rotPair.keyZ);
        });
        TranslationAdjustments posAdj = translationdjustmentsMap.computeIfAbsent(itemSlotIndex, k -> {
            HolsterWeaponBase.Vec3Pair posPair = holsterItem.holsterTransform.translationPair;
            return new TranslationAdjustments(posPair.keyX, posPair.keyY, posPair.keyZ);
        });


        Vec3 pos = posAdj.getTranslation();
        Vec3 scale = holsterItem.holsterTransform.scalePair.key;
        Quaternionf rot = rotAdj.getRotation();

        poseStack.pushPose();
        try {
            // 🔥 Attach to Epic Fight BODY bone
            Matrix4f jomlMatrix = getMatrix4f(poses, holsterItem);
            poseStack.mulPoseMatrix(jomlMatrix);

            // Local transform
            poseStack.translate(pos.x, pos.y, pos.z);
            poseStack.mulPose(rot);
            poseStack.scale((float) scale.x, (float) scale.y, (float) scale.z);

            ItemStack renderStack =
                    player.getMainHandItem() != stack
                            ? holsterStack
                            : unholsterStack;

            if (!renderStack.isEmpty()) {
                Minecraft.getInstance().getItemRenderer().renderStatic(
                        renderStack,
                        ItemDisplayContext.NONE,
                        currentLightLevel,
                        OverlayTexture.NO_OVERLAY,
                        poseStack,
                        buffer,
                        player.level(),
                        0
                );
            }
        } finally {
            poseStack.popPose();
        }
    }

    private static @NotNull Matrix4f getMatrix4f(OpenMatrix4f[] poses, HolsterWeaponBase holsterItem) {
        OpenMatrix4f bodyMatrix = poses[holsterItem.holsterJoints.getHotBarJoint().getId()];


        // Option 2: Manual conversion preserving all components
        Matrix4f jomlMatrix = new Matrix4f(
                bodyMatrix.m00, bodyMatrix.m01, bodyMatrix.m02, bodyMatrix.m03,
                bodyMatrix.m10, bodyMatrix.m11, bodyMatrix.m12, bodyMatrix.m13,
                bodyMatrix.m20, bodyMatrix.m21, bodyMatrix.m22, bodyMatrix.m23,
                bodyMatrix.m30, bodyMatrix.m31, bodyMatrix.m32, bodyMatrix.m33
        );
        return jomlMatrix;
    }

    private ItemStack findHolsteredItem(Player player){
        int index = trueSlotIndex;
        Slot slot = player.inventoryMenu.slots.get(index);
        if(slot.getItem().getItem() instanceof HolsterWeaponBase holsterBaseItem){
            if(slot.getItem() != player.getMainHandItem() || holsterBaseItem.unholsteredItem != null) {
                return slot.getItem();
            }
        }
        return ItemStack.EMPTY;
    }

    private ItemStack findOffHandHolsteredItem(Player player){
        Slot offHandSlot = player.inventoryMenu.slots.get(45);
        AtomicReference<ItemStack> stack = new AtomicReference<>(ItemStack.EMPTY);
        player.getCapability(EpicFightCapabilities.CAPABILITY_ENTITY).ifPresent(cap -> {
            if (cap instanceof PlayerPatch<?> patch) {
                boolean isValid = patch.isOffhandItemValid();
                ItemStack offHandStack = patch.getValidItemInHand(InteractionHand.OFF_HAND);
                if(isValid && offHandStack.getItem() instanceof HolsterWeaponBase){
                    stack.set(offHandStack);
                }
            }
        });

        return stack.get();
    }
    @OnlyIn(Dist.CLIENT)
    public static class SubEventHandler {
        public static int selectedIndex = 0;
        public static int getSelectedSlot(){
            return selectedIndex;
        }
        @SubscribeEvent
        public void onKeyPress(InputEvent.Key event) {
            // Get the player's currently selected hotbar slot
            UUID uuid = Minecraft.getInstance().getUser().getProfileId();
            Player player = Minecraft.getInstance().player;
            if (player == null) {
                if(event.getKey() == GLFW.GLFW_KEY_KP_MULTIPLY){
                    HolsteredItemLayer.debugMode.put(uuid, true);
                }
                if(event.getKey() == GLFW.GLFW_KEY_KP_DIVIDE){
                    HolsteredItemLayer.debugMode.remove(uuid);
                }
                return;
            }
            if(!HolsteredItemLayer.debugMode.getOrDefault(player.getUUID(), false))
                return;

            if(event.getKey() == GLFW.GLFW_KEY_0)
                selectedIndex = 0;
            if(event.getKey() == GLFW.GLFW_KEY_1)
                selectedIndex = 1;
            if(event.getKey() == GLFW.GLFW_KEY_2)
                selectedIndex = 2;
            if(event.getKey() == GLFW.GLFW_KEY_3)
                selectedIndex = 3;
            if(event.getKey() == GLFW.GLFW_KEY_4)
                selectedIndex = 4;
            if(event.getKey() == GLFW.GLFW_KEY_5)
                selectedIndex = 5;
            if(event.getKey() == GLFW.GLFW_KEY_6)
                selectedIndex = 6;
            if(event.getKey() == GLFW.GLFW_KEY_7)
                selectedIndex = 7;
            if(event.getKey() == GLFW.GLFW_KEY_8)
                selectedIndex = 8;

            // Get adjustments for the selected slot
            RotationAdjustments rotAdj = rotationAdjustmentsMap.get(getSelectedSlot());
            TranslationAdjustments posAdj = translationdjustmentsMap.get(getSelectedSlot());

            if (rotAdj == null) return;
            if(event.getModifiers() != GLFW.GLFW_MOD_SHIFT) {
                if (event.getKey() == GLFW.GLFW_KEY_RIGHT) {
                    rotAdj.addXRotValue(0.005F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_LEFT) {
                    rotAdj.addXRotValue(-0.005F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_UP) {
                    rotAdj.addYRotValue(0.005F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_DOWN) {
                    rotAdj.addYRotValue(-0.005F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_KP_ADD) {
                    rotAdj.addZRotValue(0.005F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_KP_SUBTRACT) {
                    rotAdj.addZRotValue(-0.005F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_KP_ENTER) {
                    rotAdj.LogCurrentValues();
                }
            }
            else{
                if (event.getKey() == GLFW.GLFW_KEY_RIGHT) {
                    posAdj.addXRotValue(0.001F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_LEFT) {
                    posAdj.addXRotValue(-0.001F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_UP) {
                    posAdj.addYRotValue(0.001F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_DOWN) {
                    posAdj.addYRotValue(-0.001F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_KP_ADD) {
                    posAdj.addZRotValue(0.001F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_KP_SUBTRACT) {
                    posAdj.addZRotValue(-0.001F);
                }
                if (event.getKey() == GLFW.GLFW_KEY_KP_ENTER) {
                    posAdj.LogCurrentValues();
                }
            }
        }
    }
}