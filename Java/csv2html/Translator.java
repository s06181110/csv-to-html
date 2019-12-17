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
		Integer livedYear = Integer.valueOf(aList.get(0)).intValue();
		Integer livedMonth = Integer.valueOf(aList.get(1)).intValue() - 1;
		Integer livedDay = Integer.valueOf(aList.get(2)).intValue();
		aCalendar.set(livedYear, livedMonth, livedDay);
		Long bornedDate = aCalendar.getTime().getTime();

		aCalendar = Calendar.getInstance();
		if (aList.size() > 3) {
			livedYear = Integer.valueOf(aList.get(3)).intValue();
			livedMonth = Integer.valueOf(aList.get(4)).intValue() - 1;
			livedDay = Integer.valueOf(aList.get(5)).intValue();
			aCalendar.set(livedYear, livedMonth, livedDay);
		}
		Long deadDate = aCalendar.getTime().getTime();
		Long livedDate = (deadDate - bornedDate) / 86400000L + 1L;

		return String.format("%1$,d", new Object[] { Long.valueOf(livedDate) });
	}

	/**
	* サムネイル画像から画像へ飛ぶためのHTML文字列を作成して、それを応答する。
	* @param aString 画像の文字列
	* @param aTuple タプル
	* @param aNumber 番号
	* @return サムネイル画像から画像へ飛ぶためのHTML文字列
	*/
	public String computeStringOfImage(String aString, Tuple aTuple, int aNumber)
	{
		String aImageName = aTuple.values().get(aTuple.attributes().indexOfNo());
		String aImagePath = aTuple.values().get(aTuple.attributes().indexOfThumbnail());
		BufferedImage bufferedImage = this.inputTable.thumbnails().get(aNumber);
		Integer imagePathIndex = aTuple.attributes().indexOfThumbnail();
		List<String> aList = IO.splitString(aTuple.values().get(imagePathIndex), "/");
		imagePathIndex = aList.size() - 1;
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append("<a name=\"");
		stringBuffer.append(aImageName);
		stringBuffer.append("\" href=\"");
		stringBuffer.append(aString);
		stringBuffer.append("\">");
		stringBuffer.append("<img class=\"borderless\" src=\"");
		stringBuffer.append(aImagePath);
		stringBuffer.append("\" width=\"");
		stringBuffer.append(bufferedImage.getWidth());
		stringBuffer.append("\" height=\"");
		stringBuffer.append(bufferedImage.getHeight());
		stringBuffer.append("\" alt=\"");
		stringBuffer.append(aList.get(imagePathIndex));
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
		Reader aReader = new Reader(this.inputTable);
		aReader.perform();
		List<String> aList = new ArrayList<>();
		Byte aByte = 0;
		for (String str : this.inputTable.attributes().names()) {
			if (aByte != this.inputTable.attributes().indexOfThumbnail()) {
				aList.add(str);
				if (aByte == this.inputTable.attributes().indexOfPeriod())
				aList.add("在位日数");
			}
			aByte++;
		}
		this.outputTable.attributes().names(aList);

		byte anotherByte = 0;
		for (Tuple aTuple : this.inputTable.tuples()) {
			List<String> anotherList = new ArrayList<>();
			aByte = 0;
			for (String aString : aTuple.values()) {
				if (aByte != this.inputTable.attributes().indexOfThumbnail()) {
					if (aByte == this.inputTable.attributes().indexOfImage()) {
						anotherList.add(computeStringOfImage(aString, aTuple, anotherByte));
					} else {
						anotherList.add(aString);
					}
					if (aByte == this.inputTable.attributes().indexOfPeriod())
					anotherList.add(computeNumberOfDays(aString));
				}
				aByte++;
			}
			Tuple anotherTuple = new Tuple(this.outputTable.attributes(), anotherList);
			this.outputTable.add(anotherTuple);
			anotherByte++;
		}

		return;
	}
}
