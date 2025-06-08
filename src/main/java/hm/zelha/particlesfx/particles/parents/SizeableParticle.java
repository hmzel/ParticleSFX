package hm.zelha.particlesfx.particles.parents;

public interface SizeableParticle extends IParticle {
    SizeableParticle inherit(Particle particle);

    SizeableParticle clone();

    void setSize(double size);

    Particle setSpeed(double speed);

    Particle setRadius(int radius);

    double getSize();
}