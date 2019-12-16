package primeministers;

import java.util.ArrayList;

public class Tuple {
  private Attributes attributes;
  
  private ArrayList<String> values;
  
  public Tuple(Attributes paramAttributes, ArrayList<String> paramArrayList) {
    this.attributes = paramAttributes;
    this.values = paramArrayList;
  }
  
  public Attributes attributes() { return this.attributes; }
  
  public String toString() {
    StringBuffer stringBuffer = new StringBuffer();
    Class<?> clazz = getClass();
    stringBuffer.append(clazz.getName());
    stringBuffer.append("[");
    if (attributes() != null && values() != null) {
      byte b = 0;
      for (String str : values()) {
        if (b)
          stringBuffer.append(","); 
        stringBuffer.append(attributes().at(b));
        stringBuffer.append("(" + str + ")");
        b++;
      } 
    } 
    stringBuffer.append("]");
    return stringBuffer.toString();
  }
  
  public ArrayList<String> values() { return this.values; }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/Tuple.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */