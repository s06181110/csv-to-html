package primeministers;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class Translator {
  private Table inputTable = null;
  
  private Table outputTable = null;
  
  public String computeNumberOfDays(String paramString) {
    ArrayList<String> arrayList = IO.splitString(paramString, "年月日〜");
    Calendar calendar = Calendar.getInstance();
    int i = Integer.valueOf(arrayList.get(0)).intValue();
    int j = Integer.valueOf(arrayList.get(1)).intValue() - 1;
    int k = Integer.valueOf(arrayList.get(2)).intValue();
    calendar.set(i, j, k);
    long l1 = calendar.getTime().getTime();
    calendar = Calendar.getInstance();
    if (arrayList.size() > 3) {
      i = Integer.valueOf(arrayList.get(3)).intValue();
      j = Integer.valueOf(arrayList.get(4)).intValue() - 1;
      k = Integer.valueOf(arrayList.get(5)).intValue();
      calendar.set(i, j, k);
    } 
    long l2 = calendar.getTime().getTime();
    long l3 = (l2 - l1) / 86400000L + 1L;
    return String.format("%1$,d", new Object[] { Long.valueOf(l3) });
  }
  
  public String computeStringOfImage(String paramString, Tuple paramTuple, int paramInt) {
    String str1 = paramTuple.values().get(paramTuple.attributes().indexOfNo());
    String str2 = paramTuple.values().get(paramTuple.attributes().indexOfThumbnail());
    BufferedImage bufferedImage = this.inputTable.thumbnails().get(paramInt);
    int i = paramTuple.attributes().indexOfThumbnail();
    ArrayList<String> arrayList = IO.splitString(paramTuple.values().get(i), "/");
    i = arrayList.size() - 1;
    StringBuffer stringBuffer = new StringBuffer();
    stringBuffer.append("<a name=\"");
    stringBuffer.append(str1);
    stringBuffer.append("\" href=\"");
    stringBuffer.append(paramString);
    stringBuffer.append("\">");
    stringBuffer.append("<img class=\"borderless\" src=\"");
    stringBuffer.append(str2);
    stringBuffer.append("\" width=\"");
    stringBuffer.append(bufferedImage.getWidth());
    stringBuffer.append("\" height=\"");
    stringBuffer.append(bufferedImage.getHeight());
    stringBuffer.append("\" alt=\"");
    stringBuffer.append(arrayList.get(i));
    stringBuffer.append("\"></a>");
    return stringBuffer.toString();
  }
  
  public void perform() {
    IO.deleteFileOrDirectory(IO.directoryOfPages());
    Downloader downloader = new Downloader();
    Table table = downloader.table();
    Reader reader = new Reader();
    table = reader.table();
    System.out.println(table);
    Translator translator = new Translator();
    table = translator.table(table);
    Writer writer = new Writer();
    table = writer.table(table);
    System.out.println(table);
    String str = "総理大臣のCSVファイルからHTMLページへの変換を無事に完了しました。\n";
    JOptionPane.showMessageDialog(null, str, "報告", -1);
  }
  
  public Table table(Table paramTable) {
    this.inputTable = paramTable;
    this.outputTable = new Table();
    this.outputTable.attributes(new Attributes("output"));
    ArrayList<String> arrayList = new ArrayList();
    byte b1 = 0;
    for (String str : this.inputTable.attributes().names()) {
      if (b1 != this.inputTable.attributes().indexOfThumbnail()) {
        arrayList.add(str);
        if (b1 == this.inputTable.attributes().indexOfPeriod())
          arrayList.add("在位日数"); 
      } 
      b1++;
    } 
    this.outputTable.attributes().names(arrayList);
    byte b2 = 0;
    for (Tuple tuple1 : this.inputTable.tuples()) {
      ArrayList<String> arrayList1 = new ArrayList();
      b1 = 0;
      for (String str : tuple1.values()) {
        if (b1 != this.inputTable.attributes().indexOfThumbnail()) {
          if (b1 == this.inputTable.attributes().indexOfImage()) {
            arrayList1.add(computeStringOfImage(str, tuple1, b2));
          } else {
            arrayList1.add(str);
          } 
          if (b1 == this.inputTable.attributes().indexOfPeriod())
            arrayList1.add(computeNumberOfDays(str)); 
        } 
        b1++;
      } 
      Tuple tuple2 = new Tuple(this.outputTable.attributes(), arrayList1);
      this.outputTable.add(tuple2);
      b2++;
    } 
    return this.outputTable;
  }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/Translator.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */