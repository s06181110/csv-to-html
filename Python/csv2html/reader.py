#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os

from io import IO
from tuple import Tuple

class Reader(IO):
	"""リーダ：情報を記したCSVファイルを読み込んでテーブルに仕立て上げる。"""

	def __init__(self, input_table):
		"""リーダのコンストラクタ。"""

		super(Reader, self).__init__(input_table)

		return

	def perform(self):
		"""ダウンロードしたCSVファイルを読み込む。"""
		first = True
		filename = os.path.join(self.attributes().base_directory(), self.attributes().csv_url().split('/')[-1])
		for a_line in self.read_csv(filename):
			if first:
				self.attributes()._names = a_line
				first = False
				continue
			a_tuple = Tuple(self.attributes(), a_line)
			self.table().add(a_tuple)
		return
