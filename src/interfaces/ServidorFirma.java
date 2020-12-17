/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package interfaces;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import logica.FirmaDigital;

/**
 * Correr el servidor que ejdcuta la firma digital
 * @author maste
 */
public class ServidorFirma {

    
   public static void main(String[] args) {
       InterfazServidor sI = new InterfazServidor();
       sI.start();
    }
    
}
