package GUI;

import javax.swing.*;
import java.awt.*;

public class MenuMain extends JMenuBar {
    // меню таблица
    private final JMenu fileMenu = new JMenu("Редактирование");

    // меню добавления
    private final JMenu addMenu = new JMenu("Добавить");
    private final JMenuItem addIceItem = new JMenuItem("ДВС");
    private final JMenuItem addJetItem = new JMenuItem("Реактивный");

    //изменить
    private final JMenuItem edit = new JMenuItem("Изменить");
    //удалить
    private final JMenuItem delete = new JMenuItem("Удалить");

    //кнопка закрыть
    private final JMenuItem exitItem = new JMenuItem("Выход");

    private final JMenu searchItem = new JMenu("Поиск");

    private final JLabel searchBoxLabel = new JLabel("Поиск: ");
    private final JTextField searchBox = new JTextField();
    //справка
    private final JMenu helpMenu = new JMenu("Справка");
    private final JMenuItem taskItem = new JMenuItem("Задание");
    private final JMenuItem instructionItem = new JMenuItem("Инструкция");
    public MenuMain(){
        initComponents();
        setLayout(new FlowLayout(FlowLayout.LEFT));

    }

    // добавление компонентов
    public void initComponents(){
        addMenu.add(addIceItem);
        addMenu.add(addJetItem);

        fileMenu.add(addMenu);
        fileMenu.add(edit);
        fileMenu.add(delete);
        fileMenu.add(exitItem);

        helpMenu.add(taskItem);
        helpMenu.add(instructionItem);



        add(fileMenu);
        add(searchItem);
        add(helpMenu);
    }

    // создание системного меню и панели инструментов
    public JMenu getSearchItem() {
        return searchItem;
    }

    public JMenuItem getAddIceItem() {
        return addIceItem;
    }

    public JMenuItem getAddJetItem() {
        return addJetItem;
    }

    public JMenuItem getEdit() { return edit; }

    public JMenuItem getDelete() { return delete; }

    public JMenuItem getExitItem() {
        return exitItem;
    }

    public JTextField getSearchBox() {
        return searchBox;
    }

    public JMenuItem getTaskItem() {
        return taskItem;
    }
    public JMenuItem getInstructionItem() {
        return instructionItem;
    }
}
