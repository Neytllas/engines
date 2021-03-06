package com.company.verifiers;

import javax.swing.*;

public class NumberVerifier extends InputVerifier {

    @Override
    public boolean verify(JComponent input) {
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
}
