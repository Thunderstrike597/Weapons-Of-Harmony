package net.kenji.woh.gameasset.skills;

import net.kenji.woh.api.interfaces.ITranslatableSkill;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.InteractionHand;
import yesman.epicfight.api.utils.math.ValueModifier;
import yesman.epicfight.gameasset.EpicFightSkills;
import yesman.epicfight.skill.Skill;
import yesman.epicfight.skill.SkillBuilder;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.passive.BerserkerSkill;
import yesman.epicfight.skill.passive.PassiveSkill;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.entity.eventlistener.PlayerEventListener;

import java.util.UUID;

public class KatajutsaPassive extends PassiveSkill implements ITranslatableSkill {
    private final float damageBonus;
    private final float speedBonus;
    UUID EVENT_UUID = UUID.randomUUID();

    public KatajutsaPassive(SkillBuilder builder) {
        super(builder);
        this.damageBonus = 6;
        this.speedBonus = 0.6F;
    }

    @Override
    public String getSkillName() {
        return "Katajutsu";
    }
    @Override
    public ResourceLocation getSkillTexture() {
        return EpicFightSkills.RELENTLESS_COMBO.getSkillTexture();
    }

    @Override
    public void onInitiate(SkillContainer container) {
        super.onInitiate(container);

        PlayerEventListener listener = container.getExecutor().getEventListener();

        listener.addEventListener(PlayerEventListener.EventType.MODIFY_DAMAGE_EVENT, EVENT_UUID, (event) -> {
            PlayerPatch<?> playerPatch = container.getExecutor();

            if(playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
                float attackDamage = event.getBaseDamage();
                event.attachValueModifier(ValueModifier.multiplier(attackDamage + damageBonus));
            }
        });

        listener.addEventListener(PlayerEventListener.EventType.MODIFY_ATTACK_SPEED_EVENT, EVENT_UUID, (event) -> {
            PlayerPatch<?> playerPatch = container.getExecutor();

            if(playerPatch.getHoldingItemCapability(InteractionHand.MAIN_HAND).getWeaponCategory() == CapabilityItem.WeaponCategories.FIST) {
                float attackSpeed = event.getAttackSpeed();
                event.setAttackSpeed(attackSpeed + speedBonus);
            }
        });
    }

    @Override
    public String getSkillTooltip() {
        return """
               This fighting form is a long lost technique used by ancient monks.
                Once learned, you are no longer defenceless when without your weapon, your vicious kicks and punches can do the work for you.
               """;
    }

    @Override
    public String getSkillTooltipExtra() {
        return "";
    }
}
