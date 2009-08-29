/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package pruebas;


import java.math.BigDecimal;
import java.util.Iterator;
import org.zkoss.zul.Decimalbox;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Label;
import org.zkoss.zul.Row;
import org.zkoss.zul.Rows;
import java.awt.*;
import java.awt.image.*;
import java.awt.geom.*;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

public class ExpandingMenuItemRows extends Rows {

    boolean odd = false;
			boolean odd1 = false;
    /*
  * This is just an arbitrary custom method that we can call from a zscript.
  * In it we programmatically create all of the elements that appear in the other
  * rows. The code below simply creates:
  *  <row><label value=""/><hbox>$<textbox value=""/></hbox></row>
  * and sets the two value attributes whatever was passed into the method.
  */

 public void addRow(String name, BigDecimal price){
     
             Label label = new Label();
             label.setValue(name);
             Label dollar = new Label();
             dollar.setValue("$");
             Decimalbox textbox = new Decimalbox();
             textbox.setValue(price);
             Hbox hbox = new Hbox();
             hbox.appendChild(dollar);
             hbox.appendChild(textbox);
             Row row = new Row();
             row.appendChild(label);
             row.appendChild(hbox);
           
             this.appendChild(row);
             for (Iterator it = this.getVisibleChildrenIterator(); it.hasNext();) {
                Row object = (Row) it.next();
               
                    System.out.println(object.getChildAttrs(0));
//                    System.out.println(object.getChildAttrs(1));
                }
 }



public void update() {
    org.zkoss.image.Image img;

//    File f = new File();
//    f.createTempFile(_zclass, _zclass, f);
//     ByteArrayOutputStream b;
//      b.writeTo(b);
//    FileOutputStream a = new FileOutputStream("D:\\MIARCHIVO.gif");
//    a.write(2);
//				BufferedImage bufferimg = newimg((odd1 = !odd1));
//				img.setContent(bufferimg);
}
			BufferedImage newimg(boolean update) {
				BufferedImage bi = new BufferedImage(150, 150, BufferedImage.TYPE_INT_RGB);
				Graphics2D g2d = bi.createGraphics();
				g2d.setStroke(new BasicStroke(5));
				Line2D line = null; Rectangle2D retangle = null ;
				if (update) {
					line = new Line2D.Double(10, 10, 130, 130);
					retangle = new Rectangle2D.Double(25,25,85,85);
				}else {
					line = new Line2D.Double(10, 130, 130, 10);
					retangle = new Rectangle2D.Double(25,25,85,85);
				}
				g2d.setColor(update ? Color.cyan : Color.RED);
				g2d.draw(line);
				g2d.setColor(update ? Color.yellow : Color.pink);
				g2d.draw(retangle);
				return bi;
			}
			BufferedImage bufferimg = newimg(false);
//			img.setContent(bufferimg);

 }

