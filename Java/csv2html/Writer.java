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
	public Writer(Table aTable)
	{
		super(aTable);

		return;
	}

	/**
	 * HTMLページを基にするテーブルからインデックスファイル(index.html)に書き出す。
	 */
	public void perform()
	{
		try
		{
			Attributes attributes = this.attributes();
			String fileStringOfHTML = attributes.baseDirectory() + attributes.indexHTML();
			File aFile = new File(fileStringOfHTML);
			FileOutputStream outputStream = new FileOutputStream(aFile);
			OutputStreamWriter outputWriter = new OutputStreamWriter(outputStream, StringUtility.encodingSymbol());
			BufferedWriter aWriter = new BufferedWriter(outputWriter);

			this.writeHeaderOn(aWriter);
			this.writeTableBodyOn(aWriter);
			this.writeFooterOn(aWriter);

			aWriter.close();
		}
		catch (UnsupportedEncodingException | FileNotFoundException anException) { anException.printStackTrace(); }
		catch (IOException anException) { anException.printStackTrace(); }

		return;
	}

	/**
	 * 属性リストを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeAttributesOn(BufferedWriter aWriter)
	{
		try {
			aWriter.write("\t\t\t\t\t\t");
			aWriter.write("<tr>\n");
			for (String str : attributes().names()) {
				aWriter.write("\t\t\t\t\t\t\t");
				aWriter.write("<td class=\"center-pink\"><strong>");
				aWriter.write(str);
				aWriter.write("</strong></td>\n");
			}
			aWriter.write("\t\t\t\t\t\t");
			aWriter.write("</tr>\n");
		}
		catch (IOException iOException) { iOException.printStackTrace(); }

		return;
	}

	/**
	 * フッタを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeFooterOn(BufferedWriter aWriter)
	{
		Class<?> clazz = getClass();
		String str1 = clazz.getName();
		Calendar calendar = Calendar.getInstance();
		int i = calendar.get(1);
		int j = calendar.get(2) + 1;
		int k = calendar.get(5);
		int m = calendar.get(11);
		int n = calendar.get(12);
		int i1 = calendar.get(13);
		StringBuffer stringBuffer = new StringBuffer();
		stringBuffer.append(" on ");
		stringBuffer.append(String.format("%1$04d", new Object[] { Integer.valueOf(i) }));
		stringBuffer.append("/");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(j) }));
		stringBuffer.append("/");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(k) }));
		String str2 = stringBuffer.toString();
		stringBuffer = new StringBuffer();
		stringBuffer.append(" at ");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(m) }));
		stringBuffer.append(":");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(n) }));
		stringBuffer.append(":");
		stringBuffer.append(String.format("%1$02d", new Object[] { Integer.valueOf(i1) }));
		String str3 = stringBuffer.toString();
		try {
			aWriter.write("\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</tbody>\n</table>\n<hr>\n<div class=\"right-small\">Created by " + str1 + str2 + str3 + "</div>\n</body>\n</html>\n");
		} catch (IOException iOException) {
			iOException.printStackTrace();
		}
		return;
	}

	/**
	 * ヘッダを書き出す。
	 * @param aWriter ライタ
	 */
	public void writeHeaderOn(BufferedWriter aWriter)
	{
		try {
			aWriter.write("<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html lang=\"ja\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">\n<meta name=\"keywords\" content=\"Java, PL, Project\">\n<meta name=\"description\" content=\"" + super.attributes().titleString() + " \"> \n<meta name=\"author\" content=\"AOKI Atsushi, IKEDA Keisuke, ENOMOTO Yoshiki, SHIZUNO Tomoya, TSUJI Karin and FUKUI Kosuke \">\n<link rev=\"made\" href=\"http://www.cc.kyoto-su.ac.jp/~atsushi/\">\n<link rel=\"index\" href=\"index.html\">\n<style type=\"text/css\">\n<!--\nbody {\n\tbackground-color : #ffffff;\n\tmargin : 20px;\n\tpadding : 10px;\n\tfont-family : serif;\n\tfont-size : 10pt;\n}\na {\n\ttext-decoration : underline;\n\tcolor : #000000;\n}\na:link {\n\tbackground-color : #ffddbb;\n}\na:visited {\n\tbackground-color : #ccffcc;\n}\na:hover {\n\tbackground-color : #dddddd;\n}\na:active {\n\tbackground-color : #dddddd;\n}\ndiv.belt {\n\tbackground-color : #eeeeee;\n\tpadding : 0px 4px;\n}\ndiv.right-small {\n\ttext-align : right;\n\tfont-size : 8pt;\n}\nimg.borderless {\n\tborder-width : 0px;\n\tvertical-align : middle;\n}\ntable.belt {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tbackground-color : #ffffff;\n\tpadding : 0px 0px;\n\twidth : 100%;\n}\ntable.content {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tpadding : 2px 2px;\n}\ntd.center-blue {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ddeeff;\n}\ntd.center-pink {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffddee;\n}\ntd.center-yellow {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffffcc;\n}\n-->\n</style>\n<title>" + super.attributes().titleString() + "</title>\n</head>\n<body>\n<div class=\"belt\">\n<h2>" + super.attributes().captionString() + "</h2>\n</div>\n<table class=\"belt\" summary=\"table\">\n\t<tbody>\n\t\t<tr>\n\t\t\t<td>\n\t\t\t\t<table class=\"content\" summary=\"table\">\n\t\t\t\t\t<tbody>\n");
		}
		catch (IOException iOException) { iOException.printStackTrace(); }

		return;
	}

	/**
	* ボディを書き出す。
	* @param aWriter ライタ
	*/
	public void writeTableBodyOn(BufferedWriter aWriter)
	{
		this.writeAttributesOn(aWriter);
		this.writeTuplesOn(aWriter);

		return;
	}

	/**
	* タプル群を書き出す。
	* @param aWriter ライタ
	*/
	public void writeTuplesOn(BufferedWriter aWriter)
	{
		try {
			byte b = 0;
			for (Tuple tuple : super.tuples()) {
				aWriter.write("\t\t\t\t\t\t");
				aWriter.write("<tr>\n");
				for (String str : tuple.values()) {
					aWriter.write("\t\t\t\t\t\t\t");
					if (b % 2 == 0) {
						aWriter.write("<td class=\"center-blue\">");
					} else {
						aWriter.write("<td class=\"center-yellow\">");
					}
					aWriter.write(str);
					aWriter.write("</td>\n");
				}
				aWriter.write("\t\t\t\t\t\t");
				aWriter.write("</tr>\n");
				b++;
			}
		}
		catch (IOException iOException) { iOException.printStackTrace(); }

		return;
	}
}
