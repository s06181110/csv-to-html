package csv2html;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

/**
* トランスレータ：CSVファイルをHTMLページへと変換するプログラム。
*/
public class Translator extends Object
{
	/**
	* CSVに由来するテーブルを記憶するフィールド。
	*/
	private Table inputTable;

	/**
	* HTMLに由来するテーブルを記憶するフィールド。
	*/
	private Table outputTable;

	/**
	* 属性リストのクラスを指定したトランスレータのコンストラクタ。
	* @param classOfAttributes 属性リストのクラス
	*/
	public Translator(Class<? extends Attributes> classOfAttributes)
	{
		super();

		Attributes.flushBaseDirectory();

		try
		{
			Constructor<? extends Attributes> aConstructor = classOfAttributes.getConstructor(String.class);

			this.inputTable = new Table(aConstructor.newInstance("input"));
			this.outputTable = new Table(aConstructor.newInstance("output"));
		}
		catch (NoSuchMethodException |
		InstantiationException |
		IllegalAccessException |
		InvocationTargetException anException) { anException.printStackTrace(); }

		return;
	}

	/**
	* 在位日数を計算して、それを文字列にして応答する。
	* @param periodString 在位期間の文字列
	* @return 在位日数の文字列
	*/
	public String computeNumberOfDays(String periodString)
	{
		List<String> aList = IO.splitString(periodString, "年月日〜");
		Calendar aCalendar = Calendar.getInstance();
		int i = Integer.valueOf(aList.get(0)).intValue();
		int j = Integer.valueOf(aList.get(1)).intValue() - 1;
		int k = Integer.valueOf(aList.get(2)).intValue();
		aCalendar.set(i, j, k);
		long l1 = aCalendar.getTime().getTime();

		aCalendar = Calendar.getInstance();
		if (aList.size() > 3) {
			i = Integer.valueOf(aList.get(3)).intValue();
			j = Integer.valueOf(aList.get(4)).intValue() - 1;
			k = Integer.valueOf(aList.get(5)).intValue();
			aCalendar.set(i, j, k);
		}
		long l2 = aCalendar.getTime().getTime();
		long l3 = (l2 - l1) / 86400000L + 1L;

		return String.format("%1$,d", new Object[] { Long.valueOf(l3) });
	}

	/**
	* サムネイル画像から画像へ飛ぶためのHTML文字列を作成して、それを応答する。
	* @param aString 画像の文字列
	* @param aTuple タプル
	* @param no 番号
	* @return サムネイル画像から画像へ飛ぶためのHTML文字列
	*/
	public String computeStringOfImage(String aString, Tuple aTuple, int no)
	{
		String str1 = aTuple.values().get(aTuple.attributes().indexOfNo());
		String str2 = aTuple.values().get(aTuple.attributes().indexOfThumbnail());
		BufferedImage bufferedImage = this.inputTable.thumbnails().get(no);
		int i = aTuple.attributes().indexOfThumbnail();
		List<String> aList = IO.splitString(aTuple.values().get(i), "/");
		i = aList.size() - 1;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<a name=\"");
		stringBuffer.append(str1);
		stringBuffer.append("\" href=\"");
		stringBuffer.append(aString);
		stringBuffer.append("\">");
		stringBuffer.append("<img class=\"borderless\" src=\"");
		stringBuffer.append(str2);
		stringBuffer.append("\" width=\"");
		stringBuffer.append(bufferedImage.getWidth());
		stringBuffer.append("\" height=\"");
		stringBuffer.append(bufferedImage.getHeight());
		stringBuffer.append("\" alt=\"");
		stringBuffer.append(aList.get(i));
		stringBuffer.append("\"></a>");

		return stringBuffer.toString();
	}

	/**
	* CSVファイルをHTMLページへ変換する。
	*/
	public void execute()
	{
		// 必要な情報をダウンロードする。
		Downloader aDownloader = new Downloader(this.inputTable);
		aDownloader.perform();

		// CSVに由来するテーブルをHTMLに由来するテーブルへと変換する。
		System.out.println(this.inputTable);
		this.translate();
		System.out.println(this.outputTable);

		// HTMLに由来するテーブルから書き出す。
		Writer aWriter = new Writer(this.outputTable);
		aWriter.perform();

		// ブラウザを立ち上げて閲覧する。
		try
		{
			Attributes attributes = this.outputTable.attributes();
			String fileStringOfHTML = attributes.baseDirectory() + attributes.indexHTML();
			ProcessBuilder aProcessBuilder = new ProcessBuilder("open", "-a", "Safari", fileStringOfHTML);
			aProcessBuilder.start();
		}
		catch (Exception anException) { anException.printStackTrace(); }

		return;
	}

	/**
	* 属性リストのクラスを受け取って、CSVファイルをHTMLページへと変換するクラスメソッド。
	* @param classOfAttributes 属性リストのクラス
	*/
	public static void perform(Class<? extends Attributes> classOfAttributes)
	{
		// トランスレータのインスタンスを生成する。
		Translator aTranslator = new Translator(classOfAttributes);
		// トランスレータにCSVファイルをHTMLページへ変換するように依頼する。
		aTranslator.execute();

		return;
	}

	/**
	* CSVファイルを基にしたテーブルから、HTMLページを基にするテーブルに変換する。
	*/
	public void translate()
	{
		List<String> aList1 = new ArrayList<>();
		byte b1 = 0;
		List<String> aList = new ArrayList<>();
		Byte aByte = 0;
		for (String str : this.inputTable.attributes().names()) {
			if (b1 != this.inputTable.attributes().indexOfThumbnail()) {
				aList1.add(str);
				if (b1 == this.inputTable.attributes().indexOfPeriod())
				aList1.add("在位日数");
			}
			b1++;
		}
		this.outputTable.attributes().names(aList1);

		byte b2 = 0;
		for (Tuple tuple1 : this.inputTable.tuples()) {
			List<String> aList2 = new ArrayList<>();
			b1 = 0;
			for (String str : tuple1.values()) {
				if (b1 != this.inputTable.attributes().indexOfThumbnail()) {
					if (b1 == this.inputTable.attributes().indexOfImage()) {
						aList2.add(computeStringOfImage(str, tuple1, b2));
					} else {
						aList2.add(str);
					}
					if (b1 == this.inputTable.attributes().indexOfPeriod())
					aList2.add(computeNumberOfDays(str));
				}
				b1++;
			}
			Tuple tuple2 = new Tuple(this.outputTable.attributes(), aList2);
			this.outputTable.add(tuple2);
			b2++;
		}

		return;
	}
}
