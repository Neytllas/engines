package com.company;

import java.util.ArrayList;
import java.util.Arrays;

public class ICEEngine extends Engine{
    // кол-во цилиндров
    private int cylindersCount;

    public ICEEngine(String name, String manufacturer, double power, double fuelConsumption, int cylindersCount) {
        super(name, manufacturer, power, fuelConsumption, "ДВС");
        this.cylindersCount = cylindersCount;
    }

    public ICEEngine(int id,String name, String manufacturer, double power, double fuelConsumption, int cylindersCount) {
        super(id,name, manufacturer, power, fuelConsumption, "ДВС");
        this.cylindersCount = cylindersCount;
    }
    public int getCylindersCount() {
        return cylindersCount;
    }

    public void setCylindersCount(int cylindersCount) {
        this.cylindersCount = cylindersCount;
    }

    @Override
    public String[] getData() {
        return new String[]{getName(), getManufacturer(), String.valueOf(getPower()), String.valueOf(getFuelConsumption()), String.valueOf(getCylindersCount()), ""};
    }
}
