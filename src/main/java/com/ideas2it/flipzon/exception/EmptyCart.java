package com.ideas2it.flipzon.exception;

public class EmptyCart extends RuntimeException {
    public EmptyCart(String message) {
        super(message);
    }
}
