package de.acagamics.framework.simulation;

import java.util.function.Supplier;

public interface TournamentSupplier {
    Supplier<Simulatable> apply(Long seed, Class<?> second, Class<?> third);
}
