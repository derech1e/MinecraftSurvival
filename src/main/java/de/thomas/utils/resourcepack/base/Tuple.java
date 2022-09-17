package de.thomas.utils.resourcepack.base;

import javax.annotation.Nonnull;

/**
 * Tuple type
 */
public interface Tuple {

    /**
     * Get the tuple size
     *
     * @return Tuple size
     */
    int getSize();

    /**
     * Turn the tuple into a type erased array
     *
     * @return Created array
     */
    @Nonnull Object[] toArray();

}