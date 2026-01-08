package net.kenji.woh.gameasset.skills;

import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class KatajutsaPassive extends PassiveSkill {
    private final float damageBonus;
    private final float speedBonus;
    UUID EVENT_UUID = UUID.randomUUID();

    public KatajutsaPassive(SkillBuilder builder) {
        super(builder);
        this.damageBonus = 6;
        this.speedBonus = 0.6F;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerEventListener listener = container.getExecutor().getEventListener();
        PlayerPatch<?> playerPatch = container.getExecutor();
        if(playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
            listener.addEventListener(PlayerEventListener.EventType.MODIFY_DAMAGE_EVENT, EVENT_UUID, (event) -> {
                float attackDamage = event.getBaseDamage();
                event.attachValueModifier(ValueModifier.multiplier(attackDamage + (damageBonus)));
            });
            listener.addEventListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID, (event) -> {
                float attackSpeed = event.getAttackSpeed();
                event.setAttackSpeed(attackSpeed + (speedBonus));
            });

        }
    }
}
