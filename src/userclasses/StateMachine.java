/**
 * Your application code goes here<br>
 * This file was generated by <a href="https://www.codenameone.com/">Codename
 * One</a> for the purpose of building native mobile applications using Java.
 */
package userclasses;

import com.actusense.qrlabs.QRLabsInputs;
import com.codename1.components.ScaleImageLabel;
import com.codename1.components.ShareButton;
import com.codename1.io.FileSystemStorage;
import com.codename1.io.Log;
import com.codename1.io.Preferences;
import com.codename1.io.Storage;
import com.codename1.io.Util;
import generated.StateMachineBase;
import com.codename1.ui.*;
import com.codename1.ui.events.*;
import com.codename1.ui.html.HTMLUtils;
import com.codename1.ui.plaf.Style;
import com.codename1.ui.util.ImageIO;
import com.codename1.ui.util.Resources;
import com.codename1.util.StringUtil;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;

/**
 *
 * @author Your name here
 */
public class StateMachine extends StateMachineBase {

    private String currentLab;
    private int currentLabIndex;
    private TextField[] inputFields;
    private Label[] inputLabels;
    private QRLabsInputs labInputs;
    private final boolean DEBUG = true;
    private boolean validPin;
    private String group;
    private ArrayList<String> dataList;
    private ArrayList<String> valueList;

    public StateMachine(String resFile) {
        super(resFile);
        // do not modify, write code in initVars and initialize class members there,
        // the constructor might be invoked too late due to race conditions that might occur
    }

    /**
     * this method should be used to initialize variables instead of the
     * constructor/class scope to avoid race conditions
     */
    protected void initVars(Resources res) {
        currentLab = "";
        currentLabIndex = 1;
        inputFields = new TextField[1];
        inputLabels = new Label[1];
        labInputs = new QRLabsInputs();

        // load valid pin
        validPin = Preferences.get("validPin", false);

        // load group
        group = Preferences.get("Group", "G0");

        // load any saved data
        dataList = new ArrayList<>();
        for (String file : Storage.getInstance().listEntries()) {
            if (file.contains("QRLABS_EXP")) {
                dataList.add(file);
            }
        }
    }

    @Override
    protected void beforeMain(Form f) {
        f.getToolbar().addCommandToSideMenu("Set Group", null, (e) -> onMainSetGroup());
        f.getToolbar().addCommandToSideMenu("Enter PIN", null, (e) -> onMainEnterPIN());
        f.getToolbar().addCommandToSideMenu("Scan QR Data", null, (e) -> onMainScanData());
        f.getToolbar().addCommandToSideMenu("About", null, (e) -> onMainAbout());

        // show any stored files
        Container c = findDataContainer(f);
        c.removeAll();
        c.getStyle().setBgColor(0xFFFFFF);
        c.getStyle().setBgTransparency(255);

        for (String file : dataList) {
            System.out.println("CheckBox: " + file);
            CheckBox cb = new CheckBox(file);
            cb.getStyle().setFgColor(0x000000);
            c.add(cb);
        }
    }

    @Override
    protected void beforeExperimentForm(Form f) {
        Container ic = findInputContainer();
        ic.removeAll();

        // get the input fields for this experiment
        labInputs.setCurrentLab(currentLabIndex);
        ArrayList<String> inputList = labInputs.getLabInputs();

        // load values for debuging
        if (valueList == null && DEBUG) {
            valueList = labInputs.getDegugFieldValues();
        }

        boolean[] required = labInputs.getRequiredQuestions();

        inputFields = new TextField[inputList.size()];
        inputLabels = new Label[inputList.size()];

        for (int i = 0; i < inputFields.length; i++) {
            String inputName = inputList.get(i);

            Label label;
            if (validPin && required[i]) {
                label = new Label("*PART-" + inputName);
                label.getStyle().setFgColor(0x0000FF);
            } else {
                label = new Label("PART-" + inputName);
            }

            inputLabels[i] = label;

            TextField tf = new TextField();
            tf.setHint(inputName);
            tf.setConstraint(TextArea.DECIMAL);

            if (valueList != null) {
                tf.setText(valueList.get(i));
            }

            // add label and input field to the UI
            ic.add(label).add(tf);
            inputFields[i] = tf; // store the inputfield for later use
        }

        // if valid pin enanle the store button
        if (validPin) {
            findStoreButton().setText("Create Answer Key");
        } else {
            findStoreButton().setText("Create QR Code");
        }

        f.setTitle(currentLab);

        // set the value list to null now
        valueList = null;
    }

