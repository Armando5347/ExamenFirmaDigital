/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * Aquí se almacenan los métodos para la subida y cargada de llaves
 * @author maste
 */
public class CargasLlaves {
    
    
    public static void guardarKey(Key llave, String archivo) throws FileNotFoundException, IOException {
        //generar un archivo .dat
        byte[] llavepublic = llave.getEncoded();
        FileOutputStream fos = new FileOutputStream(archivo);
        fos.write(llavepublic);
        fos.close();
    }

    public static PublicKey cargarKeyPu(String archivo) throws FileNotFoundException, IOException, NoSuchAlgorithmException, InvalidKeySpecException {
        //para poder exportar la clave pública es necesario codificarla mediante
        //una codificación certificada por  X509 (certificación de la llave)
        FileInputStream fis = new FileInputStream(archivo);
        //ver si es valida
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        
        fis.read(bytes);
        fis.close();
        
        //comprobar la llave
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        
        KeySpec spec = new X509EncodedKeySpec(bytes);
        
        PublicKey llavepublic = keyFactory.generatePublic(spec);
        
        return llavepublic;
    }

    public static PrivateKey cargarKeyPri(String archivo) throws NoSuchAlgorithmException, FileNotFoundException, IOException, InvalidKeySpecException {
        FileInputStream fis = new FileInputStream(archivo);
        //ver si es valida
        int numBytes = fis.available();
        byte[] bytes = new byte[numBytes];
        
        fis.read(bytes);
        fis.close();
        
        //comprobar la llave
        /*Porque la comprobación de la llave privada es necesario
         el certificado por parte del estandar PKCS8
        */
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        
        KeySpec spec = new PKCS8EncodedKeySpec(bytes);
        
        PrivateKey llaveprivate = keyFactory.generatePrivate(spec);
        
        return llaveprivate;
        
    }
}
