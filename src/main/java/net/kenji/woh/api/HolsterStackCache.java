package net.kenji.woh.api;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import org.jline.utils.Log;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;

public class HolsterStackCache {
        private static final Map<Item, ItemStack> cache = new HashMap<>();

        public static ItemStack get(@Nullable Item item) {
            if (item == null) return ItemStack.EMPTY;
            return cache.computeIfAbsent(item, Item::getDefaultInstance);
        }
    }