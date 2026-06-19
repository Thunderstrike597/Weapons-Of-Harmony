package net.kenji.woh.api.manager;

import com.p1nero.invincible.api.skill.ComboNode;
import com.p1nero.invincible.capability.InvincibleCapabilities;
import com.p1nero.invincible.capability.InvinciblePlayer;
import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.basegameassets.ExtendedComboBasicAttack;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE)
public class ComboBasicAttackCounterManager {
    private static final Map<UUID, ComboNode> comboNodeMap = new HashMap<>();

    private static final Map<UUID, Integer> comboCounterMap = new HashMap<>();

    public static int getInvincibleComboCounter(Player player) {
        return comboCounterMap.getOrDefault(player.getUUID(), 0);
    }

    @SubscribeEvent
    public static void onTick(TickEvent.PlayerTickEvent event) {
        Player player = event.player;

        InvinciblePlayer invinciblePlayer = InvincibleCapabilities.getPlayerCap(player);
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getPlayerPatch(player);

        if (invinciblePlayer == null || playerPatch == null) return;

        ComboNode currentNode = invinciblePlayer.getCurrentLogicNode();

        ComboNode prevNode = comboNodeMap.get(player.getUUID());

        if (playerPatch.getSkill(SkillSlots.WEAPON_INNATE) == null || !(playerPatch.getSkill(SkillSlots.WEAPON_INNATE).getSkill() instanceof ExtendedComboBasicAttack comboBasicAttack))
            return;


        AnimationPlayer animPlayer = playerPatch.getAnimator().getPlayerFor(null);
        if (animPlayer == null) return;

        if(animPlayer.getAnimation().get().getRealAnimation().get() instanceof AttackAnimation) {
            if (prevNode != currentNode) {
                if (currentNode != comboBasicAttack.getRoot()) {
                    int nextCounter = comboCounterMap.getOrDefault(player.getUUID(), 0) + 1;
                    comboCounterMap.put(player.getUUID(), nextCounter);
                    if(prevNode == comboBasicAttack.getRoot()){
                        comboCounterMap.put(player.getUUID(), 1);
                    }
                }
                comboNodeMap.put(player.getUUID(), currentNode);
            }
        }
        else{
            comboCounterMap.put(player.getUUID(), 0 );
        }
    }
}
