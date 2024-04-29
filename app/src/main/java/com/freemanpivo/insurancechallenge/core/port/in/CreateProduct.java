package com.freemanpivo.insurancechallenge.core.port.in;

import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;

public interface CreateProduct {
    Product create(ProductCandidate sample);
}
