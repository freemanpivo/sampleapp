package com.freemanpivo.insurancechallenge.core.domain;

import com.freemanpivo.insurancechallenge.core.commom.ValidationIssue;
import com.freemanpivo.insurancechallenge.core.exception.RequestValidationException;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.regex.Pattern;

public class ProductCandidate {
    private final String name;
    private final Category category;
    private final BigDecimal basePrice;
    private static final String BASE_PRICE_FIELD = "preco_base";
    private static final String NAME_FIELD = "nome";
    private static final String CATEGORY_FIELD = "categoria";
    private static final String BASIC_DESCRIPTION = "[%s] nao possui dados validos";
    private static final String BASE_PRICE_NULL = "nao pode ser nulo";
    private static final String BASE_PRICE_VALUE = "nao pode ser menor ou igual a zero";
    private static final String NAME_ISSUE_EMPTY = "nao pode ser vazio ou nulo";
    private static final String NAME_ISSUE_CHARACTERS = "nao pode conter caracteres especiais";
    private static final String CATEGORY_ISSUE_EMPTY = "nao pode ser vazio ou nulo";
    private static final String CATEGORY_ISSUE_VALUE = "tem que ser VIDA, AUTO, VIAGEM, RESIDENCIAL ou PATRIMONIAL";
    private final Pattern acceptedCharacters = Pattern.compile("^[ a-z0-9]+$", Pattern.CASE_INSENSITIVE);
    private final List<ValidationIssue> errors = new ArrayList<>();

    public ProductCandidate(String name, String category, BigDecimal basePrice) throws RequestValidationException {
        validateName(name);
        validateCategory(category);
        validateBasePrice(basePrice);

        if (!errors.isEmpty()) throw new RequestValidationException(errors, "Check the messages field for more details");

        this.name = name.trim();
        this.category = Category.from(category.trim().toUpperCase(Locale.ROOT));

        this.basePrice = basePrice;
    }

    public String name() { return name; }
    public Category category() { return category; }
    public BigDecimal basePrice() { return basePrice; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductCandidate that = (ProductCandidate) o;

        if (!Objects.equals(name, that.name)) return false;
        if (!Objects.equals(category, that.category)) return false;
        return Objects.equals(basePrice, that.basePrice);
    }

    private void validateBasePrice(BigDecimal basePrice) {
        final var issues = new ArrayList<String>();
        if (basePrice == null) issues.add(BASE_PRICE_NULL);
        if (basePrice != null) {
            if (basePrice.compareTo(BigDecimal.ZERO) <= 0) issues.add(BASE_PRICE_VALUE);
        }

        if (issues.isEmpty()) return;

        final var basePriceValidation = new ValidationIssue(BASE_PRICE_FIELD, String.format(BASIC_DESCRIPTION, BASE_PRICE_FIELD), issues);
        errors.add(basePriceValidation);
    }

    private void validateCategory(String category) {
        final var issues = new ArrayList<String>();
        if (category == null || category.trim().isEmpty()) issues.add(CATEGORY_ISSUE_EMPTY);
        if (category!= null) {
            if (!Category.contains(category.trim())) issues.add(CATEGORY_ISSUE_VALUE);
        }

        if (issues.isEmpty()) return;

        final var basePriceValidation = new ValidationIssue(CATEGORY_FIELD, String.format(BASIC_DESCRIPTION, CATEGORY_FIELD), issues);
        errors.add(basePriceValidation);
    }

    private void validateName(String name) {
        final var issues = new ArrayList<String>();
        if (name == null || name.trim().isEmpty()) issues.add(NAME_ISSUE_EMPTY);
        if (name != null) {
            if (!acceptedCharacters.matcher(name.trim()).matches()) issues.add(NAME_ISSUE_CHARACTERS);
        }

        if (issues.isEmpty()) return;

        final var basePriceValidation = new ValidationIssue(NAME_FIELD, String.format(BASIC_DESCRIPTION, NAME_FIELD), issues);
        errors.add(basePriceValidation);
    }
}
