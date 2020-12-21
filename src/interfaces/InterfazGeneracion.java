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
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.spec.InvalidKeySpecException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.filechooser.FileSystemView;
import logica.CargasLlaves;
import logica.InterfazFirmaDigital;
import logica.PDF;

class InterfazGeneracion extends Thread{
    //elementos de back
    private PrivateKey privada;
    private PublicKey publica;
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
    
    private JButton ingresarPrivada = new JButton("SELECCINAR ARCHIVO");
    private JLabel labelPrivada = new JLabel("Ingresar su clave privada");
    private JLabel labelGener = new JLabel("Generar documento");
    private JButton confirmarPDF = new JButton("Generar PDF");
    
    private JTextField nombre = new JTextField();
    private JTextField edad = new JTextField();
    private JTextField mensaje = new JTextField();
    
    private JFileChooser inputPrivada = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
    private FileNameExtensionFilter filtroLlave = new FileNameExtensionFilter("Solo aceptar llaves", "key");
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    
    private BorderLayout layout = new BorderLayout(0, 0);
    
    private boolean instanciado = false;
    
    private PDF pdf;
    File private_key;

    String name;
    
    InterfazGeneracion(PrivateKey privada, PublicKey publica) {
        try {
            this.privada = privada;
            this.publica = publica;
            
            //Bueno aqui simplemente seria cambiar por la ip que necesitamos
            System.setProperty("java.rmi.server.hostname","187.202.60.164");
            reg= LocateRegistry.getRegistry("187.202.60.164",1099);
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
        contenedorTitulo.setBackground(Color.black);
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
        form.setLayout(new GridLayout(4, 2, 10, 30));
        form.setBackground(Color.black);
        form.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));
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
                    CargasLlaves.guardarKey(publica, ("LlavePublica"+name+".key"));
//                    fdF.firmaryguardar(nombre.getText(),
//                            Integer.parseInt(edad.getText()),
//                            mensaje.getText(),
//                            CargasLlaves.cargarKeyPri(("LlavePrivada"+name+".key")));
                    mostrarConfirmacion();
                } catch (IOException ex) {
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
        confirmacion.setLayout(new GridLayout(2, 2, 10, 10));
        labelPrivada.setBackground(Color.black);
        labelPrivada.setForeground(Color.white);
        labelPrivada.setFont(f_subtit);
        
        labelGener.setBackground(Color.black);
        labelGener.setForeground(Color.white);
        labelGener.setFont(f_subtit);
        
        inputPrivada.setDialogTitle("Ingrese la llave privada");
        inputPrivada.addChoosableFileFilter(filtroLlave);
        confirmacion.add(labelPrivada);
        confirmacion.add(ingresarPrivada);
        confirmacion.add(labelGener);
        confirmacion.add(confirmarPDF);
        
        confirmarPDF.setEnabled(false);
        
        ingresarPrivada.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //llamar al inputDialog del archivo
                /*
                Toda la configuracion del JFILECHOOSER AQUI!!
                */
                inputPrivada = new JFileChooser();
                inputPrivada.setFileFilter(new FileNameExtensionFilter("key", "key"));
                inputPrivada.setDialogTitle("Buscar archivo .key");
                switch (inputPrivada.showOpenDialog(confirmacion)) {
                    case JFileChooser.APPROVE_OPTION:
                        //EN teoria recuperamos el archivo
                        private_key = inputPrivada.getSelectedFile();
                        ingresarPrivada.setText(private_key.getName());
                        confirmarPDF.setEnabled(true);
                        ingresarPrivada.setEnabled(false);
                        System.out.println(private_key.getAbsolutePath());
                        break;
                    case JFileChooser.CANCEL_OPTION:
                        ingresarPrivada.setText("Elegir archivo");
                        break;
                    case JFileChooser.ERROR_OPTION:
                        System.out.println("Algo malo debio de haber pasado");
                        ingresarPrivada.setText("Elegir archivo");
                        break;
                    default:
                        break;
                }
            }
        });
        confirmarPDF.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                //Aquí es donde ya se manda, por lo que hay que hacer el notify
                synchronized(fdF){
                    try {
                        PrivateKey key = CargasLlaves.cargarKeyPri(private_key.getName());
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
                        confirmarPDF.setEnabled(false);
                        JOptionPane.showMessageDialog(null,"Se elaboro el PDF"
                                + " con exito",
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
