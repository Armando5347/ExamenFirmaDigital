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
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import logica.FirmaDigital;
public class InterfazMenu {
    
    private FirmaDigital firma = new FirmaDigital();
    
    private InterfazGeneracion ig = new InterfazGeneracion(firma);
    private InterfazValidacion iv = new InterfazValidacion(firma);
    
    private JFrame ventana = new JFrame("Menú principal");
    
    private JPanel menus = new JPanel();
    
    private Font f_tit = new Font("Verdana", Font.BOLD, 30);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 25);
    private Font f_txt = new Font("Verdana", Font.PLAIN, 16);
    private JButton botonFirmado = new JButton("Firmar un mensaje");
    private JButton botonVerificado = new JButton("Validar un pdf");
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    private final Border bordeCompuesto = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10));
    private BorderLayout layout = new BorderLayout(5, 5);
    
    public void init() {
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titulo = new JLabel("FIRMA DIGITAL");
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        ventana.add(titulo, BorderLayout.NORTH);
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
        grupoBotones.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        botonFirmado.setBackground(Color.black);
        botonFirmado.setForeground(Color.white);
        botonFirmado.setBorder(bordeCompuesto);
        
        botonVerificado.setBackground(Color.black);
        botonVerificado.setForeground(Color.white);
        botonVerificado.setBorder(bordeCompuesto);
        
        botonFirmado.addActionListener((ActionEvent ae) -> {
            ig.init();
        });
        
        botonVerificado.addActionListener((ActionEvent ae) -> {
            iv.init();
        });
        grupoBotones.add(botonFirmado);
        grupoBotones.add(botonVerificado);
        
        menus.add(grupoBotones, BorderLayout.CENTER);
        
        ventana.add(menus, BorderLayout.CENTER);
    }
    
}
