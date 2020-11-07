package com.dungeoncrawler.Javiarenka.creature;

public class NoMoreMoneyException extends Exception {
    public static final String MESSAGE = "This transaction could not be completed - not enough money";
    @Override
    public String getMessage() {
        return MESSAGE;
    }
}
