package primeministers;

import java.util.ArrayList;

public class Attributes {
  private ArrayList<String> keys = new ArrayList<>();
  
  private ArrayList<String> names = new ArrayList<>();
  
  public Attributes(String paramString) {
    if (paramString.compareTo("input") == 0) {
      String[] arrayOfString = { "no", "order", "name", "kana", "period", "school", "party", "place", "image", "thumbnail" };
      for (String str : arrayOfString) {
        this.keys.add(str);
        this.names.add(new String());
      } 
    } 
    if (paramString.compareTo("output") == 0) {
      String[] arrayOfString = { "no", "order", "name", "kana", "period", "days", "school", "party", "place", "image" };
      for (String str : arrayOfString) {
        this.keys.add(str);
        this.names.add(new String());
      } 
    } 
  }
  
  protected String at(int paramInt) {
    String str = nameAt(paramInt);
    if (str.length() < 1)
      str = keyAt(paramInt); 
    return str;
  }
  
  private int indexOf(String paramString) {
    byte b = 0;
    for (String str : this.keys) {
      if (paramString.compareTo(str) == 0)
        return b; 
      b++;
    } 
    return -1;
  }
  
  public int indexOfDays() { return indexOf("days"); }
  
  public int indexOfImage() { return indexOf("image"); }
  
  public int indexOfKana() { return indexOf("kana"); }
  
  public int indexOfName() { return indexOf("name"); }
  
  public int indexOfNo() { return indexOf("no"); }
  
  public int indexOfOrder() { return indexOf("order"); }
  
  public int indexOfParty() { return indexOf("party"); }
  
  public int indexOfPeriod() { return indexOf("period"); }
  
  public int indexOfPlace() { return indexOf("place"); }
  
  public int indexOfSchool() { return indexOf("school"); }
  
  public int indexOfThumbnail() { return indexOf("thumbnail"); }
  
  protected String keyAt(int paramInt) { return keys().get(paramInt); }
  
  public ArrayList<String> keys() { return this.keys; }
  
  protected String nameAt(int paramInt) { return names().get(paramInt); }
  
  public ArrayList<String> names() { return this.names; }
  
  public void names(ArrayList<String> paramArrayList) { this.names = paramArrayList; }
  
  public int size() { return keys().size(); }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    Class<?> clazz = getClass();
    stringBuffer.append(clazz.getName());
    stringBuffer.append("[");
    for (byte b = 0; b < size(); b++) {
      if (b != 0)
        stringBuffer.append(","); 
      stringBuffer.append(at(b));
    } 
    stringBuffer.append("]");
    return stringBuffer.toString();
  }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/Attributes.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */