/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

/**
 * Aquó se va firmar y validar todo
 * @author maste
 */

import java.io.File;
import java.security.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder;


public class FirmaDigital {
    //objetos para cifrar y descrifar como atributos
    KeyPairGenerator generador;
    KeyPair llaves;
    Signature firma;
    byte[] resumen;
    byte[] firmaBytes;
    public FirmaDigital(){
        try {
            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
            generador = KeyPairGenerator.getInstance("RSA", "BC");
            generador.initialize(1024, new SecureRandom());
            
            llaves = generador.genKeyPair();
            
            firma = Signature.getInstance("SHA1WithRSA", "BC");
            
        } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public PrivateKey getPrivada(){
        return llaves.getPrivate();
    }
    
    public PublicKey getPublica(){
        return llaves.getPublic();
    }
    /**
     * Esté metodo se encarga de generar el pdf y firmarlo;
     * @param nombre El nombre a guardar.
     * @param edad La edad a guardar
     * @param mensaje El mensaje a guardar
     * @param llavePrivada La llave privada con la qe se cifra la firma.
     */
    public void firmaryguardar(String nombre, int edad, String mensaje, PrivateKey llavePrivada){
        try {
            firma.initSign(llavePrivada, new SecureRandom());
            
            resumen = (nombre + String.valueOf(edad) + mensaje).getBytes();
            
            firma.update(resumen);
            
            firmaBytes = firma.sign();
            
            //new BASE64Encoder().encode(firmaBytes));
            
            
            
            System.out.println(firma.verify(firmaBytes));
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public boolean cargaryverificar(File pdf, PublicKey llavePublica){
        boolean validado = false;
        try {
            
            
            firma.initVerify(llaves.getPublic());
            byte[] new_resumen = null; //aquí insertar la firma digital del pdf
            firma.update(new_resumen);
            
            validado = firma.verify(firmaBytes);
            
        } catch (InvalidKeyException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(FirmaDigital.class.getName()).log(Level.SEVERE, null, ex);
        }
        return validado;
    }
}
