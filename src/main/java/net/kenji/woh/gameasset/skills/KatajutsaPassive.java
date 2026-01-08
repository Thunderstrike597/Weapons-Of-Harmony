package net.kenji.woh.gameasset.skills;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.skill.Skill;
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

    public KatajutsaPassive(Builder<? extends Skill> builder) {
        super(builder);
        this.damageBonus = 6;
        this.speedBonus = 0.6F;
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);
        PlayerEventListener listener = container.getExecuter().getEventListener();
        PlayerPatch<?> playerPatch = container.getExecuter();
        if(playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
            listener.addEventListener(PlayerEventListener.EventType.MODIFY_DAMAGE_EVENT, EVENT_UUID, (event) -> {
                float attackDamage = event.getDamage();
                event.setDamage(attackDamage + (damageBonus));
            });
            listener.addEventListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID, (event) -> {
                float attackSpeed = event.getAttackSpeed();
                event.setAttackSpeed(attackSpeed + (speedBonus));
            });

        }
    }
}
