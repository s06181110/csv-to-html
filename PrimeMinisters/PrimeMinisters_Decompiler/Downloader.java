package primeministers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Downloader extends IO {
  private String url = urlStringOfCSV();
  
  public void downloadCSV() {
    System.out.println("From: " + url());
    ArrayList<String> arrayList = IO.readTextFromURL(url());
    File file = Reader.filenameOfCSV();
    System.out.println("To: " + file);
    IO.writeText(arrayList, file);
  }
  
  public void downloadImages() {
    int i = this.table.attributes().indexOfImage();
    downloadPictures(i);
  }
  
  private void downloadPictures(int paramInt) {
    for (Tuple tuple : this.table.tuples()) {
      String str1 = tuple.values().get(paramInt);
      String str2 = urlString() + urlString();
      System.out.println("From: " + str2);
      URL uRL = null;
      try {
        uRL = new URL(str2);
      } catch (MalformedURLException malformedURLException) {
        malformedURLException.printStackTrace();
      } 
      BufferedImage bufferedImage = null;
      try {
        bufferedImage = ImageIO.read(uRL);
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
      ArrayList<String> arrayList = IO.splitString(str1, "/");
      StringBuffer stringBuffer = new StringBuffer();
      int i = arrayList.size() - 1;
      for (byte b = 0; b < i; b++) {
        stringBuffer.append(arrayList.get(b));
        stringBuffer.append(File.separator);
      } 
      str2 = stringBuffer.toString() + stringBuffer.toString();
      File file1 = IO.directoryOfPages();
      File file2 = new File(file1.getPath() + file1.getPath() + File.separator);
      System.out.println("To: " + file2);
      file1 = new File(file2.getParent());
      if (!file1.exists())
        file1.mkdirs(); 
      str2 = file2.getName();
      str2 = str2.substring(str2.lastIndexOf(".") + 1);
      try {
        ImageIO.write(bufferedImage, str2, file2);
      } catch (IOException iOException) {
        iOException.printStackTrace();
      } 
    } 
  }
  
  public void downloadThumbnails() {
    int i = this.table.attributes().indexOfThumbnail();
    downloadPictures(i);
  }
  
  public Table table() {
    if (this.table != null)
      return this.table; 
    downloadCSV();
    Reader reader = new Reader();
    this.table = reader.table();
    downloadImages();
    downloadThumbnails();
    return this.table;
  }
  
  public String url() { return this.url; }
  
  public static String urlString() { return "http://www.cc.kyoto-su.ac.jp/~atsushi/Programs/VisualWorks/CSV2HTML/PrimeMinisters/"; }
  
  public static String urlStringOfCSV() { return urlString() + "PrimeMinisters.csv"; }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/Downloader.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */