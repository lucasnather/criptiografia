package com.natherzito.criptografia.dto;


public record CreatePaymentDto(String userDocument, String creditCardToken, Long value) {
}
