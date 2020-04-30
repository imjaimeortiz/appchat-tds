package informacionUso;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.List;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;

public class Pdf {
	public void crearPdf(List<String> toPdf)throws FileNotFoundException, DocumentException {
		FileOutputStream archivo = new FileOutputStream("C:\\Users\\Luu\\Desktop\\Contactos.pdf");
		Document documento = new Document();
		PdfWriter.getInstance(documento, archivo);
		documento.open();
		for(String s : toPdf) {
			documento.add(new Paragraph(s));
		}
		documento.close();
		
		
	}

}
