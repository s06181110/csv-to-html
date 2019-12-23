package csv2html;

/**
 * 属性リスト：京都産業大学学長の情報テーブルを入出力する際の属性情報を記憶。
 */
public class AttributesForKSUPresident extends Attributes
{
	/**
	 * 入力用("input")または出力用("output")で京都産業大学学長の属性リストを作成するコンストラクタ。
	 * @param aString 入力用("input")または出力用("output")
	 */
	public AttributesForKSUPresident(String aString)
	{
		super();

		if (aString.compareTo("input") == 0)
		{
			String[] aCollection = new String[] { "no", "name", "kana", "period", "education", "image", "thumbnail" };
			for (String each : aCollection) { this.keys().add(each); this.names().add(new String()); }
		}
		if (aString.compareTo("output") == 0)
		{
			String[] aCollection = new String[] { "no", "name", "kana", "period", "days", "education", "image"};
			for (String each : aCollection) { this.keys().add(each); this.names().add(new String()); }
		}

		return;
	}

	/**
	 * 標題文字列を応答する。
	 * @return 標題文字列
	 */
	public String captionString()
	{
		return "京都産業大学学長";
	}

	/**
	 * 京都産業大学学長のページのためのディレクトリを応答する。
	 * @return 京都産業大学学長のページのためのディレクトリ
	 */
	public String baseDirectory()
	{
		return this.baseDirectory("KSUPresident");
	}

	/**
	 * 京都産業大学学長の情報の在処(URL)を文字列で応答する。
	 * @return 京都産業大学学長の情報の在処の文字列
	 */
	public String baseUrl()
	{
		return "https://s06181110.github.io/csv-to-html/Designs/KSUPresident/";
	}

	/**
	 * 京都産業大学学長の情報を記したCSVファイルの在処(URL)を文字列で応答する。
	 * @return 情報を記したCSVファイル文字列
	 */
	public String csvUrl()
	{
		return this.baseUrl() + "KSUPresident.csv";
	}

	/**
	 * 学歴のインデックスを応答する。
	 * @return インデックス
	 */
	public int indexOfEducation()
	{
		return this.indexOf("education");
	}

	/**
	 * タイトル文字列を応答する。
	 * @return タイトル文字列
	 */
	public String titleString()
	{
		return "KSU President";
	}
}
