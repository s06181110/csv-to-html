package csv2html;

import java.io.File;
import java.util.List;
import utility.StringUtility;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

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
		Boolean[] aBoolean = {true};

		//CSVファイルから行リストを読み込む
		final List<String> firstList = IO.readTextFromFile(super.getCsvFilePath());

		//読み込んだCSVファイルを整える。
		final List<String> secondList = this.decoratedList(firstList);

		//デバック用
		// System.out.println(secondList);
		// for (Integer index = 0; index < secondList.size(); index++){
		// 	System.out.println(index + "：" + secondList.get(index));
		// }

		//整えた行リストを一つのStringにした後に、「,」（カンマ）で新たに行リストを生成する。
		final List<String> thirdList = Arrays.asList(String.join("", secondList).split(",", -1));

		//デバック用
		// System.out.println(thirdList);
		// for (Integer index = 0; index < thirdList.size(); index++){
		// 	System.out.println(index + "：" + thirdList.get(index));
		// }

		//生成した行リストを他のコンポーネントに引き渡せるように整える。
		final List<String> resultList = constructedList(thirdList);

		//デバック用
		// System.out.println(resultList);
		// for (Integer index = 0; index < resultList.size(); index++){
		// 	System.out.println(index + "：" + resultList.get(index));
		// }

		//完成した行リストをテーブル、タプルに引き渡す
		resultList.stream()
		.map(aString -> aString.replaceAll("\"", ""))
		.map(replaceString -> Arrays.asList(replaceString.split(",", -1)))
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

	/**
	 * CSVファイルから読み込んだ行リストを正規表現を用いて整える。
	 * @param beforeList CSVファイルから読み込んだ行リスト
	 * @return afterList 正規表現を用いて整えた後行リスト
	 */
	public List<String> decoratedList(List<String> beforeList)
	{
		List<String> afterList = new ArrayList<>();
		String regularExpression1 = "(.+)(\".+\\p{InCjkUnifiedIdeographs}+）*)";
		String regularExpression2 = "(.+)([縮小画像]|[thumbnails/.jpg])";

		if(super.attributes().titleString().equals("Prime Ministers")){
			regularExpression2 = "(.+)([縮小画像]|[thumbnails/.jpg])";
		}
		if(super.attributes().titleString().equals("Tokugawa Shogunate")){
			regularExpression2 = "(.+)([墓所]|\\p{InCjkUnifiedIdeographs}{2}[寺]|[日光東照宮]{5}|[谷中霊園]{4})";
		}
		
		Pattern p1 = Pattern.compile(regularExpression1);
		Pattern p2 = Pattern.compile(regularExpression2);


		for(String aString: beforeList){
			Matcher m1 = p1.matcher(aString);
			Matcher m2 = p2.matcher(aString);
			if(m2.matches())
			{
				aString = m2.replaceAll("$1$2,");
			} else if(m1.matches()){
				aString = m1.replaceAll("$1$2<br>");
			}
			afterList.add(aString);
		}

		return afterList;
	}

	/**
	 * 読み込んだ行リストを正規表現を用いて整える。
	 * @param beforeList 読み込んだ行リスト
	 * @return afterList 正規表現を用いて整えた後行リスト
	 */
	public List<String> constructedList(List<String> beforeList)
	{
		List<String> afterList = new ArrayList<>();
		String regularExpression1 = "([人目]{2})|([1234567890]{1,2})";
		String regularExpression2 = "([縮小画像]{4})|([thumbnails/]{11}.*[.jpg]{4})";
		String aString = "";

		if(super.attributes().titleString().equals("Prime Ministers")){
			regularExpression1 = "([人目]{2})|([1234567890]{1,2})";
			regularExpression2 = "([縮小画像]{4})|([thumbnails/]{11}.*[.jpg]{4})";
		}

		if(super.attributes().titleString().equals("Tokugawa Shogunate")){
			regularExpression1 = "([代]{1})|([1234567890]{1,2})";
			regularExpression2 = "([墓所]{2})|(\\p{InCjkUnifiedIdeographs}{2}[寺])|([日光東照宮]{5})|([谷中霊園]{4})";
		}

		for (Integer index = 0; index < beforeList.size(); index++){
			if(beforeList.get(index).matches(regularExpression1)){
				aString = beforeList.get(index);
			}else if(beforeList.get(index).matches(regularExpression2)){
				aString = aString + "," + beforeList.get(index);
				afterList.add(aString);
			}else {
				aString = aString + "," + beforeList.get(index);
			}
		}

		return afterList;
	}
}
