#!/usr/bin/env python
# -*- coding: utf-8 -*-

import os
import shutil
import urllib

from io import IO
from reader import Reader
from table import Table

class Downloader(IO):
	"""ダウンローダ：CSVファイル・画像ファイル・サムネイル画像ファイルをダウンロードする。"""

	def __init__(self, input_table):
		"""ダウンローダのコンストラクタ。"""

		super(Downloader, self).__init__(input_table)

		return

	def download_csv(self):
		"""情報を記したCSVファイルをダウンロードする。"""
		csv_url = self.attributes().csv_url()
		print("From: " + csv_url)
		filename = os.path.join(self.attributes().base_directory(), csv_url.split('/')[-1])
		print("To: " + filename)
		if os.path.exists(filename) and os.path.isfile(filename):
			pass
		else:
			urllib.urlretrieve(csv_url, filename)
		return

	def download_images(self, image_filenames):
		"""画像ファイル群または縮小画像ファイル群をダウンロードする。"""
		for image_file in image_filenames:
			image_url = self.attributes().base_url() + image_file
			print("From: " + image_url)
			image_path = os.path.join(self.attributes().base_directory(), image_file)
			print("To: " + image_path)
			urllib.urlretrieve(image_url,  image_path)
		return

	def perform(self):
		"""すべて（情報を記したCSVファイル・画像ファイル群・縮小画像ファイル群）をダウンロードする。"""
		self.download_csv()
		a_reader = Reader(self.table())
		a_reader.perform()
		os.makedirs(os.path.join(self.attributes().base_directory(), "images"))
		os.makedirs(os.path.join(self.attributes().base_directory(), "thumbnails"))

		self.download_images(self.table().image_filenames())
		self.download_images(self.table().thumbnail_filenames())

		return