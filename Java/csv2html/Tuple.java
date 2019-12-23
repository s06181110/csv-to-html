package csv2html;

import java.util.List;

/**
 * タプル：情報テーブルの中の各々のレコード。
 */
public class Tuple extends Object
{
	/**
	 * 属性リストを記憶するフィールド。
	 */
	private Attributes attributes;

	/**
	 * 値リストを記憶するフィールド。
	 */
	private List<String> values;

	/**
	 * 属性リストと値リストからタプルを作るコンストラクタ。
	 * @param instanceOfAttributes 属性リスト
	 * @param valueCollection 値リスト
	 */
	public Tuple(final Attributes instanceOfAttributes, final List<String> valueCollection) {
		super();
		this.attributes = instanceOfAttributes;
		this.values = valueCollection;

		return;
	}

	/**
	 * 属性リストを応答する。
	 * 
	 * @return 属性リスト
	 */
	public Attributes attributes() {
		return this.attributes;
	}

	/**
	 * 自分自身を文字列にして、それを応答する。
	 * 
	 * @return 自分自身の文字列
	 */
	public String toString() {
		final StringBuffer aBuffer = new StringBuffer();
		final Class<?> aClass = this.getClass();
		aBuffer.append(aClass.getName());
		aBuffer.append("[");
		final Integer[] index = { 0 };
		this.values.forEach(aString -> {
			if (index[0] != 0) { aBuffer.append(","); }
			aBuffer.append(this.attributes().at(index[0]));
			aBuffer.append("(" + aString + ")");
			index[0]++;
		});
		aBuffer.append("]");

		return aBuffer.toString();
	}

	/**
	 * 値リストを応答する。
	 * @return 値リスト
	 */
	public List<String> values()
	{
		return this.values;
	}
}
