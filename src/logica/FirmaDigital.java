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
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;


public class FirmaDigital extends UnicastRemoteObject implements InterfazFirmaDigital{
    //objetos para cifrar y descrifar como atributos
    KeyPairGenerator generador;
    KeyPair llaves;
    Signature firma;
    byte[] resumen;
    byte[] firmaBytes;
    
    private boolean instanciaFirmado = false;
    private boolean instanciaVerificado = false;
    
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
            firma.initSign(llavePrivada, new SecureRandom());
            
            resumen = (nombre + String.valueOf(edad) + mensaje).getBytes();
            
            firma.update(resumen);
            
            firmaBytes = firma.sign();
            System.out.println("Firma a secas:"+ firmaBytes);
            firmaLegible = new BASE64Encoder().encode(firmaBytes);
            System.out.println("Firma: "+firmaLegible);
            
            //Parte del PDF
            PDF pdf = new PDF(nombre, String.valueOf(edad), mensaje, nombre, firmaLegible);
            pdf.generarPDF();
            pdf.firmarPDF();
            
            instanciaFirmado = true;
        } catch (InvalidKeyException | SignatureException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return firmaLegible;
    }
    
    
    /**
     * Metodo para ingresar el pdf y la llave pública del cliente para validar si este pdf es autentico.
     * @param pdf El pdf a comparar
     * @param llavePublica la llave pública del cliente
     * @return true si el pdf es autentico, false de no serlo.
     */
    @Override
    public boolean cargaryverificar(byte[] new_resumen, PublicKey llavePublica){
        boolean validado = false;
        try {
            
            
            firma.initVerify(llavePublica);
            //byte[] new_resumen = null; //aquí insertar la firma digital del pdf
            firma.update(new_resumen);
            
            validado = firma.verify(firmaBytes);
            
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
