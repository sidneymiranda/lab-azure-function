package br.sidney;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

public class CpfValidatorTest {

    @Test
    void testValidCPF() {
        assertTrue(CpfValidator.isValidCPF("05585481053"));
    }

    @Test
    void testValidCPFWithFormatting() {
        assertTrue(CpfValidator.isValidCPF("055.854.810-53"));
    }

    @Test
    void testInvalidCPF() {
        assertFalse(CpfValidator.isValidCPF("11111111111"));
        assertFalse(CpfValidator.isValidCPF("12345678900"));
    }

    @Test
    void testNullCPF() {
        assertFalse(CpfValidator.isValidCPF(null));
    }

    @Test
    void testShortCPF() {
        assertFalse(CpfValidator.isValidCPF("12345"));
    }

    @Test
    void testLongCPF() {
        assertFalse(CpfValidator.isValidCPF("12345678909123"));
    }

    @Test
    void testNonNumericCPF() {
        assertFalse(CpfValidator.isValidCPF("abcdefghijk"));
    }
}
