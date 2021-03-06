/*
 * This class is part of the book "iText in Action - 2nd Edition"
 * written by Bruno Lowagie (ISBN: 9781935182610)
 * For more info, go to: http://itextpdf.com/examples/
 * This example only works with the AGPL version of iText.
 */

package part1.chapter03;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfWriter;

public class GraphicsStateStack {
    
    /** The resulting PDF. */
    public static final String RESULT
        =ColumnMovies1.class.getResource("/").getPath().substring(1)+  "results/part1/chapter03/graphics_state.pdf";

    /**
     * Main method.
     *
     * @param    args    no arguments needed
     * @throws DocumentException 
     * @throws IOException
     */
    public static void main(String[] args)
        throws IOException, DocumentException {
    	// step 1
        Document document = new Document(new Rectangle(200, 120));
        // step 2
        PdfWriter writer
             = PdfWriter.getInstance(document, new FileOutputStream(RESULT));
        // step 3
        document.open();
        // step 4
        PdfContentByte canvas = writer.getDirectContent();
        // state 1:
        canvas.setRGBColorFill(0xFF, 0x45, 0x00);
        // fill a rectangle in state 1
        canvas.rectangle(10, 10, 60, 60);
        canvas.fill();//just fill,without a border
        canvas.saveState();
        // state 2;
        canvas.setLineWidth(3);//has a border 3pt, default color
        canvas.setRGBColorFill(0x8B, 0x00, 0x00);
        // fill and stroke a rectangle in state 2
        canvas.rectangle(40, 20, 60, 60);
        canvas.fillStroke();//fill inside the rectangle, and draw the border
        canvas.saveState();
        // state 3:
        canvas.concatCTM(1, 0, 0.1f, 1, 0, 0);//change the shape,not square anymore
        canvas.setRGBColorStroke(0xFF, 0x45, 0x00);//change the color of border
        canvas.setRGBColorFill(0xFF, 0xD7, 0x00);//change the color of fill
        // fill and stroke a rectangle in state 3
        canvas.rectangle(70, 30, 60, 60);
        canvas.fillStroke();
        canvas.restoreState();//you can't invoke this method before saveState is called, and their quantity should be equal
        // stroke a rectangle in state 2
        canvas.rectangle(100, 40, 60, 60);
        canvas.stroke();//only the border is drawed, not filled
        canvas.restoreState();
        // fill and stroke a rectangle in state 1
        canvas.rectangle(130, 50, 60, 60);
        canvas.fillStroke();//fill with a border default line width 1user unit
        // step 5
        document.close();
    }
}
