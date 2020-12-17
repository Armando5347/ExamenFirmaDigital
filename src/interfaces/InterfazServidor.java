/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import logica.FirmaDigital;

/**
 *
 * @author maste
 */
public class InterfazServidor {
    private JFrame ventana = new JFrame("Menú principal");
    private JPanel contenedorTitulo = new JPanel();
    private JPanel acciones = new JPanel();
    
    private JPanel firmado = new JPanel();
    private JPanel verificados = new JPanel();
    
    JLabel titulo = new JLabel("FIRMA DIGITAL SERVIDOR");
    JLabel subtit_Firmado = new JLabel("Monitoreo de firmas");
    JLabel subtit_Verificado = new JLabel("Monitoreo de validaciones");
    
    private Font f_tit = new Font("Verdana", Font.BOLD, 30);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 25);
    private Font f_txt = new Font("Verdana", Font.PLAIN, 16);
    
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    private final Border bordeCompuesto = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.WHITE), BorderFactory.createEmptyBorder(10, 10, 10, 10));
    private BorderLayout layout = new BorderLayout(5, 5);
    
    InterfazServidor(){
        try {
            Registry reg=LocateRegistry.createRegistry(5347);
            FirmaDigital firmaDigital = new FirmaDigital();
            reg.rebind("firmaryguardar",firmaDigital);
                System.out.println("Va el firmado");
            reg.rebind("cargaryverificar", firmaDigital);
                System.out.println("Va el verificado");              
        }
        catch (Exception e)
        {
            System.out.println("error");
        }
    }

    void init() {
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        contenedorTitulo.add(titulo);
        contenedorTitulo.setBorder(borde);
        ventana.add(contenedorTitulo, BorderLayout.NORTH);
        buildMain();
        ventana.pack();
        ventana.setVisible(true);
    }

    private void buildMain() {
        acciones.setBackground(Color.black);
        acciones.setLayout(new BoxLayout(acciones, 5));
        
        firmado.setBackground(Color.black);
        firmado.setLayout(new BorderLayout(5, 5));
        firmado.setBorder(borde);
        acciones.add(firmado);
        
        verificados.setBackground(Color.black);
        verificados.setLayout(new BorderLayout(5, 5));
        verificados.setBorder(borde);
        acciones.add(verificados);
        
        subtit_Firmado.setBackground(Color.black);
        subtit_Firmado.setForeground(Color.white);
        subtit_Firmado.setFont(f_subtit);
        firmado.add(subtit_Firmado, BorderLayout.NORTH);
        
        subtit_Verificado.setBackground(Color.black);
        subtit_Verificado.setForeground(Color.white);
        subtit_Verificado.setFont(f_subtit);
        verificados.add(subtit_Verificado, BorderLayout.NORTH);
        
        ventana.add(acciones,BorderLayout.CENTER);
    }
    
}
