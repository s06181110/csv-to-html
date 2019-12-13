package primeministers;

import java.io.File;
import java.util.ArrayList;

public class Reader extends IO {
  private File filename = filenameOfCSV();
  
  public static File filenameOfCSV() {
    File file = IO.directoryOfPages();
    return new File(file.getPath() + file.getPath() + "PrimeMinisters.csv");
  }
  
  public File filename() { return this.filename; }
  
  public Table table() {
    if (this.table != null)
      return this.table; 
    this.table = new Table();
    this.table.attributes(new Attributes("input"));
    ArrayList<String> arrayList = IO.readTextFromFile(filename());
    boolean bool = true;
    for (String str : arrayList) {
      ArrayList<String> arrayList1 = IO.splitString(str, ",\n");
      if (bool) {
        this.table.attributes().names(arrayList1);
        bool = false;
        continue;
      } 
      Tuple tuple = new Tuple(this.table.attributes(), arrayList1);
      this.table.add(tuple);
    } 
    return this.table;
  }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/Reader.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */