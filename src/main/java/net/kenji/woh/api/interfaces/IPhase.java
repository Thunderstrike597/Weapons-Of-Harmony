package net.kenji.woh.api.interfaces;

public interface IPhase {
    void woh$setRecoveryOverride(float newRecovery);
    void woh$restoreRecovery();
    boolean woh$isRecoveryOverridden();
}