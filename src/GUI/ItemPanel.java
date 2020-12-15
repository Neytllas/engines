package GUI;

import com.company.Engine;
import com.company.ICEEngine;
import com.company.JetEngine;
import com.company.verifiers.IntegerVerifier;
import com.company.verifiers.NumberVerifier;
import com.company.verifiers.TextVerifier;

import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.concurrent.Flow;

//нижняя панель для редактирования и добавления
public class ItemPanel extends JFrame {
    // кнопки добавить и удалить
    private JButton saveButton = new JButton("Добавить");
    private JButton cancelButton = new JButton("Отменить");
    // список кнопок и лейблов для полей
    private ArrayList<JTextField> textFields = new ArrayList<>();
    private ArrayList<JLabel> labels = new ArrayList<>();
    private ArrayList<JLabel> errorLabels = new ArrayList<>();

    private JLabel typeLabel = new JLabel("Изменение");

    private JPanel lowerPanel;

    // флаг режима редактирования
    private boolean isEditMode = true;
    private boolean isSearchMode = false;
    // флаг типа двигателя
    private int engineType = 0;


    public ItemPanel(){
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setSize(570, 260);
        setResizable(false);

        initComponents();
    }

    public boolean isSearchMode() {
        return isSearchMode;
    }

    private void checkAllErrors() {
        for(int i=0;i<errorLabels.size();i++) {
            if(errorLabels.get(i).getText() != "") {
                saveButton.setEnabled(false);
                break;
            }
        }
    }

    //определяет поля для ввода, в зависимости от типа двигателя
    //затем в функции validate проверяет все поля на ошибки
    private void validateType(){
        labels.get(labels.size() - 1).setEnabled(true);
        textFields.get(labels.size() - 1).setEnabled(true);
        labels.get(labels.size() - 2).setEnabled(true);
        textFields.get(labels.size() - 2).setEnabled(true);

        if(engineType == 0){
            labels.get(labels.size() - 1).setEnabled(false);
            textFields.get(labels.size() - 1).setEnabled(false);
        }
        if(engineType == 1){
            labels.get(labels.size() - 2).setEnabled(false);
            textFields.get(labels.size() - 2).setEnabled(false);
        }
        for(int i = 0; i< Engine.HEADER.length; i++) {
            validate(Engine.TYPES[i], textFields.get(i), errorLabels.get(i));
        }
    }

    private JComponent getEmptyComponent(){
        return new JComponent() {
            @Override
            public void updateUI() {
                super.updateUI();
            }
        };
    }

    // инициализация компонентов
    private void initComponents(){
        lowerPanel = new JPanel(new GridLayout(Engine.HEADER.length, 3));
        lowerPanel.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        c.insets = new Insets(1,5,0,0);

        saveButton.setText(isEditMode ? "Сохранить" : "Добавить");
        typeLabel.setText(isEditMode ? "Изменение" : "Дабавление");
        typeLabel.setFont(new Font(typeLabel.getName(), Font.PLAIN, 24));

        //генерация кнопок для всех полей двигателя
        for (int i = 0; i < Engine.HEADER.length; i++) {
            c.gridx = 0;
            c.gridy = i;
            c.anchor = GridBagConstraints.LINE_START;

            JLabel fieldLabel = new JLabel(Engine.HEADER[i] + (Engine.DIMENSIONS[i].isEmpty() ? "" : (", " + Engine.DIMENSIONS[i])));
            labels.add(fieldLabel);
            lowerPanel.add(fieldLabel, c);

            JPanel fieldPanel = new JPanel();
            fieldPanel.setLayout(new BorderLayout());
            JTextField field = new JTextField();
            JLabel errorLabel = new JLabel();
            errorLabel.setForeground(Color.red);

            Engine.Types type = Engine.TYPES[i];
            field.getDocument().addDocumentListener(new DocumentListener() {

                // события на изменения поля
                @Override
                public void insertUpdate(DocumentEvent e) {
                    // валидация поля в зависимости от типа
                    validate(type, field, errorLabel);
                }

                @Override
                public void removeUpdate(DocumentEvent e) {
                    validate(type, field, errorLabel);
                }

                @Override
                public void changedUpdate(DocumentEvent e) {

                }
            });

            textFields.add(field);
            errorLabels.add(errorLabel);

            c.gridx = 1;
            c.anchor = GridBagConstraints.CENTER;

            field.setPreferredSize(new Dimension(180, 20));
            field.setSize(180, 20);
            field.setMinimumSize(new Dimension(180, 20));
            lowerPanel.add(field, c);

            c.gridx = 2;
            c.anchor = GridBagConstraints.LINE_END;
            errorLabel.setPreferredSize(new Dimension(100, 20));
            errorLabel.setSize(100, 20);
            errorLabel.setMinimumSize(new Dimension(100, 20));
            lowerPanel.add(errorLabel, c);
        }

        setLayout(new GridBagLayout());
        c.gridx = 0;
        c.gridy = 0;
        c.anchor = GridBagConstraints.PAGE_START;
        c.gridwidth = 2;
        c.insets = new Insets(10,0,0,0);
        add(typeLabel, c);

        c.gridx = 0;
        c.gridy = 1;
        c.anchor = GridBagConstraints.CENTER;
        c.gridwidth = 1;
        add(lowerPanel, c);

        c.gridx = 0;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_START;
        c.gridwidth = 1;
        c.insets = new Insets(0,0,0,0);
        add(saveButton, c);

        c.gridx = 1;
        c.gridy = 2;
        c.anchor = GridBagConstraints.LAST_LINE_END;
        c.gridwidth = 1;
        add(cancelButton, c);

        for (int i = 0; i<textFields.size();++i)
        {
            final int j = i;
            textFields.get(i).addActionListener(e ->
            {
                textFields.get((j+1)%textFields.size()).grabFocus();
            });
        }

    }

