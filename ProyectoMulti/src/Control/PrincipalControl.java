/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Control;

import Modelo.ConexionAzureSQL;
import Modelo.Empleado;
import Vista.Modificaciones;
import Vista.Principal;
import Vista.Sale;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Mateo
 */
class PrincipalControl implements ActionListener{

    private Empleado usuario;
    private Principal ObPrincipal;
    private ConexionAzureSQL ObAzureSQL;
      
    public void iniciar() {
        this.ObPrincipal.setTitle("ProntoMueble");
        this.ObPrincipal.setLocationRelativeTo(null);
        this.ObPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.ObPrincipal.setVisible(true);
 
    }
    
    public PrincipalControl() {
        this.ObPrincipal = new Principal();
        this.ObPrincipal.setExtendedState(JFrame.MAXIMIZED_BOTH);
        this.ObPrincipal.getBtnFactura().addActionListener(this);
        this.ObPrincipal.getBtnPedido().addActionListener(this);
        this.ObPrincipal.getBtnRecaudoFact().addActionListener(this);
        this.ObPrincipal.getBtnRecaudoPedido().addActionListener(this);
        this.ObPrincipal.getBtnSalir().addActionListener(this);
        this.ObPrincipal.getMnFactura().addActionListener(this);
        this.ObPrincipal.getMnPedido().addActionListener(this);
        this.ObPrincipal.getMnRecaudoFact().addActionListener(this);
        this.ObPrincipal.getMnRecaudoPedido().addActionListener(this);
        this.ObPrincipal.getMnSalir().addActionListener(this);
    }

    
    public void iniciarControles(Component [] controles){
        int cantTab=0;
        for (Component control : controles) {
            cantTab++;
            if(control instanceof JTabbedPane){
                cantTab= ((JTabbedPane) control).getTabCount();
                for (int i = 0; i <cantTab ; i++) {
                    Component panel=((JTabbedPane) control).getComponent(i);
                    if(panel instanceof JPanel){
                        iniciarControles(((JPanel) panel).getComponents());
                    }   
                }
            } else if (control instanceof JPanel){
                iniciarControles(((JPanel) control).getComponents());          
            } else if(control instanceof JTextField){
                ((JTextField) control).setText("");
            } else if(control instanceof JTable){ 
                iniciarTabla((JTable)control);
            }   
        }   
    }
    
      /**
     * Método para iniciar una tabla, eliminando todas las filas existentes.
     *
     * @param ant La tabla a iniciar.
     */
    public void iniciarTabla(JTable ant){
        DefaultTableModel model=(DefaultTableModel) ant.getModel();
        int controw= model.getRowCount();
        for (int i = controw - 1; i >=0; i--) {
            model.removeRow(i);
        }
    }

    /**
     * Método que maneja los eventos de acción de los botones y menús.
     *
     * @param e El evento de acción.
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(this.ObPrincipal.getBtnSalir()) || e.getSource().equals(this.ObPrincipal.getMnSalir())){
            int exit = JOptionPane.showConfirmDialog(ObPrincipal, "¿Desea terminar la ejecucion?", "Salir", JOptionPane.YES_NO_OPTION);
            if(exit == JOptionPane.YES_OPTION){
                this.ObPrincipal.dispose();
            }
        }
        
        if(e.getSource().equals(this.ObPrincipal.getBtnFactura()) || e.getSource().equals(this.ObPrincipal.getMnFactura())){
           Sale frmF= new Sale() ;
            this.ObPrincipal.getjDesktopPane1().add(frmF);
            //llamado al controlador JInternal
            SaleControl ctrlFrmF= new SaleControl();
            ctrlFrmF.setEmpleado(usuario);
            ctrlFrmF.iniciar();
        }
        
        if(e.getSource().equals(this.ObPrincipal.getBtnRecaudoFact()) || e.getSource().equals(this.ObPrincipal.getMnRecaudoFact())){
            ControlRH CtrlMod= new ControlRH(ObAzureSQL.conectarEmpleado(this.usuario.getId(), this.usuario.getContraseña()));
            Modificaciones frmC= new Modificaciones();
            ObPrincipal.getjDesktopPane1().add(frmC);
            frmC.setVisible(true);
            CtrlMod.iniciar();
 
        }

    }


    public Empleado getUsuario() {
        return usuario;
    }

    public void setUsuario(Empleado usuario) {
        this.usuario = usuario;
    }


    
    
}
            
   
