package com.dungeoncrawler.Javiarenka.equipment;

import com.sun.istack.NotNull;

import java.util.*;

public class Backpack {

    private Weapon leftHandSlot;
    private Equipment rightHandSlot;
    private Armor chestSlot;
    private Armor feetSlot;
    private Armor armsSlot;
    private Map<Integer, Equipment> baggage;
    private static int NUMBER_OF_WORN_EQUIPMENT_SLOTS = 5;
    private static int MAX_BAGGAGE_CAPACITY = 30;

    public Backpack() {
        baggage = new HashMap<>();
        for (int i = 0; i < MAX_BAGGAGE_CAPACITY; i++) {
            baggage.put(i, new EmptyEquipmentSlot());
        }
    }

    public static int getMaxBaggageCapacity() {
        return MAX_BAGGAGE_CAPACITY;
    }

    public static void setMaxBaggageCapacity(int maxBaggageCapacity) {
        MAX_BAGGAGE_CAPACITY = maxBaggageCapacity;
    }

    public Weapon getLeftHandSlot() {
        return leftHandSlot;
    }

    public void setLeftHandSlot(Weapon leftHandSlot) {
        this.leftHandSlot = leftHandSlot;
    }

    public Equipment getRightHandSlot() {
        return rightHandSlot;
    }

    public void setRightHandSlot(Equipment rightHandSlot) {
        this.rightHandSlot = rightHandSlot;
    }

    public Armor getChestSlot() {
        return chestSlot;
    }

    public void setChestSlot(Armor chestSlot) {
        this.chestSlot = chestSlot;
    }

    public Armor getFeetSlot() {
        return feetSlot;
    }

    public void setFeetSlot(Armor feetSlot) {
        this.feetSlot = feetSlot;
    }

    public Armor getArmsSlot() {
        return armsSlot;
    }

    public void setArmsSlot(Armor armsSlot) {
        this.armsSlot = armsSlot;
    }

    public Map<Integer, Equipment> getBaggage() {
        return baggage;
    }

    public void setBaggage(Map<Integer, Equipment> baggage) {
        this.baggage = baggage;
    }

    public static int getNumberOfWornEquipmentSlots() {
        return NUMBER_OF_WORN_EQUIPMENT_SLOTS;
    }

    public static void setNumberOfWornEquipmentSlots(int numberOfWornEquipmentSlots) {
        NUMBER_OF_WORN_EQUIPMENT_SLOTS = numberOfWornEquipmentSlots;
    }

    public int size() {
        int size = 0;
        size = leftHandSlot == null ? size : size + 1;
        size = rightHandSlot == null ? size : size + 1;
        size = chestSlot == null ? size : size + 1;
        size = feetSlot == null ? size : size + 1;
        size = armsSlot == null ? size : size + 1;
        size = size + baggage.size();
        return size;
    }

    public int baggageSize() {
        return baggage.size();
    }

    public boolean isEmpty() {
        if (leftHandSlot != null || rightHandSlot != null || chestSlot != null || feetSlot != null || armsSlot != null) {
            return false;
        } else return baggage == null || baggage.isEmpty();
    }

    public boolean contains(Equipment o) {
        if (leftHandSlot.equals(o) || rightHandSlot.equals(o) || chestSlot.equals(o) || feetSlot.equals(o) || armsSlot.equals(o)) {
            return true;
        } else return baggage.containsValue(o);
    }

    public Iterator<Equipment> iterator() {
        return new Iterator<>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                if (currentIndex > NUMBER_OF_WORN_EQUIPMENT_SLOTS) {
                    return currentIndex < size() && baggage.get(currentIndex) != null;
                } else {
                    switch (currentIndex) {
                        case 1:
                            return leftHandSlot != null;
                        case 2:
                            return rightHandSlot != null;
                        case 3:
                            return chestSlot != null;
                        case 4:
                            return feetSlot != null;
                        case 5:
                            return armsSlot != null;
                    }
                }
                return false;
            }