    // функция валидатор
    private void validate(Engine.Types type, JTextField field, JLabel errorLabel){
        boolean validate = false;

        if(type == Engine.Types.Text){
            validate = validateText(field);
        }
        if(type == Engine.Types.Number){
            validate = validateNumber(field);
        }
        if(type == Engine.Types.Integer){
            validate = validateInteger(field);
        }

        if(validate || isSearchMode){
            errorLabel.setText("");
            saveButton.setEnabled(true);
            checkAllErrors();
        }
        if(field.isEnabled() && !validate && !isSearchMode){
            if(field == textFields.get(0)||field==textFields.get(1)){
            errorLabel.setText("Заполните поле");
            }
            else errorLabel.setText("Введите число");
            saveButton.setEnabled(false);
        }

    }

    // валидация - проверка

    // функция валидатор для типа текст
    private boolean validateText(JTextField input){
        String text = ((JTextField) input).getText();

        if(text.length() > 0){
            return true;
        }
        return false;
    }

    // функция валидатор для типа double
    private boolean validateNumber(JTextField input){
        String text = ((JTextField) input).getText();

        if(text.length() < 0)
            return false;

        try{
            double value = Double.parseDouble(text);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    // функция валидатор для типа integer
    private boolean validateInteger(JTextField input){
        String text = ((JTextField) input).getText();

        if(text.length() < 0)
            return false;

        try{
            int value = Integer.parseInt(text);
            return true;
        }catch (NumberFormatException e){
            return false;
        }
    }

    public JButton getSaveButton() {
        return saveButton;
    }

    public JButton getCancelButton() {
        return cancelButton;
    }

    public ArrayList<JTextField> getTextFields() {
        return textFields;
    }

    public boolean isEditMode() {
        return isEditMode;
    }

    public void setMode(boolean editMode) {
        if(editMode == isEditMode)
            if(!isSearchMode)
                return;

        isSearchMode = false;
    }

    // отчистка полей
    private void clearFields(){
        for(JTextField tf : textFields){
            tf.setText("");
        }
    }

    // Устанавливает значение указанного параметра по заданному объекту.
    public void setObject(Engine engine){
        if(engine instanceof ICEEngine)
            setEngineType(0);
        if(engine instanceof JetEngine)
            setEngineType(1);

        String[] data = engine.getData();

        if(getEngineType() == 0){
           textFields.get(0).setText(data[0]);
           textFields.get(1).setText(data[1]);
           textFields.get(2).setText(data[2]);
           textFields.get(3).setText(data[3]);
           textFields.get(4).setText(data[4]);
        }
        if(getEngineType() == 1){
            textFields.get(0).setText(data[0]);
            textFields.get(1).setText(data[1]);
            textFields.get(2).setText(data[2]);
            textFields.get(3).setText(data[3]);
            textFields.get(5).setText(data[5]);
        }
    }

    // изменение выбранного двигателя
    public void editObject(Engine engine){
        clearFields();
        setObject(engine);
        validateType();

        setTitle("Изменение");
        isEditMode = true;
        if(engine.getType() == "ДВС")
            typeLabel.setText("Изменение двигателя ДВС");
        else
            typeLabel.setText("Изменение турбореактивного двигателя");
        saveButton.setText("Изменить");


        setVisible(true);
    }

    // добавление двигателя
    public void addNew(Engine engine){
        clearFields();
        validateType();

        setTitle("Добавление");
        isEditMode = false;
        if(engine.getType() == "ДВС")
            typeLabel.setText("Добавление двигателя ДВС");
        else
            typeLabel.setText("Добавление турбореактивного двигателя");
        saveButton.setText("Добавить");

        setVisible(true);
    }

    public int getEngineType() {
        return engineType;
    }

    public void setEngineType(int engineType) {
        this.engineType = engineType;
        validateType();
    }

    // поиск
    public void setSearchMode() {
        clearFields();

        typeLabel.setText("Поиск");
        isSearchMode = true;

        for(int i=0;i<textFields.size();i++) {
            textFields.get(i).setEnabled(true);
            validate(Engine.TYPES[i], textFields.get(i), errorLabels.get(i));
        }

        saveButton.setText("Найти");

        setVisible(true);
    }
}
