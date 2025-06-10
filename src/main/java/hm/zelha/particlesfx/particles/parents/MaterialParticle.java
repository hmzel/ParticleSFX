package hm.zelha.particlesfx.particles.parents;

import org.bukkit.Material;

public interface MaterialParticle extends IParticle {
    MaterialParticle inherit(Particle particle);

    MaterialParticle clone();

    void setMaterial(Material material);

    Material getMaterial();
}