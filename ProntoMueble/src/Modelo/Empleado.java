package Modelo;

import java.time.LocalDate;

/**
 *
 * @author Mateo
 */
public class Empleado extends Persona{

    private double sueldo;
    private String rol, contrato;
    private LocalDate fecha_nac;

    public Empleado(double Sueldo, String rol, String contrato, LocalDate fecha_nac, String id, String nombre, String direccion, String telefono, String email) {
        super(id, nombre, direccion, telefono, email);
        this.sueldo = Sueldo;
        this.rol = rol;
        this.contrato = contrato;
        this.fecha_nac = fecha_nac;
    }

    public Empleado() {
        super();
        this.sueldo = 0.0;
        this.rol = "";
        this.contrato = "";
        this.fecha_nac = LocalDate.now();
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

    public LocalDate getFecha_nac() {
        return fecha_nac;
    }

    public void setFecha_nac(LocalDate fecha_nac) {
        this.fecha_nac = fecha_nac;
    }

    @Override
    public String toString() {
        return "Empleado \nSueldo=" + sueldo + "'nRol=" + rol + "\nContrato=" + contrato + "\nFecha_nac=" + fecha_nac + super.toString();
    }
    
    
}
