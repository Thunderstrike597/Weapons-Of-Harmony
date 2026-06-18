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
import yesman.epicfight.skill.modules.ChargeableSkill;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HybridHoldableSkill extends HybridSkill implements ChargeableSkill {


    public HybridHoldableSkill(SkillBuilder<? extends Skill> builder, float stackChargeTime) {
        super(builder, stackChargeTime);
    }

    @Override
    public int getAllowedMaxChargingTicks() {
        return 0;
    }

    @Override
    public int getMaxChargingTicks() {
        return 0;
    }

    @Override
    public int getMinChargingTicks() {
        return 0;
    }

    @Override
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
    }


}
