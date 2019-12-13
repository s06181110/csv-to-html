#!/usr/bin/env python
# -*- coding: utf-8 -*-

class Tuple(object):
	"""タプル：情報テーブルの中の各々のレコード。"""

	def __init__(self, attributes, values):
		"""属性リストと値リストからタプルを作るコンストラクタ。"""

		super(Tuple, self).__init__()

		self._attributes = attributes
		self._values = values

		return

	def __str__(self):
		"""自分自身を文字列にして、それを応答する。"""

		keys = self.attributes().keys()
		string = '['
		for index in range(0, len(keys)):
			if index != 0: string += ', '
			string += keys[index] + ':' + self._values[index]
		string += ']'

		return string

	def attributes(self):
		"""属性リストを応答する。"""

		return self._attributes

	def values(self):
		"""値リストを応答する。"""

		return self._values
