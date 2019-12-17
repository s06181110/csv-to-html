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

		return
