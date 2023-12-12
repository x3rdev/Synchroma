package com.github.x3r.synchroma.common.item.cyberware;

public enum ImplantLocation {
    HEAD("Head"),
    EYES("Eyes"),
    HEART("Heart"),
    LUNGS("Lungs"),
    DIGESTION("Digestion"),
    GLANDS("Glands"),
    RIGHT_ARM("Right Arm"),
    LEFT_ARM("Left Arm"),
    LEGS("Legs");

    private final String name;
    ImplantLocation(String name){
        this.name = name;
    }

    public ImplantLocation previous() {
        int v = this.ordinal()-1 >= 0 ? this.ordinal()-1 : values().length-1;
        return values()[v];
    }
    public ImplantLocation next() {
        int v = this.ordinal()+1 < values().length ? this.ordinal()+1 : 0;
        return values()[v];
    }
    public String getName() {
        return name;
    }
    @Override
    public String toString() {
        return name;
    }
}
