#!/usr/bin/env python
# -*- coding: utf-8 -*-

import subprocess
import datetime
import os
import os.path
import re

from PIL import Image

from csv2html.downloader import Downloader
from csv2html.io import IO
from csv2html.table import Table
from csv2html.tuple import Tuple
from csv2html.writer import Writer

class Translator(object):
	"""トランスレータ：CSVファイルをHTMLページへと変換するプログラム。"""

	def __init__(self, classOfAttributes):
		"""トランスレータのコンストラクタ。"""

		super(Translator, self).__init__()

		self._input_table = Table('input', classOfAttributes)
		self._output_table = Table('output', classOfAttributes)

		return

	def compute_string_of_days(self, period):
		"""在位日数を計算して、それを文字列にして応答する。"""

		time_list = [int(x) for x in re.split('[年月日〜]', period) if x != '']
		start_datetime = datetime.datetime(*time_list[:3])
		dt_now = datetime.datetime.now()
		end_datetime = datetime.datetime(*time_list[3:]) if len(time_list) > 3 else datetime.datetime(dt_now.year, dt_now.month, dt_now.day)
		reigned_days = end_datetime - start_datetime
		return str(reigned_days.days)

	def compute_string_of_image(self, tuple):
		"""サムネイル画像から画像へ飛ぶためのHTML文字列を作成して、それを応答する。"""
		values = tuple.values()
		image_name = values[tuple.attributes().keys().index("no")]
		image_path = values[tuple.attributes().keys().index("image")]
		thumbnail_path = values[tuple.attributes().keys().index("thumbnail")]
		thumbnail_Image = Image.open(os.path.join(tuple.attributes().base_directory(), thumbnail_path))
		alt = thumbnail_path.split("/")
		html_string = '<a name="{}" href="{}"><img class="borderless" src="{}" width="{}" height="{}" alt="{}"></a>'
		html_string = html_string.format(image_name, image_path, thumbnail_path, thumbnail_Image.width, thumbnail_Image.height, alt[1])
		return IO.html_canonical_string(html_string)

	def execute(self):
		"""CSVファイルをHTMLページへと変換する。"""

		# ダウンローダに必要なファイル群をすべてダウンロードしてもらい、
		# 入力となるテーブルを獲得する。
		a_downloader = Downloader(self._input_table)
		a_downloader.perform()

		# トランスレータに入力となるテーブルを渡して変換してもらい、
		# 出力となるテーブルを獲得する。
		print(self._input_table)
		self.translate()
		print(self._output_table)
		# ライタに出力となるテーブルを渡して、
		# Webページを作成してもらう。
		a_writer = Writer(self._output_table)
		a_writer.perform()

		# 作成したページをウェブブラウザで閲覧する。
		class_attributes = self._output_table.attributes().__class__
		base_directory = class_attributes.base_directory()
		index_html = class_attributes.index_html()
		a_command = 'open -a Safari ' + base_directory + os.sep + index_html
		subprocess.call(a_command.split())

		return

	@classmethod
	def perform(the_class, class_attributes):
		"""属性リストのクラスを受け取り、CSVファイルをHTMLページへと変換する。"""

		# トランスレータのインスタンスを生成する。
		a_translator = the_class(class_attributes)
		# トランスレータにCSVファイルをHTMLページへ変換するように依頼する。
		a_translator.execute()

		return

	def translate(self):
		"""CSVファイルを基にしたテーブルから、HTMLページを基にするテーブルに変換する。"""
		output_names = []
		input_keys = self._input_table.attributes().keys()
		input_names = self._input_table.attributes().names()
		for a_string in input_names:
			if input_names.index(a_string) != input_keys.index("thumbnail"):
				output_names.append(a_string)
				if input_names.index(a_string) == input_keys.index("period"):
					output_names.append("在位日数")
		self._output_table.attributes()._names = output_names

		for a_tuple in self._input_table.tuples():
			output_values = []
			for a_string in a_tuple.values():
				if a_tuple.values().index(a_string) != input_keys.index("thumbnail"):
					output_values.append(self.compute_string_of_image(a_tuple) if a_tuple.values().index(a_string) == input_keys.index("image") else a_string)
				if a_tuple.values().index(a_string) == input_keys.index("period"):
					output_values.append(self.compute_string_of_days(a_string))
			output_tuple = Tuple(self._output_table.attributes(),output_values)
			self._output_table.add(output_tuple)

		return
