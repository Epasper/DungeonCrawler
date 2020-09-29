package com.dungeoncrawler.Javiarenka.equipment;

public class InvalidEquipmentTypeException extends Throwable {
    @Override
    public String getMessage() {
        return "The equipment that you want to manipulate is of incorrect type";
    }
}
