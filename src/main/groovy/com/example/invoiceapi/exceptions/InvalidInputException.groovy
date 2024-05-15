package com.example.invoiceapi.exceptions

class InvalidInputException extends RuntimeException {
    InvalidInputException(String message) {
        super(message)
    }
}
