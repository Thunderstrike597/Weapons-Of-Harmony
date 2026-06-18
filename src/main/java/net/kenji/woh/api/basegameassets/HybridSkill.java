package net.kenji.woh.api.basegameassets;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import net.kenji.woh.api.interfaces.IHybridSkill;
import net.kenji.woh.api.interfaces.ITranslatableSkill;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.lwjgl.opengl.GL11;
import yesman.epicfight.api.utils.math.Vec2f;
import yesman.epicfight.client.gui.BattleModeGui;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HybridSkill extends Skill implements IHybridSkill, ITranslatableSkill {
    protected static final int MAX_HOLD_COOLDOWN = 16;

    private static final Vec2f[] CLOCK_POS = new Vec2f[]{new Vec2f(0.5F, 0.5F), new Vec2f(0.5F, 0.0F), new Vec2f(0.0F, 0.0F), new Vec2f(0.0F, 1.0F), new Vec2f(1.0F, 1.0F), new Vec2f(1.0F, 0.0F)};
    public final Map<UUID, Boolean> didActivate = new HashMap<>();
    public boolean wasHoldingSkill = false;
    public final float stackChargeTime;
    public int holdCooldown = MAX_HOLD_COOLDOWN;

    public HybridSkill(SkillBuilder<? extends Skill> builder, float stackChargeTime) {
        super(builder);
        this.stackChargeTime = stackChargeTime;
    }

    protected void tickHoldCooldown(){
        if(holdCooldown > 0)
            holdCooldown--;
    }
    protected void setMaxHoldCooldown(){
        holdCooldown = MAX_HOLD_COOLDOWN;
    }
    @Override
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }
    @Override
    public float getCooldownRegenPerSecond(PlayerPatch<?> playerpatch) {
        return 1.0F / getStackChargeTime(); // 1 stack every 10 seconds → full 3 stacks in 30s
    }
    @Override
    public float getStackChargeTime() {
        return stackChargeTime;
    }

    @Override
    public boolean canHoldInnate() {
        return holdCooldown <= 0;
    }

    @Override
    public void setWasHoldingSkill(boolean value) {
        wasHoldingSkill = value;
    }

    @Override
    public boolean getWasHoldingSkill() {
        return wasHoldingSkill;
    }

    @Override
    public void sendSkillActivateToClient(boolean value, ServerPlayer player) {

    }

    @Override
    public boolean shouldDraw(SkillContainer container) {
        return true;
    }

    @Override
    public void drawOnGui(BattleModeGui gui, SkillContainer container, GuiGraphics guiGraphics, float x, float y, float partialTick) {
        guiGraphics.pose().pushPose();
        guiGraphics.pose().translate(0.0F, (float)gui.getSlidingProgression(), 0.0F);
        boolean creative = ((Player)container.getExecutor().getOriginal()).isCreative();
        boolean fullstack = creative || container.isFull();
        boolean canUse = !container.isDisabled() && container.getSkill().checkExecuteCondition(container);
        float cooldownRatio = !fullstack && !container.isActivated() ? container.getResource(partialTick) : 1.0F;
        int vertexNum = 0;
        float iconSize = 32.0F;
        float bottom = y + iconSize;
        float right = x + iconSize;
        float middle = x + iconSize * 0.5F;
        float lastVertexX = 0.0F;
        float lastVertexY = 0.0F;
        float lastTexX = 0.0F;
        float lastTexY = 0.0F;
        if (cooldownRatio < 0.125F) {
            vertexNum = 6;
            lastTexX = cooldownRatio / 0.25F;
            lastTexY = 0.0F;
            lastVertexX = middle + iconSize * lastTexX;
            lastVertexY = y;
            lastTexX += 0.5F;
        } else if (cooldownRatio < 0.375F) {
            vertexNum = 5;
            lastTexX = 1.0F;
            lastTexY = (cooldownRatio - 0.125F) / 0.25F;
            lastVertexX = right;
            lastVertexY = y + iconSize * lastTexY;
        } else if (cooldownRatio < 0.625F) {
            vertexNum = 4;
            lastTexX = (cooldownRatio - 0.375F) / 0.25F;
            lastTexY = 1.0F;
            lastVertexX = right - iconSize * lastTexX;
            lastVertexY = bottom;
            lastTexX = 1.0F - lastTexX;
        } else if (cooldownRatio < 0.875F) {
            vertexNum = 3;
            lastTexX = 0.0F;
            lastTexY = (cooldownRatio - 0.625F) / 0.25F;
            lastVertexX = x;
            lastVertexY = bottom - iconSize * lastTexY;
            lastTexY = 1.0F - lastTexY;
        } else {
            vertexNum = 2;
            lastTexX = (cooldownRatio - 0.875F) / 0.25F;
            lastTexY = 0.0F;
            lastVertexX = x + iconSize * lastTexX;
            lastVertexY = y;
        }

        RenderSystem.enableBlend();
        RenderSystem.setShaderTexture(0, container.getSkill().getSkillTexture());
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        if (canUse) {
            if (container.getStack() > 0) {
                RenderSystem.setShaderColor(0.0F, 0.64F, 0.72F, 0.8F);
            } else {
                RenderSystem.setShaderColor(0.0F, 0.5F, 0.5F, 0.6F);
            }
        } else {
            RenderSystem.setShaderColor(0.5F, 0.5F, 0.5F, 0.6F);
        }

        Tesselator tessellator = Tesselator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuilder();
        bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_TEX);

        for(int j = 0; j < vertexNum; ++j) {
            bufferbuilder.vertex(guiGraphics.pose().last().pose(), x + iconSize * CLOCK_POS[j].x, y + iconSize * CLOCK_POS[j].y, 0.0F).uv(CLOCK_POS[j].x, CLOCK_POS[j].y).endVertex();
        }

        bufferbuilder.vertex(guiGraphics.pose().last().pose(), lastVertexX, lastVertexY, 0.0F).uv(lastTexX, lastTexY).endVertex();
        tessellator.end();
        if (canUse) {
            RenderSystem.setShaderColor(0.08F, 0.79F, 0.95F, 1.0F);
        } else {
            RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        }

        GL11.glCullFace(1028);
        bufferbuilder.begin(VertexFormat.Mode.TRIANGLE_FAN, DefaultVertexFormat.POSITION_TEX);

        for(int j = 0; j < 2; ++j) {
            bufferbuilder.vertex(guiGraphics.pose().last().pose(), x + iconSize * CLOCK_POS[j].x, y + iconSize * CLOCK_POS[j].y, 0.0F).uv(CLOCK_POS[j].x, CLOCK_POS[j].y).endVertex();
        }

        for(int j = CLOCK_POS.length - 1; j >= vertexNum; --j) {
            bufferbuilder.vertex(guiGraphics.pose().last().pose(), x + iconSize * CLOCK_POS[j].x, y + iconSize * CLOCK_POS[j].y, 0.0F).uv(CLOCK_POS[j].x, CLOCK_POS[j].y).endVertex();
        }

        bufferbuilder.vertex(guiGraphics.pose().last().pose(), lastVertexX, lastVertexY, 0.0F).uv(lastTexX, lastTexY).endVertex();
        tessellator.end();
        GL11.glCullFace(1029);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        if (!container.isActivated() || container.getSkill().getActivateType() != ActivateType.DURATION && container.getSkill().getActivateType() != ActivateType.DURATION_INFINITE) {
            if (!fullstack) {
                String s = String.valueOf((int)(cooldownRatio * 100.0F));
                int stringWidth = (gui.getFont().width(s) - 6) / 3;
                guiGraphics.drawString(gui.getFont(), s, x + 13.0F - (float)stringWidth, y + 13.0F, 16777215, true);
            }
        } else {
            String s = String.format("%.0f", (float)container.getRemainDuration() / 20.0F);
            int stringWidth = (gui.getFont().width(s) - 6) / 3;
            guiGraphics.drawString(gui.getFont(), s, x + 13.0F - (float)stringWidth, y + 13.0F, 16777215, true);
        }

        if (container.getSkill().getMaxStack() > 1) {
            String s = String.valueOf(container.getStack());
            int stringWidth = (gui.getFont().width(s) - 6) / 3;
            guiGraphics.drawString(gui.getFont(), s, x + 25.0F - (float)stringWidth, y + 22.0F, 16777215, true);
        }

        guiGraphics.pose().popPose();
    }

    @Override
    public String getSkillName() {
        return "";
    }

    @Override
    public String getSkillTooltip() {
        return "";
    }

    @Override
    public String getSkillTooltipExtra() {
        return "";
    }
}
