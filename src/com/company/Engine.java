package com.company;

public abstract class Engine {
    private String type;
    private String name;
    private String manufacturer;
    private double power;
    private double fuelConsumption;

    public enum Parameters {
        Name,
        Manufacturer,
        Power,
        FuelConsumption,
        CylindersCount,
        Traction
    }

    // типы данных для валидации
    public enum Types{
        Text,
        Number,
        Integer
    }

    // шапка для таблицы
    public static final String[] HEADER =
            new String[]{
                    "Название",
                    "Производитель",
                    "Мощность",
                    "Расход топлива",
                    "Цилиндры",
                    "Тяга"
            };

    // размерности
    public static final String[] DIMENSIONS =
            new String[]{
                    "",
                    "",
                    "л.с",
                    "л/100км",
                    "шт",
                    "тонн"
            };

    // типы данных для каждого поля
    public static final Types[] TYPES = new Types[]{Types.Text, Types.Text, Types.Number, Types.Number, Types.Integer, Types.Number};

    public Engine(String name, String manufacturer, double power, double fuelConsumption, String type) {
        this.name = name;
        this.manufacturer = manufacturer;
        this.power = power;
        this.fuelConsumption = fuelConsumption;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getManufacturer() {
        return manufacturer;
    }

    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public double getPower() {
        return power;
    }

    public void setPower(double power) {
        this.power = power;
    }

    public double getFuelConsumption() {
        return fuelConsumption;
    }

    public void setFuelConsumption(double fuelConsumption) {
        this.fuelConsumption = fuelConsumption;
    }

    public void setType(String type) {
        this.type = type;
    }
    public String getType() {
        return type;
    }

    public String[] getData(){
        return new String[]{getName(), getManufacturer(), String.valueOf(getPower()), String.valueOf(getFuelConsumption())};
    }
}
