package com.freemanpivo.insurancechallenge.core.domain.compute.strategy;

import com.freemanpivo.insurancechallenge.core.domain.Category;
import com.freemanpivo.insurancechallenge.core.domain.compute.ChargedPriceStrategy;
import com.freemanpivo.insurancechallenge.core.domain.compute.formula.PriceComputeOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class PriceStrategyToPatrimonial extends PriceComputeOperation implements ChargedPriceStrategy {
    private final BigDecimal iofPercentage = new BigDecimal("0.05");
    private final BigDecimal pisPercentage = new BigDecimal("0.03");
    private final BigDecimal cofinsPercentage = BigDecimal.ZERO;
    private static final Logger logger = LoggerFactory.getLogger(PriceStrategyToPatrimonial.class);

    @Override
    public BigDecimal compute(Category category, BigDecimal basePrice) {
        logger.info("Computing PATRIMONIAL charge price...");
        final var cofinsValue = cofinsTax(basePrice, cofinsPercentage);
        final var iofValue = iofTax(basePrice, iofPercentage);
        final var pisValue = pisTax(basePrice, pisPercentage);
        final var chargedPrice = chargedPrice(basePrice, iofValue, pisValue, cofinsValue);
        logger.info("PATRIMONIAL charge price computed!");

        return chargedPrice;
    }

}
