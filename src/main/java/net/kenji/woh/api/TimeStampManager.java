package net.kenji.woh.api;

import net.minecraft.util.Mth;
import yesman.epicfight.api.animation.AnimationPlayer;
import yesman.epicfight.api.animation.types.StaticAnimation;
import yesman.epicfight.api.client.animation.Layer;
import yesman.epicfight.world.capabilities.entitypatch.player.PlayerPatch;

import java.util.HashMap;
import java.util.Map;

public class TimeStampManager {

    public static class TimeStampData {
        public StaticAnimation animation;

        public float startTimeStamp;
        public float endTimeStamp;

        public boolean hasSheath;
        public float sheathTimestamp;
    }

    private static final Map<StaticAnimation, TimeStampData> REGISTRY = new HashMap<>();

    public static void register(StaticAnimation animation,
                                float start,
                                float end) {

        TimeStampData data = new TimeStampData();
        data.animation = animation;
        data.startTimeStamp = start;
        data.endTimeStamp = end;


        REGISTRY.put(animation, data);
    }
    public static void register(StaticAnimation animation,
                                float normStart,
                                float normEnd,
                                boolean hasSheath,
                                float sheathTimeStamp) {

        TimeStampData data = new TimeStampData();
        data.animation = animation;
        data.startTimeStamp = normStart;
        data.endTimeStamp = normEnd;
        data.hasSheath = hasSheath;
        data.sheathTimestamp = sheathTimeStamp;

        REGISTRY.put(animation, data);
    }

    public static TimeStampData get(StaticAnimation animation) {
        return REGISTRY.get(animation);
    }

    public static float getNormalizedTime(PlayerPatch<?> playerPatch, Layer.Priority priority) {
        AnimationPlayer animPlayer = playerPatch.getClientAnimator()
                .getCompositeLayer(priority)
                .animationPlayer;

        float total = animPlayer.getAnimation().get().getTotalTime();
        float elapsed = animPlayer.getElapsedTime();
        return Mth.clamp(elapsed / total, 0f, 1f);
    }

    public static boolean isInBetween(PlayerPatch<?> playerPatch, StaticAnimation animation) {
        TimeStampData data = REGISTRY.get(animation);
        if (data == null) return false;

        float norm = getNormalizedTime(playerPatch, animation.getPriority());
        return norm >= data.startTimeStamp && norm <= data.endTimeStamp;
    }
    public static boolean isHigherThanStart(PlayerPatch<?> playerPatch, StaticAnimation animation) {
        TimeStampData data = REGISTRY.get(animation);
        if (data == null) return false;

        float norm = getNormalizedTime(playerPatch, animation.getPriority());
        return norm > data.startTimeStamp;
    }
    public static boolean isLowerThanEnd(PlayerPatch<?> playerPatch, StaticAnimation animation) {
        TimeStampData data = REGISTRY.get(animation);
        if (data == null) return false;

        float norm = getNormalizedTime(playerPatch, animation.getPriority());
        return norm < data.endTimeStamp;
    }
}
