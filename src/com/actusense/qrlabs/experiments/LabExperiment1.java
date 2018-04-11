/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.actusense.qrlabs.experiments;

import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.util.MathUtil;
import java.util.List;

/**
 *
 * @author nathan
 */
public class LabExperiment1 extends LabExperiment {
    public LabExperiment1() {
        // questions labels used for generating the UI textfields and labels
        // the anwers are their for testiong diuring developement
        questions = "A.1:120:y:g, "
                + "A.2:155:y:g, "
                + "A.3:115:y:g, "
                + "B.4:12:y:cm, "
                + "B.5a:14:y:cm,"
                + "B.5b:1.9:y:cm, "
                + "C.6:40:n:cc, "
                + "C.7:37:n:mL, "
                + "D.8:76:y:C, "
                + "D.9:3:y:C,"
                + "D.10:23:y:C, "
                + "D.11:0.12:n:kg, "
                + "D.12:124:n:mm, "
                + "D.13:0.037:n:L,"
                + "D.14:349:n:K, "
                + "D.15:37.2:n:F, "
                + "D.16:0.01:n:gl";
    }
    
    @Override
    public void processInputs(TextField[] inputFields, Label[] inputLabels) throws Exception {
        // calculator the percent difference for a.1, a.2,a.3, b.4
        List<String> questionLabels = getQuestionLabels();
        int i;
        int error;
        boolean valid;
        String answer;
        
        for(i = 0; i < 4; i++) {
            answer = getQuestionAnswer(i);
            double aN = getValue(inputFields[i].getText());
            double aNAns = getValue(answer);
            error = (int)((Math.abs(aN - aNAns)/aNAns)*100);
            
            // display the errors
            setLabelError(inputLabels[i], questionLabels.get(i), error);
        }
        
        // comapre the actual test and calculate test tube value and compare to users
        double b5a = getValue(inputFields[4].getText());
        double b5b = getValue(inputFields[5].getText());
        double b6 = getValue(inputFields[6].getText()); // calculated
        double b7 = getValue(inputFields[7].getText()); // actual
        
        // calculate B6 and compare to student value
        i = 6;
        double b6C = Math.PI*MathUtil.pow(b5b/2.0, 2)*b5a;
        error = (int)((Math.abs(b6C - b6)/b6C)*100);
        valid = error <= 5;
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, b6C);
        
        
        // display percent error between calculated and actual
        i = 7;
        error = (int)((Math.abs(b6 - b7)/b7)*100);
        setLabelError(inputLabels[i], questionLabels.get(i), error);
        
        // compare hot bath, cold batch and room temperature
        for(i = 8; i < 11; i++) {
            valid = false;
            answer = getQuestionAnswer(i);
            double dN = getValue(inputFields[i].getText());
            double dNAns = getValue(answer);
            
            if(i == 8 && dN >= dNAns) valid = true; // greater the default 50
            if(i == 9 && dN <= dNAns) valid = true; // less that the 5 degress
            if(i == 10 && Math.abs(dN - dNAns) < 10) valid = true; // within 10 degrees
            
            setLabelValid(inputLabels[i], questionLabels.get(i), valid, dNAns);
        }
        
        // convert grams to kg
        i= 11;
        double d11 = getValue(inputFields[i].getText());
        double a1 = getValue(inputFields[0].getText());
        double d11Ans = a1/1000.0; //convert g to kg
        valid = ((Math.abs(d11 - d11Ans)/d11Ans)*100) <= 2; // 2% error allowed
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, d11Ans);
        
        // convert cm to mm
        i= 12;
        double d12 = getValue(inputFields[i].getText());
        double b4 = getValue(inputFields[3].getText());
        double d12Ans = b4*10; //convert cm to mm
        valid = ((Math.abs(d12 - d12Ans)/d12Ans)*100) <= 2; // 2% error allowed
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, d12Ans);
        
        // convert cm to mm
        i= 13;
        double d13 = getValue(inputFields[i].getText());
        double c7 = getValue(inputFields[7].getText());
        double d13Ans = c7/1000.0; //convert mm to L
        valid  = ((Math.abs(d13 - d13Ans)/d12Ans)*100) <= 2; // 2% error allowed
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, d13Ans);
        
        // convert deg C to K
        i= 14;
        double d14 = getValue(inputFields[i].getText());
        double d8 = getValue(inputFields[8].getText());
        double d14Ans = d8 + 273; //convert deg C to K
        valid = ((Math.abs(d14 - d14Ans)/d14Ans)*100) <= 2; // 2% error allowed
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, d14Ans);
        
        // convert deg C to F
        i= 15;
        double d15 = getValue(inputFields[i].getText());
        double d9 = getValue(inputFields[9].getText());
        double d15Ans = d9*1.8 + 32; //convert deg C to F
        valid = ((Math.abs(d15 - d15Ans)/d15Ans)*100) <= 2; // 2% error allowed
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, d15Ans);
        
        i= 16;
        double d16 = getValue(inputFields[i].getText());
        double d16Ans = b7*0.00026417; //convert mL to gallons
        valid = ((Math.abs(d16 - d16Ans)/d16Ans)*100) <= 2; // 2% error allowed
        setLabelValid(inputLabels[i], questionLabels.get(i), valid, d16Ans);
    }  
}
