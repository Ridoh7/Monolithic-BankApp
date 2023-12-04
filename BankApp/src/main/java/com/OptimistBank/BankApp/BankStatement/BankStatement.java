package com.OptimistBank.BankApp.BankStatement;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.OutputStream;

@Slf4j
public class BankStatement {

    private static final String FILE="C:\\Users\\ridoh.lawal\\Documents\\Bank Statement.Sample.pdf";

    public static void generateStatement() throws FileNotFoundException, DocumentException {

        Rectangle rectangle=new Rectangle(PageSize.A4);
        Document document=new Document(rectangle);
        log.info("Setting size for document");

        OutputStream outputStream=new FileOutputStream(FILE);
        PdfWriter.getInstance(document,outputStream);
        document.open();
        document.add(new Paragraph("Sample test"));
        document.add(new Chunk("IJA Bank Statement"));
        document.add(new Phrase("\nI'm enjoying this"));
        document.add(new Phrase(" "));

        Anchor anchor=new Anchor("I-academy website");
        anchor.setReference("https://www.i-academy.org");
        document.add(anchor);

        List orderList=new List(List.ORDERED);
        orderList.add(new ListItem("Emperor"));
        orderList.add(new ListItem("Pelumi"));
        document.add(orderList);

        List unOrderedList =new List(List.ORDERED);
        unOrderedList.add(new ListItem("Testing"));
        unOrderedList.add(new ListItem("Testing2"));
        document.add(unOrderedList);
        document.add(new Paragraph(" "));

        log.info("Setting table to 3 columns");
        PdfPTable table = new PdfPTable(3);
        PdfPCell serialNo = new PdfPCell(new Paragraph("S/N"));
        PdfPCell firstNameColumn = new PdfPCell(new Paragraph("First Name"));
        PdfPCell lastnameColumn = new PdfPCell(new Paragraph("Last Name"));

        log.info("populating list");
        String[] firstNamesArray={"Adeolu","Oyin","Noah"};
        String[] lastNamesArray={"Oduniyi","Alasoluyi","Johnson"};


        table.addCell(serialNo);
        table.addCell(firstNameColumn);
        table.addCell(lastnameColumn);

        for (int i=0;i<firstNamesArray.length;i++){
            PdfPCell serialNo1= new PdfPCell(new Paragraph(String.valueOf(i+1)));
            PdfPCell firstName= new PdfPCell(new Paragraph(firstNamesArray[i]));
            PdfPCell lastName=new PdfPCell(new Paragraph(lastNamesArray[i]));
            table.addCell(serialNo1).setBackgroundColor(BaseColor.BLUE);
            table.addCell(firstName);
            table.addCell(lastName);
        }

        document.add(table);



        document.close();
        log.info("File has been created");
    }
    public static void main(String[] args) throws DocumentException, FileNotFoundException {
        System.out.println(FILE);
        generateStatement();

    }
}
