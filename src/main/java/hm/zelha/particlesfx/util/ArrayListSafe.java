package hm.zelha.particlesfx.util;

import hm.zelha.particlesfx.shapers.parents.RotationHandler;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * only meant to be used in ParticleSFX internals <br><br>
 *
 * if you're wondering what this does, it's meant to handle the indexing and everything related to keeping
 * ParticleShapeCompound working properly, it's nothing you should worry about unless youre trying to create things that
 * extend RotationHandler or ParticleShaper and want to allow them to be inside a ParticleShapeCompound. <br><br>
 *
 * even if thats the case, i doubt theres any way you could screw it up. <br><br>
 *
 * if you're wondering why i did it this way, its because i didnt want anyone potentially messing with any public methods
 * i might make in order to accomplish this, so i made it really hard to mess with haha
 */
public class ArrayListSafe<L extends LocationSafe> extends ArrayList<L> {

    private final Map<ParticleShapeCompound, addMechanic> addMechanics = new HashMap<>();
    private final Map<ParticleShapeCompound, removeMechanic> removeMechanics = new HashMap<>();
    private final RotationHandler owner;

    public ArrayListSafe(RotationHandler owner) {
        this.owner = owner;
    }

    @Override
    public boolean add(L l) {
        boolean b = super.add(l);

        for (addMechanic mechanic : addMechanics.values()) {
            mechanic.accept(owner, l);
        }

        return b;
    }

    @Override
    public void add(int index, L element) {
        super.add(index, element);

        for (addMechanic mechanic : addMechanics.values()) {
            mechanic.accept(owner, element);
        }
    }

    @Override
    public L remove(int index) {
        L generic = super.remove(index);

        for (removeMechanic mechanic : removeMechanics.values()) {
            mechanic.accept(owner, index);
        }

        return generic;
    }

    public void addAddMechanics(ParticleShapeCompound owner, addMechanic mechanic) {
        addMechanics.put(owner, mechanic);
    }

    public void addRemoveMechanics(ParticleShapeCompound owner, removeMechanic mechanic) {
        removeMechanics.put(owner, mechanic);
    }

    public void removeMechanics(ParticleShapeCompound owner) {
        addMechanics.remove(owner);
        removeMechanics.remove(owner);
    }


    public interface addMechanic {
        public void accept(RotationHandler owner, LocationSafe added);
    }


    public interface removeMechanic {
        public void accept(RotationHandler owner, int index);
    }
}
