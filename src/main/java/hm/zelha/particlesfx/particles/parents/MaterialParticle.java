package hm.zelha.particlesfx.particles.parents;

import org.bukkit.material.MaterialData;

public interface MaterialParticle extends IParticle {
    MaterialParticle inherit(Particle particle);

    MaterialParticle clone();

    void setMaterialData(MaterialData data);

    MaterialData getMaterialData();
}