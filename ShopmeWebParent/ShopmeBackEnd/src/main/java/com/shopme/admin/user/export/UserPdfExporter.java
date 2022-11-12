package com.shopme.admin.user.export;

import java.awt.Color;
import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.swing.text.Document;

import com.lowagie.text.Font;
import com.lowagie.text.FontFactory;
import com.lowagie.text.PageSize;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Phrase;
import com.lowagie.text.pdf.PdfCell;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfTable;
import com.lowagie.text.pdf.PdfWriter;
import com.shopme.admin.AbstractExporter;
import com.shopme.common.entity.User;

public class UserPdfExporter extends AbstractExporter{

	public void export(List<User> listUsers, HttpServletResponse response) throws IOException {
		super.setResponseHeader(response, "application/pdf", ".pdf", "users_");
		
		com.lowagie.text.Document document  = new com.lowagie.text.Document(PageSize.A4);
		PdfWriter.getInstance(document, response.getOutputStream());
		
		document.open();
		Font font =  FontFactory.getFont(FontFactory.HELVETICA_BOLD);
		font.setSize(18);
		font.setColor(Color.BLUE);
		Paragraph paragraph = new Paragraph("List of users", font);
		paragraph.setAlignment(Paragraph.ALIGN_CENTER);
		
		
		document.add(new Paragraph(paragraph));
		
		PdfPTable table = new PdfPTable(6); 
		table.setWidthPercentage(100f);
		table.setSpacingBefore(10);
		table.setWidths(new float[] {1.5f, 3.5f, 3.0f, 3.0f, 3.0f, 1.5f});
		 
		writeTableHeader(table);
		writeTableData(table, listUsers);
		
		document.add(table);
		document.close();
		
		
	}

	private void writeTableData(PdfPTable table, List<User> listUsers) {
		for(User user : listUsers) {
			table.addCell(String.valueOf(user.getId()));
			table.addCell(user.getEmail());
			table.addCell(user.getFirstName());
			table.addCell(user.getLastName());
			table.addCell(user.getRoles().toString());
			table.addCell(String.valueOf(user.isEnabled()));
		}
	}

	private void writeTableHeader(PdfPTable table) {
		PdfPCell cell = new PdfPCell();
		cell.setBackgroundColor(Color.BLUE);
		cell.setPadding(5);
		
		
		Font font =  FontFactory.getFont(FontFactory.HELVETICA);
		font.setSize(16);
		font.setColor(Color.WHITE);
		
		cell.setPhrase(new Phrase("User Id"));	
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("E-mail"));	
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("First Name"));	
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Last Name"));	
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Roles"));	
		table.addCell(cell);
		
		cell.setPhrase(new Phrase("Enable"));	
		table.addCell(cell);
		
	}

}
