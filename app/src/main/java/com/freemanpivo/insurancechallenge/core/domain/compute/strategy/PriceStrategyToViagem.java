package com.freemanpivo.insurancechallenge.core.domain.compute.strategy;

import com.freemanpivo.insurancechallenge.core.domain.Category;
import com.freemanpivo.insurancechallenge.core.domain.compute.ChargedPriceStrategy;
import com.freemanpivo.insurancechallenge.core.domain.compute.formula.PriceComputeOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PriceStrategyToViagem extends PriceComputeOperation implements ChargedPriceStrategy {
    private final BigDecimal iofPercentage = new BigDecimal("0.02");
    private final BigDecimal pisPercentage = new BigDecimal("0.04");
    private final BigDecimal cofinsPercentage = new BigDecimal("0.01");
    private static final Logger logger = LoggerFactory.getLogger(PriceStrategyToViagem.class);

    @Override
    public BigDecimal compute(Category category, BigDecimal basePrice) {
        logger.info("Computing VIAGEM charge price...");
        final var cofinsValue = cofinsTax(basePrice, cofinsPercentage);
        final var iofValue = iofTax(basePrice, iofPercentage);
        final var pisValue = pisTax(basePrice, pisPercentage);
        final var chargedPrice = chargedPrice(basePrice, iofValue, pisValue, cofinsValue);
        logger.info("VIAGEM charge price computed!");

        return chargedPrice;
    }

}
