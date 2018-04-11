/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.actusense.qrlabs.experiments;

import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.util.MathUtil;
import com.codename1.util.StringUtil;
import java.util.List;

/**
 *
 * @author nathan
 */
public class LabExperiment6 extends LabExperiment {
    public LabExperiment6() {
        // questions labels used for generating the UI textfields and labels
        // the anwers are their for testiong diuring developement
        questions = "A.1:50.1; 49.2:n:g,"
                + "A.2:52.5; 52.8:n:g,"
                + "A.3:51.2; 51.0:n:g,"
                + "A.4:2.4; 3.6:n:g,"
                + "A.5:1.3; 1.8:n:g,"
                + "A.6:54.2; 50:n:%,"
                + "A.7:50:n:%,"
                + "A.8:45.576:n:%, "
                + "A.9:9.8:n:%,"
                + "B.UNK:A:y:%,"
                + "B.10:50.1; 49.2:n:g,"
                + "B.11:52.5; 52.8:n:g,"
                + "B.12:51.2; 51.0:n:g,"
                + "B.13:2.4; 3.6:n:g,"
                + "B.14:1.3; 1.8:n:g,"
                + "B.15:54.2; 50:n:%";
    }
    
    @Override
    public void processInputs(TextField[] inputFields, Label[] inputLabels) throws Exception {
        List<String> questionLabels = getQuestionLabels();
        int i = 0;
        int error;
        boolean valid;
        
        // comapre the actual test and calculate test tube value and compare to users
        double a1 = averageValues(inputFields[i++].getText()); 
        double a2 = averageValues(inputFields[i++].getText());
        double a3 = averageValues(inputFields[i++].getText());
        double a4 = averageValues(inputFields[i].getText());
        double a4C = a2 - a1;
        error = (int)((Math.abs(a4C - a4)/a4C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, a4C);
        
        i++;
        double a5 = averageValues(inputFields[i].getText());
        double a5C = a2 - a3;
        error = (int)((Math.abs(a5C - a5)/a5C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, a5C);
        
        i++;
        double a6 = averageValues(inputFields[i].getText());
        double a6C = (a5C/a4C)*100;
        error = (int)((Math.abs(a6C - a6)/a6C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, a6C);
        
        i++;
        double a7 = averageValues(inputFields[i].getText());
        double a7C = a6C;
        error = (int)((Math.abs(a7C - a7)/a7C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, a7C);
        
        i++;
        double a8 = getValue(inputFields[i].getText());
        double a8C = getValue(getQuestionAnswer(i));
        error = (int)((Math.abs(a8C - a8)/a8C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, a8C);
        
        i++;
        double a9 = getValue(inputFields[i].getText());
        double a9C = (Math.abs(a8C - a7C)/a8C)*100;
        error = (int)((Math.abs(a9C - a9)/a9C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, a9C);
        
        // calculate the percent for the unknown
        i++;
        int unknownIndex = i;
        
        i++;
        double b10 = averageValues(inputFields[i++].getText()); 
        double b11 = averageValues(inputFields[i++].getText());
        double b12 = averageValues(inputFields[i++].getText());
        double b13 = averageValues(inputFields[i].getText());
        double b13C = b11 - b10;
        error = (int)((Math.abs(b13C - b13)/b13C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, b13C);
        
        i++;
        double b14 = averageValues(inputFields[i].getText());
        double b14C = b11 - b12;
        error = (int)((Math.abs(b14C - b14)/b14C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, a5C);
        
        i++;
        double b15 = averageValues(inputFields[i].getText());
        double b15C = (b14C/b13C)*100;
        error = (int)((Math.abs(b15C - b15)/b15C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, b15C);
        
        // check that the results match the unknown
        checkUnknown(inputFields, inputLabels, questionLabels, unknownIndex, b15);
    }

    private void checkUnknown(TextField[] inputFields, Label[] inputLabels, List<String> questionLabels, int index, double b15) {
        String unknown = inputFields[index].getText().trim();
        String s = StringUtil.tokenize(answerKey.get(index), "=").get(1);
        List<String> answers = StringUtil.tokenize(s, ";");
        
        double realValue = 0;
        if(unknown.equalsIgnoreCase("A")) {
            realValue = Double.parseDouble(answers.get(0));
        } else if(unknown.equalsIgnoreCase("B")) {
            realValue = Double.parseDouble(answers.get(1));
        } else if(unknown.equalsIgnoreCase("C")) {
            realValue = Double.parseDouble(answers.get(2));
        }
        
        // calculate the % error and indicate if the choice is good
        int error = (int)((Math.abs(realValue - b15)/realValue)*100);
        boolean valid = error <= 10;
        setLabelValid(inputLabels[index], questionLabels.get(index), valid, realValue);
    }
   
}
