package primeministers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;

public class Writer extends IO {
  public Attributes attributes() { return this.table.attributes(); }
  
  public static File filenameOfHTML() {
    File file = IO.directoryOfPages();
    return new File(file.getPath() + file.getPath() + "index.html");
  }
  
  public Table table(Table paramTable) {
    this.table = paramTable;
    try {
      File file = filenameOfHTML();
      FileOutputStream fileOutputStream = new FileOutputStream(file);
      BufferedWriter bufferedWriter = new BufferedWriter(new OutputStreamWriter(fileOutputStream, IO.encodingSymbol()));
      writeHeaderOn(bufferedWriter);
      writeTableBodyOn(bufferedWriter);
      writeFooterOn(bufferedWriter);
      bufferedWriter.close();
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      unsupportedEncodingException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return this.table;
  }
  
  public ArrayList<Tuple> tuples() { return this.table.tuples(); }
  
  public void writeAttributesOn(BufferedWriter paramBufferedWriter) {
    try {
      paramBufferedWriter.write("\t\t\t\t\t\t");
      paramBufferedWriter.write("<tr>\n");
      for (String str : attributes().names()) {
        paramBufferedWriter.write("\t\t\t\t\t\t\t");
        paramBufferedWriter.write("<td class=\"center-pink\"><strong>");
        paramBufferedWriter.write(str);
        paramBufferedWriter.write("</strong></td>\n");
      } 
      paramBufferedWriter.write("\t\t\t\t\t\t");
      paramBufferedWriter.write("</tr>\n");
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public void writeFooterOn(BufferedWriter paramBufferedWriter) {
    Class<?> clazz = getClass();
    String str1 = clazz.getName();
    Calendar calendar = Calendar.getInstance();
    int i = calendar.get(1);
    int j = calendar.get(2) + 1;
    int k = calendar.get(5);
    int m = calendar.get(11);
    int n = calendar.get(12);
    int i1 = calendar.get(13);
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append(" on ");
    stringBuffer.append(String.format("%1$04d", new Object[] { Integer.valueOf(i) }));
    stringBuffer.append("/");
    stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(j) }));
    stringBuffer.append("/");
    stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(k) }));
    String str2 = stringBuffer.toString();
    stringBuffer = new StringBuffer();
    stringBuffer.append(" at ");
    stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(m) }));
    stringBuffer.append(":");
    stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(n) }));
    stringBuffer.append(":");
    stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(i1) }));
    String str3 = stringBuffer.toString();
    try {
      paramBufferedWriter.write("\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</tbody>\n</table>\n<hr>\n<div class=\"right-small\">Created by " + str1 + str2 + str3 + "</div>\n</body>\n</html>\n");
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public void writeHeaderOn(BufferedWriter paramBufferedWriter) {
    try {
      paramBufferedWriter.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html lang=\"ja\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">\n<meta name=\"keywords\" content=\"Smalltalk,Oriented,Programming\">\n<meta name=\"description\" content=\"Prime Ministers\">\n<meta name=\"author\" content=\"AOKI Atsushi\">\n<link rev=\"made\" href=\"http://www.cc.kyoto-su.ac.jp/~atsushi/\">\n<link rel=\"index\" href=\"index.html\">\n<style type=\"text/css\">\n<!--\nbody {\n\tbackground-color : #ffffff;\n\tmargin : 20px;\n\tpadding : 10px;\n\tfont-family : serif;\n\tfont-size : 10pt;\n}\na {\n\ttext-decoration : underline;\n\tcolor : #000000;\n}\na:link {\n\tbackground-color : #ffddbb;\n}\na:visited {\n\tbackground-color : #ccffcc;\n}\na:hover {\n\tbackground-color : #dddddd;\n}\na:active {\n\tbackground-color : #dddddd;\n}\ndiv.belt {\n\tbackground-color : #eeeeee;\n\tpadding : 0px 4px;\n}\ndiv.right-small {\n\ttext-align : right;\n\tfont-size : 8pt;\n}\nimg.borderless {\n\tborder-width : 0px;\n\tvertical-align : middle;\n}\ntable.belt {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tbackground-color : #ffffff;\n\tpadding : 0px 0px;\n\twidth : 100%;\n}\ntable.content {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tpadding : 2px 2px;\n}\ntd.center-blue {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ddeeff;\n}\ntd.center-pink {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffddee;\n}\ntd.center-yellow {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffffcc;\n}\n-->\n</style>\n<title>Prime Ministers</title>\n</head>\n<body>\n<div class=\"belt\">\n<h2>Prime Ministers</h2>\n</div>\n<table class=\"belt\" summary=\"table\">\n\t<tbody>\n\t\t<tr>\n\t\t\t<td>\n\t\t\t\t<table class=\"content\" summary=\"table\">\n\t\t\t\t\t<tbody>\n");
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public void writeTableBodyOn(BufferedWriter paramBufferedWriter) {
    writeAttributesOn(paramBufferedWriter);
    writeTuplesOn(paramBufferedWriter);
  }
  
  public void writeTuplesOn(BufferedWriter paramBufferedWriter) {
    try {
      byte b = 0;
      for (Tuple tuple : tuples()) {
        paramBufferedWriter.write("\t\t\t\t\t\t");
        paramBufferedWriter.write("<tr>\n");
        for (String str : tuple.values()) {
          paramBufferedWriter.write("\t\t\t\t\t\t\t");
          if (b % 2 == 0) {
            paramBufferedWriter.write("<td class=\"center-blue\">");
          } else {
            paramBufferedWriter.write("<td class=\"center-yellow\">");
          } 
          paramBufferedWriter.write(str);
          paramBufferedWriter.write("</td>\n");
        } 
        paramBufferedWriter.write("\t\t\t\t\t\t");
        paramBufferedWriter.write("</tr>\n");
        b++;
      } 
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/Writer.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */