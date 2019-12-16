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
		Table aTable = this.table();
		List<String> aList = IO.readTextFromURL(aTable.attributes().csvUrl());
		Boolean aBoolean = true;
		for(String aString : aList){
			List<String> anotherList =  IO.splitString(aString, ",\n");
			if(aBoolean) {
				aTable.attributes().names(anotherList);
				aBoolean = false;
				continue;
			}
			Tuple aTuple = new Tuple(aTable.attributes(), anotherList);
			aTable.add(aTuple);
		}

		return;
	}
}
