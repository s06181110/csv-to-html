#!/usr/bin/env python
# -*- coding: utf-8 -*-

import datetime
import os
import sys

from io import IO

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
		with open(html_filename, 'wb') as a_file:
			self.write_header(a_file)
			self.write_body(a_file)
			self.write_footer(a_file)

		return

	def write_body(self, file):
		"""ボディを書き出す。つまり、属性リストを書き出し、タプル群を書き出す。"""

		return

	def write_footer(self, file):
		"""フッタを書き出す。"""

		return

	def write_header(self, file):
		"""ヘッダを書き出す。"""

		return
