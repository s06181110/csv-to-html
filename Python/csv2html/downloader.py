#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import shutil
import urllib

from io import IO
from reader import Reader
from table import Table

import requests

class Downloader(IO):
	"""ダウンローダ：CSVファイル・画像ファイル・サムネイル画像ファイルをダウンロードする。"""

	def __init__(self, input_table):
		"""ダウンローダのコンストラクタ。"""

		super(Downloader, self).__init__(input_table)

		return

	def download_csv(self):
		"""情報を記したCSVファイルをダウンロードする。"""
		a_file = requests.get(Attributes.csv_url(Attributes.class))
		return

	def download_images(self, image_filenames):
		"""画像ファイル群または縮小画像ファイル群をダウンロードする。"""
		an_image = requests.get(Attributes.base_url + 'image_filenames')
		return

	def perform(self):
		"""すべて（情報を記したCSVファイル・画像ファイル群・縮小画像ファイル群）をダウンロードする。"""
		self.download_csv()
		self.download_images()

		return
