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
    	filename = os.path.join(self.attributes().base_directory(), csv_url.split('/')[-1])
    	with codecs.open(filename, 'r', 'utf-8') as a_filename:
        	for a_line in filename:
            	a_string = a_line.split()[0]
				if first :
					Table(attributes.names, a_line)
					first = False
				Tuple(aTable.attributes, a_line)
				Table.add(a_tuple)
		return
