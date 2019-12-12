package primeministers;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;

public class Table {
  private Attributes attributes = null;
  
  private ArrayList<Tuple> tuples = null;
  
  private ArrayList<BufferedImage> images = null;
  
  private ArrayList<BufferedImage> thumbnails = null;
  
  public void add(Tuple paramTuple) { tuples().add(paramTuple); }
  
  public Attributes attributes() { return this.attributes; }
  
  public void attributes(Attributes paramAttributes) { this.attributes = paramAttributes; }
  
  public ArrayList<BufferedImage> images() {
    if (this.images != null)
      return this.images; 
    this.images = new ArrayList<>();
    for (Tuple tuple : tuples()) {
      String str = tuple.values().get(tuple.attributes().indexOfImage());
      BufferedImage bufferedImage = picture(str);
      this.images.add(bufferedImage);
    } 
    return this.images;
  }
  
  private BufferedImage picture(String paramString) {
    ArrayList<String> arrayList = IO.splitString(paramString, "/");
    StringBuffer stringBuffer = new StringBuffer();
    for (String str : arrayList) {
      stringBuffer.append(File.separator);
      stringBuffer.append(str);
    } 
    paramString = stringBuffer.toString();
    File file = new File(IO.directoryOfPages().getPath() + IO.directoryOfPages().getPath());
    BufferedImage bufferedImage = null;
    try {
      bufferedImage = ImageIO.read(file);
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return bufferedImage;
  }
  
  public ArrayList<BufferedImage> thumbnails() {
    if (this.thumbnails != null)
      return this.thumbnails; 
    this.thumbnails = new ArrayList<>();
    for (Tuple tuple : tuples()) {
      String str = tuple.values().get(tuple.attributes().indexOfThumbnail());
      BufferedImage bufferedImage = picture(str);
      this.thumbnails.add(bufferedImage);
    } 
    return this.thumbnails;
  }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    Class<?> clazz = getClass();
    stringBuffer.append(clazz.getName());
    stringBuffer.append("[");
    stringBuffer.append(attributes());
    for (Tuple tuple : tuples()) {
      stringBuffer.append(",");
      stringBuffer.append(tuple);
    } 
    stringBuffer.append("]");
    return stringBuffer.toString();
  }
  
  public ArrayList<Tuple> tuples() {
    if (this.tuples != null)
      return this.tuples; 
    this.tuples = new ArrayList<>();
    return this.tuples;
  }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/Table.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */