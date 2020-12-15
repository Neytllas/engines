package com.company.verifiers;

import javax.swing.*;

public class TextVerifier extends InputVerifier {
    @Override
    public boolean verify(JComponent input) {
        String text = ((JTextField) input).getText();

        if(text.length() > 0){
            return true;
        }
        return false;
    }
}
