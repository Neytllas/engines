package GUI;

import com.company.Engine;

import javax.swing.*;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

public class TableEngines extends JTable {
    // модель отображения для таблицы
    private DefaultTableModel tableModel;
    //сортировщик строк таблицы, нужен для фильтрации
    private final TableRowSorter<DefaultTableModel> sorter;
    // список двигателей
    private ArrayList<Engine> engines = new ArrayList<>();
    // контекстное меню таблицы
    private MenuTable menuPopup = new MenuTable();

    //бинд объекта для доступа к нему из нутри вложенных классов (ActionListener)
    private TableEngines self = this;

    public Engine getRow(int row)
    {
        return engines.get(row);
    }

    public TableEngines() {
        super();



        // установкка шапки таблицы
        ArrayList<String> tableHeader = new ArrayList<>();
        for (int i = 0; i < Engine.HEADER.length; i++) {
            tableHeader.add(Engine.HEADER[i] + (Engine.DIMENSIONS[i].isEmpty() ? "" : (", " + Engine.DIMENSIONS[i])));
        }

        //инициализация модели
        tableModel = new DefaultTableModel(tableHeader.toArray(), 0){
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        sorter = new TableRowSorter<>(tableModel);

        setRowSorter(sorter);
        // задание модели для таблицы
        setModel(tableModel);

        // установка событий контекстного меню
        menuPopup.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                SwingUtilities.invokeLater(new Runnable() {
                    @Override
                    public void run() {
                        int rowAtPoint = rowAtPoint(SwingUtilities.convertPoint(menuPopup, new Point(0,0), self));
                        if(rowAtPoint > -1){
                            self.setRowSelectionInterval(rowAtPoint, rowAtPoint);
                        }
                    }
                });
            }

            // всплывающие окна

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });

        menuPopup.getRemoveItem().addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                self.deleteRow(self.getSelectedRow());
            }
        });

        setComponentPopupMenu(menuPopup);

    }


    //добавление строк
    public void addRow(Object[] data){
        tableModel.addRow(data);
    }

    public void addRow(Engine data){
        engines.add(data);
        addRow(data.getData());
    }

    public void addRows(Engine[] data){
        for (Engine e: data){
            addRow(e);
        }
    }

    //изменение строки
    public void setRow(int row, Object[] data){
        for (int i = 0; i < tableModel.getColumnCount(); i++) {
            tableModel.setValueAt(data[i], row, i);
        }
    }

    public void setRow(int row, Engine engine){
        engines.set(row, engine);
        setRow(row, engine.getData());
    }

    public int findEngine(String query){
        int index = 0;

        for(Vector<String> o: tableModel.getDataVector()){
            for(String column: o){
                if(column.contains(query))
                    return index;
            }
            index++;
        }

        return -1;
    }

    //удаление строки
    public void deleteRow(int row){
        engines.remove(row);
        tableModel.removeRow(row);
    }

    public void deleteRow(String query){
        deleteRow(findEngine(query));
    }


    public ArrayList<Engine> getEngines() {
        return engines;
    }

    public MenuTable getMenuPopup() {
        return menuPopup;
    }

    public TableRowSorter<DefaultTableModel> getSorter() {
        return sorter;
    }
}
