/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */
package ai.agenticity.cv.generator.cl;

import com.itextpdf.text.Chapter;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.sql.SQLException;

import com.itextpdf.text.Chunk;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.List;
import com.itextpdf.text.ListItem;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.TabSettings;
import com.itextpdf.text.TabStop;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfAction;
import com.itextpdf.text.pdf.PdfDestination;
import com.itextpdf.text.pdf.PdfNumber;
import com.itextpdf.text.pdf.PdfOutline;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.collection.PdfTargetDictionary;
import ai.agenticity.cv.generator.domain.WorkExperience;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
//import com.lowagie.database.DatabaseConnection;
//import com.lowagie.database.HsqldbConnection;
//import com.lowagie.filmfestival.Movie;
//import com.lowagie.filmfestival.PojoFactory;

/**
 * This example explains the use of portable collections, a new feature
 * introduced in PDF 1.7
 */
public class CL_with_field {

    /**
     * The resulting PDF.
     */
    public static final String RESULT = "BULLINI_CoverLetter.pdf";//results/part4/chapter16/
    /**
     * The path to an image used in the example.
     */
    public static final String IMG_BOX = "info.png";//resources/img/
    /**
     * Path to the resources.
     */
    public static final String RESOURCE = "info.png";//resources/posters/
    /**
     * The relative widths of the PdfPTable columns.
     */

    public static final float SPACING_BEFORE = 10;
    public static final float SPACING_AFTER = 10;

