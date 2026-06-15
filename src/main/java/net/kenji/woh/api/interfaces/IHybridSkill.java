package net.kenji.woh.api.interfaces;

import net.minecraft.client.KeyMapping;
import net.minecraft.server.level.ServerPlayer;

public interface IHybridSkill {

    KeyMapping getKeyMapping();

    void setWasHoldingSkill(boolean value);
    boolean getWasHoldingSkill();
    void sendSkillActivateToClient(boolean value, ServerPlayer player);
    float getStackChargeTime();
}
