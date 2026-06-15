package net.kenji.woh.api.manager;

import net.kenji.woh.WeaponsOfHarmony;
import net.kenji.woh.api.DualSkillWeaponCapability;
import net.kenji.woh.api.basegameassets.HybridHoldableSkill;
import net.kenji.woh.api.interfaces.IHybridSkill;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import org.jline.utils.Log;
import yesman.epicfight.client.ClientEngine;
import yesman.epicfight.client.events.engine.ControlEngine;
import yesman.epicfight.client.input.EpicFightKeyMappings;
import yesman.epicfight.client.world.capabilites.entitypatch.player.LocalPlayerPatch;
import yesman.epicfight.network.EpicFightNetworkManager;
import yesman.epicfight.network.client.CPSkillRequest;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.skill.SkillSlots;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;

@Mod.EventBusSubscriber(modid = WeaponsOfHarmony.MODID, bus = Mod.EventBusSubscriber.Bus.FORGE, value = Dist.CLIENT)
public class DualSkillInputHandler {

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {

        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.screen != null) return;


        // Only intercept the weapon innate key
        if (event.getKey() != EpicFightKeyMappings.WEAPON_INNATE_SKILL.getKey().getValue()) return;


        LocalPlayerPatch playerPatch = EpicFightCapabilities.getEntityPatch(mc.player, LocalPlayerPatch.class);
        if (playerPatch == null || playerPatch.getPlayerMode() != PlayerPatch.PlayerMode.EPICFIGHT) return;

        // Check if weapon is DualSkillWeaponCapability
        CapabilityItem cap = EpicFightCapabilities.getItemStackCapability(mc.player.getMainHandItem());
        if (!(cap instanceof DualSkillWeaponCapability dualCap)) return;


        SkillContainer container = playerPatch.getSkill(dualCap.getSecondarySkill(playerPatch.getOriginal().getMainHandItem()));


        if (container == null) return;

        if (!(container.getSkill() instanceof IHybridSkill skill)) return;


        // Only redirect if charge skill is not yet activated
        // Once activated, combos take over the key normally
        if (container.isActivated()) return;


        // Redirect: send HOLD_START to WEAPON_PASSIVE instead of WEAPON_INNATE
        CPSkillRequest holdPacket = new CPSkillRequest(dualCap.getSecondarySkillSlot(), CPSkillRequest.WorkType.HOLD_START);
        EpicFightNetworkManager.sendToServer(holdPacket);
        KeyMapping keyMapping = skill.getKeyMapping();


        // Tell ControlEngine to track the hold on WEAPON_PASSIVE
        // so its ChargeableSkill release/tick logic works correctly

        ControlEngine controlEngine = ClientEngine.getInstance().controlEngine;
        controlEngine.setHoldingKey(dualCap.getSecondarySkillSlot(), keyMapping);
        Log.info("Logging Hybrid Skill!");
    }
}