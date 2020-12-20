 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.File;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.security.PrivateKey;
import java.security.PublicKey;

/**
 * La interfaz que posee los metodos para implementar la interfaz por parte del cliente
 * @author maste
 */
public interface InterfazFirmaDigital extends Remote{
    
    /**
     *
     */
    String firmaLE = ""; 
     /**
     * Esté metodo se encarga de generar el pdf y firmarlo;
     * @param nombre El nombre a guardar.
     * @param edad La edad a guardar
     * @param mensaje El mensaje a guardar
     * @param llavePrivada La llave privada con la qe se cifra la firma.
     * @return la cedana de bytes de la firma digital
     * @throws java.rmi.RemoteException Si un error en del objeto remoto ocurre
     */
    public String firmaryguardar(String nombre, int edad, String mensaje, PrivateKey llavePrivada) throws RemoteException;
    
    /**
     * Metodo para ingresar el pdf y la llave pública del cliente para validar si este pdf es autentico.
     * @param new_Resumen los bits de la firma digitalq ue el pdf ya leyó
     * @param llavePublica la llave pública del cliente
     * @return true si el pdf es autentico, false de no serlo.
     * @throws java.rmi.RemoteException Si un error en del objeto remoto ocurre
     */
    public boolean cargaryverificar(byte[] new_Resumen, PublicKey llavePublica) throws RemoteException;
}
