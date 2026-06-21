package net.kenji.woh.api;

import net.kenji.woh.gameasset.AttackHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.animation.property.AnimationEvent;
import yesman.epicfight.api.animation.types.AttackAnimation;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.api.utils.TimePairList;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class AnimationConfig {
    public final String path;
    public final int phaseCount;
    public final float attackSpeed;
    public final float convertTime;
    public final float[] start, antic, contact, recovery, end;
    public final Supplier<SoundEvent>[] swingSound;
    public final Supplier<SoundEvent>[] hitSound;
    public final RegistryObject<HitParticleType>[] hitParticle;
    public final AttackAnimation.JointColliderPair[][] colliders;
    public final WOHAnimationUtils.AttackAnimationType attackType;
    public final StunType stunType;
    public final float eventFirstTime;
    public final float eventSecondTime;
    public final boolean ignoreFallDamage;
    public final float movementMultiplier;
    public final float slashAngle;
    public final float[] attackDamage;
    public final float[] impact;
    public final TimePairList airTime;
    public final boolean useComboCounterReset;
    public final AnimationEvent.E0 startEvent;
    public final AnimationEvent.E0 endEvent;

    private AnimationConfig(Builder b) {
        this.path = b.path;
        this.phaseCount = b.phaseCount;
        this.attackSpeed = b.speed;
        this.convertTime = b.convertTime;
        this.start = b.start;
        this.antic = b.antic;
        this.contact = b.contact;
        this.recovery = b.recovery;
        this.end = b.end;
        this.swingSound = b.swingSound;
        this.hitSound = b.hitSound;
        this.hitParticle = b.hitParticle;
        this.colliders = b.colliders;
        this.attackType = b.attackType;
        this.stunType = b.stunType;
        this.eventFirstTime = b.unsheatheTime;
        this.eventSecondTime = b.sheathTime;
        this.ignoreFallDamage = b.ignoreFallDamage;
        this.movementMultiplier = b.movementMultiplier;
        this.slashAngle = b.slashAngle;
        this.attackDamage = b.attackDamage;
        this.impact = b.impact;
        this.airTime = b.airTime;
        this.useComboCounterReset = b.useComboCounterReset;
        this.startEvent = b.startEvent;
        this.endEvent = b.endEvent;
    }

    public static Builder of(String path) {
        return new Builder(path);
    }

    public static class Builder {
        private final String path;
        private int phaseCount = 1;
        private float speed = 0.1F;
        public float[] attackDamage;
        public float[] impact;
        private float convertTime = 0.1F;
        public float movementMultiplier = 1;
        public TimePairList airTime;
        public AnimationEvent.E0 startEvent;
        public AnimationEvent.E0 endEvent;
        public WOHAnimationUtils.AttackAnimationType attackType = WOHAnimationUtils.AttackAnimationType.BASIC_ATTACK;

        // Phase timing rows — transposed to parallel arrays at build()
        private final List<float[]> phaseRows = new ArrayList<>();
        private float[] start, antic, contact, recovery, end;

        // Per-phase lists — index = phase index
        private final List<Supplier<SoundEvent>> swingSoundList = new ArrayList<>();
        private final List<Supplier<SoundEvent>> hitSoundList = new ArrayList<>();
        private final List<RegistryObject<HitParticleType>> hitParticleList = new ArrayList<>();
        private final List<AttackAnimation.JointColliderPair[]> colliderList = new ArrayList<>();
        // Finalized arrays (set at build time)
        private Supplier<SoundEvent>[] swingSound;
        private Supplier<SoundEvent>[] hitSound;
        private RegistryObject<HitParticleType>[] hitParticle;
        private AttackAnimation.JointColliderPair[][] colliders;
        private List<Float> attackDamageList = new ArrayList<>();
        private List<Float> impactList = new ArrayList<>();

        private StunType stunType = StunType.SHORT;

        private float unsheatheTime = -1F;
        private float sheathTime = -1F;
        private boolean ignoreFallDamage = false;
        private float slashAngle = -1F;
        public boolean useComboCounterReset = false;

        private Builder(String path) { this.path = path; }

        // ── Phase timing ──────────────────────────────────────────────────────

        public Builder phases(float start, float antic, float contact, float recovery, float end) {
            this.phaseRows.add(new float[]{start, antic, contact, recovery, end});
            return this;
        }

        // Raw parallel-array overload for edge cases
        public Builder phases(float[] start, float[] antic, float[] contact, float[] recovery, float[] end) {
            this.start = start; this.antic = antic; this.contact = contact;
            this.recovery = recovery; this.end = end;
            return this;
        }

        // ── Swing sound (shared across all phases, no index needed) ───────────

        @SuppressWarnings("unchecked")
        public Builder swing(Supplier<SoundEvent> s) {
            this.swingSound = new Supplier[]{s};
            return this;
        }
        public Builder movementMultiplier(float value){
            this.movementMultiplier = value;
            return this;
        }


        @SuppressWarnings("unchecked")
        public Builder swing(Supplier<SoundEvent> s, int phaseIndex) {
            ensureSize(swingSoundList, phaseIndex + 1);
            swingSoundList.set(phaseIndex, s);
            return this;
        }


        // ── Hit sound — indexed per phase ─────────────────────────────────────

        // Single-phase shorthand (no index = phase 0 only)
        @SuppressWarnings("unchecked")
        public Builder hit(Supplier<SoundEvent> s) {
            this.hitSound = new Supplier[]{s};
            return this;
        }

        // Per-phase: grows the list to fit the index, fills gaps with null
        public Builder hit(Supplier<SoundEvent> s, int phaseIndex) {
            ensureSize(hitSoundList, phaseIndex + 1);
            hitSoundList.set(phaseIndex, s);
            return this;
        }

        // ── Particle — indexed per phase ──────────────────────────────────────

        @SuppressWarnings("unchecked")
        public Builder particle(RegistryObject<HitParticleType> p) {
            this.hitParticle = new RegistryObject[]{p};
            return this;
        }

        public Builder particle(RegistryObject<HitParticleType> p, int phaseIndex) {
            ensureSize(hitParticleList, phaseIndex + 1);
            hitParticleList.set(phaseIndex, p);
            return this;
        }
        public Builder damage(float d) {
            this.attackDamage = new float[]{d};
            return this;
        }
        public Builder damage(float d, int phaseIndex) {
            ensureSize(attackDamageList, phaseIndex + 1);
            attackDamageList.set(phaseIndex, d);
            return this;
        }
        public Builder impact(float i) {
            this.impact = new float[]{i};
            return this;
        }
        public Builder impact(float i, int phaseIndex) {
            ensureSize(impactList, phaseIndex + 1);
            impactList.set(phaseIndex, i);
            return this;
        }
        // ── Collider — indexed per phase ──────────────────────────────────────

        // Single collider shorthand (no index = phase 0 only)
        public Builder collider(Collider c, Joint j) {
            AttackAnimation.JointColliderPair[] pair = new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(j,c)};

            this.colliders = new AttackAnimation.JointColliderPair[][]{pair};

            return this;
        }
        public Builder collider(AttackAnimation.JointColliderPair... jp) {
            AttackAnimation.JointColliderPair[] pair = new AttackAnimation.JointColliderPair[jp.length];
            for (int i = 0; i < jp.length; i++) {
                pair[i] = AttackAnimation.JointColliderPair.of(jp[i].getFirst(), jp[i].getSecond());
            }
            this.colliders = new AttackAnimation.JointColliderPair[][]{pair};

            return this;
        }
        // Per-phase
        public Builder collider(Collider c, Joint j, int phaseIndex) {
            ensureSize(colliderList, phaseIndex + 1);
            AttackAnimation.JointColliderPair[] pair = new AttackAnimation.JointColliderPair[]{AttackAnimation.JointColliderPair.of(j,c)};
            colliderList.set(phaseIndex, pair);
            return this;
        }

        // ── Other options ─────────────────────────────────────────────────────

        public Builder speed(float speed) { this.speed = speed; return this; }
        public Builder startEvent(AnimationEvent.E0 event) {
            this.startEvent = event;
            return this;
        }
        public Builder endEvent(AnimationEvent.E0 event) {
            this.endEvent = event;
            return this;
        }
        public Builder convert(float convertTime) { this.convertTime = convertTime; return this; }
        public Builder phaseCount(int n) { this.phaseCount = n; return this; }
        public Builder stun(StunType stun) { this.stunType = stun; return this; }
        public Builder eventStartEnd(float start, float end) { this.unsheatheTime = start; this.sheathTime = end; return this; }
        public Builder ignoreFallDamage(boolean ignoreFallDamage)
        {
            this.ignoreFallDamage = ignoreFallDamage;
            return this;
        }
        public Builder comboCounterAttackReset(boolean useComboCounterReset){
            this.useComboCounterReset = useComboCounterReset;
            return this;
        }

        public Builder airTime(float airTimeStart, float airTimeEnd) {
            this.airTime = TimePairList.create(airTimeStart, airTimeEnd);
            return this;
        }

        public Builder slashAngle(float slashAngle)
        {
            this.slashAngle = slashAngle;
            return this;
        }
        public Builder attackType(WOHAnimationUtils.AttackAnimationType type){
            this.attackType = type;
            return this;
        }

        // ── Build ─────────────────────────────────────────────────────────────

        @SuppressWarnings("unchecked")
        public AnimationConfig build() {
            // Transpose phaseRows → parallel float arrays
            if (!phaseRows.isEmpty()) {
                int n = phaseRows.size();
                start = new float[n]; antic = new float[n]; contact = new float[n];
                recovery = new float[n]; end = new float[n];
                for (int i = 0; i < n; i++) {
                    float[] row = phaseRows.get(i);
                    start[i] = row[0]; antic[i] = row[1]; contact[i] = row[2];
                    recovery[i] = row[3]; end[i] = row[4];
                }
            }

            // Auto-derive phaseCount
            if (phaseCount == 1 && start != null && start.length > 1) {
                phaseCount = start.length;
            }



            if (!swingSoundList.isEmpty()) {
                mergeIntoList(swingSoundList, swingSound != null ? swingSound[0] : null);
                fillNulls(swingSoundList, EpicFightSounds.WHOOSH);
                swingSound = swingSoundList.toArray(new Supplier[0]);
            } else if (swingSound == null) {
                swingSound = new Supplier[]{EpicFightSounds.WHOOSH};
            }

// hitSound
            if (!hitSoundList.isEmpty()) {
                mergeIntoList(hitSoundList, hitSound != null ? hitSound[0] : null);
                fillNulls(hitSoundList, EpicFightSounds.BLUNT_HIT);
                hitSound = hitSoundList.toArray(new Supplier[0]);
            } else if (hitSound == null) {
                hitSound = new Supplier[]{EpicFightSounds.BLUNT_HIT};
            }

// hitParticle
            if (!hitParticleList.isEmpty()) {
                mergeIntoList(hitParticleList, hitParticle != null ? hitParticle[0] : null);
                fillNulls(hitParticleList, EpicFightParticles.HIT_BLUNT);
                hitParticle = hitParticleList.toArray(new RegistryObject[0]);
            } else if (hitParticle == null) {
                hitParticle = new RegistryObject[]{EpicFightParticles.HIT_BLUNT};
            }
            if (!attackDamageList.isEmpty()) {
                mergeIntoList(attackDamageList, attackDamage != null ? attackDamage[0] : null);
                fillNulls(attackDamageList, 0F);
                attackDamage =  toFloatArray(attackDamageList);
            } else if (attackDamage == null) {
                attackDamage = new float[0];
            }
            if (!impactList.isEmpty()) {
                mergeIntoList(impactList, impact != null ? impact[0] : null);
                fillNulls(impactList, 0F);
                impact =  toFloatArray(impactList);
            } else if (impact == null) {
                impact = new float[0];
            }
            if (!colliderList.isEmpty()) {
                // Indexed calls were made — merge shorthand into slot 0 if empty, then finalize
                mergeIntoList(colliderList, colliders != null ? colliders[0] : null);
                fillNulls(colliderList, null);
                colliders = colliderList.toArray(new AttackAnimation.JointColliderPair[0][]);
            } else if (colliders == null) {
                // Neither shorthand nor indexed — empty arrays
                colliders = new AttackAnimation.JointColliderPair[][]{};
            }

            return new AnimationConfig(this);
        }

        // ── Util ──────────────────────────────────────────────────────────────
        private static <T> void mergeIntoList(List<T> list, T shorthandValue) {
            if (shorthandValue == null) return; // nothing to inject
            if (list.isEmpty()) {
                // No indexed calls were made — don't add anything, let the null-check handle default
                return;
            }
            // Indexed calls exist — inject shorthand at slot 0 if it's unset
            if (list.get(0) == null) {
                list.set(0, shorthandValue);
            }
        }

        private static <T> void fillNulls(List<T> list, T fallback) {
            // Forward pass: fill nulls with the last seen non-null
            T last = fallback;
            for (int i = 0; i < list.size(); i++) {
                if (list.get(i) != null) {
                    last = list.get(i);
                } else {
                    list.set(i, last);
                }
            }
        }
        private static <T> void ensureSize(List<T> list, int size) {
            while (list.size() < size) list.add(null);
        }
        private static float[] toFloatArray(List<Float> list) {
            float[] arr = new float[list.size()];
            for (int i = 0; i < arr.length; i++) {
                arr[i] = list.get(i); // auto-unboxes Float -> float
            }
            return arr;
        }
        private static AttackHand deriveHand(Joint joint) {
            if (joint == null) return AttackHand.RIGHT_HAND;
            if (joint == Armatures.BIPED.get().toolL || joint == Armatures.BIPED.get().handL) return AttackHand.LEFT_HAND;
            if (joint == Armatures.BIPED.get().legL || joint == Armatures.BIPED.get().thighL) return AttackHand.LEFT_LEG;
            if (joint == Armatures.BIPED.get().legR || joint == Armatures.BIPED.get().thighR) return AttackHand.RIGHT_LEG;
            return AttackHand.RIGHT_HAND; // toolR, handR, etc. all default right
        }
    }
}