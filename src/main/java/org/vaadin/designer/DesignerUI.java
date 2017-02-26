package org.vaadin.designer;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;

import com.vaadin.annotations.Theme;
import com.vaadin.server.VaadinRequest;
import com.vaadin.spring.annotation.SpringUI;
import com.vaadin.ui.UI;
import com.vaadin.ui.declarative.Design;

@SpringUI
@Theme("designerapp")
public class DesignerUI extends UI {

    @Autowired
    ApplicationArguments args;

    @Autowired
    RenderComponent renderComponent;

    @Override
    protected void init(VaadinRequest request) {
        if (args.containsOption("designFile")) {
            setContentFromCommandLineParam();
        } else {
            setContent(renderComponent);
        }
    }

    private void setContentFromCommandLineParam() {
        final String designFile = args.getOptionValues("designFile").get(0);
        try {
            setContent(
                    Design.read(Files.newInputStream(Paths.get(designFile))));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
