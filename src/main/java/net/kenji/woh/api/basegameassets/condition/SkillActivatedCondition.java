package net.kenji.woh.api.basegameassets.condition;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

public class SkillActivatedCondition implements Condition<PlayerPatch<?>> {
    public Condition<PlayerPatch<?>> read(CompoundTag compoundTag) {
        return this;
    }

    public CompoundTag serializePredicate() {
        return new CompoundTag();
    }

    public Skill skill;
    public boolean inverted;

    public SkillActivatedCondition(Skill skill, boolean inverted){
        this.skill = skill;
        this.inverted = inverted;
    }
    public boolean predicate(PlayerPatch<?> playerPatch) {
        Player player = (Player)playerPatch.getOriginal();
        SkillContainer container = playerPatch.getSkill(skill);
        boolean condition = container != null && container.isActivated();
        return inverted != condition;
    }

    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
