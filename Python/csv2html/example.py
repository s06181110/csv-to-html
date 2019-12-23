#!/usr/bin/env python
# -*- coding: utf-8 -*-

from csv2html.attributes import AttributesForPrimeMinisters, AttributesForTokugawaShogunate, AttributesForKSUPresidents
from csv2html.translator import Translator

class Example(object):
	"""例題プログラム：CSVファイルをHTMLページへと変換する。"""

	def main(self):
		"""CSVファイルをHTMLページへと変換するメインプログラム。"""

		# 総理大臣と徳川幕府の属性リストのクラス群を作る。
		classes = []
		classes.append(AttributesForPrimeMinisters)
		classes.append(AttributesForTokugawaShogunate)
		classes.append(AttributesForKSUPresidents)

		for class_attributes in classes:
			Translator.perform(class_attributes)

		return 0

if __name__ == "__main__":
	an_example = Example()
	import sys
	sys.exit(an_example.main())
