package com.freemanpivo.insurancechallenge.core.domain.compute;

import com.freemanpivo.insurancechallenge.core.domain.Category;
import com.freemanpivo.insurancechallenge.core.domain.Price;

import java.math.BigDecimal;

public interface ChargedPriceStrategy {
    BigDecimal compute(Category category, BigDecimal basePrice);
    Category category();
}
