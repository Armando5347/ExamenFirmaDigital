/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.Border;
import logica.FirmaDigital;

/**
 *
 * @author maste
 */
public class InterfazServidor extends Thread{
    
    FirmaDigital firmaDigital;
    
    private JFrame ventana = new JFrame("Menú principal");
    private JPanel contenedorTitulo = new JPanel();
    private JPanel acciones = new JPanel();
    
    private JPanel firmado = new JPanel();
    private JPanel verificados = new JPanel();
    
    JLabel titulo = new JLabel("FIRMA DIGITAL SERVIDOR");
    JLabel subtit_Firmado = new JLabel("Monitoreo de firmas");
    JLabel subtit_Verificado = new JLabel("Monitoreo de validaciones");
    
    private Font f_tit = new Font("Verdana", Font.BOLD, 50);
    private Font f_subtit = new Font("Verdana", Font.BOLD, 35);
    private Font f_txt = new Font("Verdana", Font.BOLD, 25);
    
    private JTextField statusFirmado = new JTextField("Sin firmar");
    private JTextField statusVerificado = new JTextField("Sin verificar");
    
    private final Border borde = BorderFactory.createLineBorder(Color.darkGray,3);
    private final Border bordeCompuesto = BorderFactory.createCompoundBorder(BorderFactory.createLineBorder(Color.DARK_GRAY,3), BorderFactory.createEmptyBorder(15, 15, 15, 15));
    private BorderLayout layout = new BorderLayout(0, 0);
    
    InterfazServidor(){
        try {
            Registry reg=LocateRegistry.createRegistry(5347);
            firmaDigital = new FirmaDigital();
            reg.rebind("firmaryguardar",firmaDigital);
                System.out.println("Va el firmado");
            reg.rebind("cargaryverificar", firmaDigital);
                System.out.println("Va el verificado");              
        }
        catch (RemoteException e)
        {
            System.out.println("error");
        }
    }

    @Override
    public void run() {
        ventana.setBackground(Color.black);
        ventana.setLayout(layout);
        titulo.setBackground(Color.black);
        titulo.setForeground(Color.white);
        titulo.setFont(f_tit);
        contenedorTitulo.add(titulo);
        contenedorTitulo.setBorder(borde);
        contenedorTitulo.setBackground(Color.black);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ventana.add(contenedorTitulo, BorderLayout.NORTH);
        buildMain();
        ventana.pack();
        ventana.setVisible(true);
        loopVerificacion();
    }

    private void buildMain() {
        acciones.setBackground(Color.black);
        acciones.setLayout(new BoxLayout(acciones, BoxLayout.X_AXIS));
        acciones.setBorder(bordeCompuesto);
        firmado.setBackground(Color.black);
        firmado.setLayout(new BorderLayout(5, 5));
        firmado.setBorder(borde);
        subtit_Firmado.setBackground(Color.black);
        subtit_Firmado.setForeground(Color.white);
        subtit_Firmado.setFont(f_subtit);
        firmado.add(subtit_Firmado, BorderLayout.NORTH);
        statusFirmado.setFont(f_txt);
        statusFirmado.setEditable(false);
        statusFirmado.setMaximumSize(new Dimension(200,60));
        firmado.add(statusFirmado,BorderLayout.CENTER);
        
        acciones.add(firmado);
        
        verificados.setBackground(Color.black);
        verificados.setLayout(new BorderLayout(5, 5));
        verificados.setBorder(borde);
        subtit_Verificado.setBackground(Color.black);
        subtit_Verificado.setForeground(Color.white);
        subtit_Verificado.setFont(f_subtit);
        firmado.add(subtit_Verificado, BorderLayout.NORTH);
        statusVerificado.setFont(f_txt);
        statusVerificado.setEditable(false);
        statusVerificado.setMaximumSize(new Dimension(200,60));
        verificados.add(statusVerificado,BorderLayout.CENTER);
        acciones.add(verificados);
        
        subtit_Firmado.setBackground(Color.black);
        subtit_Firmado.setForeground(Color.white);
        subtit_Firmado.setFont(f_subtit);
        subtit_Firmado.setHorizontalAlignment(SwingConstants.CENTER);
        firmado.add(subtit_Firmado, BorderLayout.NORTH);
        
        subtit_Verificado.setBackground(Color.black);
        subtit_Verificado.setForeground(Color.white);
        subtit_Verificado.setFont(f_subtit);
        subtit_Verificado.setHorizontalAlignment(SwingConstants.CENTER);
        verificados.add(subtit_Verificado, BorderLayout.NORTH);
        
        
        ventana.add(acciones,BorderLayout.CENTER);
    }

    private void loopVerificacion() {
        while(true){
            synchronized(firmaDigital){
                try {
                    firmaDigital.wait();
                    if(firmaDigital.isInstanciaFirmado()){
                        actualizarFirmado();
                    }
                    if(firmaDigital.isInstanciaVerificado()){
                        actualizarVerificado();
                    }
                } catch (InterruptedException ex) {
                    Logger.getLogger(InterfazServidor.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    /**
     * Cargar en l ainterfaz una notificación que avise que se aplicó una firma digital
     */
    private void actualizarFirmado() {
        statusFirmado.setText("Se ha realizado una firma");
        statusFirmado.repaint();
        statusFirmado.updateUI();
        firmado.repaint();
        firmado.updateUI();
        
    }

    /**
     * Cargar en la aplicación una nitificación que avise que se aplicó una validación de la firma
     */
    private void actualizarVerificado() {
        statusVerificado.setText("El  verificado fue: "+String.valueOf(firmaDigital.verificado));
        statusVerificado.repaint();
        statusVerificado.updateUI();
        verificados.repaint();
        verificados.updateUI();
    }

}