    public static final Font times_roman_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);
    public static final Font times_roman_10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    public static final Font times_roman_bold_10 = new Font(Font.FontFamily.TIMES_ROMAN, 10);
    public static final Font times_roman_bold_12 = new Font(Font.FontFamily.TIMES_ROMAN, 12);

    /**
     * Creates the PDF.
     *
     * @return the bytes of a PDF file.
     * @throws DocumentException
     * @throws IOException
     * @throws SQLException
     */
    public void createPdf(String filename) throws DocumentException, IOException, SQLException {
        // step 1
        Document document = new Document(PageSize.A4);
        // step 2
        FileOutputStream fos = new FileOutputStream(filename);
        PdfWriter writer = PdfWriter.getInstance(document, fos);
        // --------
        document.open();
        //---------------- OUTLINE  bookmarks - tree
        writer.setViewerPreferences(PdfWriter.PageModeUseOutlines);
        PdfOutline root = writer.getRootOutline();
        PdfOutline movieBookmark;
        PdfOutline link;
        movieBookmark = new PdfOutline(root,
                new PdfDestination(
                        PdfDestination.FITH, writer.getVerticalPosition(true)),
                "title", true);
        //---------------- Main Parts
        Paragraph par1_title = new Paragraph();
        //par1_title.setA
        //Paragraph par1_title = new Paragraph("JAVA SENOIR DEVELOPER");
        applySubjectStyle(par1_title);

        Paragraph par1_recipient_address = new Paragraph();
        Phrase phr1 = new Phrase();
        Chunk chunk1_1 = new Chunk("Mr. Alexis");
        Chunk chunk1_2 = new Chunk("HR Recruiter");
        Chunk chunk1_3 = new Chunk("Company");
        DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        Calendar cal = Calendar.getInstance();
        String curDay = dateFormat.format(cal.getTime());
        //String curDay = cal.DAY_OF_MONTH+"/0"+cal.MONTH+"/"+cal.YEAR;

        //java.util.Date d=gc.getTime();
        Chunk chunk1_4 = new Chunk("Gex, " + curDay);

        phr1.add(chunk1_1);
        phr1.add(Chunk.NEWLINE);
        phr1.add(chunk1_2);
        phr1.add(Chunk.NEWLINE);
        phr1.add(chunk1_3);
        phr1.add(Chunk.NEWLINE);
        phr1.add(Chunk.NEWLINE);
        phr1.add(chunk1_4);
        phr1.add(Chunk.NEWLINE);
        phr1.setFont(times_roman_bold_12);
        par1_recipient_address.add(phr1);
        applyAddressStyle(par1_recipient_address);
//-----------------------------------------
        Paragraph par2_subject_00 = new Paragraph("Subject: Response to the job advertisement for " + "Java Developer");
        //par2_subject_title.setAlignment(alignment);
        //par1_title.setA
        //Paragraph par1_title = new Paragraph("JAVA SENOIR DEVELOPER");
        applySubjectStyle(par2_subject_00);
        Paragraph par2_subject = new Paragraph();

        Phrase phr2_1 = new Phrase();
        //Chunk chunk2_1 = new Chunk("Java Developer");
        //PdfAction action_js = PdfAction.javaScript(RESOURCE, writer);//<<<<<<<<<
        //chunk2_1.setAction(action_js);

        //phr2_1.add(chunk2_1);
        //phr2_1.add(chunk2_2);
        //phr2_1.setFont(times_roman_bold_12);
        par2_subject.add(phr2_1);
//-------------------------------------------                         
//        Image img = Image.getInstance(IMG_BOX);
//        document.add(img);
        List list = new List(List.UNORDERED, 20);
        PdfDestination PDFObject_DEST = new PdfDestination(PdfDestination.FIT);
        PDFObject_DEST.addFirst(new PdfNumber(1));
        PdfTargetDictionary target;
        Chunk chunk;
        ListItem item;
        PdfAction action = null;

        Paragraph par3_title = new Paragraph("Dear Hiring Manager");
        //par1_title.setA
        //Paragraph par1_title = new Paragraph("JAVA SENOIR DEVELOPER");
        applySubjectStyle(par3_title);
        Paragraph par3 = new Paragraph();
        //--------------------------------------------------
        Paragraph par4_salutation_title = new Paragraph();
        applySubjectStyle(par4_salutation_title);
        Paragraph par4_salutation = new Paragraph();
        Phrase phr4_1 = new Phrase();
        Chunk chunk4_1 = new Chunk("In summary, for all the above reasons, I'm confident that I would be a valuable addition to your workforce and an excellent match for this position");
        Chunk chunk4_2 = new Chunk("Thank you for you time and consideration.");
        Chunk chunk4_2a = new Chunk("I look forward to hear from you soon.");

        PdfAction action_js_4 = PdfAction.javaScript(RESOURCE, writer);//<<<<<<<<<
        chunk4_1.setAction(action_js_4);

        phr4_1.add(chunk4_1);
        phr4_1.add(Chunk.NEWLINE);
        phr4_1.add(chunk4_2);
        phr4_1.setFont(times_roman_bold_12);
        phr4_1.add(chunk4_2a);
        par4_salutation.add(phr4_1);
        //-------------------------------------------------------------------
        Paragraph par5_signature_title = new Paragraph();
        Paragraph par5_signature_body = new Paragraph();
        applySubjectStyle(par5_signature_title);
      
        Phrase phr5_1 = new Phrase();
        Chunk chunk5_1 = new Chunk("Syncerely");
        phr5_1.add(Chunk.NEWLINE);
        
        Phrase phr5_2 = new Phrase();
        Chunk chunk5_2 = new Chunk("Francesco M. Bullini");
        phr5_2.add(chunk5_1);
       

        phr5_2.add(chunk5_2);
        phr5_2.setFont(times_roman_bold_12);
        par5_signature_title.add(phr5_1);
         
        par5_signature_body.add(phr5_2);
        //-----------------------------------------------------
        Chapter ch1 = new Chapter("", 1);
        ch1.setNumberDepth(0);
        Chapter ch2 = new Chapter("", 2);
        ch2.setNumberDepth(0);
        Chapter ch3 = new Chapter("", 3);
        ch3.setNumberDepth(0);
        Chapter ch4 = new Chapter("", 4);
        ch4.setNumberDepth(0);
        Chapter ch5 = new Chapter("", 5);
        ch5.setNumberDepth(0);
        //ch1.setAutomaticNumber(0);
//        Section s1 = ch1.addSection(par1_title);
//        s1.setNumberDepth(0);
//        Section s2 = ch1.addSection(par1);
//        s2.setNumberDepth(0);
        ch1.add(par1_title);
        ch1.add(par1_recipient_address);
        ch1.add(par2_subject_00);
        //ch1.add(par2_subject);
        ch1.add(par3_title);
        ch1.add(par3);
        ch1.add(par4_salutation_title);
        ch1.add(par4_salutation);
        ch1.add(par5_signature_title);
        //http://stackoverflow.com/questions/11120775/itext-image-resize
        Image image1 = Image.getInstance("C:\\Users\\Francesco\\pers\\___LAVORO___\\__APPLICATIONS_SENT-DOCS\\Enterprises\\signature.JPG");
        image1.scaleAbsolute(200, 80);
        ch1.add(image1);
        ch1.add(par5_signature_body);

        //Section s1=ch1.addSection(par5);
        document.add(ch1);
//        document.add(ch2);
//        document.add(ch3);
//        document.add(ch4);
//        document.add(ch5);

        document.close();
        writer.close();
    }

    /**
     * Creates the PDF.
     *
     * @return the bytes of a PDF file.
     * @throws DocumentExcetpion
     * @throws IOException
     * @throws SQLException
     */
    public byte[] createWorkExpPage(WorkExperience work_exp) throws DocumentException, IOException {
        // step 1
        Document document = new Document();
        // step 2
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        PdfWriter.getInstance(document, baos);
        // step 3
        document.open();
        // step 4
        Paragraph p = new Paragraph(work_exp.getCompanyName(),
                FontFactory.getFont(BaseFont.HELVETICA, BaseFont.WINANSI, BaseFont.NOT_EMBEDDED, 16));
        document.add(p);
        document.add(Chunk.NEWLINE);

        Chunk c1 = new Chunk("Year: " + work_exp.getStartYear() + " ");
        Chunk c2 = new Chunk("Role: " + work_exp.getRole() + " ");
        Chunk c3 = new Chunk("Duration: " + work_exp.getDuration() + "\n");
        document.add(Chunk.NEWLINE);
        Chunk c4 = new Chunk("Duties: \n" + work_exp.getDuties() + "\n");
        document.add(Chunk.NEWLINE);
        Chunk c5 = new Chunk("Achievements: \n" + work_exp.getAchievements() + "\n");
        //TODO collection and then add collection ...
        Paragraph p_text = new Paragraph();
        p_text.add(c1);
        p_text.add(c2);
        p_text.add(c3);
        p_text.add(c4);
        p_text.add(c5);
        document.add(p_text);

//      Original        
//        PdfPTable table = new PdfPTable(WIDTHS);
//        table.addCell(Image.getInstance(String.format(RESOURCE, work_exp.getCompanyName())));
//        PdfPCell cell = new PdfPCell();
//        cell.addElement(new Paragraph("Role: " + work_exp.getCompanyName()));
//        cell.addElement(new Paragraph("Duties: " + work_exp.getCompanyName()));
//        cell.addElement(new Paragraph("Achievements: " + work_exp.getCompanyName()));
//        cell.addElement(new Paragraph("Year: " + work_exp.getStartYear()));
//        cell.addElement(new Paragraph("Duration: " + work_exp.getCompanyName()));
//        table.addCell(cell);
//        document.add(table);
        PdfDestination dest = new PdfDestination(PdfDestination.FIT);
        dest.addFirst(new PdfNumber(0));
        PdfTargetDictionary target = new PdfTargetDictionary(false);
        Chunk chunk = new Chunk("Go to original document");
        PdfAction action = PdfAction.gotoEmbedded(null, target, dest, false);
        chunk.setAction(action);
        document.add(chunk);
        // step 5
        document.close();
        return baos.toByteArray();
    }

    public static void applyAddressStyle(Paragraph par1_title) {
        par1_title.setSpacingBefore(SPACING_BEFORE);
        par1_title.setSpacingAfter(SPACING_AFTER);
        par1_title.setAlignment(Element.ALIGN_RIGHT);
        par1_title.setIndentationLeft(150);
        par1_title.setIndentationRight(50);
        par1_title.setFont(times_roman_bold_12);
        TabSettings ts=new TabSettings();
        java.util.List<TabStop> tabStops = ts.getTabStops();
        //ts.setTabStops(tabStops);
        par1_title.setTabSettings(null);
    }

    public static void applySubjectStyle(Paragraph par1_title) {
        par1_title.setSpacingBefore(SPACING_BEFORE);
        par1_title.setSpacingAfter(SPACING_AFTER);
        par1_title.setAlignment(Element.ALIGN_LEFT);
        //par1_title.setIndentationLeft(50);
        //par1_title.setIndentationRight(50);
        par1_title.setFont(times_roman_bold_12);
    }

    public static void applyStyleText(Paragraph par) {
//        par1_title.setSpacingBefore(SPACING_BEFORE);
//        par1_title.setSpacingAfter(SPACING_AFTER);
        par.setAlignment(Element.ALIGN_LEFT);
        par.setAlignment(Element.ALIGN_JUSTIFIED);
        par.setIndentationLeft(50);
        par.setIndentationRight(50);
        par.setFont(times_roman_10);
    }

    /**
     * Main method.
     *
     * @param args no arguments needed
     * @throws DocumentException
     * @throws IOException
     * @throws SQLException
     */
    public static void main(String[] args) throws DocumentException, IOException, SQLException {
        new CL_with_field().createPdf(RESULT);
    }
}
