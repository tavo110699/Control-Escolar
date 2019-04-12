/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlescolar.controller;

import controlescolar.model.Alumno;
import controlescolar.model.Carrera;
import controlescolar.model.Direccion;
import controlescolar.view.DataView;
import controlescolar.view.DirectionsView;
import controlescolar.view.HomeView;
import controlescolar.view.LoginView;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.util.prefs.Preferences;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.table.DefaultTableModel;
import mdlaf.animation.MaterialUIMovement;
import mdlaf.utils.MaterialColors;

/**
 *
 * @author hugoluna
 */
public class HomeController implements ActionListener {

    private Alumno alumno;
    private HomeView homeView;
    private DataView dataView;
    private String dat[] = {"Ciudad", "Colonia", "Calle", "Entidad", "C.P", "Numero"};

    public HomeController(Alumno alumno, HomeView homeView) {
        this.alumno = alumno;
        this.homeView = homeView;
        this.homeView.setVisible(true);
        this.homeView.btnHome.addActionListener(this);
        this.homeView.btnReinscribir.addActionListener(this);
        this.homeView.btnExit.addActionListener(this);
        dataView = new DataView();

        addPanelInfo();
        setData();
    }

    public void setData() {
        this.homeView.labelName.setText(alumno.getNombre() + " " + alumno.getApellido_p() + " " + alumno.getApellido_m());
        this.homeView.labelNumControl.setText(String.valueOf(alumno.getNumControl()));
        this.homeView.labelIcon.setText(alumno.getFirstLetter(alumno.getNombre()) + "" + alumno.getFirstLetter(alumno.getApellido_p()));
        setDataToDataView();

    }

    public void setDataToDataView() {
        dataView.nameField.setText(alumno.getNombre());
        dataView.apField.setText(alumno.getApellido_p());
        dataView.amField.setText(alumno.getApellido_m());
        LocalDate date = LocalDate.parse(alumno.getFecha_nacimiento());

        dataView.fechaNacField.setDate(date);

        switch (alumno.getSexo()) {
            case "Masculino":
                dataView.sexField.setSelectedIndex(0);
                break;
            case "Femenino":
                dataView.sexField.setSelectedIndex(1);
                break;
            default:
                break;

        }

        dataView.curpField.setText(alumno.getCurp());
        dataView.nacimientoField.setText(alumno.getLugar_nacimiento());
        dataView.carreraField.setText(new Carrera().getData(alumno.getId_carrera()).getNombre());
        dataView.btnViewDirections.addActionListener(this);

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == this.homeView.btnExit) {
            deletePreferences();
            goToLogin();
        }

        if (e.getSource() == this.homeView.btnHome) {
            addPanelInfo();
        }

        if (e.getSource() == this.homeView.btnReinscribir) {
            viewReinscribir();
        }

        if (e.getSource() == dataView.btnViewDirections) {
            viewDirection();
        }

    }

    public void viewReinscribir() {
        this.homeView.containerBody.remove(dataView);
        this.homeView.containerBody.revalidate();
        this.homeView.containerBody.repaint();
    }

    public void viewDirection() {
        JDialog dialog = new JDialog();

        DirectionsView directionsView = new DirectionsView();
        //directionsView.tableField.add
        DefaultTableModel defaultTableModel = new DefaultTableModel();
        defaultTableModel.setColumnIdentifiers(dat);

        //directionsView.tableField.setModel();
        //"Ciudad", "Colonia", "Calle", "Entidad", "C.P", "Numero"
        for (int i = 0; i < new Direccion().getData(alumno.getNumControl()).size(); i++) {
            defaultTableModel.addRow(new Object[]{
                new Direccion().getData(alumno.getNumControl()).get(i).getCiudad(),
                new Direccion().getData(alumno.getNumControl()).get(i).getColonia(),
                new Direccion().getData(alumno.getNumControl()).get(i).getCalle(),
                new Direccion().getData(alumno.getNumControl()).get(i).getEntidadFederativa(),
                new Direccion().getData(alumno.getNumControl()).get(i).getCodigoPostal(),
                new Direccion().getData(alumno.getNumControl()).get(i).getNumeroInterior(),});

        }

        directionsView.tableField.setModel(defaultTableModel);

        dialog.setContentPane(directionsView);
        dialog.setLocationRelativeTo(null);
        dialog.pack();
        dialog.setVisible(true);
    }

    public void addPanelInfo() {
        this.homeView.containerBody.remove(dataView);
        this.homeView.containerBody.revalidate();
        this.homeView.containerBody.repaint();

        this.homeView.containerBody.setLayout(new FlowLayout());
        this.homeView.containerBody.add(dataView);

    }

    public void deletePreferences() {

        Preferences prefs = Preferences.userNodeForPackage(controlescolar.controller.LoginController.class);
        final String PREF_NAME = "num_control";
        prefs.put(PREF_NAME, "default");

    }

    public void goToLogin() {
        this.homeView.setVisible(false);
        new LoginController(new LoginView());
    }
}
