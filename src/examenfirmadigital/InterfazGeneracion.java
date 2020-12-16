/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package examenfirmadigital;

/**
 *
 * @author maste
 */
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import logica.CargasLlaves;
import logica.FirmaDigital;

class InterfazGeneracion {

    private JFrame ventana = new JFrame("Ingresar datos para pdf");
    
    private JPanel contenedorMain = new JPanel();
    private JPanel form = new JPanel();
    private JPanel confirmacion = new JPanel();
    
    private FirmaDigital firma;
    
    private Font f_tit = new Font("Verdana", Font.BOLD, 30);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 25);
    private Font f_txt = new Font("Verdana", Font.PLAIN, 16);
    private JButton botonGeneracion = new JButton("GENERAR PDF");
    
    private JLabel titulo = new JLabel("Generación de PDF");
    
    private JLabel labelNombre = new JLabel("Nombre:");
    private JLabel labelEdad = new JLabel("Edad:");
    private JLabel labelMensaje = new JLabel("Mensaje:");
    
    private JButton guardarInfo = new JButton("Guardar información");
    private JButton confirmarPDF = new JButton("Generar PDF");
    
    private JTextField nombre = new JTextField();
    private JTextField edad = new JTextField();
    private JTextField mensaje = new JTextField();
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    
    private BorderLayout layout = new BorderLayout(5, 5);
    
    private boolean instanciado = false;

    String name;
    
    InterfazGeneracion(FirmaDigital firma) {
        this.firma = firma;   
    }
    
    
    void init() {
        if(instanciado) return;
        instanciado = true;
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titulo = new JLabel("Generación de firma digital");
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        
        ventana.add(titulo, BorderLayout.NORTH);
        contenedorMain.setBackground(Color.black);
        contenedorMain.setLayout(new BoxLayout(contenedorMain, BoxLayout.Y_AXIS));
        ventana.add(contenedorMain, BorderLayout.CENTER);
        buildForm();
        buildConfirmacion();
        ventana.pack();
        ventana.setVisible(true);
    }

    private void buildForm() {
        form.setLayout(new GridLayout(4, 2, 20, 30));
        Component[][] celdas = new Component[4][2];
        labelNombre.setBackground(Color.black);
        labelNombre.setForeground(Color.white);
        labelNombre.setFont(f_txt);
        celdas[0][0] = labelNombre;
        labelEdad.setBackground(Color.black);
        labelEdad.setForeground(Color.white);
        labelEdad.setFont(f_txt);
        celdas[1][0] = labelEdad;
        labelMensaje.setBackground(Color.black);
        labelMensaje.setForeground(Color.white);
        labelMensaje.setFont(f_txt);
        celdas[2][0] = labelMensaje;
        
        botonGeneracion.setFont(f_subtit);
        botonGeneracion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    name = nombre.getText().replaceAll("\\s", "");
                    CargasLlaves.guardarKey(firma.getPrivada(), ("LlavePrivada"+name+".key"));
                    mostrarConfirmacion();
                    
                } catch (IOException ex) {
                    Logger.getLogger(InterfazGeneracion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void mostrarConfirmacion() {
                confirmacion.setVisible(true);
                confirmacion.updateUI();
                
            }
        });
        celdas[3][0] = botonGeneracion;
        
        nombre.setFont(f_txt);
        celdas[0][1] = labelNombre;
        edad.setFont(f_txt);
        celdas[1][1] = labelEdad;
        labelMensaje.setFont(f_txt);
        celdas[2][1] = labelMensaje;
        
        
        //adds
        form.add(celdas[0][0]);
        
        contenedorMain.add(form);
    }

    private void buildConfirmacion() {
        
        confirmarPDF.addActionListener((ae) -> {
                    try {
                        firma.firmaryguardar(nombre.getText(), 
                                Integer.parseInt(edad.getText()), 
                                mensaje.getText(),
                                CargasLlaves.cargarKeyPri(("LlavePrivada"+name+".key")));//reemplazar por el .key obtenido
                    } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException ex) {
                        Logger.getLogger(InterfazGeneracion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                });
        contenedorMain.add(confirmacion);
        confirmacion.setVisible(false);
    }


    
}
