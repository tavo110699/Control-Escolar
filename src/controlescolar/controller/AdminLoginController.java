/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlescolar.controller;

import controlescolar.model.Administrador;
import controlescolar.model.Alumno;
import controlescolar.view.AdminHomeView;
import controlescolar.view.HomeView;
import controlescolar.view.LoginView;
import controlescolar.view.components.Toaster.Toaster;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;

/**
 *
 * @author hugoluna
 */
public class AdminLoginController implements ActionListener {

    private LoginView loginView;
    private Toaster toaster;

    public AdminLoginController(LoginView loginView) {
        this.loginView = loginView;
        this.loginView.setVisible(true);
        this.loginView.textPrincipal.setText("Administrador");
        this.loginView.setTitle("Ingresar como administrador");
        this.loginView.textNum.setText("Usuario");
        this.loginView.textNum.setToolTipText("Usuario");
        this.loginView.btnEntrar.addActionListener(this);
        this.loginView.btnRegistrar.setText("Alumno");
        this.loginView.btnRegistrar.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == loginView.btnEntrar) {
            btnLogin();
        }
        
        if(e.getSource() == loginView.btnRegistrar){
            goToLoginAlumno();
        }
        

    }

    
    
    
    public void btnLogin(){
        String usuario = loginView.numField.getText().toString();
        String password = loginView.passField.getText().toString();
        toaster = new Toaster(this.loginView.mainContainer);
        if (!usuario.isEmpty() && !password.isEmpty()) {

            Administrador admin = new Administrador();
            admin.setUsuario(usuario);
            admin.setPassword(password);
            
            /**
             * Esperar respuesta de la BD
             *
             */
            if (admin.loginProcessAdmin(admin)) {
                toaster.success("¡Genial!", "Has ingresado correctamente");

                savePreferences(String.valueOf(admin.getUsuario()));

                this.loginView.setVisible(false);
                System.out.println("Go To Admin Home");
                new AdminHomeController(new AdminHomeView(), new Administrador().getData(usuario));
                
                
            } else {
                toaster.error("¡Ups ocurrio un error!", "Es posible que los datos no existan en nuestros registros");

            }

        } else {
            toaster.info("¡Ups!.", "Debes llenar los campos para ingresar");

        }
        
        
    }
    
    public void goToLoginAlumno(){
        
        
        this.loginView.setVisible(false);
        new LoginController(new LoginView());
        
    }
    
    
    
    public void savePreferences(String id) {

        Preferences prefs = Preferences.userNodeForPackage(controlescolar.controller.LoginController.class);
        final String PREF_NAME = "id";
        final String PREF_TYPE = "type";
        

        
        prefs.put(PREF_NAME, id);
        prefs.put(PREF_TYPE, "admin");

    }
    
    
}
