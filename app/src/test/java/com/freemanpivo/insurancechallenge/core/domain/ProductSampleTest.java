package com.freemanpivo.insurancechallenge.core.domain;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.math.BigDecimal;
import java.util.stream.Stream;


public class ProductSampleTest {

    @ParameterizedTest
    @MethodSource("validInputs")
    public void validScenarios(String name, String category, BigDecimal basePrice) {
        Assertions.assertDoesNotThrow(() -> new ProductCandidate(name, category, basePrice));
    }

    @ParameterizedTest
    @MethodSource("invalidInputs")
    public void invalidScenarios(String name, String category, BigDecimal basePrice) {
        Assertions.assertThrows(Exception.class, () -> new ProductCandidate(name, category, basePrice));
    }

    static Stream<Arguments> validInputs() {
        return Stream.of(
                Arguments.of("Minha Vida", "vIdA", BigDecimal.ONE),
                Arguments.of("   minha viDA   ", "   VIDA", BigDecimal.TEN),
                Arguments.of("Minha Vida    ", "vida   ", BigDecimal.TEN),
                Arguments.of("Auto", "aUtO", BigDecimal.ONE),
                Arguments.of("   auto   ", "   AUTO", BigDecimal.TEN),
                Arguments.of("Auto    ", "auto   ", BigDecimal.ONE),
                Arguments.of("Minha Viagem", "viAGEm", BigDecimal.TEN),
                Arguments.of("   minha Viagem   ", "   VIAGEM", BigDecimal.ONE),
                Arguments.of("Minha Viagem    ", "viagem   ", BigDecimal.TEN),
                Arguments.of("Meu Reside", "rESidenCIAL", BigDecimal.ONE),
                Arguments.of("   meu ReSIde   ", "   RESIDENCIAL", BigDecimal.ONE),
                Arguments.of("Meu resIdE    ", "residencial   ", BigDecimal.ONE),
                Arguments.of("Meu Patrimonial", "PAtrimoNIal", BigDecimal.TEN),
                Arguments.of("   meu Patrimonial   ", "   PATRIMONIAL", BigDecimal.TEN),
                Arguments.of("Meu patrimonIAL    ", "patrimonial   ", BigDecimal.TEN)
        );
    }
    static Stream<Arguments> invalidInputs() {
        return Stream.of(
                Arguments.of("Minha Vid@", "vIdA", BigDecimal.ONE),
                Arguments.of("   minha v!DA   ", "   VIDA", BigDecimal.TEN),
                Arguments.of("Minha V1d@    ", "v1da   ", BigDecimal.TEN),
                Arguments.of("Aut0oo&", "aUt0", BigDecimal.ONE),
                Arguments.of("   aut0#   ", "   AUTO", BigDecimal.TEN),
                Arguments.of("@uto    ", "aut0   ", BigDecimal.ONE),
                Arguments.of("Minha Viag3m", "viAG3m", BigDecimal.TEN),
                Arguments.of("   minha Vi@gem   ", "   VIAG3M", BigDecimal.ONE),
                Arguments.of("Minha Viagem    ", "vi@gem   ", BigDecimal.TEN),
                Arguments.of("Meu Resid3", "rESidenCI@L", BigDecimal.ONE),
                Arguments.of("   meu ReS!d3   ", "   RESIDENCIAL", BigDecimal.ONE),
                Arguments.of("Meu res1dE    ", "teste   ", BigDecimal.ONE),
                Arguments.of("Meu Patrimonial", "    ", BigDecimal.TEN),
                Arguments.of("   meu Patrimonial   ", null, new BigDecimal("-1")),
                Arguments.of("Meu patrimonIAL    ", "p@trimonial   ", BigDecimal.ZERO)
        );
    }
}