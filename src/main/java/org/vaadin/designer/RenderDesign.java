package org.vaadin.designer;

import com.vaadin.annotations.AutoGenerated;
import com.vaadin.annotations.DesignRoot;
import com.vaadin.ui.declarative.Design;
import com.vaadin.ui.VerticalLayout;
import com.vaadin.ui.Upload;
import com.vaadin.ui.Panel;
import com.vaadin.ui.Label;

/**
 * !! DO NOT EDIT THIS FILE !!
 *
 * This class is generated by Vaadin Designer and will be overwritten.
 *
 * Please make a subclass with logic and additional interfaces as needed,
 * e.g class LoginView extends LoginDesign implements View { }
 */
@DesignRoot
@AutoGenerated
@SuppressWarnings("serial")
public class RenderDesign extends VerticalLayout {
    protected Upload designUpload;
    protected Upload themeUpload;
    protected Label currentDesign;
    protected Label currentTheme;
    protected Panel content;

    public RenderDesign() {
        Design.read(this);
    }
}
