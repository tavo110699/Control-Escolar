/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlescolar.model;

import com.mysql.jdbc.Connection;
import controlescolar.utils.Security;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author hugoluna
 */
public class Administrador {
    private int id;
    private String nombre;
    private String apellido_p;
    private String apellido_m;
    private String usuario;
    private String password;

    public Administrador() {
    }

    public Administrador(int id, String nombre, String apellido_p, String apellido_m, String usuario, String password) {
        this.id = id;
        this.nombre = nombre;
        this.apellido_p = apellido_p;
        this.apellido_m = apellido_m;
        this.usuario = usuario;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_p() {
        return apellido_p;
    }

    public void setApellido_p(String apellido_p) {
        this.apellido_p = apellido_p;
    }

    public String getApellido_m() {
        return apellido_m;
    }

    public void setApellido_m(String apellido_m) {
        this.apellido_m = apellido_m;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    
    
    
    public Administrador getData(String usuario){
         String query = "SELECT * FROM administrador where usuario = ?";
        Administrador admin = new Administrador();

        try {
            Connection conexion = new Conexion().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, String.valueOf(id));
            ResultSet rs = sentencia.executeQuery();
            if (rs.next()) {
                 admin.setId(rs.getInt("id"));
                 admin.setNombre(rs.getString("nombre"));
                 admin.setApellido_p(rs.getString("apellido_p"));
                 admin.setApellido_m(rs.getString("apellido_m"));
                 admin.setUsuario(rs.getString("usuario"));
            }
            
            
        } catch (SQLException e) {
            System.err.println(e.getStackTrace());
        }
        
        return admin;
        
    }
    
    
    
    
    
    
    public boolean loginProcessAdmin(Administrador admin) {

        String query = "SELECT * FROM administrador where usuario = ? AND password = ?";
        boolean checkUser = false;

        try {
            Connection conexion = new Conexion().getConnection();
            PreparedStatement sentencia = conexion.prepareStatement(query);
            sentencia.setString(1, String.valueOf(admin.getUsuario()));
            sentencia.setString(2, Security.cifrar(admin.getPassword()));
            ResultSet rs = sentencia.executeQuery();
            
            if (rs.next()) {
                checkUser = true;
            }
        } catch (SQLException e) {

        }
        
        return checkUser;

    }
    
}
