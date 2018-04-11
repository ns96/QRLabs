package com.actusense.qrlabs.experiments;

import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.util.StringUtil;
import java.util.ArrayList;
import java.util.List;

/**
 * The base class for all experiments
 *
 * @author nathan
 */
public abstract class LabExperiment {

    protected ArrayList<String> answerKey;
    
    protected String questions;
    
    protected boolean validPin = false;

    /**
     * Process the inputs for lab
     *
     * @param inputFields
     * @param inputLabels
     * @throws java.lang.Exception
     * @throws NumberFormatException
     */
    public abstract void processInputs(TextField[] inputFields, Label[] inputLabels) throws Exception;

    public void setQuestionLabels(String questions) {
        this.questions = questions;
    }
    
    public ArrayList<String> getQuestionLabels() {
        ArrayList<String> al = new ArrayList<>();
        
        List<String> qlist = splitQuestions(0);
        List<String> ulist = splitQuestions(3);
        
        for(int i = 0; i < qlist.size(); i++) {
            String qlabel = qlist.get(i) + " (" + ulist.get(i) + ")";
            al.add(qlabel);
        }
        
        return al;
    }
    
    public ArrayList<String> getQuestionValues() {
        return splitQuestions(1);
    }
    
    public ArrayList<String> getQuestionUnits() {
        return splitQuestions(3);
    }
    
    /**
     * Returns a list of required questions for generating answer key
     * 
     * @return 
     */
    public boolean[] getRequiredQuestions() {
        ArrayList<String> requiredList = splitQuestions(2); 
        boolean[] required = new boolean[requiredList.size()];
        
        for(int i = 0; i < required.length; i++) {
            required[i] = requiredList.get(i).equals("y");
        }
        
        return required;
    }
    
    public ArrayList<String> splitQuestions(int index) {
        ArrayList<String> al = new ArrayList<>();
        for(String q: StringUtil.tokenize(questions, ",")) {
            String value = StringUtil.tokenize(q, ":").get(index).trim();
            al.add(value);
        }
        return al;
    }

    public void setAnswerKey(ArrayList<String> answers) {
        answerKey = answers;
    }

    public String getQuestionAnswer(int index) {
        return StringUtil.tokenize(answerKey.get(index), "=").get(1);
    }

    /**
     * Change label color based on the percent error
     *
     * @param label
     * @param error
     */
    public void setLabelColor(Label label, int error) {
        if (error <= 15) {
            label.getAllStyles().setFgColor(0x9900); // green
        } else if (error <= 50) {
            label.getAllStyles().setFgColor(0xff6600); // orange
        } else {
            label.getAllStyles().setFgColor(0xff0000); // red
        }
    }
    
    public void setLabelError(Label label, String question, int error) {
        String result = "PART-" + question + " / (% ERROR: " + error + ")";
        label.setText(result);
        setLabelColor(label, error);
    }
    
    public void setLabelValid(Label label, String question, boolean valid, double ans) {
        if (valid) {
            String result = "PART-" + question + " / (VALID)";
            if(validPin) {
                result = "PART-" + question + " / (VALID) => " + ans;
            }
            
            label.setText(result);
            setLabelColor(label, 0);
        } else {
            String result = "PART-" + question + " / (INVALID)";
            if(validPin) {
                result = "PART-" + question + " / (INVALID) => " + ans;
            }
            
            label.setText(result);
            setLabelColor(label, 100);
        }
    }
    
    public void setValidPin(boolean validPin) {
        this.validPin = validPin;
    }
    
    public double averageValues(String values) {
        if(values.contains(";")) {
            List<String> valueList = StringUtil.tokenize(values, ";");
            double total = 0;
            double average = 0;
            
            for(String value: valueList) {
                double d = Double.parseDouble(value.trim());
                total += d;
            }
            
            average = total/valueList.size();
            return average;
        } else {
            return Double.parseDouble(values);
        }
    }
    
    public double getValue(String value) {
        return Double.parseDouble(value);
    }
}
