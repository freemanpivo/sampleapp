package com.freemanpivo.insurancechallenge.core.domain;

import com.freemanpivo.insurancechallenge.UnitTest;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;

@UnitTest
public class PriceTest {

    @ParameterizedTest
    @MethodSource("validInputs")
    public void validScenarios(BigDecimal basePrice, Category category, BigDecimal expectedChargedPrice, String expectedCurrency) {
        final var price = Assertions.assertDoesNotThrow(() -> new Price(basePrice, category));
        Assertions.assertEquals(expectedChargedPrice, price.chargedPrice());
        Assertions.assertEquals(expectedCurrency, price.chargedPriceAsCurrency());
    }

    static Stream<Arguments> validInputs() {
        return Stream.of(
                Arguments.of(BigDecimal.ONE, Category.AUTO, new BigDecimal("1.11"), "R$ 1,11"),
                Arguments.of(BigDecimal.TEN, Category.AUTO, new BigDecimal("11.05"), "R$ 11,05"),
                Arguments.of(new BigDecimal("50.00"), Category.AUTO, new BigDecimal("55.25"), "R$ 55,25"),
                Arguments.of(new BigDecimal("100.00"), Category.AUTO, new BigDecimal("110.50"), "R$ 110,50"),
                Arguments.of(new BigDecimal("1733.00"), Category.AUTO, new BigDecimal("1914.97"), "R$ 1.914,97"),

                Arguments.of(BigDecimal.ONE, Category.PATRIMONIAL, new BigDecimal("1.08"), "R$ 1,08"),
                Arguments.of(BigDecimal.TEN, Category.PATRIMONIAL, new BigDecimal("10.80"), "R$ 10,80"),
                Arguments.of(new BigDecimal("50.00"), Category.PATRIMONIAL, new BigDecimal("54.00"), "R$ 54,00"),
                Arguments.of(new BigDecimal("100.00"), Category.PATRIMONIAL, new BigDecimal("108.00"), "R$ 108,00"),
                Arguments.of(new BigDecimal("1733.00"), Category.PATRIMONIAL, new BigDecimal("1871.64"), "R$ 1.871,64"),

                Arguments.of(BigDecimal.ONE, Category.RESIDENCIAL, new BigDecimal("1.07"), "R$ 1,07"),
                Arguments.of(BigDecimal.TEN, Category.RESIDENCIAL, new BigDecimal("10.70"), "R$ 10,70"),
                Arguments.of(new BigDecimal("50.00"), Category.RESIDENCIAL, new BigDecimal("53.50"), "R$ 53,50"),
                Arguments.of(new BigDecimal("100.00"), Category.RESIDENCIAL, new BigDecimal("107.00"), "R$ 107,00"),
                Arguments.of(new BigDecimal("1733.00"), Category.RESIDENCIAL, new BigDecimal("1854.31"), "R$ 1.854,31"),


                Arguments.of(BigDecimal.ONE, Category.VIAGEM, new BigDecimal("1.07"), "R$ 1,07"),
                Arguments.of(BigDecimal.TEN, Category.VIAGEM, new BigDecimal("10.70"), "R$ 10,70"),
                Arguments.of(new BigDecimal("50.00"), Category.VIAGEM, new BigDecimal("53.50"), "R$ 53,50"),
                Arguments.of(new BigDecimal("100.00"), Category.VIAGEM, new BigDecimal("107.00"), "R$ 107,00"),
                Arguments.of(new BigDecimal("1733.00"), Category.VIAGEM, new BigDecimal("1854.31"), "R$ 1.854,31"),

                Arguments.of(BigDecimal.ONE, Category.VIDA, new BigDecimal("1.03"), "R$ 1,03"),
                Arguments.of(BigDecimal.TEN, Category.VIDA, new BigDecimal("10.32"), "R$ 10,32"),
                Arguments.of(new BigDecimal("50.00"), Category.VIDA, new BigDecimal("51.60"), "R$ 51,60"),
                Arguments.of(new BigDecimal("100.00"), Category.VIDA, new BigDecimal("103.20"), "R$ 103,20"),
                Arguments.of(new BigDecimal("1733.00"), Category.VIDA, new BigDecimal("1788.46"), "R$ 1.788,46")

        );
    }

}