package GUI;

import javax.swing.*;
import java.util.Random;

    //класс панели содержащей таблицу
public class PanelTable extends JScrollPane {
    private TableEngines tableEngines;
    private Random rnd = new Random();

    // вывод таблицы
    public PanelTable(TableEngines tableEngines) {
        super(tableEngines);
        this.tableEngines = tableEngines;
        //initComponents();
    }

//    //генерация параметров
//    private void initComponents(){
//        tableEngines.addRows(EngineFactory.generate(rnd.nextInt(6) + 40));
//    }

    public TableEngines getTableEngines() {
        return tableEngines;
    }
}
