package csv2html;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * 表：情報テーブル。
 */
public class Table extends Object
{
	/**
	 * 属性リストを記憶するフィールド。
	 */
	private Attributes attributes;

	/**
	 * タプル群を記憶するフィールド。
	 */
	private List<Tuple> tuples;

	/**
	 * 画像群を記憶するフィールド。
	 */
	private List<BufferedImage> images;

	/**
	 * サムネイル画像群を記憶するフィールド。
	 */
	private List<BufferedImage> thumbnails;

	/**
	 * テーブルのコンストラクタ。
	 * @param instanceOfAttributes 属性リスト
	 */
	public Table(final Attributes instanceOfAttributes) {
		super();
		this.attributes = instanceOfAttributes;
		this.tuples = null;
		this.images = null;
		this.thumbnails = null;

		return;
	}

	/**
	 * タプルを追加する。
	 * 
	 * @param aTuple タプル
	 */
	public void add(final Tuple aTuple) {
		this.tuples().add(aTuple);

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
	 * 属性リストを設定する。
	 * 
	 * @param instanceOfAttributes 属性リスト
	 */
	public void attributes(final Attributes instanceOfAttributes) {
		this.attributes = instanceOfAttributes;

		return;
	}

	/**
	 * 画像群を応答する。
	 * 
	 * @return 画像群
	 */
	public List<BufferedImage> images() {
		if (this.images != null) {
			return this.images;
		}
		this.images = new ArrayList<BufferedImage>();
		this.tuples().forEach(aTuple -> {
			final String aString = aTuple.values().get(aTuple.attributes().indexOfImage());
			final BufferedImage anImage = this.picture(aString);
			this.images.add(anImage);
		});

		return this.images;
	}

	/**
	 * 画像またはサムネイル画像の文字列を受けとって当該画像を応答する。
	 * 
	 * @param aString 画像またはサムネイル画像の文字列
	 * @return 画像
	 */
	private BufferedImage picture(final String aString) {
		final List<String> aList = IO.splitString(aString, "/");
		final String aImagePath = this.attributes.baseDirectory() + aList.get(0) + File.separator + aList.get(1);
		final File aFile = new File(aImagePath);
		System.out.println(aImagePath);
		BufferedImage bufferedImage = null;
		try {
			bufferedImage = ImageIO.read(aFile);
		} catch (final IOException iOException) {
			iOException.printStackTrace();
		}

		return bufferedImage;
	}

	/**
	 * サムネイル画像群を応答する。
	 * 
	 * @return サムネイル画像群
	 */
	public List<BufferedImage> thumbnails() {
		if (thumbnails != null) {
			return this.thumbnails;
		}
		this.thumbnails = new ArrayList<BufferedImage>();
		this.tuples.forEach(aTuple -> {
			final String aString = aTuple.values().get(aTuple.attributes().indexOfThumbnail());
			final BufferedImage anImage = this.picture(aString);
			this.thumbnails.add(anImage);
		});

		return this.thumbnails;
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
		aBuffer.append(this.attributes());
		this.tuples().forEach(aTuple -> {
			aBuffer.append(",");
			aBuffer.append(aTuple);
		});
		aBuffer.append("]");

		return aBuffer.toString();
	}

	/**
	 * タプル群を応答する。
	 * @return タプル群
	 */
	public List<Tuple> tuples()
	{
		if (this.tuples != null) { return this.tuples; }
		this.tuples = new ArrayList<Tuple>();

		return this.tuples;
	}
}
