#!/usr/bin/env python
# -*- coding: utf-8 -*-

import commands
import datetime
import locale
import os
import os.path
import re
import shutil

from PIL import Image

from downloader import Downloader
from io import IO
from table import Table
from tuple import Tuple
from writer import Writer

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

		return None

	def compute_string_of_image(self, tuple):
		"""サムネイル画像から画像へ飛ぶためのHTML文字列を作成して、それを応答する。"""

		return None

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
		commands.getoutput(a_command)

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

		return
