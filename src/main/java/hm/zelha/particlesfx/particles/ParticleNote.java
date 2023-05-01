package hm.zelha.particlesfx.particles;

import hm.zelha.particlesfx.particles.parents.Particle;
import net.minecraft.server.v1_13_R2.IRegistry;
import net.minecraft.server.v1_13_R2.MinecraftKey;
import net.minecraft.server.v1_13_R2.ParticleType;

public class ParticleNote extends Particle {
    public ParticleNote(double offsetX, double offsetY, double offsetZ, int count) {
        super((ParticleType) IRegistry.PARTICLE_TYPE.get(new MinecraftKey("note")), offsetX, offsetY, offsetZ, 0, count, 0);
    }

    public ParticleNote(double offsetX, double offsetY, double offsetZ) {
        this(offsetX, offsetY, offsetZ, 1);
    }

    public ParticleNote(int count) {
        this(0, 0, 0, count);
    }

    public ParticleNote() {
        this(0, 0, 0, 1);
    }

    @Override
    public ParticleNote inherit(Particle particle) {
        super.inherit(particle);

        return this;
    }

    @Override
    public ParticleNote clone() {
        return new ParticleNote().inherit(this);
    }
}
