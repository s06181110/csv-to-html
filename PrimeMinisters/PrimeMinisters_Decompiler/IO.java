package primeministers;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;

public abstract class IO {
  protected Table table = null;
  
  public static void deleteFileOrDirectory(File paramFile) {
    if (!paramFile.exists())
      return; 
    if (paramFile.isFile())
      paramFile.delete(); 
    if (paramFile.isDirectory()) {
      File[] arrayOfFile = paramFile.listFiles();
      for (File file : arrayOfFile)
        deleteFileOrDirectory(file); 
      paramFile.delete();
    } 
  }
  
  public static File directoryOfPages() {
    String str = System.getProperty("user.home");
    File file = new File(str + str + "Desktop" + File.separator + "PrimeMinisters");
    if (!file.exists())
      file.mkdirs(); 
    return file;
  }
  
  public static String encodingSymbol() { return "UTF-8"; }
  
  public static ArrayList<String> readTextFromFile(File paramFile) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      FileInputStream fileInputStream = new FileInputStream(paramFile);
      InputStreamReader inputStreamReader = new InputStreamReader(fileInputStream, encodingSymbol());
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      while ((str = bufferedReader.readLine()) != null)
        arrayList.add(str); 
      bufferedReader.close();
    } catch (FileNotFoundException fileNotFoundException) {
      fileNotFoundException.printStackTrace();
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      unsupportedEncodingException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return arrayList;
  }
  
  public static ArrayList<String> readTextFromFile(String paramString) {
    File file = new File(paramString);
    return readTextFromFile(file);
  }
  
  public static ArrayList<String> readTextFromURL(String paramString) {
    URL uRL = null;
    try {
      uRL = new URL(paramString);
    } catch (MalformedURLException malformedURLException) {
      malformedURLException.printStackTrace();
    } 
    return readTextFromURL(uRL);
  }
  
  public static ArrayList<String> readTextFromURL(URL paramURL) {
    ArrayList<String> arrayList = new ArrayList();
    try {
      InputStream inputStream = paramURL.openStream();
      InputStreamReader inputStreamReader = new InputStreamReader(inputStream, encodingSymbol());
      BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
      String str = null;
      while ((str = bufferedReader.readLine()) != null)
        arrayList.add(str); 
      bufferedReader.close();
    } catch (UnsupportedEncodingException unsupportedEncodingException) {
      unsupportedEncodingException.printStackTrace();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
    return arrayList;
  }
  
  public static ArrayList<String> splitString(String paramString1, String paramString2) {
    ArrayList<Integer> arrayList = new ArrayList();
    arrayList.add(Integer.valueOf(-1));
    int i = paramString1.length();
    byte b;
    for (b = 0; b < i; b++) {
      if (paramString2.indexOf(paramString1.charAt(b)) >= 0)
        arrayList.add(Integer.valueOf(b)); 
    } 
    arrayList.add(Integer.valueOf(i));
    i = arrayList.size() - 1;
    ArrayList<String> arrayList1 = new ArrayList();
    for (b = 0; b < i; b++) {
      int j = ((Integer)arrayList.get(b)).intValue() + 1;
      int k = ((Integer)arrayList.get(b + 1)).intValue() - 1;
      if (k >= j)
        arrayList1.add(paramString1.substring(j, k + 1)); 
    } 
    return arrayList1;
  }
  
  public Table table() { return this.table; }
  
  public static void writeText(ArrayList<String> paramArrayList, File paramFile) {
    try {
      FileOutputStream fileOutputStream = new FileOutputStream(paramFile);
      OutputStreamWriter outputStreamWriter = new OutputStreamWriter(fileOutputStream, encodingSymbol());
      BufferedWriter bufferedWriter = new BufferedWriter(outputStreamWriter);
      for (String str : paramArrayList)
        bufferedWriter.write(str + "\n"); 
      bufferedWriter.close();
    } catch (IOException iOException) {
      iOException.printStackTrace();
    } 
  }
  
  public static void writeText(ArrayList<String> paramArrayList, String paramString) {
    File file = new File(paramString);
    writeText(paramArrayList, file);
  }
}


/* Location:              /Users/g1744069/Desktop/AOK/PrimeMinisters.app/Contents/Resources/Java/primeministers.jar!/primeministers/IO.class
 * Java compiler version: 13 (57.0)
 * JD-Core Version:       1.1.2
 */