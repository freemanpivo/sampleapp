package com.freemanpivo.insurancechallenge.core.domain;

import java.math.BigDecimal;
import java.util.Objects;

public class Product {
    private final ProductIdentifier id;
    private final String name;
    private final Category category;
    private final Price price;

    private Product(ProductCandidate sample, Price price, ProductIdentifier id) {
        this.id = id;
        this.name = sample.name();
        this.category = sample.category();
        this.price = price;
    }

    public static Product create(ProductCandidate sample, Price price, ProductIdentifier id) {
        return new Product(sample, price, id);
    }

    public String id() { return id.value(); }
    public String name() { return name; }
    public String category() { return category.name(); }
    public BigDecimal basePrice() { return price.basePrice(); }
    public BigDecimal chargedPrice() { return price.chargedPrice(); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Product product = (Product) o;

        if (!Objects.equals(id, product.id)) return false;
        if (!Objects.equals(name, product.name)) return false;
        if (category != product.category) return false;
        return Objects.equals(price, product.price);
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", category=" + category +
                ", price=" + price +
                '}';
    }
}
