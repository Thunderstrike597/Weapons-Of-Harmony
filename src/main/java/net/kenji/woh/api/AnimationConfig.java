package net.kenji.woh.api;

import net.kenji.woh.gameasset.AttackHand;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.registries.RegistryObject;
import yesman.epicfight.api.animation.Joint;
import yesman.epicfight.api.collider.Collider;
import yesman.epicfight.client.particle.HitParticle;
import yesman.epicfight.gameasset.Armatures;
import yesman.epicfight.gameasset.EpicFightSounds;
import yesman.epicfight.particle.EpicFightParticles;
import yesman.epicfight.particle.HitParticleType;
import yesman.epicfight.world.damagesource.StunType;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

public class AnimationConfig {
    public final String path;
    public final int phaseCount;
    public final float speed;
    public final float convertTime;
    public final float[] start, antic, contact, recovery, end;
    public final Supplier<SoundEvent>[] swingSound;
    public final Supplier<SoundEvent>[] hitSound;
    public final RegistryObject<HitParticleType>[] hitParticle;
    public final Collider[] colliders;
    public final Joint[] colliderJoints;
    public final StunType stunType;
    public final AttackHand[] attackingHands;
    public final float eventFirstTime;
    public final float eventSecondTime;
    public final boolean ignoreFallDamage;
    public final float movementMultiplier;
    public final float slashAngle;


    private AnimationConfig(Builder b) {
        this.path = b.path;
        this.phaseCount = b.phaseCount;
        this.speed = b.speed;
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
        this.colliderJoints = b.colliderJoints;
        this.stunType = b.stunType;
        this.attackingHands = b.attackingHands;
        this.eventFirstTime = b.unsheatheTime;
        this.eventSecondTime = b.sheathTime;
        this.ignoreFallDamage = b.ignoreFallDamage;
        this.movementMultiplier = b.movementMultiplier;
        this.slashAngle = b.slashAngle;
    }

    public static Builder of(String path) {
        return new Builder(path);
    }

    public static class Builder {
        private final String path;
        private int phaseCount = 1;
        private float speed = 0.1F;
        private float convertTime = 0.1F;
        public float movementMultiplier = 1;
        // Phase timing rows — transposed to parallel arrays at build()
        private final List<float[]> phaseRows = new ArrayList<>();
        private float[] start, antic, contact, recovery, end;

        // Per-phase lists — index = phase index
        private final List<Supplier<SoundEvent>> swingSoundList = new ArrayList<>();
        private final List<Supplier<SoundEvent>> hitSoundList = new ArrayList<>();
        private final List<RegistryObject<HitParticleType>> hitParticleList = new ArrayList<>();
        private final List<Collider> colliderList = new ArrayList<>();
        private final List<Joint> colliderJointList = new ArrayList<>();
        private List<AttackHand> attackingHandsList = new ArrayList<>();
        // Finalized arrays (set at build time)
        private Supplier<SoundEvent>[] swingSound;
        private Supplier<SoundEvent>[] hitSound;
        private RegistryObject<HitParticleType>[] hitParticle;
        private Collider[] colliders;
        private Joint[] colliderJoints;

        private StunType stunType = StunType.SHORT;
        private AttackHand[] attackingHands;

        private float unsheatheTime = -1F;
        private float sheathTime = -1F;
        private boolean ignoreFallDamage = false;
        private float slashAngle = -1F;
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

        // ── Collider — indexed per phase ──────────────────────────────────────

        // Single collider shorthand (no index = phase 0 only)
        public Builder collider(Collider c, Joint j) {
            this.colliders = new Collider[]{c};
            this.colliderJoints = new Joint[]{j};
            return this;
        }

        // Per-phase
        public Builder collider(Collider c, Joint j, int phaseIndex) {
            ensureSize(colliderList, phaseIndex + 1);
            ensureSize(colliderJointList, phaseIndex + 1);
            colliderList.set(phaseIndex, c);
            colliderJointList.set(phaseIndex, j);
            return this;
        }
        public Builder attackHand(AttackHand hand, int phaseIndex) {
            ensureSize(attackingHandsList, phaseIndex + 1);
            attackingHandsList.set(phaseIndex, hand);
            return this;
        }
        // ── Other options ─────────────────────────────────────────────────────

        public Builder speed(float speed) { this.speed = speed; return this; }
        public Builder convert(float convertTime) { this.convertTime = convertTime; return this; }
        public Builder phaseCount(int n) { this.phaseCount = n; return this; }
        public Builder stun(StunType stun) { this.stunType = stun; return this; }
        public Builder sheathe(float unsheathe, float sheathe) { this.unsheatheTime = unsheathe; this.sheathTime = sheathe; return this; }
        public Builder ignoreFallDamage(boolean ignoreFallDamage)
        {
            this.ignoreFallDamage= ignoreFallDamage;
            return this;
        }
        public Builder slashAngle(float slashAngle)
        {
            this.slashAngle = slashAngle;
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

            if (!colliderList.isEmpty()) {
                // Indexed calls were made — merge shorthand into slot 0 if empty, then finalize
                mergeIntoList(colliderList, colliders != null ? colliders[0] : null);
                mergeIntoList(colliderJointList, colliderJoints != null ? colliderJoints[0] : null);
                fillNulls(colliderList, null);
                fillNulls(colliderJointList, null);
                colliders = colliderList.toArray(new Collider[0]);
                colliderJoints = colliderJointList.toArray(new Joint[0]);
            } else if (colliders == null) {
                // Neither shorthand nor indexed — empty arrays
                colliders = new Collider[]{};
                colliderJoints = new Joint[]{};
            }

            if (!attackingHandsList.isEmpty()) {
                attackingHands = attackingHandsList.toArray(new AttackHand[0]);
            } else if (colliderJoints.length > 0) {
                attackingHands = new AttackHand[colliderJoints.length];
                for (int i = 0; i < colliderJoints.length; i++) {
                    attackingHands[i] = deriveHand(colliderJoints[i]);
                }
            } else {
                attackingHands = new AttackHand[0];
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
        private static AttackHand deriveHand(Joint joint) {
            if (joint == null) return AttackHand.RIGHT_HAND;
            if (joint == Armatures.BIPED.get().toolL || joint == Armatures.BIPED.get().handL) return AttackHand.LEFT_HAND;
            if (joint == Armatures.BIPED.get().legL || joint == Armatures.BIPED.get().thighL) return AttackHand.LEFT_LEG;
            if (joint == Armatures.BIPED.get().legR || joint == Armatures.BIPED.get().thighR) return AttackHand.RIGHT_LEG;
            return AttackHand.RIGHT_HAND; // toolR, handR, etc. all default right
        }
    }
}