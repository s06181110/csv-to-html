#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Table(object):
	"""表：情報テーブル。"""

	def __init__(self, kind_string, class_attributes):
		"""テーブルのコンストラクタ。"""

		super(Table, self).__init__()

		self._attributes = class_attributes(kind_string)
		self._tuples = []

		return

	def __str__(self):
		"""自分自身を文字列にして、それを応答する。"""

		string = '0: ' + str(self.attributes())
		no = 1
		for tuple in self._tuples:
			string += '\n' + str(no) + ': ' + str(tuple)
			no += 1

		return string

	def add(self, tuple):
		"""タプルを追加する。"""

		self._tuples.append(tuple)

		return

	def attributes(self):
		"""属性リストを応答する。"""

		return self._attributes

	def image_filenames(self):
		"""画像ファイル群をリストにして応答する。"""

		return None

	def thumbnail_filenames(self):
		"""縮小画像ファイル群をリストにして応答する。"""

		return None

	def tuples(self):
		"""タプル群を応答する。"""

		return self._tuples
