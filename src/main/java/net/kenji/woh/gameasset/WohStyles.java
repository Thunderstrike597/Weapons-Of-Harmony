package net.kenji.woh.gameasset;

import yesman.epicfight.world.capabilities.item.Style;

public enum WohStyles implements Style {
    THROWN_ONE_HAND(true),
    THROWN_TWO_HAND(false);

    final boolean canUseOffhand;
    final int id;

    private WohStyles(boolean canUseOffhand) {
        this.id = Style.ENUM_MANAGER.assign(this);
        this.canUseOffhand = canUseOffhand;
    }

    public int universalOrdinal() {
        return this.id;
    }

    public boolean canUseOffhand() {
        return this.canUseOffhand;
    }
}
