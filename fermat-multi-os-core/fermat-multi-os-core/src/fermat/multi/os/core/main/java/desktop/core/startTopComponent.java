package fermat.multi.os.core.main.java.desktop.core;

import fermat.multi.os.core.main.java.Controller.CumpleEstadisticasController;
import fermat.multi.os.core.main.java.Model.Person;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URL;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javax.swing.JOptionPane;
import org.netbeans.api.settings.ConvertAsProperties;
import org.openide.awt.ActionID;
import org.openide.awt.ActionReference;
import org.openide.util.Exceptions;
import org.openide.windows.TopComponent;
import org.openide.util.NbBundle.Messages;
/**
 * Top component which displays something.
 */
@ConvertAsProperties(
        dtd = "-//main.java.desktop.core//start//EN",
        autostore = false
)
@TopComponent.Description(
        preferredID = "startTopComponent",
        //iconBase="SET/PATH/TO/ICON/HERE", 
        persistenceType = TopComponent.PERSISTENCE_ALWAYS
)
@TopComponent.Registration(mode = "output", openAtStartup = false)
@ActionID(category = "Window", id = "main.java.desktop.core.startTopComponent")
@ActionReference(path = "Menu/Window" /*, position = 333 */)
@TopComponent.OpenActionRegistration(
        displayName = "#CTL_startAction",
        preferredID = "startTopComponent"
)
@Messages({
    "CTL_startAction=start",
    "CTL_startTopComponent=start Window",
    "HINT_startTopComponent=This is a start window"
})
public final class startTopComponent extends TopComponent {

    private CumpleEstadisticasController birthController;
    private JFXPanel fxPanel;
    
    public startTopComponent() {
        initComponents();
        setName(Bundle.CTL_startTopComponent());
        setToolTipText(Bundle.HINT_startTopComponent());
        setLayout(new BorderLayout());
        
        final com.bitdubai.fermat_core.Platform platform = new com.bitdubai.fermat_core.Platform();
        
        /*try {
            platform.start();
        } catch (CantStartPlatformException ex) {
            Exceptions.printStackTrace(ex);
        } catch (CantReportCriticalStartingProblem ex) {
            Exceptions.printStackTrace(ex);
        }*/
        
        setLayout(new BorderLayout());
        Button btn = new Button("Tocame!");
        btn.addActionListener(new ActionListener(){

            @Override
            public void actionPerformed(ActionEvent e) {
                    JOptionPane.showMessageDialog(null,platform.getCryptoNetworkLayer().toString());
            }
            
        });
        add(btn,BorderLayout.SOUTH);
        
        init();
        
    }

    public void init() {
        fxPanel = new JFXPanel();
        add(fxPanel, BorderLayout.CENTER);
        Platform.setImplicitExit(false);
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                createScene();
            }
        });
    }

    private void createScene() {
        try {
            URL location = getClass().getResource("/View/FXML.fxml");
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(location);
            fxmlLoader.setBuilderFactory(new JavaFXBuilderFactory());
            Parent root = (Parent) fxmlLoader.load(location.openStream());
            Scene scene = new Scene(root);
            fxPanel.setScene(scene);
            birthController = (CumpleEstadisticasController) fxmlLoader.getController();
            List<Person> lstPerson = new ArrayList<Person>();
            lstPerson.add(new Person("matias","furszyfer"));
            lstPerson.add(new Person("juan","perez"));
            lstPerson.add(new Person("ernesto","santos"));
            Person p = new Person("ernesto","eustacio");
            p.setBirthday(LocalDate.MIN);
            lstPerson.add(p);
            p = new Person("pepe","carlos");
            p.setBirthday(LocalDate.MAX);
            lstPerson.add(p);
            p = new Person("juan","eustacio");
            p.setBirthday(LocalDate.now());
            lstPerson.add(p);
            p = new Person("luis","gel");
            p.setBirthday(LocalDate.now());
            lstPerson.add(p);
            p = new Person("ferna","gat");
            p.setBirthday(LocalDate.of(2015,7,4));
            lstPerson.add(p);
            p = new Person("herrna","gat");
            p.setBirthday(LocalDate.of(2015,5,4));
            lstPerson.add(p);
            p = new Person("ferna","gat");
            p.setBirthday(LocalDate.of(2015,4,4));
            p = new Person("carna","gat");
            p.setBirthday(LocalDate.of(2015,5,4));
            lstPerson.add(p);
            p = new Person("forna","gat");
            p.setBirthday(LocalDate.of(2015,8,4));
            p = new Person("rana","gat");
            p.setBirthday(LocalDate.of(2015,8,4));
            lstPerson.add(p);
            p = new Person("gef","gat");
            p.setBirthday(LocalDate.of(2015,8,4));
            lstPerson.add(p);
            p = new Person("arrna","gat");
            p.setBirthday(LocalDate.of(2015,9,4));
            lstPerson.add(p);
            p = new Person("fe","gat");
            p.setBirthday(LocalDate.of(2015,10,4));
            
            birthController.setPersonData(lstPerson);
        } catch (IOException ex) {
            Exceptions.printStackTrace(ex);
        }
    }
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 400, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 300, Short.MAX_VALUE)
        );
    }// </editor-fold>//GEN-END:initComponents

    // Variables declaration - do not modify//GEN-BEGIN:variables
    // End of variables declaration//GEN-END:variables
    @Override
    public void componentOpened() {
        // TODO add custom code on component opening
    }

    @Override
    public void componentClosed() {
        // TODO add custom code on component closing
    }

    void writeProperties(java.util.Properties p) {
        // better to version settings since initial version as advocated at
        // http://wiki.apidesign.org/wiki/PropertyFiles
        p.setProperty("version", "1.0");
        // TODO store your settings
    }

    void readProperties(java.util.Properties p) {
        String version = p.getProperty("version");
        // TODO read your settings according to their version
    }
}
