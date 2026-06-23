package net.kenji.woh.api.basegameassets.condition;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.EpicFightCapabilities;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;
import yesman.epicfight.world.capabilities.item.CapabilityItem;
import yesman.epicfight.world.capabilities.item.Style;

import java.util.Arrays;
import java.util.List;

public class StyleCondition implements Condition<PlayerPatch<?>> {
    public Condition<PlayerPatch<?>> read(CompoundTag compoundTag) {
        return this;
    }

    public CompoundTag serializePredicate() {
        return new CompoundTag();
    }

    public final Style[] styles;
    public final boolean inverted;

    public StyleCondition(Style... styles){
        this.styles = styles;
        this.inverted = false;
    }
    public StyleCondition(Style style[], boolean inverted){
        this.styles = style;
        this.inverted = inverted;
    }



    public boolean predicate(PlayerPatch<?> playerPatch) {
        Player player = (Player) playerPatch.getOriginal();
        CapabilityItem capabilityItem = EpicFightCapabilities.getItemStackCapability(player.getMainHandItem());
        if(!inverted) {
            for (Style style : styles) {
                if (style == capabilityItem.getStyle(playerPatch))
                    return true;
            }
            return false;
        }
        else{
            return !Arrays.stream(styles).toList().contains(capabilityItem.getStyle(playerPatch));
        }
    }

    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
