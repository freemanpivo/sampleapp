package com.freemanpivo.insurancechallenge.core.domain.compute.formula;

import java.math.BigDecimal;
import java.math.RoundingMode;

public abstract class PriceComputeOperation {
    public BigDecimal chargedPrice(BigDecimal basePrice, BigDecimal iof, BigDecimal pis, BigDecimal cofins) {
        return basePrice.add(iof).add(pis).add(cofins);
    }

    public BigDecimal cofinsTax(BigDecimal basePrice, BigDecimal cofinsPercentage) {
        return basePrice.multiply(cofinsPercentage).setScale(5, RoundingMode.FLOOR);
    }

    public BigDecimal iofTax(BigDecimal basePrice, BigDecimal iofPercentage) {
        return basePrice.multiply(iofPercentage).setScale(5, RoundingMode.FLOOR);
    }

    public BigDecimal pisTax(BigDecimal basePrice, BigDecimal pisPercentage) {
        return basePrice.multiply(pisPercentage).setScale(5, RoundingMode.FLOOR);
    }

}
