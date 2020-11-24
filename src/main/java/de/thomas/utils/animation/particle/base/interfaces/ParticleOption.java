package de.thomas.utils.animation.particle.base.interfaces;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface ParticleOption {

    boolean canPlayParallel();
    boolean playAtLeast() default false;

}
