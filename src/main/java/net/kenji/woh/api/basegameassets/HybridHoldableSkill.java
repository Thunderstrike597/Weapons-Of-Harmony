package net.kenji.woh.api.basegameassets;

import net.kenji.woh.api.interfaces.ITranslatableSkill;
import net.minecraft.client.KeyMapping;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.modules.ChargeableSkill;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class HybridHoldableSkill extends Skill implements ChargeableSkill, ITranslatableSkill {

    public final Map<UUID, Boolean> didActivate = new HashMap<>();
    public boolean wasHoldingSkill = false;

    public HybridHoldableSkill(SkillBuilder<? extends Skill> builder) {
        super(builder);
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
        return null;
    }
}
