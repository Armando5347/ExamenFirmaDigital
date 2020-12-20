/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

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
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import logica.CargasLlaves;
import logica.FirmaDigital;
import logica.InterfazFirmaDigital;
import logica.PDF;

class InterfazGeneracion extends Thread{
    //elementos de back
    private PrivateKey privada;
    Registry reg;
    InterfazFirmaDigital fdF;
    //elementos de fort
    private JFrame ventana = new JFrame("Ingresar datos para pdf");
    
    private JPanel contenedorTitulo = new JPanel();
    private JPanel contenedorMain = new JPanel();
    private JPanel form = new JPanel();
    private JPanel confirmacion = new JPanel();

    private Font f_tit = new Font("Verdana", Font.BOLD, 30);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 25);
    private Font f_txt = new Font("Verdana", Font.PLAIN, 16);
    private JButton botonGeneracion = new JButton("Guardar información");
    
    private JLabel titulo = new JLabel("Generación de PDF");
    
    private JLabel labelNombre = new JLabel("Nombre:");
    private JLabel labelEdad = new JLabel("Edad:");
    private JLabel labelMensaje = new JLabel("Mensaje:");
    
    private JButton confirmarPDF = new JButton("Generar PDF");
    
    private JTextField nombre = new JTextField();
    private JTextField edad = new JTextField();
    private JTextField mensaje = new JTextField();
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    
    private BorderLayout layout = new BorderLayout(5, 5);
    
    private boolean instanciado = false;
    
    private PDF pdf;

    String name;
    
    InterfazGeneracion(PrivateKey privada) {
        try {
            this.privada = privada;
            reg= LocateRegistry.getRegistry("127.0.0.1",5347);
            fdF=(InterfazFirmaDigital)reg.lookup("firmaryguardar");
        } catch (RemoteException | NotBoundException ex) {
            Logger.getLogger(InterfazGeneracion.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void run() {
        if(instanciado) return;
        instanciado = true;
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        titulo = new JLabel("Generación de firma digital");
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        contenedorTitulo.add(titulo);
        contenedorTitulo.setBorder(borde);
        ventana.add(contenedorTitulo, BorderLayout.NORTH);
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
        form.setBackground(Color.black);
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
                    CargasLlaves.guardarKey(privada, ("LlavePrivada"+name+".key"));
                    fdF.firmaryguardar(nombre.getText(),
                            Integer.parseInt(edad.getText()),
                            mensaje.getText(),
                            CargasLlaves.cargarKeyPri(("LlavePrivada"+name+".key")));
                    mostrarConfirmacion();
                } catch (IOException | NoSuchAlgorithmException | InvalidKeySpecException ex) {
                    Logger.getLogger(InterfazGeneracion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }

            private void mostrarConfirmacion() {
                form.setVisible(false);
                confirmacion.setVisible(true);
                confirmacion.repaint();
                confirmacion.updateUI();
            }
        });
        celdas[3][0] = botonGeneracion;
        
        nombre.setFont(f_txt);
        celdas[0][1] = nombre;
        edad.setFont(f_txt);
        celdas[1][1] = edad;
        mensaje.setFont(f_txt);
        celdas[2][1] = mensaje;
        
        
        //adds
        form.add(celdas[0][0]);
        form.add(celdas[0][1]);
        form.add(celdas[1][0]);
        form.add(celdas[1][1]);
        form.add(celdas[2][0]);
        form.add(celdas[2][1]);
        form.add(celdas[3][0]);
        contenedorMain.add(form);
    }

    private void buildConfirmacion() {
        confirmacion.setBackground(Color.black);
        JLabel tit_conf = new JLabel("Cargar clave Privada y generar pdf");
        confirmacion.add(tit_conf);
        confirmacion.add(confirmarPDF);
        confirmarPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Aquí es donde ya se manda, por lo que hay que hacer el notify
                synchronized(fdF){
                    try {
                        PrivateKey key = CargasLlaves.cargarKeyPri(("LlavePrivada"+name+".key"));
                        String firmaLegible = fdF.firmaryguardar(nombre.getText(), 
                                Integer.parseInt(edad.getText()),
                                mensaje.getText(),
                                key);//reemplazar por el .key obtenido
                        
                        pdf = new PDF(nombre.getText(), 
                                String.valueOf(edad.getText()), 
                                mensaje.getText(),
                                nombre.getText(),
                                firmaLegible);
                        
                        pdf.generarPDF();
                        pdf.firmarPDF();
                        pdf.getVariables();
                        fdF.notify();
                        JOptionPane.showMessageDialog(null,"Se elaboro el PDF con exito",
                                "Mensaje de aviso", JOptionPane.INFORMATION_MESSAGE);
                    } catch (NoSuchAlgorithmException | IOException | InvalidKeySpecException ex) {
                        Logger.getLogger(InterfazGeneracion.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                
                
            }
        });
        
        contenedorMain.add(confirmacion,BorderLayout.EAST);
        confirmacion.setVisible(false);
    }


    
}
