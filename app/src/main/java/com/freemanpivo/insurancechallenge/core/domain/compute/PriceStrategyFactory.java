package com.freemanpivo.insurancechallenge.core.domain.compute;

import com.freemanpivo.insurancechallenge.core.domain.Category;
import com.freemanpivo.insurancechallenge.core.domain.compute.strategy.*;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class PriceStrategyFactory {
    private final Map<Category, ChargedPriceStrategy> implementations;

    public PriceStrategyFactory() {
        implementations = Map.of(
                Category.AUTO, new PriceStrategyToAuto(),
                Category.PATRIMONIAL, new PriceStrategyToPatrimonial(),
                Category.RESIDENCIAL, new PriceStrategyToResidencial(),
                Category.VIAGEM, new PriceStrategyToViagem(),
                Category.VIDA, new PriceStrategyToVida()
        );
    }

    public ChargedPriceStrategy strategy(Category category) {
        return implementations.get(category);
    }
}
