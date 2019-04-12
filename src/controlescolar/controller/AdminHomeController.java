/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlescolar.controller;

import controlescolar.model.Administrador;
import controlescolar.model.Alumno;
import controlescolar.view.AdminHomeView;
import controlescolar.view.DatosAlumno;
import controlescolar.view.ListaAlumnos;
import controlescolar.view.LoginView;
import controlescolar.view.RegistroAdmin;
import controlescolar.view.RegistroAlumno;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.prefs.Preferences;
import javax.swing.JOptionPane;

/**
 *
 * @author gustavo
 */
public class AdminHomeController implements ActionListener {
    
    private AdminHomeView adminHomeView;
    private Administrador administrador;
    private DatosAlumno datosAlumno;
    private RegistroAlumno registroAlumno;
    private RegistroAdmin registroAdmin;
    private ListaAlumnos listaAlumnos;

    public AdminHomeController(AdminHomeView adminHomeView, Administrador administrador) {
        this.adminHomeView = adminHomeView;
        this.administrador = administrador;
        this.adminHomeView.setVisible(true);
        this.adminHomeView.btnExit.addActionListener(this);
        this.adminHomeView.btnRegisterAlumn.addActionListener(this);
        this.adminHomeView.contenedor.setLayout(new BorderLayout());
        
        datosAlumno = new DatosAlumno();
        datosAlumno.btnRegistar.addActionListener(this);
        
        
        showRegisterAlumnoView();
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == adminHomeView.btnExit) {
            deletePreferences();
            goToLogin();
        }
        
        if (e.getSource() == adminHomeView.btnRegisterAlumn) {
            showRegisterAlumnoView();
        }
        
        if (e.getSource() == datosAlumno.btnRegistar) {
            createAlumn();
        }

    }
    
    
    public void showRegisterAlumnoView(){
        
         quitarVistas();

        this.adminHomeView.contenedor.add(datosAlumno);
        
        
    }
    
    
      public void quitarVistas() {
        this.adminHomeView.contenedor.remove(datosAlumno);
        this.adminHomeView.contenedor.revalidate();
        this.adminHomeView.contenedor.repaint();

    }
    
    public void deletePreferences() {

        Preferences prefs = Preferences.userNodeForPackage(controlescolar.controller.LoginController.class);
        final String PREF_NAME = "id";
        final String PREF_TYPE = "type";
        prefs.put(PREF_NAME, "default");
        prefs.put(PREF_TYPE, "default");
    }
    
    
    public void goToLogin(){
        this.adminHomeView.setVisible(false);
        new LoginController(new LoginView());
    }
    
    
    /**
     * Falta validar los datos
     **/
    
    public void createAlumn() {

        String noControl = datosAlumno.no_control.getText();
        String nombre = datosAlumno.nombre.getText();
        String apellidoP = datosAlumno.ap_paterno.getText();
        String apellidoM = datosAlumno.ap_Materno.getText();
        String fecha_Nacimiento = datosAlumno.fechaNacField.getDateStringOrEmptyString();
        String lugar_nac = datosAlumno.lugar_nac.getText();
        String curp = datosAlumno.curpField.getText();
        String correo = datosAlumno.correo.getText();
        String telefono = datosAlumno.telefono.getText();
        String sexo = datosAlumno.sexo.getSelectedItem().toString();
        int carrera = datosAlumno.carrera.getSelectedIndex() + 1;
        String contrasena = datosAlumno.password.getText();
        

        Alumno alum = new Alumno();
        alum.setNumControl(Integer.parseInt(noControl));
        alum.setNombre(nombre);
        alum.setApellido_p(apellidoP);
        alum.setApellido_m(apellidoM);
        alum.setFecha_nacimiento(fecha_Nacimiento);
        alum.setLugar_nacimiento(lugar_nac);
        alum.setCurp(curp);
        alum.setCorreo(correo);
        alum.setTelefono(telefono);
        alum.setSexo(sexo);
        alum.setId_carrera(carrera);
        alum.setPassword(contrasena);

        if (new Alumno().createAlumn(alum)) {
            limpiarCamposAlumn();
            JOptionPane.showMessageDialog(adminHomeView, "Se completo con exito el registro del alumno");

        } else {
            JOptionPane.showMessageDialog(adminHomeView, "Ocurrio un error al registrarse, contacte al administrador");
        }

    }
    
    
    
    public void limpiarCamposAlumn() {
        datosAlumno.no_control.setText(null);
        datosAlumno.nombre.setText(null);
        datosAlumno.ap_paterno.setText(null);
        datosAlumno.ap_Materno.setText(null);
        datosAlumno.fechaNacField.setText(null);
        datosAlumno.curpField.setText(null);
        datosAlumno.lugar_nac.setText(null);
        datosAlumno.correo.setText(null);
        datosAlumno.telefono.setText(null);
        datosAlumno.sexo.setSelectedIndex(0);
        datosAlumno.carrera.setSelectedIndex(0);
        datosAlumno.password.setText(null);

    }
    
    
    
    
}
