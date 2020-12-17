/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.io.File;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;
import java.security.Security;
import java.security.Signature;
import logica.InterfazFirmaDigital;

/**
 *
 * @author maste
 */
public class ClienteFirma {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //Un experimento en la consola
//        try{
//            Registry reg= LocateRegistry.getRegistry("127.0.0.1",5347);
//            InterfazFirmaDigital fdF=(InterfazFirmaDigital)reg.lookup("firmaryguardar");
//            InterfazFirmaDigital fdV=(InterfazFirmaDigital)reg.lookup("cargaryverificar");
//            String nombre = "Armando";
//            int edad = 17;
//            String mensaje = "un saludo mi gente";
//            System.out.println ("Datos a firmar: \n"
//                    + "Nombre: "+nombre+ "\n"
//                            + "Edad: "+edad+"\n"
//                                    + "Mensaje: "+mensaje);
//            
//            Security.addProvider(new org.bouncycastle.jce.provider.BouncyCastleProvider());
//            KeyPairGenerator generador = KeyPairGenerator.getInstance("RSA", "BC");
//            generador.initialize(1024, new SecureRandom());
//            
//            KeyPair llaves = generador.genKeyPair();
//
//            String resumen_prueba = fdF.firmaryguardar(nombre, edad, mensaje, llaves.getPrivate());
//            System.out.println("Ejecutando firmado...");
//            System.out.println("Verificando...");
//            System.out.println("Resultado verificado: "+fdV.cargaryverificar(resumen_prueba.getBytes(), llaves.getPublic()));
//        }catch(Exception e){
//            
//        }
        
        InterfazMenu im = new InterfazMenu();
        im.init();
    }
    
}
