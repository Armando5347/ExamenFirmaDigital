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
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.Border;
import logica.FirmaDigital;

class InterfazValidacion {
    private JFrame ventana = new JFrame("Validación del pdf");
    
    private FirmaDigital firma;
    
    private JPanel main = new JPanel();
    
    private JPanel resultado = new JPanel();
    
    private Font f_tit = new Font("Verdana", Font.BOLD, 30);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 25);
    private Font f_txt = new Font("Verdana", Font.PLAIN, 16);
    private JButton botonVerificado = new JButton("Validar pdf");
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    
    private BorderLayout layout = new BorderLayout(5, 5);
    
    private boolean instanciado = false;

    InterfazValidacion(FirmaDigital firma) {
        this.firma = firma;
    }
    
    void init() {
        if(instanciado) return;
        instanciado = true;
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel titulo = new JLabel("Validación de PDF");
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        ventana.add(titulo, BorderLayout.NORTH);
        ventana.pack();
        ventana.setVisible(true);
    }
    
}
