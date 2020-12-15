package GUI;

import com.company.Engine;
import com.company.EngineData;
import com.company.ICEEngine;
import com.company.JetEngine;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MenuEvent;
import javax.swing.event.MenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class FrameMain extends JFrame {
    private EngineData data = new EngineData();

    private final JPanel mainPanel = new JPanel();
    private final PanelTable panelTable = new PanelTable(new TableEngines());
    private final MenuMain menuMain = new MenuMain();
    private final ItemPanel itemPanel = new ItemPanel();

    //Определяет цветовую модель экрана этого инструментария
    Toolkit toolkit = Toolkit.getDefaultToolkit();
    //Получает размер экрана
    Dimension screen = toolkit.getScreenSize();

    private final int WIDTH = 800;
    private final int HEIGHT = 500;

    private FrameMain self = this;

    public FrameMain(){
        super("Двигатели");

        //Устанавливаем размеры и положение
        setBounds(
                screen.width / 2 - WIDTH / 2,
                screen.height / 2 - HEIGHT / 2,
                WIDTH,
                HEIGHT
        );

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        //Устанавливает обработчик для закрытия приложения

        mainPanel.setLayout(new GridLayout(2, 1));

        setJMenuBar(menuMain);

        add(panelTable);

        initEvents();

        //Сделаем окно видимым
        setVisible(true);
    }

    // обработчики событий
    private void initEvents(){
        menuMain.getSearchBox().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String q = menuMain.getSearchBox().getText();

                panelTable.getTableEngines().getSorter().setRowFilter(RowFilter.regexFilter(q));
            }
        });

        //Сообщение о задание
        menuMain.getTaskItem().addActionListener(e -> {
            JOptionPane.showMessageDialog(self, "Задание:\n                Для выполнения задания необходимо разработать графический\n                        пользовательский интерфейс средствами java для работы с классами\n                        реализованными при выполнении либо 3-ей, либо 4-ой лабораторной работы.\n                        Графический пользовательский интерфейс должен предоставлять\n                        возможности работы со всеми возможными функциями программы (ввод и\n                        вывод данных в соответствующей форме, изменение условий выполнения\n                        задания, работа с файлами и т.д.). Состав используемых компонентов\n                        определяется самостоятельно, но для них должны быть предусмотренны\n                        соответствующие планировщики раскладки компонентов в основных контейнерах.\n");
        });

        //Сообщение об инструкции
        menuMain.getInstructionItem().addActionListener(e -> {
            JOptionPane.showMessageDialog(self, "Инструкция:\n                        Импользуйте меню редактирования для добавления новый двигателей.\n                        Нажмите на двигатель в таблице чтобы редактировать его.\n                        Используйте контекстное меню на правой кнопке, чтоюы добавить новый двигатель\n                        или удалить текущий.\n                        Нажмите на кнопку поиск чтобы войти в режим поиска.\n                        Нажмите на кнопку справка, чтобы вывести это сообщение.");
        });

        // реакция на кнопку
        itemPanel.getSaveButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Engine engine = null;

                if(itemPanel.isSearchMode()){
                    int i = 0;
                    ArrayList<RowFilter<Object, Object>> filters = new ArrayList<>();
                    for(JTextField tf: itemPanel.getTextFields())
                    {
                        String text = tf.getText();

                        if(!text.isEmpty())
                        {
                            filters.add(RowFilter.regexFilter(text, i));
                        }
                        ++i;
                    }

                    panelTable.getTableEngines().getSorter().setRowFilter(RowFilter.andFilter(filters));

                    return;
                }


                try{
                    if(itemPanel.getEngineType() == 0){
                        engine = new ICEEngine(
                                itemPanel.getTextFields().get(0).getText(),
                                itemPanel.getTextFields().get(1).getText(),
                                Double.parseDouble(itemPanel.getTextFields().get(2).getText()),
                                Double.parseDouble(itemPanel.getTextFields().get(3).getText()),
                                Integer.parseInt(itemPanel.getTextFields().get(4).getText())
                        );
                    }
                    if(itemPanel.getEngineType() == 1){
                        engine = new JetEngine(
                                itemPanel.getTextFields().get(0).getText(),
                                itemPanel.getTextFields().get(1).getText(),
                                Double.parseDouble(itemPanel.getTextFields().get(2).getText()),
                                Double.parseDouble(itemPanel.getTextFields().get(3).getText()),
                                Double.parseDouble(itemPanel.getTextFields().get(5).getText())
                        );
                    }

                    // обработчик событий
                }catch (NumberFormatException ex){
                    JOptionPane.showMessageDialog(self, "Проверьте введенные данные", "Ошибка", JOptionPane.ERROR_MESSAGE);
                }

                String message = itemPanel.isEditMode() ? "Сохранить изменения?" : String.format("Добавить %s?", engine.getName());

                int result = JOptionPane.showConfirmDialog(self, message, "Сохранить", JOptionPane.YES_NO_OPTION);

                if(result != JOptionPane.YES_OPTION)
                    return;


                if(!itemPanel.isEditMode()){
                    panelTable.getTableEngines().addRow(engine);
                }else{
                    int index = panelTable.getTableEngines().getSelectedRow();
                    panelTable.getTableEngines().setRow(index, engine);
                }
                itemPanel.dispose();
            }
        });

        menuMain.getSearchItem().addMenuListener(new MenuListener() {

            // отображение пунктов меню
            @Override
            public void menuSelected(MenuEvent e) {
                itemPanel.setSearchMode();
            }

            @Override
            public void menuDeselected(MenuEvent e) {

            }

            @Override
            public void menuCanceled(MenuEvent e) {

            }
        });

        // закрытие окна
        itemPanel.getCancelButton().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int result = JOptionPane.showConfirmDialog(self, "Закрыть окно?", "Отмена", JOptionPane.YES_NO_OPTION);
                if(result == JOptionPane.YES_OPTION) {
                    if(itemPanel.isSearchMode())
                        panelTable.getTableEngines().getSorter().setRowFilter(null);
                    itemPanel.dispose();
                }
                else
                    return;
            }
        });


        panelTable.getTableEngines().getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                try{
                    int index = panelTable.getTableEngines().getSelectedRow();

                    Engine engine = panelTable.getTableEngines().getEngines().get(index);
                }catch
                (Exception ex){


                }
            }
        });

        panelTable.getTableEngines().getMenuPopup().getAddIceItem().addActionListener(e -> addIceEngine());
        panelTable.getTableEngines().getMenuPopup().getAddJetItem().addActionListener(e -> addJetEngine());

        menuMain.getAddIceItem().addActionListener(e -> addIceEngine());
        menuMain.getAddJetItem().addActionListener(e -> addJetEngine());

        menuMain.getEdit().addActionListener(e -> editEngine());
        menuMain.getDelete().addActionListener(e -> deleteEngine());

        // выход из программы
        menuMain.getExitItem().addActionListener(e -> {
            int result = JOptionPane.showConfirmDialog(self, "Выйти из программы?", "Выход", JOptionPane.YES_NO_OPTION);

            if(result == JOptionPane.YES_OPTION)
                System.exit(0);
        });

        panelTable.getTableEngines().getSelectionModel().setSelectionInterval(0,0);
    }

    // поля для ДВС
    private void addIceEngine(){
        ICEEngine engine = new ICEEngine("", "" , 0 , 0 , 0);

        itemPanel.addNew(engine);
    }

    // поля для турбореактивного
    private void addJetEngine(){
        JetEngine engine = new JetEngine("", "" , 0 , 0 , 0);

        itemPanel.addNew(engine);
    }

    // изменение
    private void editEngine() {
        int index = panelTable.getTableEngines().getSelectedRow();
        Engine engine = panelTable.getTableEngines().getEngines().get(index);
        itemPanel.editObject(engine);
    }

    // удаление
    private void deleteEngine() {
        int index = panelTable.getTableEngines().getSelectedRow();
        Engine engine = panelTable.getTableEngines().getEngines().get(index);

        int result = JOptionPane.showConfirmDialog(self, String.format("Удалить %s?", engine.getName()), "Удаление", JOptionPane.YES_NO_OPTION);
        if( result != JOptionPane.YES_OPTION)
            return;

        panelTable.getTableEngines().deleteRow(index);
    }

}
