package csv2html;

import java.awt.image.BufferedImage;
import java.io.File;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;

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
	public Translator(final Class<? extends Attributes> classOfAttributes) {
		super();
		Attributes.flushBaseDirectory();
		try {
			final Constructor<? extends Attributes> aConstructor = classOfAttributes.getConstructor(String.class);
			this.inputTable = new Table(aConstructor.newInstance("input"));
			this.outputTable = new Table(aConstructor.newInstance("output"));
		} catch (NoSuchMethodException | InstantiationException | IllegalAccessException
				| InvocationTargetException anException) {
			anException.printStackTrace();
		}

		return;
	}

	/**
	 * 在位日数を計算して、それを文字列にして応答する。
	 * 
	 * @param periodString 在位期間の文字列
	 * @return 在位日数の文字列
	 */
	public String computeNumberOfDays(final String periodString) {
		final List<String> aList = IO.splitString(periodString, "年月日〜");
		Calendar aCalendar = Calendar.getInstance();
		Integer livedYear = Integer.valueOf(aList.get(0)).intValue();
		Integer livedMonth = Integer.valueOf(aList.get(1)).intValue() - 1;
		Integer livedDay = Integer.valueOf(aList.get(2)).intValue();
		aCalendar.set(livedYear, livedMonth, livedDay);
		final Long bornedDate = aCalendar.getTime().getTime();
		aCalendar = Calendar.getInstance();
		if (aList.size() > 3) {
			livedYear = Integer.valueOf(aList.get(3)).intValue();
			livedMonth = Integer.valueOf(aList.get(4)).intValue() - 1;
			livedDay = Integer.valueOf(aList.get(5)).intValue();
			aCalendar.set(livedYear, livedMonth, livedDay);
		}
		final Long deadDate = aCalendar.getTime().getTime();
		final Long livedDate = (deadDate - bornedDate) / 86400000L + 1L;

		return String.format("%1$,d", new Object[] { Long.valueOf(livedDate) });
	}

	/**
	 * サムネイル画像から画像へ飛ぶためのHTML文字列を作成して、それを応答する。
	 * 
	 * @param aString 画像の文字列
	 * @param aTuple  タプル
	 * @param aNumber 番号
	 * @return サムネイル画像から画像へ飛ぶためのHTML文字列
	 */
	public String computeStringOfImage(final String aString, final Tuple aTuple, final int aNumber) {
		final String aImageName = aTuple.values().get(aTuple.attributes().indexOfNo());
		final String aImagePath = aTuple.values().get(aTuple.attributes().indexOfThumbnail());
		final BufferedImage bufferedImage = this.inputTable.thumbnails().get(aNumber);
		Integer imagePathIndex = aTuple.attributes().indexOfThumbnail();
		final List<String> aList = IO.splitString(aTuple.values().get(imagePathIndex), "/");
		imagePathIndex = aList.size() - 1;
		final StringBuffer stringBuffer = new StringBuffer();
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
	public void execute() {
		// 必要な情報をダウンロードする。
		final Downloader aDownloader = new Downloader(this.inputTable);
		aDownloader.start();
		try {
			aDownloader.join();
		} catch (final InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}

		// CSVに由来するテーブルをHTMLに由来するテーブルへと変換する。
		System.out.println(this.inputTable);
		this.translate();
		System.out.println(this.outputTable);

		// HTMLに由来するテーブルから書き出す。
		final Writer aWriter = new Writer(this.outputTable);
		aWriter.start();
		try {
			aWriter.join();
		} catch (final InterruptedException interruptedException) {
			interruptedException.printStackTrace();
		}

		// ブラウザを立ち上げて閲覧する。
		try {
			final Attributes attributes = this.outputTable.attributes();
			final String fileStringOfHTML = attributes.baseDirectory() + attributes.indexHTML();
			final ProcessBuilder aProcessBuilder = new ProcessBuilder("open", "-a", "Safari", fileStringOfHTML);
			aProcessBuilder.start();
		} catch (final Exception anException) {
			anException.printStackTrace();
		}

		return;
	}

	/**
	 * 属性リストのクラスを受け取って、CSVファイルをHTMLページへと変換するクラスメソッド。
	 * 
	 * @param classOfAttributes 属性リストのクラス
	 */
	public static void perform(final Class<? extends Attributes> classOfAttributes) {
		// トランスレータのインスタンスを生成する。
		final Translator aTranslator = new Translator(classOfAttributes);
		// トランスレータにCSVファイルをHTMLページへ変換するように依頼する。
		aTranslator.execute();

		return;
	}

	/**
	 * CSVファイルを基にしたテーブルから、HTMLページを基にするテーブルに変換する。
	 */
	public void translate() {
		final List<String> aList = new ArrayList<>();
		Integer aInteger = 0;

		for (final String str : this.inputTable.attributes().names()) {
			if (aInteger != this.inputTable.attributes().indexOfThumbnail()) {
				aList.add(str);
				if (aInteger == this.inputTable.attributes().indexOfPeriod())
					aList.add("在位日数");
			}
			aInteger++;
		}
		this.outputTable.attributes().names(aList);

		Integer anotherInteger = 0;
		for (final Tuple aTuple : this.inputTable.tuples()) {
			final List<String> anotherList = new ArrayList<>();
			aInteger = 0;
			for (final String aString : aTuple.values()) {
				if (aInteger != this.inputTable.attributes().indexOfThumbnail()) {
					if (aInteger == this.inputTable.attributes().indexOfImage()) {
						anotherList.add(computeStringOfImage(aString, aTuple, anotherInteger));
					} else {
						anotherList.add(aString);
					}
					if (aInteger == this.inputTable.attributes().indexOfPeriod())
						anotherList.add(computeNumberOfDays(aString));
				}
				aInteger++;
			}
			final Tuple anotherTuple = new Tuple(this.outputTable.attributes(), anotherList);
			this.outputTable.add(anotherTuple);
			anotherInteger++;
		}

		return;
	}

	
}
