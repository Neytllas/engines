package Database;

import com.company.Engine;
import com.company.ICEEngine;
import com.company.JetEngine;

import java.sql.*;
import java.util.ArrayList;

public class Database {
    public static final String PATH_TO_DB_FAIL = "engines.db";
    public static final String URL = "jdbc:sqlite:" + PATH_TO_DB_FAIL;

    //создаем объект для соединения
    public static Connection connection;
    //создаем объект для выполнения SQL-запросов
    public static Statement statement;
    //создаем объект для создания результата запроса
    public static ResultSet resultSet;

    //закрытие соединения
    public static void closeConnection() throws SQLException {
        connection.close();
        statement.close();
        resultSet.close();
    }

    //соединение
    public static void openConnection() throws SQLException {
        connection = DriverManager.getConnection(URL);
        if (connection != null) {
            System.out.println("База Подключена!");
            createDB();
        }
    }
    //метод для создания таблицы
    public static void createDB() throws SQLException {
        statement = connection.createStatement();
//запрос на создание таблицы
        statement .execute("CREATE TABLE if not exists \"Engines\" (\n" +
                "\t\"id\"\tINTEGER,\n" +
                "\t\"type\"\ttext,\n" +
                "\t\"name\"\ttext,\n" +
                "\t\"manufacturer\"\ttext,\n" +
                "\t\"power\"\treal,\n" +
                "\t\"fuelConsumption\"\treal,\n" +
                "\t\"cylindersCount\"\tINTEGER,\n" +
                "\t\"traction\"\tREAL,\n" +
                "\tPRIMARY KEY(\"id\" AUTOINCREMENT)\n" +
                ");");
        System.out.println("Таблица создана или уже существует.");
    }

    //метод для записи в таблицу БД
    public static void AddControl(Engine engine) throws SQLException {
//выполняем запрос
        PreparedStatement statement = connection.prepareStatement("INSERT INTO Engines(`type`,`name`,`manufacturer`,`power`,`fuelConsumption`, `cylindersCount`, `traction`) " +
                "VALUES(?,?,?,?,?,?,?)");
//определяем значение параметров
        statement.setObject(1, engine.getType());
        statement.setObject(2, engine.getName());
        statement.setObject(3, engine.getManufacturer());
        statement.setObject(4, engine.getPower());
        statement.setObject(5, engine.getFuelConsumption());
        if (engine instanceof JetEngine) {
            statement.setObject(7, ((JetEngine)engine).getTraction());
            statement.setObject(6, null);
        }
        if (engine instanceof ICEEngine) {
            statement.setObject(6, ((ICEEngine)engine).getCylindersCount());
            statement.setObject(7, null);
        }
        statement.execute();
    }

    public static void UpdateControl(Engine engine) throws SQLException {
//выполняем запрос
        PreparedStatement statement = connection.prepareStatement("" +
                "UPDATE Engines SET `type` = ?,`name` = ?,`manufacturer` = ?,`power` = ?,`fuelConsumption` = ?, `cylindersCount` = ?, `traction` = ? " +
                "WHERE id = ?");
//определяем значение параметров
        statement.setObject(1, engine.getType());
        statement.setObject(2, engine.getName());
        statement.setObject(3, engine.getManufacturer());
        statement.setObject(4, engine.getPower());
        statement.setObject(5, engine.getFuelConsumption());
        if (engine instanceof JetEngine) {
            statement.setObject(7, ((JetEngine)engine).getTraction());
            statement.setObject(6, null);
        }
        if (engine instanceof ICEEngine) {
            statement.setObject(6, ((ICEEngine)engine).getCylindersCount());
            statement.setObject(7, null);
        }
        statement.setObject(8, engine.getId());
        statement.execute();
    }

    //метод для чтения таблицы БД
    public static ArrayList<Engine> read() throws SQLException {
        ArrayList<Engine> list = new ArrayList<>();
//создаем объект statement
        statement = connection.createStatement();
//выполняем запрос
        resultSet = statement.executeQuery("SELECT * FROM Engines");
// пока есть что выбирать, выполняем
        while (resultSet.next())
        {
            int id = resultSet.getInt("id");
            String type = resultSet.getString("type");
            String name = resultSet.getString("name");
            String manufacturer = resultSet.getString("manufacturer");
            Double power = resultSet.getDouble("power");
            Double fuelConsumption = resultSet.getDouble("fuelConsumption");
            Double traction = resultSet.getDouble("traction");
            int cylindersCount = resultSet.getInt("cylindersCount");

            Engine ins = null;
            if ("ДВС".equals(type))
            {
                ins = new ICEEngine(id, name, manufacturer, power, fuelConsumption, cylindersCount);
            }

            if ("Турбореактивный двигатель".equals(type)) {
                ins = new JetEngine(id, name, manufacturer,power, fuelConsumption, traction);
            }
            /*Engine ins = null;
            switch (ins.getType()){
                case "Турбореактивный двигатель" :
                    ins = new ICEEngine(id, name, manufacturer,power, fuelConsumption, cylindersCount);

                case "ДВС" :
                    ins = new JetEngine(id, name, manufacturer,power, fuelConsumption, traction);
            }*/
            list.add(ins);
        }

//возвращаем список
        return list;
    }

    //метод для удаления записи из таблицы БД
    public static void deleteControl(int id) throws SQLException {
        PreparedStatement statement = connection.prepareStatement("DELETE FROM Engines WHERE id = ? ");
//определяем значение параметра
        statement.setObject(1, id);
// Выполняем запрос
        statement.execute();

    }
}
