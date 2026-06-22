package net.kenji.woh.api;

import net.kenji.woh.api.animation_types.WohAttackAnimation;
import yesman.epicfight.api.animation.AnimationManager;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.animation.types.BasicAttackAnimation;
import yesman.epicfight.api.animation.types.StaticAnimation;

import java.util.function.Supplier;

public class WohAnimationBuilder {


    public static AnimationManager.AnimationAccessor<AttackAnimation> createAttackAnimation(
            AnimationManager.AnimationBuilder builder,
            AnimationConfig config
    ) {
        AnimationManager.AnimationAccessor<AttackAnimation> animation;

        animation = builder.nextAccessor(config.path, accessor -> new WohAttackAnimation(accessor, config));;


        AnimationManager.AnimationAccessor<? extends AttackAnimation> finalAnimation = animation;

        Supplier<StaticAnimation> setupSupplier = () -> {
            AttackAnimation anim = finalAnimation.get();

            if (config.startEvent != null && config.endEvent != null) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(config.eventFirstTime, config.startEvent, AnimationEvent.Side.BOTH),
                        AnimationEvent.InTimeEvent.create(config.eventSecondTime, config.endEvent, AnimationEvent.Side.BOTH)
                });
            }else if (config.endEvent != null) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(config.eventSecondTime, config.endEvent, AnimationEvent.Side.BOTH)
                });
            }else if (config.startEvent != null) {
                anim.addEvents(new AnimationEvent[]{
                        AnimationEvent.InTimeEvent.create(config.eventFirstTime, config.startEvent, AnimationEvent.Side.BOTH)
                });
            }

            return anim;
        };

        WOHAnimationUtils.DEFERRED_SETUP.add(setupSupplier);
        return animation;
    }

}
