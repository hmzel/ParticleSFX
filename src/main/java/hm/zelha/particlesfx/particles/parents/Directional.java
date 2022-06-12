package hm.zelha.particlesfx.particles.parents;

import org.bukkit.block.BlockFace;

public interface Directional {

    public void setDirection(BlockFace face);

    public BlockFace getDirection();
}
