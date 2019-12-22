package csv2html;

import java.io.File;
import java.util.List;
import utility.StringUtility;
import java.util.Arrays;

/**
 * リーダ：情報を記したCSVファイルを読み込んでテーブルに仕立て上げる。
 */
public class Reader extends IO
{
	/**
	 * リーダのコンストラクタ。
	 * @param aTable テーブル
	 */
	public Reader(final Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	 * ダウンロードしたCSVファイルを読み込む。
	 */
	public synchronized void run()
	{
		final Table aTable = this.table();
		final List<String> aList = IO.readTextFromURL(aTable.attributes().csvUrl());
		Boolean[] aBoolean = {true};
		aList.stream()
		.map(aString -> Arrays.asList(aString.split(",", -1)))
		.forEach(anotherList -> {
			if (aBoolean[0]) {
				aTable.attributes().names(anotherList);
				aBoolean[0] = false;
				return;
			}
			final Tuple aTuple = new Tuple(aTable.attributes(), anotherList);
			aTable.add(aTuple);
		});

		return;
	}
}
