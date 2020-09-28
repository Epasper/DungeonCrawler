package com.dungeoncrawler.Javiarenka.equipment;

import java.util.*;

public class Backpack implements Collection<Equipment> {

    Weapon leftHandSlot;
    Equipment rightHandSlot;
    Armor chestSlot;
    Armor feetSlot;
    Armor armsSlot;
    Map<Integer, Equipment> baggage;

    @Override
    public int size() {
        int size = 0;
        size = leftHandSlot == null ? size : size + 1;
        size = rightHandSlot == null ? size : size + 1;
        size = chestSlot == null ? size : size + 1;
        size = feetSlot == null ? size : size + 1;
        size = armsSlot == null ? size : size + 1;
        size = size + baggage.size();
        return 0;
    }

    public int baggageSize() {
        return baggage.size();
    }

    @Override
    public boolean isEmpty() {
        if (leftHandSlot != null || rightHandSlot != null || chestSlot != null || feetSlot != null || armsSlot != null) {
            return false;
        } else return baggage == null || baggage.isEmpty();
    }

    @Override
    public boolean contains(Object o) {
        if (leftHandSlot.equals(o) || rightHandSlot.equals(o) || chestSlot.equals(o) || feetSlot.equals(o) || armsSlot.equals(o)) {
            return true;
        } else return baggage.containsValue(o);
    }

    @Override
    public Iterator<Equipment> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[]{leftHandSlot, rightHandSlot, chestSlot, feetSlot, armsSlot, baggage};
    }

    @Override
    public boolean add(Equipment equipment) {
        try {
            putEquipmentToFirstAvailableSlot(equipment);
            return true;
        } catch (InventorySlotsFullException e) {
            return false;
        }
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
                        rightHandSlot = (Weapon) equipment;
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

    @Override
    public boolean remove(Object o) {
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

    public boolean remove(EquipmentSlots equipmentSlot) {
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

    @Override
    public boolean addAll(Collection collection) {
        //todo if there's a need to use this method, we'll have to write it.
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        //todo if there's a need to use this method, we'll have to write it.
        throw new UnsupportedOperationException();
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
    public boolean retainAll(Collection collection) {
        //todo if there's a need to use this method, we'll have to write it.
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean removeAll(Collection collection) {
        //todo if there's a need to use this method, we'll have to write it.
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection collection) {
        //todo if there's a need to use this method, we'll have to write it.
        throw new UnsupportedOperationException();
    }

    @Override
    public Object[] toArray(Object[] objects) {
        //todo if there's a need to use this method, we'll have to write it.
        throw new UnsupportedOperationException();
    }

    public void putEquipmentToFirstAvailableSlot(Equipment equipment) throws InventorySlotsFullException {
        for (Integer i = 0; i < baggage.size(); i++) {
            if (!baggage.containsKey(i)) {
                baggage.put(i, equipment);
            }
        }
        throw new InventorySlotsFullException();
    }
}
