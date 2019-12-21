package csv2html;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.List;

import utility.ImageUtility;

/**
 * ダウンローダ：CSVファイル・画像ファイル・サムネイル画像ファイルをダウンロードする。
 */
public class Downloader extends IO
{
	/**
	 * ダウンローダのコンストラクタ。
	 * @param aTable テーブル
	 */
	public Downloader(Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	 * 総理大臣の情報を記したCSVファイルをダウンロードする。
	 */
	public void downloadCSV()
	{
		System.out.println("From: " + super.attributes().csvUrl());
		List<String> aList = IO.readTextFromURL(super.attributes().csvUrl());
		System.out.println("To: " + super.attributes().baseDirectory());
		String aString = super.attributes().baseDirectory() + File.separator + "csv" + File.separator + "'" + super.attributes().titleString() + "'" + ".csv";
		IO.writeText(aList, aString);
	}

	/**
	 * 総理大臣の画像群をダウンロードする。
	 */
	public void downloadImages()
	{
		int indexOfImage = this.attributes().indexOfImage();
		this.downloadPictures(indexOfImage);

		return;
	}

	/**
	 * 総理大臣の画像群またはサムネイル画像群をダウンロードする。
	 * @param indexOfPicture 画像のインデックス
	 */
	private void downloadPictures(final int indexOfPicture)
	{
		super.tuples().stream()
		.map(aTuple -> aTuple.values().get(indexOfPicture))
		.forEach(this::downloadPicturesLog);
			
		return;
	}

	/**
	 * 総理大臣の画像群またはサムネイル画像群をダウンロードする時のログを出力する。
	 * @param 各タプルの画像の属性
	 */
	public void downloadPicturesLog(String aString){
		String anotherString = super.attributes().baseUrl() + aString;
		System.out.println("From: " + anotherString);
		BufferedImage aBufferedImage = ImageUtility.readImageFromURL(anotherString);
		aString = super.attributes().baseDirectory() + aString;
		ImageUtility.writeImage(aBufferedImage, aString);
		System.out.println("To: " + aString);
	}

	/**
	 * 総理大臣の画像群をダウンロードする。
	 */
	public void downloadThumbnails()
	{
		int indexOfThumbnail = this.attributes().indexOfThumbnail();
		this.downloadPictures(indexOfThumbnail);

		return;
	}

	/**
	 * 総理大臣の情報を記したCSVファイルをダウンロードして、画像群やサムネイル画像群もダウロードする。
	 */
	public synchronized void run()
	{
		Reader aReader = new Reader(super.table());
		aReader.start();
		try {
            aReader.join();
		} catch (InterruptedException interruptedException) { interruptedException.printStackTrace(); }
		this.makeAssetDir("csv");
		this.makeAssetDir("images");
		this.makeAssetDir("thumbnails");
		this.downloadCSV();
		this.downloadImages();
		this.downloadThumbnails();

		return;
	}

	/**
	 * 画像やcsvファイルを格納するディレクトリを生成する。
	 * @param aString 生成するディレクトリ名
	 */
	public void makeAssetDir(String aString){
		aString = super.attributes().baseDirectory() + aString;
		File aDirectory = new File(aString);
		if (!aDirectory.exists()) { aDirectory.mkdir(); }

		return;
	}
}
