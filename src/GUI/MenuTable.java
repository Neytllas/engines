package GUI;

import javax.swing.*;

// класс контекстного меню таблицы
public class MenuTable extends JPopupMenu {

    // меню добавить
    private final JMenu addMenu = new JMenu("Добавить");
    private final JMenuItem addIceItem = new JMenuItem("ДВС");
    private final JMenuItem addJetItem = new JMenuItem("Реактивный");
    // пункт удалить
    private JMenuItem removeItem = new JMenuItem("Удалить");

    // выпадающий список для добавления определенного типа двигателя
    public MenuTable(){
        addMenu.add(addIceItem);
        addMenu.add(addJetItem);

        add(addMenu);
        add(removeItem);
    }

    // создание панели инструментов
    public JMenuItem getAddIceItem() {
        return addIceItem;
    }

    public JMenuItem getAddJetItem() {
        return addJetItem;
    }

    public JMenuItem getRemoveItem() {
        return removeItem;
    }

}
