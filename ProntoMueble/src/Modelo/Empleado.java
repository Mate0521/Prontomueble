package Modelo;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

/**
 *
 * @author Mateo
 */
public class Empleado extends Persona{

    private double sueldo;
    private String rol, contrato, contraseña;
    private Date fecha_nac;

    public Empleado(double Sueldo, String rol, String contrato, Date fecha_nac, String id, String nombre, String direccion, String telefono, String email, String contraseña) {
        super(id, nombre, direccion, telefono, email);
        this.sueldo = Sueldo;
        this.rol = rol;
        this.contrato = contrato;
        this.fecha_nac = fecha_nac;
        this.contraseña= contraseña;
    }

    public Empleado() {
        super();
        this.sueldo = 0.0;
        this.rol = "";
        this.contrato = "";
        this.fecha_nac = Date.from(Instant.MIN);
        this.contraseña="";
    }

    public double getSueldo() {
        return sueldo;
    }

    public void setSueldo(double sueldo) {
        this.sueldo = sueldo;
    }

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }

    public String getContrato() {
        return contrato;
    }

    public void setContrato(String contrato) {
        this.contrato = contrato;
    }

    public Date getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(Date fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
    
    


    @Override
    public String toString() {
        return "Empleado \nSueldo=" + sueldo + "'nRol=" + rol + "\nContrato=" + contrato + "\nFecha_nac=" + fecha_nac + super.toString();
    }
    
    
}
