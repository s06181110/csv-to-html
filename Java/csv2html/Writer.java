package csv2html;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.Calendar;
import utility.StringUtility;

/**
 * ライタ：情報のテーブルをHTMLページとして書き出す。
 */
public class Writer extends IO
{
	/**
	 * ライタのコンストラクタ。
	 * @param aTable テーブル
	 */
	public Writer(final Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	 * HTMLページを基にするテーブルからインデックスファイル(index.html)に書き出す。
	 */
	public synchronized void run()
	{
		try {
			final Attributes attributes = this.attributes();
			final String fileStringOfHTML = attributes.baseDirectory() + attributes.indexHTML();
			final File aFile = new File(fileStringOfHTML);
			final FileOutputStream outputStream = new FileOutputStream(aFile);
			final OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, StringUtility.encodingSymbol());
			final BufferedWriter aWriter = new BufferedWriter(outputWriter);
			this.writeHeaderOn(aWriter);
			this.writeTableBodyOn(aWriter);
			this.writeFooterOn(aWriter);
			aWriter.close();
		}
		catch (UnsupportedEncodingException | FileNotFoundException anException) { anException.printStackTrace(); }
		catch (final IOException anException) { anException.printStackTrace(); }

		return;
	}

	/**
	 * 属性リストを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeAttributesOn(final BufferedWriter aWriter)
	{
		try {
			aWriter.write("\t\t\t\t\t\t");
			aWriter.write("<tr>\n");
			attributes().names().forEach(aString -> {
				try {
					aWriter.write("\t\t\t\t\t\t\t");
					aWriter.write("<td class=\"center-pink\"><strong>");
					aWriter.write(aString);
					aWriter.write("</strong></td>\n");
				} catch (final IOException iOException) { iOException.printStackTrace(); }
			});
			aWriter.write("\t\t\t\t\t\t");
			aWriter.write("</tr>\n");
		} catch (final IOException iOException) { iOException.printStackTrace(); }

		return;
	}

	/**
	 * フッタを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeFooterOn(final BufferedWriter aWriter)
	{
		final Calendar calendar = Calendar.getInstance();
		final int year = calendar.get(1);
		final int month = calendar.get(2) + 1;
		final int day = calendar.get(5);
		final int hour = calendar.get(11);
		final int minute = calendar.get(12);
		final int second = calendar.get(13);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" on ");
		stringBuffer.append(String.format("%1$04d", new Object[] { Integer.valueOf(year) }));
		stringBuffer.append("/");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(month) }));
		stringBuffer.append("/");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(day) }));
		final String aDate = stringBuffer.toString();
		stringBuffer = new StringBuffer();
		stringBuffer.append(" at ");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(hour) }));
		stringBuffer.append(":");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(minute) }));
		stringBuffer.append(":");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(second) }));
		final String aTime = stringBuffer.toString();
		try {
			aWriter.write("\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</tbody>\n</table>\n<hr>\n<div class=\"right-small\">Created by AOKI Atsushi, IKEDA Keisuke, ENOMOTO Yoshiki, SHIZUNO Tomoya, TSUJI Karin and FUKUI Kosuke (CSV2HTML written by Java)" + aDate + aTime + "</div>\n</body>\n</html>\n");
		} catch (final IOException iOException) { iOException.printStackTrace(); }

		return;
	}

	/**
	 * ヘッダを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeHeaderOn(final BufferedWriter aWriter)
	{
		try {
			aWriter.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html lang=\"ja\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">\n<meta name=\"keywords\" content=\"Java, PL, Project\">\n<meta name=\"description\" content=\"" + super.attributes().titleString() + " \"> \n<meta name=\"author\" content=\"AOKI Atsushi, IKEDA Keisuke, ENOMOTO Yoshiki, SHIZUNO Tomoya, TSUJI Karin and FUKUI Kosuke \">\n<link rev=\"made\" href=\"http://www.cc.kyoto-su.ac.jp/~atsushi/\">\n<link rel=\"index\" href=\"index.html\">\n<style type=\"text/css\">\n<!--\nbody {\n\tbackground-color : #ffffff;\n\tmargin : 20px;\n\tpadding : 10px;\n\tfont-family : serif;\n\tfont-size : 10pt;\n}\na {\n\ttext-decoration : underline;\n\tcolor : #000000;\n}\na:link {\n\tbackground-color : #ffddbb;\n}\na:visited {\n\tbackground-color : #ccffcc;\n}\na:hover {\n\tbackground-color : #dddddd;\n}\na:active {\n\tbackground-color : #dddddd;\n}\ndiv.belt {\n\tbackground-color : #eeeeee;\n\tpadding : 0px 4px;\n}\ndiv.right-small {\n\ttext-align : right;\n\tfont-size : 8pt;\n}\nimg.borderless {\n\tborder-width : 0px;\n\tvertical-align : middle;\n}\ntable.belt {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tbackground-color : #ffffff;\n\tpadding : 0px 0px;\n\twidth : 100%;\n}\ntable.content {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tpadding : 2px 2px;\n}\ntd.center-blue {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ddeeff;\n}\ntd.center-pink {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffddee;\n}\ntd.center-yellow {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffffcc;\n}\n-->\n</style>\n<title>" + super.attributes().titleString() + "</title>\n</head>\n<body>\n<div class=\"belt\">\n<h2>" + super.attributes().captionString() + "</h2>\n</div>\n<table class=\"belt\" summary=\"table\">\n\t<tbody>\n\t\t<tr>\n\t\t\t<td>\n\t\t\t\t<table class=\"content\" summary=\"table\">\n\t\t\t\t\t<tbody>\n");
		} catch (final IOException iOException) { iOException.printStackTrace(); }

		return;
	}

	/**
	 * ボディを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeTableBodyOn(final BufferedWriter aWriter)
	{
		this.writeAttributesOn(aWriter);
		this.writeTuplesOn(aWriter);

		return;
	}

	/**
	 * タプル群を書き出す。
	 * @param aWriter ライタ
	 */
	public void writeTuplesOn(final BufferedWriter aWriter)
	{
		
		final Integer aInteger[] = { 0 };
		super.tuples().forEach(aTuple -> {
			try{
				aWriter.write("\t\t\t\t\t\t");
				aWriter.write("<tr>\n");
				aTuple.values().forEach(aString -> {
					try {
						aWriter.write("\t\t\t\t\t\t\t");
						if (aInteger[0] % 2 == 0) {
							aWriter.write("<td class=\"center-blue\">");
						} else {
							aWriter.write("<td class=\"center-yellow\">");
						}
						aWriter.write(aString);
						aWriter.write("</td>\n");
					} catch (final IOException iOException) { iOException.printStackTrace(); }
				});
				aWriter.write("\t\t\t\t\t\t");
				aWriter.write("</tr>\n");
				aInteger[0]++;
			} catch (final IOException iOException) { iOException.printStackTrace(); }
		});
		
		return;
	}
}
