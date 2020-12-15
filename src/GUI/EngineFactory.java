package GUI;

import com.company.Engine;
import com.company.ICEEngine;
import com.company.JetEngine;

import java.util.ArrayList;
import java.util.Random;

    //класс генерации двигателей
public class EngineFactory {
    private static Random rnd = new Random();

    public static Engine[] generate(int count){
        ArrayList<Engine> engines = new ArrayList<>();

        for (int i = 0; i < count; i++) {
            switch (rnd.nextInt(2)){
                case(0):
                    engines.add(generateICE());
                    break;
                case(1):
                    engines.add(generateJet());
                    break;
            }
        }

        return engines.toArray(Engine[]::new);
    }

    // ДВС
    public static ICEEngine generateICE(){
        // генерация номера двигателя
        String name = String.format("ДВС №%d", rnd.nextInt(500));
        // генерация номера завода
        String manufacturer = String.format("Завод %d", rnd.nextInt(10));
        // сила
        double power = rnd.nextDouble() * 20 + 7;
        // потребление топлива
        double fuelConsumption = rnd.nextDouble() * 10 + 8;
        // количество цилиндров
        int cylinderCount = (rnd.nextInt(3) + 2) * 2;
        return new ICEEngine(name, manufacturer, power, fuelConsumption, cylinderCount);
    }

    // турбореактивный
    public static JetEngine generateJet(){
        String name = String.format("Турбореактивный двигатель №%d", rnd.nextInt(500));
        String manufacturer = String.format("Завод %d", rnd.nextInt(10));
        double power = rnd.nextDouble() * 10 + 10;
        double fuelConsumption = rnd.nextDouble() * 10 + 10;
        // тяга
        double traction = rnd.nextDouble() * 10 + 10;
        return new JetEngine(name, manufacturer, power, fuelConsumption, traction);
    }
}