    @Override
    protected void onMain_ChemIListAction(Component c, ActionEvent event) {
        List list = (List) c;
        labInputs.setValidPin(validPin);
        currentLab = list.getSelectedItem().toString();
        currentLabIndex = list.getSelectedIndex() + 1;
        showForm("ExperimentForm", null);
    }

    @Override
    protected void onExperimentForm_ScanButtonAction(Component c, ActionEvent event) {
        try {
            // TODO scan QR code here to get data
            ArrayList<String> answerKey = loadAnswerKey();

            // process the input now
            try {
                labInputs.setAnswerKey(answerKey);
                labInputs.processInputs(inputFields, inputLabels);
                storeExperimentData(inputFields);
            } catch (Exception e) {
                Log.e(e);
                Dialog.show("Input Error", "Invalid or Mising Input", "OK", null);
            }
        } catch (IOException ex) {
            Log.e(ex);
        }
    }

    protected void onMainSetGroup() {
        TextField tf = new TextField();
        tf.setHint("Set Group Name");
        Command cmd = Dialog.show("Set Group", tf, new Command("OK"), new Command("Cancel"));

        if (cmd.getCommandName().equals("OK")) {
            String value = tf.getText().trim();

            //check pin and make sure it divisble by 17 and 13
            if (!value.isEmpty()) {
                Preferences.set("Group", value);
                group = value;
            }
        }
    }

    protected void onMainEnterPIN() {
        TextField tf = new TextField();
        tf.setHint("Enter PIN #");
        tf.setConstraint(TextArea.NUMERIC);
        Command cmd = Dialog.show("Enter PIN", tf, new Command("OK"), new Command("Cancel"));

        if (cmd.getCommandName().equals("OK")) {
            String pin = tf.getText();
            validPin = false;

            //check pin and make sure it divisble by 17 and 13
            if (pin.length() == 4 && !pin.equals("0000")) {
                int n = Integer.parseInt(pin);
                if (n % 13 == 0 && n % 17 == 0) {
                    validPin = true;
                }
            }

            if (!validPin) {
                onMainEnterPIN();
            }
        }

        // store this for the future
        Preferences.set("validPin", validPin);
    }

    protected void onMainAbout() {
        String message = "";

        if (!validPin) {
            message = "QLabs version 0.1\nStudent Mode\n\nBy Nathan Stevens";
        } else {
            message = "QLabs version 0.1\nInstructor Mode\n\nBy Nathan Stevens";
        }

        Dialog.show("About", message, "OK", null);
    }

    @Override
    protected void onExperimentForm_StoreButtonAction(Component c, ActionEvent event) {
        try {
            if (validPin) {
                String answerKey = labInputs.getInputFieldValuesForAnswerKey(inputFields);
                showQRCodeDialog("Answer Key QR", answerKey, true);
            } else {
                String data = labInputs.getInputFieldValuesForQRCode(inputFields);
                showQRCodeDialog("Data QR", data, true);
            }
        } catch (Exception ex) {
            Log.e(ex);
        }
    }

    private void showQRCodeDialog(String title, String data, boolean showShare) {
        //String answerKeyEncoded = Util.xorEncode(data);
        System.out.println(data);
        String dataEncoded = HTMLUtils.encodeString(data);
        String url = "http://qrickit.com/api/qr.php?d=" + dataEncoded + "&t=j";
        Long time = new Date().getTime();
        String filename = "labQR_" + time + ".jpg";

        int size = Display.getInstance().getDisplayWidth() - 20;
        EncodedImage placeholder = EncodedImage.createFromImage(Image.createImage(size, size, 0xFFFFFF), true);
        URLImage background = URLImage.createToStorage(placeholder, filename, url);
        background.fetch();

        ScaleImageLabel label = new ScaleImageLabel(background);
        label.setBackgroundType(Style.BACKGROUND_IMAGE_SCALED_FILL);
        Container ct = new Container();
        ct.add(label);

        if (showShare) {
            // store the image to the file system now so we can share iy
            filename = FileSystemStorage.getInstance().getAppHomePath() + filename;
            try (OutputStream os = FileSystemStorage.getInstance().openOutputStream(filename)) {
                ImageIO.getImageIO().save(background, os, ImageIO.FORMAT_JPEG, 1);
            } catch (IOException err) {
                Log.e(err);
            }

            ShareButton sb = new ShareButton();
            sb.setText("Share");
            sb.setImageToShare(filename, "image/jpg");
            ct.add(sb);
        }
        // display the dialog containing QR code
        Dialog.show(title, ct, new Command("OK"));
    }

