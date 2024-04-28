package com.freemanpivo.insurancechallenge.database.entity;

import com.freemanpivo.insurancechallenge.core.domain.*;
import jakarta.persistence.*;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "products")
public class ProductEntity implements Serializable {
    //Esta classe usa GETTERs e SETTERs porque o JPA espera um construtor vazio para fazer a (de)serializacao.
    @Id @Column(nullable = false) private UUID id;
    @Column(nullable = false) private String name;
    @Column(nullable = false) private String category;
    @Column(nullable = false) private BigDecimal basePrice;
    @Column(nullable = false) private BigDecimal chargedPrice;

    public ProductEntity() {}

    public static Product to(ProductEntity entity) {
        final var sample = new ProductCandidate(entity.getName(), entity.getCategory(), entity.getBasePrice());
        final var id = ProductIdentifier.from(entity.getId().toString());
        final var price = new Price(entity.getBasePrice(), Category.from(entity.getCategory()));

        return Product.create(sample, price, id);
    }

    public static ProductEntity from(Product product) {
        final var entity = new ProductEntity();
        entity.setId(UUID.fromString(product.id()));
        entity.setCategory(product.category());
        entity.setName(product.name());
        entity.setBasePrice(product.basePrice());
        entity.setChargedPrice(product.chargedPrice());

        return entity;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(category, that.category)) return false;
        if (!Objects.equals(basePrice, that.basePrice)) return false;
        return Objects.equals(chargedPrice, that.chargedPrice);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (basePrice != null ? basePrice.hashCode() : 0);
        result = 31 * result + (chargedPrice != null ? chargedPrice.hashCode() : 0);
        return result;
    }

    public UUID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCategory() {
        return category;
    }

    public BigDecimal getBasePrice() {
        return basePrice;
    }

    public BigDecimal getChargedPrice() {
        return chargedPrice;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setBasePrice(BigDecimal basePrice) {
        this.basePrice = basePrice;
    }

    public void setChargedPrice(BigDecimal chargedPrice) {
        this.chargedPrice = chargedPrice;
    }
}
