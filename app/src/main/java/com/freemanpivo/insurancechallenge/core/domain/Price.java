package com.freemanpivo.insurancechallenge.core.domain;

import com.freemanpivo.insurancechallenge.core.domain.compute.ChargedPriceStrategy;
import com.freemanpivo.insurancechallenge.core.domain.compute.PriceStrategyFactory;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.Locale;
import java.util.Objects;

public class Price {
    private final BigDecimal basePrice;
    private final BigDecimal chargedPrice;

    private final ChargedPriceStrategy strategy;

    public Price(BigDecimal basePrice, Category category) {
        this.basePrice = basePrice;
        this.strategy = new PriceStrategyFactory().strategy(category);
        this.chargedPrice = strategy.compute(category, basePrice).setScale(2, RoundingMode.HALF_UP);
    }

    public BigDecimal basePrice() { return basePrice; }
    public BigDecimal chargedPrice() { return chargedPrice; }
    public String chargedPriceAsCurrency() {
        final var pattern = NumberFormat.getInstance(Locale.GERMANY);
        pattern.setMinimumFractionDigits(2);

        return String.format("R$ %s", pattern.format(chargedPrice));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Price price = (Price) o;

        if (!Objects.equals(basePrice, price.basePrice)) return false;
        return chargedPrice.equals(price.chargedPrice);
    }

    @Override
    public String toString() {
        return "Price{" +
                "basePrice=" + basePrice +
                ", chargedPrice=" + chargedPrice +
                '}';
    }
}
