package net.kenji.woh.mixins;

import net.kenji.woh.gameasset.WohSkills;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ShieldItem;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import yesman.epicfight.skill.SkillContainer;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

@Mixin(ShieldItem.class)
public class ShieldMixin {

    @Inject(method = "use", at = @At("HEAD"), cancellable = true, remap = false)
    public void onUse(Level pLevel, Player pPlayer, InteractionHand pHand, CallbackInfoReturnable<InteractionResultHolder<ItemStack>> cir){
        PlayerPatch<?> playerPatch = EpicFightCapabilities.getEntityPatch(pPlayer, PlayerPatch.class);
        if (playerPatch == null) return;
        SkillContainer container = playerPatch.getSkill(WohSkills.ARBITERS_SLASH);
        if(container == null) return;

        if(container.isActivated()) {
            ItemStack itemstack = pPlayer.getItemInHand(pHand);
            cir.cancel();
            cir.setReturnValue(InteractionResultHolder.pass(itemstack));
        }
    }
}