    private ArrayList<String> loadAnswerKey() throws IOException {
        String filename = "exp" + currentLabIndex + ".txt";
        InputStream inputStream = fetchResourceFile().getData(filename);
        String text = Util.readToString(inputStream);
        ArrayList<String> answerKey = (ArrayList<String>) StringUtil.tokenize(text, "\n");
        
        return answerKey;
    }

    /**
     * Store the data in the experiment input
     *
     * @param inputFields
     */
    private void storeExperimentData(TextField[] inputFields) {
        try {
            String[] data = labInputs.getInputFieldValuesForStorage(inputFields);
            OutputStream os = Storage.getInstance().createOutputStream(data[0]);
            os.write(data[1].getBytes("UTF-8"));
            dataList.add(data[0]);

            // store this is the share button
            String text = labInputs.getTitleFromFile(data[0]) + "\n\n" + data[1];
            ShareButton sb = findShareButton();
            sb.setTextToShare(text);
        } catch (Exception ex) {
            Log.e(ex);
        }
    }

    @Override
    protected void onMain_DeleteDataButtonAction(Component c, ActionEvent event) {
        CheckBox allCB = findAllCheckBox();
        ArrayList<String> deleteList = new ArrayList<>();
        Container cnt = findDataContainer();

        if (allCB.isSelected()) {
            for (String file : dataList) {
                deleteList.add(file);
            }
        } else {
            int size = cnt.getComponentCount();
            for (int i = 0; i < size; i++) {
                CheckBox cb = (CheckBox) cnt.getComponentAt(i);
                if (cb.isSelected()) {
                    deleteList.add(cb.getText());
                }
            }
        }

        // now delete the file from file storage and the dataList
        for (String file : deleteList) {
            Storage.getInstance().deleteStorageFile(file);
            dataList.remove(file);
        }

        // update the UI now
        cnt.removeAll();
        for (String file : dataList) {
            CheckBox cb = new CheckBox(file);
            cb.getStyle().setFgColor(0x000000);
            cnt.add(cb);
        }

        cnt.revalidate(); // need to call tis so UI is updated
    }

    @Override
    protected void onMain_LoadDataButtonAction(Component c, ActionEvent event) {
        Container cnt = findDataContainer();
        String file = "";

        // find the text file to open. it will only open the first selected one
        int size = cnt.getComponentCount();
        for (int i = 0; i < size; i++) {
            CheckBox cb = (CheckBox) cnt.getComponentAt(i);
            if (cb.isSelected()) {
                file = cb.getText();
                break;
            }
        }

        // load the data from the file now
        try {
            InputStream is = Storage.getInstance().createInputStream(file);
            String data = Util.readToString(is, "UTF-8");
            loadData(file, data);
        } catch (IOException err) {
            Dialog.show(file, "Error Loading Data ...", "OK", null);
            Log.e(err);
        }
    }

    private void loadData(String file, String data) {
        valueList = labInputs.getValuesFromData(data);
        labInputs.setValidPin(validPin);
        currentLab = labInputs.getTitleFromFile(file);
        currentLabIndex = labInputs.getLabIndexFromFile(file);
        showForm("ExperimentForm", null);
    }

    /**
     * Scan data contained in QR code
     *
     * @return
     */
    private void onMainScanData() {
        /**
         * Container cnt = new Container(); cnt.setLayout(new
         * BoxLayout(BoxLayout.Y_AXIS));
         *
         * TextField tf = new TextField(); tf.setHint("Enter Group");
         * cnt.add(tf);
         *
         * CheckBox cb = new CheckBox("Load Data Now"); //cb.setSelected(true);
         * cnt.add(cb); Command cmd = Dialog.show("Enter Group", cnt, new
         * Command("OK"), new Command("Cancel"));
         *
         * if (cmd.getCommandName().equals("OK")) {
         */
        try {
            //***TODO can QR code here***. For now 

            String filename = "exp1_data.txt";
            InputStream inputStream = fetchResourceFile().getData(filename);
            String text = Util.readToString(inputStream);
            String[] data = labInputs.getValuesFromQRCode(text, null);

            // store data to data list and store it
            OutputStream os = Storage.getInstance().createOutputStream(data[0]);
            os.write(data[1].getBytes("UTF-8"));
            dataList.add(data[0]);
            reloadForm();
        } catch (Exception ex) {
            Log.e(ex);
        }
    }
}
