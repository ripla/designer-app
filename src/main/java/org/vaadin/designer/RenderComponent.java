package org.vaadin.designer;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import com.vaadin.server.VaadinServlet;
import com.vaadin.spring.annotation.SpringComponent;
import com.vaadin.spring.annotation.UIScope;
import com.vaadin.ui.Upload;
import com.vaadin.ui.declarative.Design;

@SpringComponent
@UIScope
public class RenderComponent extends RenderDesign {

    private File currentDesignFile;
    private File currentThemeFile;

    public RenderComponent() {
        this.designUpload
                .setReceiver((Upload.Receiver) (filename, mimeType) -> {
                    try {
                        currentDesignFile = File.createTempFile(filename, null);
                        return new FileOutputStream(currentDesignFile);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return null;
                    }
                });

        this.designUpload
                .addSucceededListener((Upload.SucceededListener) event -> {
                    getDesignerAppServlet()
                            .setCurrentSessionDesignFile(currentDesignFile);
                    readDesign(currentDesignFile);
                });

        this.themeUpload.setReceiver((Upload.Receiver) (filename, mimeType) -> {
            try {
                currentThemeFile = File.createTempFile(filename, null);
                return new FileOutputStream(currentThemeFile);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        });

        this.themeUpload
                .addSucceededListener((Upload.SucceededListener) event -> {
                    getDesignerAppServlet()
                            .setCurrentSessionThemeFile(currentThemeFile);
                    currentTheme.setValue(event.getFilename());
                });

        if (getDesignerAppServlet().getCurrentSessionDesignFile() != null) {
            readDesign(getDesignerAppServlet().getCurrentSessionDesignFile());
        }

        if (getDesignerAppServlet().getCurrentSessionThemeFile() != null) {
            this.currentTheme.setValue(
                    getDesignerAppServlet().getCurrentSessionThemeFile()
                            .getName());
        }
    }

    private void readDesign(File designFile) {
        try {
            content.setContent(Design.read(new FileInputStream(designFile)));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        currentDesign.setValue(designFile.getName());
    }

    private DesignerAppServlet getDesignerAppServlet() {
        return (DesignerAppServlet) VaadinServlet.getCurrent();
    }
}
