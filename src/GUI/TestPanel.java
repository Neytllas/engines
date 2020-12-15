/*
 * Created by JFormDesigner on Wed Oct 07 18:27:08 MSK 2020
 */

package GUI;

import java.awt.*;
import javax.swing.*;

public class TestPanel extends JPanel {
    public TestPanel() {
        initComponents();
    }

    private void initComponents() {

        //создаётся JScrollPane(панель с прокруткой), устанавливаются границы, задаётся табличный менеджер расположения, для него настраивается количество и ширина столбцов/строк
        //JScrollPane на форму в методе add, в который передаются GirdBagConstraints
        scrollPane1 = new JScrollPane();
        table1 = new JTable();

        setBorder ( new javax . swing. border .CompoundBorder ( new javax . swing. border .TitledBorder ( new javax . swing. border .
        EmptyBorder ( 0, 0 ,0 , 0) ,  "JFor\u006dDesi\u0067ner \u0045valu\u0061tion" , javax. swing .border . TitledBorder. CENTER ,javax . swing
        . border .TitledBorder . BOTTOM, new java. awt .Font ( "Dia\u006cog", java .awt . Font. BOLD ,12 ) ,
        java . awt. Color .red ) , getBorder () ) );  addPropertyChangeListener( new java. beans .PropertyChangeListener ( )
        { @Override public void propertyChange (java . beans. PropertyChangeEvent e) { if( "bord\u0065r" .equals ( e. getPropertyName () ) )
        throw new RuntimeException( ) ;} } );

        //GridBagLayout предполагает размещение компонентов в ячейках таблицы заданной размерности
        setLayout(new GridBagLayout());
        ((GridBagLayout)getLayout()).columnWidths = new int[] {0, 0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).rowHeights = new int[] {0, 0, 0, 0, 0, 0, 0, 0};
        ((GridBagLayout)getLayout()).columnWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};
        ((GridBagLayout)getLayout()).rowWeights = new double[] {0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 1.0E-4};

        //делаем видимой для пользователя
        {
            scrollPane1.setViewportView(table1);
        }

        //GirdBagConstraints - значения которые определяют положение панели на форме (отступы и т.д.)
        add(scrollPane1, new GridBagConstraints(1, 2, 2, 2, 0.0, 0.0,
            GridBagConstraints.CENTER, GridBagConstraints.BOTH,
            new Insets(0, 0, 5, 5), 0, 0));
    }


    private JScrollPane scrollPane1;
    private JTable table1;
}
