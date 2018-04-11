package com.actusense.qrlabs;

import com.actusense.qrlabs.experiments.LabExperiment;
import com.actusense.qrlabs.experiments.LabExperiment1;
import com.actusense.qrlabs.experiments.LabExperiment6;
import com.codename1.l10n.Format;
import com.codename1.l10n.SimpleDateFormat;
import com.codename1.ui.Label;
import com.codename1.ui.TextField;
import com.codename1.util.StringUtil;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * Class to store input text for all the experiments
 *
 * @author nathan
 */
public class QRLabsInputs {

    private HashMap<String, ArrayList<String>> labInputsMap = new HashMap<>();
    private HashMap<String, ArrayList<String>> fieldValuesMap = new HashMap<>();
    private ArrayList<String> answerKey;
    private int labIndex = 0;
    private LabExperiment currentLab, labExp1, labExp6;
    private boolean validPin = false;

    public QRLabsInputs() {
        generateLabInputs();
    }

    private void generateLabInputs() {
        labExp1 = new LabExperiment1();
        labInputsMap.put("1", labExp1.getQuestionLabels());
        fieldValuesMap.put("1", labExp1.getQuestionValues()); // used for debugging only
        
        labExp6 = new LabExperiment6();
        labInputsMap.put("6", labExp6.getQuestionLabels());
        fieldValuesMap.put("6", labExp6.getQuestionValues()); // used for debugging only
    }

    public ArrayList<String> getLabInputs() {
        String key = "" + labIndex;
        return labInputsMap.get(key);
    }

    public ArrayList<String> getDegugFieldValues() {
        String key = "" + labIndex;
        return fieldValuesMap.get(key);
    }

    public boolean[] getRequiredQuestions() {
        return currentLab.getRequiredQuestions();
    }

    public void setAnswerKey(ArrayList<String> answers) {
        answerKey = answers;
    }

    public void setCurrentLab(int labIndex) {
        this.labIndex = labIndex;

        if (labIndex == 1) {
            currentLab = labExp1;
        } else if(labIndex == 6) {
            currentLab = labExp6;
        }
    }

    public void processInputs(TextField[] inputFields, Label[] inputLabels) throws Exception {
        currentLab.setValidPin(validPin);
        currentLab.setAnswerKey(answerKey);
        currentLab.processInputs(inputFields, inputLabels);
    }

    public String getInputFieldValuesForAnswerKey(TextField[] inputFields) throws Exception {
        return getInputFieldValues(inputFields, true, false);
    }

    public String[] getInputFieldValuesForStorage(TextField[] inputFields) throws Exception {
        Long time = new Date().getTime();
        String filename = "QRLABS_EXP" + labIndex + "_" + time + ".txt";
        String data = getInputFieldValues(inputFields, false, true);
        String[] sa = {filename, data};
        return sa;
    }
    
    public String getInputFieldValuesForQRCode(TextField[] inputFields) throws Exception {
        String name = "EXP" + labIndex;
        String data = getInputFieldValues(inputFields, false, false);
        return name + "\n" + data;
    }
    
    public String getInputFieldValues(TextField[] inputFields, boolean indicateRequired, boolean useLabel) throws Exception {
        StringBuilder sb = new StringBuilder();
        ArrayList<String> questionList = currentLab.getQuestionLabels();
        boolean[] required = currentLab.getRequiredQuestions();

        for (int i = 0; i < inputFields.length; i++) {
            String value = inputFields[i].getText();
            String question = "" + (i + 1);
            if(useLabel) {
                question = questionList.get(i);
            }

            if (indicateRequired) {
                if (required[i]) {
                    sb.append(question).append("=").append(value).append("\n");
                } else {
                    sb.append(question).append("=").append("?").append("\n");
                }
            } else {
                sb.append(question).append("=").append(value).append("\n");
            }
        }

        return sb.toString().trim();
    }

    public void setValidPin(boolean validPin) {
        this.validPin = validPin;
    }
    
    public String[] getValuesFromQRCode(String text, String group) throws Exception {
        List<String> dataList = StringUtil.tokenize(text, "\n");
        StringBuilder sb = new StringBuilder();
        String filename = null;
        ArrayList<String> questionList = null;
        
        for(int i = 0; i < dataList.size(); i++) {
            if(i == 0) {
                if(group == null || group.isEmpty()) {
                    group = dataList.get(i).trim();
                }
                
                Date now = new Date();
                filename = "QRLABS_" + group + "_" + now.getTime() + ".txt";
                int lab = getLabIndexFromFile(filename);
                setCurrentLab(lab);
                questionList = currentLab.getQuestionLabels();
            } else {
                String value = dataList.get(i).trim();
                if(value.contains("=")) {
                    value = StringUtil.tokenize(value, "=").get(1).trim();
                    String question = questionList.get(i-1);
                    sb.append(question).append("=").append(value).append("\n");
                }
            }
        }
        
        String[] sa = {filename, sb.toString().trim()};
        return sa;
    }
    
    public ArrayList<String> getValuesFromData(String data) {
        ArrayList<String> al = new ArrayList<>();
        for(String q: StringUtil.tokenize(data, "\n")) {
            if(q.contains("=")) {
                String value = StringUtil.tokenize(q, "=").get(1).trim();
                al.add(value);
            }
        }
        return al;
    }
    
    public int getLabIndexFromFile(String file) {
        String exp = StringUtil.tokenize(file, "_").get(1);
        exp = StringUtil.replaceAll(exp, "EXP", "");
        
        return Integer.parseInt(exp);
    }
    
    public String getTitleFromFile(String file){
        String title;
        List<String> parts = StringUtil.tokenize(file, "_");
        int size = parts.size();
        
        String exp = parts.get(1);
        String endText = parts.get(size - 1);
        endText = StringUtil.replaceAll(endText, ".txt", "");
        
        // see if there is group information
        String group = "";
        if(size == 4) {
            group = " ( " + parts.get(2) + " )";
        }
        
        try {
            long time = Long.parseLong(endText);
            Date date = new Date(time);
            Format format = new SimpleDateFormat("MM/dd/yyyy @ HH:mm");
            title = exp +  group + " -- " + format.format(date);
        } catch(NumberFormatException nfe) {
            title = exp + group + " -- " + endText;
        }
        
        return title;
    }
}
