#!/usr/bin/env python
# -*- coding: utf-8 -*-

import datetime
import os

from csv2html.io import IO

class Writer(IO):
	"""ライタ：情報のテーブルをHTMLページとして書き出す。"""

	def __init__(self, output_table):
		"""ライタのコンストラクタ。HTMLページを基にするテーブルを受け取る。"""

		super(Writer, self).__init__(output_table)

		return

	def perform(self):
		"""HTMLページを基にするテーブルから、インデックスファイル(index_html)に書き出す。"""

		class_attributes = self.attributes().__class__
		base_directory = class_attributes.base_directory()
		index_html = class_attributes.index_html()

		html_filename = os.path.join(base_directory, index_html)
		print(html_filename)
		with open(html_filename, 'wb') as a_file:
			self.write_header(a_file)
			self.write_body(a_file)
			self.write_footer(a_file)

		return

	def write_body(self, file):
		"""ボディを書き出す。つまり、属性リストを書き出し、タプル群を書き出す。"""
		html_list = []
		html_list.append("\t\t\t\t\t\t")
		html_list.append("<tr>\n")
		for an_attribute in self._table.attributes().names():
			html_list.append("\t\t\t\t\t\t\t")
			html_list.append("<td class=\"center-pink\"><strong>")
			html_list.append(an_attribute)
			html_list.append("</strong></td>\n")
		html_list.append("\t\t\t\t\t\t")
		html_list.append("</tr>\n")
		for index, a_tuple in enumerate(self._table.tuples()):
			html_list.append("\t\t\t\t\t\t")
			html_list.append("<tr>\n")
			for a_value in a_tuple.values():
				html_list.append("\t\t\t\t\t\t\t")
				if index % 2 == 0:
					html_list.append("<td class=\"center-blue\">")
				else:
					html_list.append("<td class=\"center-yellow\">")
				html_list.append(a_value)
				html_list.append("</td>\n")
			html_list.append("\t\t\t\t\t\t")
			html_list.append("</tr>\n")
		html_string = ''.join(html_list)
		file.write(html_string.encode('utf-8'))
		return

	def write_footer(self, file):
		"""フッタを書き出す。"""
		html_string = "\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</td>\n\t\t</tr>\n\t</tbody>\n</table>\n<hr>\n<div class=\"right-small\">Created by AOKI Atsushi, IKEDA Keisuke, ENOMOTO Yoshiki, SHIZUNO Tomoya, TSUJI Karin and FUKUI Kosuke (CSV2HTML written by Python)" + datetime.date.today().strftime("%Y/%m/%d") + datetime.datetime.now().strftime("%H:%M:%S") + "</div>\n</body>\n</html>\n"
		file.write(html_string.encode('utf-8'))
		return

	def write_header(self, file):
		"""ヘッダを書き出す。"""
		class_attributes = self.attributes().__class__
		html_string = "<!DOCTYPE HTML PUBLIC \"-//W3C//DTD HTML 4.01 Transitional//EN\" \"http://www.w3.org/TR/html4/loose.dtd\">\n<html lang=\"ja\">\n<head>\n<meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n<meta http-equiv=\"Content-Style-Type\" content=\"text/css\">\n<meta http-equiv=\"Content-Script-Type\" content=\"text/javascript\">\n<meta name=\"keywords\" content=\"Java, PL, Project\">\n<meta name=\"description\" content=\"" + class_attributes.title_string() + " \"> \n<meta name=\"author\" content=\"AOKI Atsushi, IKEDA Keisuke, ENOMOTO Yoshiki, SHIZUNO Tomoya, TSUJI Karin and FUKUI Kosuke \">\n<link rev=\"made\" href=\"http://www.cc.kyoto-su.ac.jp/~atsushi/\">\n<link rel=\"index\" href=\"index.html\">\n<style type=\"text/css\">\n<!--\nbody {\n\tbackground-color : #ffffff;\n\tmargin : 20px;\n\tpadding : 10px;\n\tfont-family : serif;\n\tfont-size : 10pt;\n}\na {\n\ttext-decoration : underline;\n\tcolor : #000000;\n}\na:link {\n\tbackground-color : #ffddbb;\n}\na:visited {\n\tbackground-color : #ccffcc;\n}\na:hover {\n\tbackground-color : #dddddd;\n}\na:active {\n\tbackground-color : #dddddd;\n}\ndiv.belt {\n\tbackground-color : #eeeeee;\n\tpadding : 0px 4px;\n}\ndiv.right-small {\n\ttext-align : right;\n\tfont-size : 8pt;\n}\nimg.borderless {\n\tborder-width : 0px;\n\tvertical-align : middle;\n}\ntable.belt {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tbackground-color : #ffffff;\n\tpadding : 0px 0px;\n\twidth : 100%;\n}\ntable.content {\n\tborder-style : solid;\n\tborder-width : 0px;\n\tborder-color : #000000;\n\tpadding : 2px 2px;\n}\ntd.center-blue {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ddeeff;\n}\ntd.center-pink {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffddee;\n}\ntd.center-yellow {\n\tpadding : 2px 2px;\n\ttext-align : center;\n\tbackground-color : #ffffcc;\n}\n-->\n</style>\n<title>" + class_attributes.title_string() + "</title>\n</head>\n<body>\n<div class=\"belt\">\n<h2>" + class_attributes.caption_string() + "</h2>\n</div>\n<table class=\"belt\" summary=\"table\">\n\t<tbody>\n\t\t<tr>\n\t\t\t<td>\n\t\t\t\t<table class=\"content\" summary=\"table\">\n\t\t\t\t\t<tbody>\n"
		file.write(html_string.encode('utf-8'))
		return
