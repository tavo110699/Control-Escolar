/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlescolar.controller;

import controlescolar.model.Alumno;
import controlescolar.view.HomeView;
import controlescolar.view.LoginView;
import controlescolar.view.components.Toaster.Toaster;
import java.awt.Desktop;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.prefs.Preferences;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javax.swing.JColorChooser;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author hugoluna
 */
public class LoginController implements ActionListener {

    private LoginView loginView;
    private Toaster toaster;

    public LoginController(LoginView loginView) {
        this.loginView = loginView;
        this.loginView.btnEntrar.addActionListener(this);
        this.loginView.btnResetPass.addActionListener(this);
        this.loginView.btnActionHelp.addActionListener(this);
        this.loginView.btnRegistrar.addActionListener(this);
        this.loginView.setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == loginView.btnEntrar) {

            actionBtnEntrar();

        } else if (e.getSource() == loginView.btnRegistrar) {
            actionBtnRegister();
        } else if (e.getSource() == loginView.btnResetPass) {
            actionResetPass();
        } else if (e.getSource() == loginView.btnActionHelp) {
            actionHelp();
        } else {
            actionRegistrar();
        }

    }

    public void actionBtnEntrar() {
        String numControl = loginView.numField.getText().toString();
        String password = loginView.passField.getText().toString();
        toaster = new Toaster(this.loginView.mainContainer);
        if (!numControl.isEmpty() && !password.isEmpty()) {

            Alumno alumno = new Alumno();
            alumno.setNumControl(Integer.parseInt(numControl));
            alumno.setPassword(password);
            
            /**
             * Esperar respuesta de la BD
             *
             */
            if (alumno.loginProcess(alumno)) {
                toaster.success("¡Genial!", "Has ingresado correctamente");

                savePreferences(String.valueOf(alumno.getNumControl()));

                this.loginView.setVisible(false);
                new HomeController(new Alumno().getData(alumno.getNumControl()), new HomeView());

            } else {
                toaster.error("¡Ups ocurrio un error!", "Es posible que los datos no existan en nuestros registros");

            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos para ingresar");

        }
    }

    public void actionBtnRegister() {
        this.loginView.setVisible(false);
        new AdminLoginController(new LoginView());
            
        
    }

    public void actionResetPass() {
        

    }

    public void actionHelp() {
        try {
            Desktop.getDesktop().browse(new URI("https://google.com"));
        } catch (Exception ex) {
            Logger.getLogger(LoginController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void actionRegistrar() {

    }

    public void savePreferences(String id) {

        Preferences prefs = Preferences.userNodeForPackage(controlescolar.controller.LoginController.class);
        final String PREF_NAME = "id";
        final String PREF_TYPE = "type";
        

        
        prefs.put(PREF_NAME, id);
        prefs.put(PREF_TYPE, "alumno");

    }
    
    
    
    
    
    

}
