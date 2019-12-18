#!/usr/bin/env python
# -*- coding: utf-8 -*-

import csv

class IO(object):
	"""入出力：リーダ・ダウンローダ・ライタを抽象する。"""

	def __init__(self, a_table):
		"""入出力のコンストラクタ。"""

		super(IO, self).__init__()

		self._table = a_table

		return

	def attributes(self):
		"""属性リストを応答する。"""

		return self.table().attributes()

	def read_csv(self, filename):
		"""指定されたファイルをCSVとして読み込み、行リストを応答する。"""
		rows = []
		first = True
		with open(filename, 'r') as a_file:
			reader = csv.reader(a_file)
			for row in reader:
				rows.append(row)
		return rows

	@classmethod
	def html_canonical_string(the_class, a_string):
		"""指定された文字列をHTML内に記述できる正式な文字列に変換して応答する。"""
		byteCode = a_string.encode('utf-8')
		a_string = byteCode.decode('utf-8')
		return a_string

	def table(self):
		"""テーブルを応答する。"""

		return self._table

	def tuples(self):
		"""タプル群を応答する。"""

		return self.table().tuples()

	def write_csv(self, filename, rows):
		"""指定されたファイルにCSVとして行たち(rows)を書き出す。"""
		with open(filename, 'w', encoding='utf-8') as a_file:
			writer = csv.writer(a_file, delimiter=' ', quotechar='|')
			for row in rows:
				writer.writerow(row)

		return self.filename
