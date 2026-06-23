package net.kenji.woh.api.basegameassets.condition;

import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import yesman.epicfight.data.conditions.Condition;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.List;

public class OffHandItemCondition implements Condition<PlayerPatch<?>> {
    public Condition<PlayerPatch<?>> read(CompoundTag compoundTag) {
        return this;
    }

    public CompoundTag serializePredicate() {
        return new CompoundTag();
    }

    public final Class<? extends Item> item;
    public final boolean inverted;

    public OffHandItemCondition(Class<? extends Item> item, boolean inverted){
        this.item = item;
        this.inverted = inverted;
    }
    public OffHandItemCondition(Class<? extends Item> item){
        this.item = item;
        this.inverted = false;
    }


    public boolean predicate(PlayerPatch<?> playerPatch) {
        Player player = (Player) playerPatch.getOriginal();

        return inverted != item.isInstance(player.getOffhandItem().getItem());
    }

    public List<ParameterEditor> getAcceptingParameters(Screen screen) {
        return null;
    }
}
