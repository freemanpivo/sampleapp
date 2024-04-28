package com.freemanpivo.insurancechallenge.core.port;

import com.freemanpivo.insurancechallenge.core.domain.Product;
import com.freemanpivo.insurancechallenge.core.domain.ProductCandidate;

public interface UpdateProduct {
    Product update(String id, ProductCandidate sample);
}
