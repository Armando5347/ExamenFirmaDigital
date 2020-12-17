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
    
    public PDF(){
        
    }

    public PDF(String contenido, String nombre_archivo, String firma){
        this.contenido = contenido;
        this.nombre_archivo = nombre_archivo;
        this.firma = firma;
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
            PdfReader lector = new PdfReader(this.nombre_archivo + ".pdf");
            // por cada pagina, leeremos su contenido
            int totalPaginas = lector.getNumberOfPages();
            for (int iPagina = 1; iPagina <= totalPaginas; iPagina++) {
                // extraemos el contenido de la pagina
                this.contenido = PdfTextExtractor.getTextFromPage(lector, iPagina);
            }
            this.contenido = this.contenido+this.firma;
            FileOutputStream file = new FileOutputStream(this.nombre_archivo + ".pdf");
            Document doc = new Document();
            PdfWriter.getInstance(doc, file);
            doc.open();
            
            doc.add(new Paragraph(this.contenido));
            doc.close();
            System.out.println("Firmo el archivo");
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
    
}
