PYTHON	= python
PYDOC	= pydoc
PYCS	= $(shell find . -name "*.pyc")
TARGET	= csv2html.py
PACKAGE	= csv2html
INSTDIR	= CSV2HTML.app/Contents/Resources/Python/
ARCHIVE	= $(shell basename `pwd`)
WORKDIR    = ~/Desktop

all:
	@rm -rf ~/Desktop/CSV2HTML_[A-Za-z]*_[0-9]*

clean:
	@for each in ${PYCS} ; do echo "rm -f $${each}" ; rm -f $${each} ; done
	@if [ -e $(INSTDIR) ] ; then echo "rm -f -r $(INSTDIR)" ; rm -f -r $(INSTDIR) ; fi
	@if [ -e MANIFEST ] ; then echo "rm -f MANIFEST" ; rm -f MANIFEST ; fi
	@find . -name ".DS_Store" -exec rm {} ";" -exec echo rm -f {} ";"

test:
	$(PYTHON) $(TARGET)

install:
	@if [ ! -e $(INSTDIR) ] ; then echo "mkdir $(INSTDIR)" ; mkdir $(INSTDIR) ; fi
	cp -p -r $(TARGET) $(PACKAGE) $(INSTDIR)

zip: clean
	(cd ../ ; zip -r ./$(ARCHIVE).zip ./$(ARCHIVE)/)

sdist: clean
	$(PYTHON) setup.py sdist

pydoc: clean
	(sleep 3 ; open http://localhost:9999/$(PACKAGE).html) & $(PYDOC) -p 9999

# 
# pip is the PyPA recommended tool for installing Python packages.
# 
pip:
	@if [ -z `which pip` ]; \
	then \
		(cd $(WORKDIR); curl -O https://bootstrap.pypa.io/get-pip.py); \
		(cd $(WORKDIR); sudo -H python get-pip.py); \
		(cd $(WORKDIR); rm -r get-pip.py); \
	else \
		(cd $(WORKDIR); sudo -H pip install -U pip); \
	fi

# 
# Pillow is the friendly PIL fork. PIL is the Python Imaging Library.
# 
pil:
	@if [ -z `pip list --format=freeze | grep Pillow` ]; \
	then \
		(cd $(WORKDIR); sudo -H pip install Pillow); \
	else \
		(cd $(WORKDIR); sudo -H pip install -U Pillow); \
	fi

# 
# List of the required packages
# 
list: pip
	@(pip list --format=freeze | grep pip)
	@(pip list --format=freeze | grep Pillow)
	@(pip list --format=freeze | grep olefile)

prepare: pip pil

update: pip pil
