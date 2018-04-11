/**
 * This class contains generated code from the Codename One Designer, DO NOT MODIFY!
 * This class is designed for subclassing that way the code generator can overwrite it
 * anytime without erasing your changes which should exist in a subclass!
 * For details about this file and how it works please read this blog post:
 * http://codenameone.blogspot.com/2010/10/ui-builder-class-how-to-actually-use.html
*/
package generated;

import com.codename1.ui.*;
import com.codename1.ui.util.*;
import com.codename1.ui.plaf.*;
import java.util.Hashtable;
import com.codename1.ui.events.*;

public abstract class StateMachineBase extends UIBuilder {
    private Container aboutToShowThisContainer;
    /**
     * this method should be used to initialize variables instead of
     * the constructor/class scope to avoid race conditions
     */
    /**
    * @deprecated use the version that accepts a resource as an argument instead
    
**/
    protected void initVars() {}

    protected void initVars(Resources res) {}

    public StateMachineBase(Resources res, String resPath, boolean loadTheme) {
        startApp(res, resPath, loadTheme);
    }

    public Container startApp(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("List", com.codename1.ui.List.class);
        UIBuilder.registerCustomComponent("ShareButton", com.codename1.components.ShareButton.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    if(resPath.endsWith(".res")) {
                        res = Resources.open(resPath);
                        System.out.println("Warning: you should construct the state machine without the .res extension to allow theme overlays");
                    } else {
                        res = Resources.openLayered(resPath);
                    }
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        if(res != null) {
            setResourceFilePath(resPath);
            setResourceFile(res);
            initVars(res);
            return showForm(getFirstFormName(), null);
        } else {
            Form f = (Form)createContainer(resPath, getFirstFormName());
            initVars(fetchResourceFile());
            beforeShow(f);
            f.show();
            postShow(f);
            return f;
        }
    }

    protected String getFirstFormName() {
        return "Main";
    }

    public Container createWidget(Resources res, String resPath, boolean loadTheme) {
        initVars();
        UIBuilder.registerCustomComponent("Container", com.codename1.ui.Container.class);
        UIBuilder.registerCustomComponent("Form", com.codename1.ui.Form.class);
        UIBuilder.registerCustomComponent("Button", com.codename1.ui.Button.class);
        UIBuilder.registerCustomComponent("CheckBox", com.codename1.ui.CheckBox.class);
        UIBuilder.registerCustomComponent("Tabs", com.codename1.ui.Tabs.class);
        UIBuilder.registerCustomComponent("List", com.codename1.ui.List.class);
        UIBuilder.registerCustomComponent("ShareButton", com.codename1.components.ShareButton.class);
        if(loadTheme) {
            if(res == null) {
                try {
                    res = Resources.openLayered(resPath);
                } catch(java.io.IOException err) { err.printStackTrace(); }
            }
            initTheme(res);
        }
        return createContainer(resPath, "Main");
    }

    protected void initTheme(Resources res) {
            String[] themes = res.getThemeResourceNames();
            if(themes != null && themes.length > 0) {
                UIManager.getInstance().setThemeProps(res.getTheme(themes[0]));
            }
    }

    public StateMachineBase() {
    }

    public StateMachineBase(String resPath) {
        this(null, resPath, true);
    }

    public StateMachineBase(Resources res) {
        this(res, null, true);
    }

    public StateMachineBase(String resPath, boolean loadTheme) {
        this(null, resPath, loadTheme);
    }

    public StateMachineBase(Resources res, boolean loadTheme) {
        this(res, null, loadTheme);
    }

    public com.codename1.ui.Button findLoadDataButton(Component root) {
        return (com.codename1.ui.Button)findByName("loadDataButton", root);
    }

    public com.codename1.ui.Button findLoadDataButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("loadDataButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("loadDataButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findStoreButton(Component root) {
        return (com.codename1.ui.Button)findByName("storeButton", root);
    }

    public com.codename1.ui.Button findStoreButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("storeButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("storeButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findDeleteDataButton(Component root) {
        return (com.codename1.ui.Button)findByName("deleteDataButton", root);
    }

    public com.codename1.ui.Button findDeleteDataButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("deleteDataButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("deleteDataButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findInputContainer(Component root) {
        return (com.codename1.ui.Container)findByName("inputContainer", root);
    }

    public com.codename1.ui.Container findInputContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("inputContainer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("inputContainer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer1(Component root) {
        return (com.codename1.ui.Container)findByName("Container1", root);
    }

    public com.codename1.ui.Container findContainer1() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container1", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container1", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer2(Component root) {
        return (com.codename1.ui.Container)findByName("Container2", root);
    }

    public com.codename1.ui.Container findContainer2() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container2", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container2", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findDataContainer(Component root) {
        return (com.codename1.ui.Container)findByName("dataContainer", root);
    }

    public com.codename1.ui.Container findDataContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("dataContainer", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("dataContainer", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Container findContainer(Component root) {
        return (com.codename1.ui.Container)findByName("Container", root);
    }

    public com.codename1.ui.Container findContainer() {
        com.codename1.ui.Container cmp = (com.codename1.ui.Container)findByName("Container", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Container)findByName("Container", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.components.ShareButton findShareButton(Component root) {
        return (com.codename1.components.ShareButton)findByName("shareButton", root);
    }

    public com.codename1.components.ShareButton findShareButton() {
        com.codename1.components.ShareButton cmp = (com.codename1.components.ShareButton)findByName("shareButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.components.ShareButton)findByName("shareButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.CheckBox findAllCheckBox(Component root) {
        return (com.codename1.ui.CheckBox)findByName("allCheckBox", root);
    }

    public com.codename1.ui.CheckBox findAllCheckBox() {
        com.codename1.ui.CheckBox cmp = (com.codename1.ui.CheckBox)findByName("allCheckBox", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.CheckBox)findByName("allCheckBox", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Button findScanButton(Component root) {
        return (com.codename1.ui.Button)findByName("scanButton", root);
    }

    public com.codename1.ui.Button findScanButton() {
        com.codename1.ui.Button cmp = (com.codename1.ui.Button)findByName("scanButton", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Button)findByName("scanButton", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.List findChemIList(Component root) {
        return (com.codename1.ui.List)findByName("chemIList", root);
    }

    public com.codename1.ui.List findChemIList() {
        com.codename1.ui.List cmp = (com.codename1.ui.List)findByName("chemIList", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.List)findByName("chemIList", aboutToShowThisContainer);
        }
        return cmp;
    }

    public com.codename1.ui.Tabs findTabs(Component root) {
        return (com.codename1.ui.Tabs)findByName("Tabs", root);
    }

    public com.codename1.ui.Tabs findTabs() {
        com.codename1.ui.Tabs cmp = (com.codename1.ui.Tabs)findByName("Tabs", Display.getInstance().getCurrent());
        if(cmp == null && aboutToShowThisContainer != null) {
            cmp = (com.codename1.ui.Tabs)findByName("Tabs", aboutToShowThisContainer);
        }
        return cmp;
    }

    protected void exitForm(Form f) {
        if("ExperimentForm".equals(f.getName())) {
            exitExperimentForm(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            exitMain(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void exitExperimentForm(Form f) {
    }


    protected void exitMain(Form f) {
    }

    protected void beforeShow(Form f) {
    aboutToShowThisContainer = f;
        if("ExperimentForm".equals(f.getName())) {
            beforeExperimentForm(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            beforeMain(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeExperimentForm(Form f) {
    }


    protected void beforeMain(Form f) {
    }

    protected void beforeShowContainer(Container c) {
        aboutToShowThisContainer = c;
        if("ExperimentForm".equals(c.getName())) {
            beforeContainerExperimentForm(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(c.getName())) {
            beforeContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void beforeContainerExperimentForm(Container c) {
    }


    protected void beforeContainerMain(Container c) {
    }

    protected void postShow(Form f) {
        if("ExperimentForm".equals(f.getName())) {
            postExperimentForm(f);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            postMain(f);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postExperimentForm(Form f) {
    }


    protected void postMain(Form f) {
    }

    protected void postShowContainer(Container c) {
        if("ExperimentForm".equals(c.getName())) {
            postContainerExperimentForm(c);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(c.getName())) {
            postContainerMain(c);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void postContainerExperimentForm(Container c) {
    }


    protected void postContainerMain(Container c) {
    }

    protected void onCreateRoot(String rootName) {
        if("ExperimentForm".equals(rootName)) {
            onCreateExperimentForm();
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(rootName)) {
            onCreateMain();
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void onCreateExperimentForm() {
    }


    protected void onCreateMain() {
    }

    protected Hashtable getFormState(Form f) {
        Hashtable h = super.getFormState(f);
        if("ExperimentForm".equals(f.getName())) {
            getStateExperimentForm(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

        if("Main".equals(f.getName())) {
            getStateMain(f, h);
            aboutToShowThisContainer = null;
            return h;
        }

            return h;
    }


    protected void getStateExperimentForm(Form f, Hashtable h) {
    }


    protected void getStateMain(Form f, Hashtable h) {
    }

    protected void setFormState(Form f, Hashtable state) {
        super.setFormState(f, state);
        if("ExperimentForm".equals(f.getName())) {
            setStateExperimentForm(f, state);
            aboutToShowThisContainer = null;
            return;
        }

        if("Main".equals(f.getName())) {
            setStateMain(f, state);
            aboutToShowThisContainer = null;
            return;
        }

            return;
    }


    protected void setStateExperimentForm(Form f, Hashtable state) {
    }


    protected void setStateMain(Form f, Hashtable state) {
    }

    protected boolean setListModel(List cmp) {
        String listName = cmp.getName();
        if("chemIList".equals(listName)) {
            return initListModelChemIList(cmp);
        }
        return super.setListModel(cmp);
    }

    protected boolean initListModelChemIList(List cmp) {
        return false;
    }

    protected void handleComponentAction(Component c, ActionEvent event) {
        Container rootContainerAncestor = getRootAncestor(c);
        if(rootContainerAncestor == null) return;
        String rootContainerName = rootContainerAncestor.getName();
        Container leadParentContainer = c.getParent().getLeadParent();
        if(leadParentContainer != null && leadParentContainer.getClass() != Container.class) {
            c = c.getParent().getLeadParent();
        }
        if(rootContainerName == null) return;
        if(rootContainerName.equals("ExperimentForm")) {
            if("scanButton".equals(c.getName())) {
                onExperimentForm_ScanButtonAction(c, event);
                return;
            }
            if("storeButton".equals(c.getName())) {
                onExperimentForm_StoreButtonAction(c, event);
                return;
            }
            if("shareButton".equals(c.getName())) {
                onExperimentForm_ShareButtonAction(c, event);
                return;
            }
        }
        if(rootContainerName.equals("Main")) {
            if("chemIList".equals(c.getName())) {
                onMain_ChemIListAction(c, event);
                return;
            }
            if("loadDataButton".equals(c.getName())) {
                onMain_LoadDataButtonAction(c, event);
                return;
            }
            if("deleteDataButton".equals(c.getName())) {
                onMain_DeleteDataButtonAction(c, event);
                return;
            }
            if("allCheckBox".equals(c.getName())) {
                onMain_AllCheckBoxAction(c, event);
                return;
            }
        }
    }

      protected void onExperimentForm_ScanButtonAction(Component c, ActionEvent event) {
      }

      protected void onExperimentForm_StoreButtonAction(Component c, ActionEvent event) {
      }

      protected void onExperimentForm_ShareButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_ChemIListAction(Component c, ActionEvent event) {
      }

      protected void onMain_LoadDataButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_DeleteDataButtonAction(Component c, ActionEvent event) {
      }

      protected void onMain_AllCheckBoxAction(Component c, ActionEvent event) {
      }

}
