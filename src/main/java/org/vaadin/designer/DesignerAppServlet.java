package org.vaadin.designer;

import java.io.File;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

import com.vaadin.server.Page;
import com.vaadin.spring.server.SpringVaadinServlet;
import com.vaadin.util.CurrentInstance;

@Component("vaadinServlet")
public class DesignerAppServlet extends SpringVaadinServlet {

    private File currentSessionDesignFile;

    private File currentSessionThemeFile;

    public File getCurrentSessionThemeFile() {
        return currentSessionThemeFile;
    }

    public void setCurrentSessionThemeFile(File currentSessionThemeFile) {
        this.currentSessionThemeFile = currentSessionThemeFile;
        Page.getCurrent().reload();
    }

    public File getCurrentSessionDesignFile() {
        return currentSessionDesignFile;
    }

    public void setCurrentSessionDesignFile(File currentSessionDesignFile) {
        this.currentSessionDesignFile = currentSessionDesignFile;
    }

    @Override
    protected void service(HttpServletRequest request,
            HttpServletResponse response) throws ServletException, IOException {
        if (!request.getRequestURI().contains("styles.css") ||
                getCurrentSessionThemeFile() == null) {
            super.service(request, response);
            return;
        }

        // We are going to handle theme resources externally
        getService().setCurrentInstances(null, null);

        // Set cache control
        response.setHeader("Cache-Control",
                "no-store, no-cache, must-revalidate, max-age=0, post-check=0, pre-check=0");
        response.setDateHeader("Expires", -1);
        response.setHeader("Pragma", "no-cache");

        response.setContentType("text/css");

        writeStaticResourceResponse(request, response,
                getCurrentSessionThemeFile().toURI().toURL());
        CurrentInstance.clearAll();
    }
}
