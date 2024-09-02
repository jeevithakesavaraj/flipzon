package com.ideas2it.flipzon.exception;

public class OutOfStock extends Exception {
    public OutOfStock(String outOfStock) {
        super("Currently not Available");
    }
}
