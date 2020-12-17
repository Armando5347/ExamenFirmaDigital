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
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.rmi.RemoteException;
import java.security.PublicKey;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.border.Border;
import logica.FirmaDigital;
import logica.InterfazFirmaDigital;

class InterfazValidacion {
    
    InterfazFirmaDigital fdV;
    
    private JFrame ventana = new JFrame("Validación del pdf");
    
    private PublicKey publica;
    
    private JPanel main = new JPanel();
    private JPanel contenedorTitulo = new JPanel();
    private JPanel resultado = new JPanel();
    
    private Font f_tit = new Font("Verdana", Font.BOLD, 30);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 25);
    private Font f_txt = new Font("Verdana", Font.PLAIN, 16);
    
    private JLabel labelLlavePublica = new JLabel("Ingrese la llave pública");
    private JLabel labelPDF = new JLabel("Ingrese el pdf");
    
    private JTextField llavepublica = new JTextField();
    private JTextField pdf = new JTextField();
    
    private JButton botonVerificado = new JButton("Validar pdf");
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    
    private BorderLayout layout = new BorderLayout(5, 5);
    
    private boolean instanciado = false;

    InterfazValidacion(PublicKey publica) {
        this.publica = publica;
        
    }
    
    void init() {
        if(instanciado) return;
        instanciado = true;
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        contenedorTitulo.setBorder(borde);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titulo = new JLabel("Validación de PDF");
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        contenedorTitulo.add(titulo);
        ventana.add(contenedorTitulo, BorderLayout.NORTH);
        buildmain();
        ventana.pack();
        ventana.setVisible(true);
    }

    private void buildmain() {
        main.setBackground(Color.black);
        main.setLayout(new BorderLayout(5,5));
        JLabel subTit = new JLabel("Ingrese su clave pública y el pdf");
        subTit.setBackground(Color.black);
        subTit.setForeground(Color.white);
        subTit.setFont(f_subtit);
        main.add(subTit, BorderLayout.NORTH);
        JPanel pedidos = new JPanel();
        pedidos.setLayout(new GridLayout(2, 3, 10, 10));
        labelLlavePublica.setBackground(Color.black);
        labelPDF.setBackground(Color.black);
        labelLlavePublica.setForeground(Color.white);
        labelPDF.setForeground(Color.white);
        labelLlavePublica.setFont(f_txt);
        labelPDF.setFont(f_txt);
        llavepublica.setFont(f_txt);
        pdf.setFont(f_txt);
        pedidos.add(labelLlavePublica);
        pedidos.add(llavepublica);
        pedidos.add(labelPDF);
        pedidos.add(pdf);
        
        botonVerificado.setFont(f_subtit);
        botonVerificado.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                try {
                    //de momento no lee pdf ni nada, y un 15 por que si
                    fdV.cargaryverificar(new byte[15], publica);
                } catch (RemoteException ex) {
                    Logger.getLogger(InterfazValidacion.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
        
        pedidos.add(botonVerificado);
        main.add(pedidos, BorderLayout.CENTER);
        ventana.add(main, BorderLayout.CENTER);
    }
    
}
