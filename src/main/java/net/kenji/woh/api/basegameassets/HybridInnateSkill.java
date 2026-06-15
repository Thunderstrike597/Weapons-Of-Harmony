package net.kenji.woh.api.basegameassets;

import net.kenji.woh.api.interfaces.IHybridSkill;
import net.kenji.woh.api.interfaces.ITranslatableSkill;
import net.minecraft.client.KeyMapping;
import net.minecraft.server.level.ServerPlayer;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.weaponinnate.WeaponInnateSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HybridInnateSkill extends WeaponInnateSkill implements ITranslatableSkill, IHybridSkill {

    public final Map<UUID, Boolean> didActivate = new HashMap<>();
    public boolean wasHoldingSkill = false;
    public final float stackChargeTime;

    public HybridInnateSkill(SkillBuilder<? extends WeaponInnateSkill> builder, float stackChargeTime) {
        super(builder);
        this.stackChargeTime = stackChargeTime;
    }
    @Override
    public float getCooldownRegenPerSecond(PlayerPatch<?> playerpatch) {
        return 1.0F / getStackChargeTime(); // 1 stack every 10 seconds → full 3 stacks in 30s
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


    @Override
    public KeyMapping getKeyMapping() {
        return EpicFightKeyMappings.WEAPON_INNATE_SKILL;
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
    public void sendSkillActivateToClient(boolean value, ServerPlayer serverPlayer) {

    }

    @Override
    public float getStackChargeTime() {
        return stackChargeTime;
    }
}
