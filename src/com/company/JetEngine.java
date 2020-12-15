package com.company;

public class JetEngine extends Engine{
    // тяга
    private double traction;

    public JetEngine(String name, String manufacturer, double power, double fuelConsumption, double traction) {
        super(name, manufacturer, power, fuelConsumption, "Турбореактивный двигатель");
        this.traction = traction;
    }

    public double getTraction() {
        return traction;
    }

    public void setTraction(double traction) {
        this.traction = traction;
    }

    @Override
    public String[] getData() {
        return new String[]{getName(), getManufacturer(), String.valueOf(getPower()), String.valueOf(getFuelConsumption()), "", String.valueOf(getTraction())};
    }
}
