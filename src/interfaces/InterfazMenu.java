/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;



import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import logica.CargasLlaves;
import logica.FirmaDigital;
public class InterfazMenu {
    //esto me va generar mis llaves
    private final CargasLlaves parDeLlaves = new CargasLlaves();
    
    private InterfazGeneracion ig = new InterfazGeneracion(parDeLlaves.getPrivada(), parDeLlaves.getPublica());
    private InterfazValidacion iv = new InterfazValidacion(parDeLlaves.getPublica());
    
    private JFrame ventana = new JFrame("Menú principal");
    private JPanel contenedorTitulo = new JPanel();
    private JPanel menus = new JPanel();
    
    private Font f_tit = new Font("Verdana", Font.BOLD, 50);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 35);
    private Font f_txt = new Font("Verdana", Font.PLAIN, 25);
    private JButton botonFirmado = new JButton("Firmar un mensaje");
    private JButton botonVerificado = new JButton("Validar un pdf");
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    private final Border bordeCompuesto = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(15, 15, 15, 15));
    private BorderLayout layout = new BorderLayout(15, 15);
    private Dimension minimo = new Dimension(150,60);
    
    public void init() {
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        JLabel titulo = new JLabel("FIRMA DIGITAL");
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        contenedorTitulo.add(titulo);
        contenedorTitulo.setBorder(borde);
        contenedorTitulo.setBackground(Color.black);
        ventana.add(contenedorTitulo, BorderLayout.NORTH);
        buildMain();
        ventana.pack();
        ventana.setVisible(true);
    }

    private void buildMain() {
        menus.setBackground(Color.black);
        menus.setBorder(borde);
        menus.setLayout(new BorderLayout(2,2));
        JLabel opciones = new JLabel("Opciones");
        opciones.setBackground(Color.black);
        opciones.setForeground(Color.white);
        opciones.setFont(f_subtit);
        menus.add(opciones, BorderLayout.NORTH);
        JPanel grupoBotones = new JPanel();
        grupoBotones.setBackground(Color.black);
        grupoBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        botonFirmado.setBackground(Color.black);
        botonFirmado.setForeground(Color.white);
        botonFirmado.setBorder(bordeCompuesto);
        botonFirmado.setMinimumSize(minimo);
        
        botonVerificado.setBackground(Color.black);
        botonVerificado.setForeground(Color.white);
        botonVerificado.setBorder(bordeCompuesto);
        botonVerificado.setMinimumSize(minimo);
        botonFirmado.addActionListener((ActionEvent ae) -> {
            ig.start();
        });
        
        botonVerificado.addActionListener((ActionEvent ae) -> {
            iv.start();
        });
        grupoBotones.add(botonFirmado);
        grupoBotones.add(botonVerificado);
        
        menus.add(grupoBotones, BorderLayout.CENTER);
        
        ventana.add(menus, BorderLayout.CENTER);
    }
    
}
