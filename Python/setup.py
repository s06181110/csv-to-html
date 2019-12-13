#!/usr/bin/env python
# -*- coding: utf-8 -*-

"""
ソースコードディストリビューション（sdist）のための設え（しつらえ：setup）です。
$ python setup.py sdist
"""

__author__ = 'AOKI Atsushi'
__version__ = '1.0.6'
__date__ = '2017/02/26 (Created: 2016/01/24)'

from distutils.core import setup

setup( \
	name='CSV2HTML', \
	version=__version__, \
	description='CSV2HTML written by Python', \
	url='http://www.cc.kyoto-su.ac.jp/~atsushi/', \
	author=__author__, \
	author_email='atsushi@cc.kyoto-su.ac.jp', \
	license='The BSD 2-Clause License', \
	long_description='総理大臣ページや徳川幕府ページを生成します。', \
	platforms='macOS (10.12.3) Sierra', \
	packages=['csv2html'], \
)
