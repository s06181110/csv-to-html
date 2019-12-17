package csv2html;

import java.io.File;
import java.util.List;
import utility.StringUtility;

/**
* リーダ：情報を記したCSVファイルを読み込んでテーブルに仕立て上げる。
*/
public class Reader extends IO
{
	/**
	* リーダのコンストラクタ。
	* @param aTable テーブル
	*/
	public Reader(Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	* ダウンロードしたCSVファイルを読み込む。
	*/
	public void perform()
	{
		String fileString = super.attributes().baseDirectory() + "csv" + File.separator + "'" + super.attributes().titleString() + "'" + ".csv";
		List<String> csvList = IO.readTextFromFile(fileString);
		csvList.forEach(aString -> {
			List<String> stringList = IO.splitString(aString, ",\n");
			if(csvList.get(0) == aString) {
				super.attributes().names(stringList);
				return;
			}
			Tuple aTuple = new Tuple(super.attributes(), stringList);
			super.table().add(aTuple);
		});

		return;
	}
}
