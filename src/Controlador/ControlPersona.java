/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Controlador;

import Modelo.ModeloPersona;
import Modelo.Persona;
import Vista.VistaPersonas;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
/**
 *
 * @author Usuario
 */
public class ControlPersona {
    private ModeloPersona modelo;
    private VistaPersonas vista;
    
    public ControlPersona(ModeloPersona modelo,VistaPersonas vista){
        this.modelo=modelo;
        this.vista=vista;
        vista.setVisible(true);
    }
    private void cargaPersonas(){
       //Control para consultar a mi modelo o base de datos 
       //Y luego en la vista.
       List<Persona>listap = modelo.listaPersonas();
       DefaultTableModel mTabla;
       mTabla=(DefaultTableModel)vista.getTbPersona().getModel();
       mTabla.setNumRows(0);//Limpio la tabla 
       listap.stream().forEach(pe->{
           Object[]filanueva={pe.getIdpersona(),pe.getNombres(),pe.getApellidos(),String.valueOf(pe.getFechanacimiento()),pe.getSexo(),pe.getTelefono(),String.valueOf(pe.getSueldo()),String.valueOf(pe.getCupo())};
           mTabla.addRow(filanueva);
           
       });
    }
//    public void iniciaControl(){
//        vista.getBtnActualizar().addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
//            }
//        });
//    }
    
    public void iniciaControl(){
       vista.getBtnActualizar().addActionListener(l->cargaPersonas());
       vista.getBtnCrear().addActionListener(l-> abrirDialogo(1));
        vista.getBtnEditar().addActionListener(l-> abrirDialogo(2));
        vista.getBtnAceptar().addActionListener(l-> crearEditarPersona());
    }
    
    private void abrirDialogo(int ce) {
        String title;
        if (ce == 1) {
            title = "Crear nueva persona";
            vista.getDlgCrud().setName("crear");
        } else {

            title = "Editar";
            vista.getDlgCrud().setName("editar");
        }

        vista.getDlgCrud().setLocationRelativeTo(vista);
        vista.getDlgCrud().setSize(700, 500);
        vista.getDlgCrud().setTitle(title);
        vista.getDlgCrud().setVisible(true);
    }
    
 private void crearEditarPersona() {
        if ("crear".equals(vista.getDlgCrud().getName())) {
            //INSERTAR
            String cedula = vista.getTxtIdentificacion().getText();
            String nombres = vista.getTxtNombre().getText();
            String apellidos = vista.getTxtApellidos().getText();
            String sexo = vista.getTxtSexo().getText();
            String telefono = vista.getTxtTelefono().getText();
            Date fecha = vista.getjDateChooser1().getDate();
            double sueldo = Double.parseDouble(vista.getTxtSueldo().getText());
            int cupo = Integer.parseInt(vista.getTxtCupo().getText());

            ModeloPersona persona = new ModeloPersona();
            persona.setIdpersona(cedula);
            persona.setNombres(nombres);
            persona.setApellidos(apellidos);
            persona.setSexo(sexo);
            persona.setTelefono(telefono);

            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());//Paso de util.Date a sql.Date
            persona.setFechanacimiento(fechaSQL);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);

            if (persona.crearPersona()==null) {
                vista.getDlgCrud().setVisible(false);
                JOptionPane.showMessageDialog(vista, "Persona Creada Satisfactoriamente");
            } else {
                JOptionPane.showMessageDialog(vista, "No se pudo crear la persona");
            }

        }else {
             String cedula = vista.getTxtIdentificacion().getText();
        String nombres = vista.getTxtNombre().getText();
        String apellidos = vista.getTxtApellidos().getText();
        String sexo = vista.getTxtSexo().getText();
        String telefono = vista.getTxtTelefono().getText();
        Date fecha = vista.getjDateChooser1().getDate();
            double sueldo = Double.parseDouble(vista.getTxtSueldo().getText());
            int cupo = Integer.parseInt(vista.getTxtCupo().getText());

            ModeloPersona persona = new ModeloPersona();
            persona.setIdpersona(cedula);
            persona.setNombres(nombres);
            persona.setApellidos(apellidos);
            persona.setSexo(sexo);
            persona.setTelefono(telefono);

            java.sql.Date fechaSQL = new java.sql.Date(fecha.getTime());//Paso de util.Date a sql.Date
            persona.setFechanacimiento(fechaSQL);
            persona.setSueldo(sueldo);
            persona.setCupo(cupo);

        if (persona.modificarPersona()==null) {
            vista.getDlgCrud().setVisible(false);
            JOptionPane.showMessageDialog(vista, "Persona Modificada Satisfactoriamente");
        } else {
            JOptionPane.showMessageDialog(vista, "No se pudo modificar la persona");
        }
        }
//else hacemos el editar
        //EDITAR
       
        
        cargaPersonas(); //Actualizo la tabla con los datos

    }
}
