/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package logica;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import java.io.FileInputStream;
import java.io.FileOutputStream;

/**
 *
 * @author taspi
 */
public class PDF {
    
    private String contenido ="";
    private String nombre_archivo="";
    private String firma = "";
    
    private String nombre ="";
    private String edad   ="";
    private String mensaje="";
    
    public PDF(){
        
    }

    public PDF(String contenido, String nombre_archivo, String firma){
        this.contenido = contenido;
        this.nombre_archivo = nombre_archivo;
        this.firma = firma;
    }
    
    public PDF(String nombre, String edad, String msg,String nombre_archivo,
            String firma){
        this.nombre_archivo = nombre_archivo;
        this.firma = firma;
        this.nombre = nombre;
        this.edad = edad;
        this.mensaje = msg;
        this.contenido = "Nombre: " + nombre + "\n" + "Edad: " + edad + "\n" 
                + "Mensaje: " + msg;
        System.out.println(this.contenido);
    }
    
    public void generarPDF(){
        try {
            //FileOutputStream file = FileOutputStream(nombre + ".pdf");
            FileOutputStream file = new FileOutputStream(this.nombre_archivo + ".pdf");
            Document doc = new Document();
            PdfWriter.getInstance(doc, file);
            doc.open();
            
            doc.add(new Paragraph(this.contenido));
            doc.close();
            System.out.println("Se creo el archivo uwu");
        } catch (Exception e) {
            System.out.println("Falla al intentar generar PDF");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public void firmarPDF(){
        try {
            Document doc = new Document();
            
            PdfWriter.getInstance(doc, new FileOutputStream(this.nombre_archivo+".pdf"));
            doc.open();
            
            doc.add(new Paragraph(this.contenido + "\nFirma digital: " + this.firma));
            doc.close();
            System.out.println("Se firmo el archivo");
        } catch (Exception e) {
            System.out.println("Algo a pasado al abrir el archivo al "
                    + "intentar firmarlo");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    public String getContenidoPDF(){
        try{
            PdfReader lector = new PdfReader(this.nombre_archivo + ".pdf");
            // por cada pagina, leeremos su contenido
            int totalPaginas = lector.getNumberOfPages();
            for (int iPagina = 1; iPagina <= totalPaginas; iPagina++) {
                // extraemos el contenido de la pagina
                this.contenido = PdfTextExtractor.getTextFromPage(lector, iPagina);
            }
        }catch(Exception e){
            System.out.println("Fallo al recuperar contenido");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
        return this.contenido;
    }
    
    /**
     * Para obtener variables de la clase a la hora de validar
     */
    public void getVariables(){
        try{
            PdfReader lector = new PdfReader(this.nombre_archivo + ".pdf");
            // por cada pagina, leeremos su contenido
            int totalPaginas = lector.getNumberOfPages();
            String text = "";
            for (int iPagina = 1; iPagina <= totalPaginas; iPagina++) {
                // extraemos el contenido de la pagina
                //System.out.println(PdfTextExtractor.getTextFromPage(lector, iPagina));;
                text = PdfTextExtractor.getTextFromPage(lector, iPagina);
            }
                        //Obtenermos las viables que necesitamos
            System.out.println(text);
            //Obtenemos la variable nombre
            this.nombre = text.substring("Nombre: ".length(),
                    text.indexOf("Edad: ")-1);
            this.edad = text.substring(text.indexOf("Edad: ") + 
                    "Edad: ".length(), text.indexOf("Mensaje: ")-1);
            this.mensaje = text.substring(text.indexOf("Mensaje: ") + 
                    "Mensaje: ".length(), text.indexOf("Firma digital:")-1);
            this.firma = text.substring(text.indexOf("Firma digital:") + 
                    "Firma digital:".length()+1, text.length());
            //Imprimimos para ver que sea cierto
            System.out.println(nombre);
            System.out.println(edad);
            System.out.println(mensaje);
            System.out.println(firma);
            
            //Ahora si ya podran consultar informacion que necesiten siempre y vuando
            //este en este formato
            lector.close();//Para evitar problemas
        }catch(Exception e){
            System.out.println("Fallo al recuperar contenido");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Para obtener variables de la clase a la hora de validar pero ahora }
     * con ruta
     * @param nombre
     */
    public void getVariables(String nombre){
        try{
            PdfReader lector = new PdfReader(nombre + ".pdf");
            // por cada pagina, leeremos su contenido
            int totalPaginas = lector.getNumberOfPages();
            String text = "";
            for (int iPagina = 1; iPagina <= totalPaginas; iPagina++) {
                // extraemos el contenido de la pagina
                //System.out.println(PdfTextExtractor.getTextFromPage(lector, iPagina));;
                text = PdfTextExtractor.getTextFromPage(lector, iPagina);
            }
            
            //Obtenemos la variable nombre
            this.nombre = text.substring("Nombre: ".length(),
                    text.indexOf("Edad: ")-1);
            this.edad = text.substring(text.indexOf("Edad: ") + 
                    "Edad: ".length(), text.indexOf("Mensaje: ")-1);
            this.mensaje = text.substring(text.indexOf("Mensaje: ") + 
                    "Mensaje: ".length(), text.indexOf("Firma digital:")-1);
            this.firma = text.substring(text.indexOf("Firma digital:") + 
                    "Firma digital:".length()+1, text.length());
            //Imprimimos para ver que sea cierto
            System.out.println(nombre);
            System.out.println(edad);
            System.out.println(mensaje);
            System.out.println(firma);
            
            lector.close();//Para evitar problemas
        }catch(Exception e){
            System.out.println("Fallo al recuperar contenido");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }
    
    /**
     * Es exactamente lo mismo pero con ruta
     * la ruta al final solo debe de ser el nombre de la carpeta
     * @param nombre nombre del pdf
     * @param ruta la ruta donde se guarda
     */
    public void getVariables(String nombre, String ruta){
        try{
            PdfReader lector = new PdfReader(ruta + "/" + nombre + ".pdf");
            // por cada pagina, leeremos su contenido
            int totalPaginas = lector.getNumberOfPages();
            String text = "";
            for (int iPagina = 1; iPagina <= totalPaginas; iPagina++) {
                // extraemos el contenido de la pagina
                //System.out.println(PdfTextExtractor.getTextFromPage(lector, iPagina));;
                text = PdfTextExtractor.getTextFromPage(lector, iPagina);
            }
            
            //Obtenemos la variable nombre
            this.nombre = text.substring("Nombre: ".length(),
                    text.indexOf("Edad: ")-1);
            this.edad = text.substring(text.indexOf("Edad: ") + 
                    "Edad: ".length(), text.indexOf("Mensaje: ")-1);
            this.mensaje = text.substring(text.indexOf("Mensaje: ") + 
                    "Mensaje: ".length(), text.indexOf("Firma digital:")-1);
            this.firma = text.substring(text.indexOf("Firma digital:") + 
                    "Firma digital:".length()+1, text.length());
            //Imprimimos para ver que sea cierto
            System.out.println(this.nombre);
            System.out.println(edad);
            System.out.println(mensaje);
            System.out.println(firma);
            
            lector.close();//Para evitar problemas
        }catch(Exception e){
            System.out.println("Fallo al recuperar contenido");
            System.out.println(e.getMessage());
            e.printStackTrace();
        }
    }

    public String getContenido() {
        return contenido;
    }

    public void setContenido(String contenido) {
        this.contenido = contenido;
    }

    public String getNombre_archivo() {
        return nombre_archivo;
    }

    public void setNombre_archivo(String nombre_archivo) {
        this.nombre_archivo = nombre_archivo;
    }

    public String getFirma() {
        return firma;
    }

    public void setFirma(String firma) {
        this.firma = firma;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getEdad() {
        return edad;
    }

    public void setEdad(String edad) {
        this.edad = edad;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }
    
}
