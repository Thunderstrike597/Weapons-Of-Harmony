package net.kenji.woh.api.manager;

import com.p1nero.invincible.capability.InvincibleCapabilities;
import com.p1nero.invincible.capability.InvinciblePlayer;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.basegameassets.ExtendedComboBasicAttack;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class BasicComboAttackHandler {

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {

        PlayerPatch<?> patch = EpicFightCapabilities.getPlayerPatch(event.player);
        if(patch == null) return;

        if(patch.getSkill(SkillSlots.WEAPON_INNATE) != null && patch.getSkill(SkillSlots.WEAPON_INNATE).getSkill() instanceof ExtendedComboBasicAttack comboBasicAttack){
           InvinciblePlayer invinciblePlayer = InvincibleCapabilities.getPlayerCap(patch.getOriginal());
           if(!invinciblePlayer.getCurrentLogicNode().hasNext()){
               invinciblePlayer.setCurrentLogicNode(comboBasicAttack.getRoot());
               comboBasicAttack.setWasChanged(true);
           }
        }
    }
}