            @Override
            public Equipment next() {
                currentIndex++;
                if (currentIndex > NUMBER_OF_WORN_EQUIPMENT_SLOTS) {
                    return baggage.get(currentIndex);
                } else {
                    switch (currentIndex) {
                        case 1:
                            return leftHandSlot;
                        case 2:
                            return rightHandSlot;
                        case 3:
                            return chestSlot;
                        case 4:
                            return feetSlot;
                        case 5:
                            return armsSlot;
                    }
                }
                return null;
            }

            @Override
            public void remove() {
                throw new UnsupportedOperationException();
            }
        };
    }

    public boolean add(Equipment equipment, EquipmentSlots equipmentSlot) {
        try {
            switch (equipmentSlot) {
                case ARMS:
                    if (equipment instanceof Armor) {
                        armsSlot = (Armor) equipment;
                    } else {
                        throw new InvalidEquipmentTypeException();
                    }
                    break;
                case FEET:
                    if (equipment instanceof Armor) {
                        feetSlot = (Armor) equipment;
                    } else {
                        throw new InvalidEquipmentTypeException();
                    }
                    break;
                case CHEST:
                    if (equipment instanceof Armor) {
                        chestSlot = (Armor) equipment;
                    } else {
                        throw new InvalidEquipmentTypeException();
                    }
                    break;
                case LEFT_HAND:
                    if (equipment instanceof Weapon) {
                        leftHandSlot = (Weapon) equipment;
                    } else {
                        throw new InvalidEquipmentTypeException();
                    }
                    break;
                case RIGHT_HAND:
                    if (equipment instanceof Weapon) {
                        rightHandSlot = equipment;
                    } else {
                        throw new InvalidEquipmentTypeException();
                    }
                    break;
                case BAGGAGE:
                    putEquipmentToFirstAvailableSlot(equipment);
                    break;
            }
            return true;
        } catch (InvalidEquipmentTypeException e) {
            return false;
        } catch (InventorySlotsFullException e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean remove(Equipment o) {
        if (!this.contains(o)) {
            throw new NoSuchElementException();
        } else {
            if (leftHandSlot.equals(o)) {
                leftHandSlot = null;
                return true;
            } else if (rightHandSlot.equals(o)) {
                rightHandSlot = null;
                return true;
            } else if (chestSlot.equals(o)) {
                chestSlot = null;
                return true;
            } else if (feetSlot.equals(o)) {
                feetSlot = null;
                return true;
            } else if (armsSlot.equals(o)) {
                armsSlot = null;
                return true;
            } else {
                baggage.values().removeIf(value -> !value.equals(o));
                return true;
            }
        }
    }

    public boolean remove(@NotNull EquipmentSlots equipmentSlot) {
        switch (equipmentSlot) {
            case ARMS:
                armsSlot = null;
                break;
            case FEET:
                feetSlot = null;
                break;
            case CHEST:
                chestSlot = null;
                break;
            case LEFT_HAND:
                leftHandSlot = null;
                break;
            case RIGHT_HAND:
                rightHandSlot = null;
                break;
            case BAGGAGE:
                return false;
        }
        return true;
    }

    public boolean removeFromBaggage(int slotNumber) {
        baggage.keySet().removeIf(key -> key != slotNumber);
        return true;
    }

    public void clear() {
        leftHandSlot = null;
        rightHandSlot = null;
        armsSlot = null;
        chestSlot = null;
        feetSlot = null;
        baggage.clear();
    }

    @Override
    public boolean equals(Object o) {
        return super.equals(o);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public String toString() {
        return "Backpack{" +
                "leftHandSlot=" + leftHandSlot +
                ", rightHandSlot=" + rightHandSlot +
                ", chestSlot=" + chestSlot +
                ", feetSlot=" + feetSlot +
                ", armsSlot=" + armsSlot +
                ", baggage=" + baggage +
                '}';
    }

    public void putEquipmentToFirstAvailableSlot(Equipment equipment) throws InventorySlotsFullException {
        for (Integer i = 0; i < MAX_BAGGAGE_CAPACITY; i++) {
            if (baggage.get(i).getClass() == EmptyEquipmentSlot.class) {
                baggage.put(i, equipment);
                return;
            }
        }
        throw new InventorySlotsFullException();
    }
}
