/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 * Aquí se va firmar y validar todo, por parte del servidor fungiendo como un objeto remoto
 * @author maste
 */

import java.io.File;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.security.*;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import sun.misc.BASE64Encoder;


public class FirmaDigital extends UnicastRemoteObject implements InterfazFirmaDigital{
    //objetos para cifrar y descrifar como atributos
    KeyPairGenerator generador;
    KeyPair llaves;
    Signature firma;
    byte[] resumen;
    byte[] firmaBytes;
    PrivateKey buffPrivate;
    
    SecureRandom random = new SecureRandom();
    private boolean instanciaFirmado = false;
    private boolean instanciaVerificado = false;
    
    public boolean verificado = false;
    
    public FirmaDigital() throws RemoteException{
        super();
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            //generador = KeyPairGenerator.getInstance("RSA", "BC");
            //generador.initialize(1024, new SecureRandom());
            
            //llaves = generador.genKeyPair();
            
            firma = Signature.getInstance("SHA256WithRSA", "BC");
            
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * Esté metodo se encarga de generar el pdf y firmarlo;
     * @param nombre El nombre a guardar.
     * @param edad La edad a guardar
     * @param mensaje El mensaje a guardar
     * @param llavePrivada La llave privada con la qe se cifra la firma.
     */
    @Override
    public String firmaryguardar(String nombre, int edad, String mensaje, PrivateKey llavePrivada){
        String firmaLegible = "";
        try {
            firma.initSign(llavePrivada, random);
            buffPrivate = llavePrivada;
            resumen = (nombre + String.valueOf(edad) + mensaje).getBytes();
            System.out.println("Mensaje:" +(nombre + String.valueOf(edad) + mensaje));
            System.out.println("Longitud cadena: "+ (nombre + String.valueOf(edad) + mensaje).length());
            firma.update(resumen);
            System.out.println("Mensaje en bytes juntitos: " +resumen);
            
            System.out.println("Mensjae con Array.toStirng: "+ Arrays.toString(resumen));
            firmaBytes = firma.sign();
            System.out.println("Firma a secas:"+ firmaBytes);
            firmaLegible = new BASE64Encoder().encode(firmaBytes);
            System.out.println("Firma: "+firmaLegible);
            JOptionPane.showMessageDialog(null, "Se ha firmado un archivo", "Firma realizada", JOptionPane.INFORMATION_MESSAGE);
            instanciaFirmado = true;
        } catch (InvalidKeyException | SignatureException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return firmaLegible;
    }
    
    
    /**
     * Metodo para ingresar el pdf y la llave pública del cliente para validar si este pdf es autentico.
     * @param new_resumen: los bytes del resumen con los que se valida la firma
     * @param llavePublica la llave pública del cliente
     * @return true si el pdf es autentico, false de no serlo.
     */
    @Override
    public boolean cargaryverificar(String nombre, int edad, String mensaje, PublicKey llavePublica){
        boolean validado = false;
        try {
            byte[] new_resumen = (nombre + String.valueOf(edad) + mensaje).getBytes();
            System.out.println("New resumen junto: "+ new_resumen);
            System.out.println("New resumen Arrays.toString: "+ Arrays.toString(new_resumen));
           
            firma.initVerify(llavePublica);
            //byte[] new_resumen = null; //aq(firmaBytesuí insertar la firma digital del pdf
            firma.update(new_resumen);
            
            validado = firma.verify(firmaBytes);
            verificado = validado;
            System.out.println("Verificación de la firma digital: "+ validado);
            JOptionPane.showMessageDialog(null, "Se ha verificado la firma digital del archivo \n El resultado es: "+validado, "Verificación realizada", JOptionPane.INFORMATION_MESSAGE);
            instanciaVerificado = true;
        } catch (InvalidKeyException | SignatureException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validado;
    }

    public boolean isInstanciaFirmado() {
        return instanciaFirmado;
    }

    public void setInstanciaFirmado(boolean instanciaFirmado) {
        this.instanciaFirmado = instanciaFirmado;
    }

    public boolean isInstanciaVerificado() {
        return instanciaVerificado;
    }

    public void setInstanciaVerificado(boolean instanciaVerificado) {
        this.instanciaVerificado = instanciaVerificado;
    }
    
    
}
