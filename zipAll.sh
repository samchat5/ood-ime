#!/bin/bash
rm -r code.zip

# zip PPMImages
cd res/PPMImages
for file in *.ppm; do zip ${file%.*}.zip $file; done
zip -r ppm.zip *.zip
mv ppm.zip ..
rm -r *.zip

# zip PNGImages
cd ../PNGImages
for file in *.png; do zip ${file%.*}.zip $file; done
zip -r png.zip *.zip
mv png.zip ..
rm -r *.zip

# zip JPGImages
cd ../JPGImages
for file in *.jpg; do zip ${file%.*}.zip $file; done
zip -r jpg.zip *.zip
mv jpg.zip ..
rm -r *.zip

cd ..
zip -r IME.zip IME.jar
zip -r Commands.zip Commands.txt
zip -r res.zip *.zip
mv res.zip ..
rm -r *.zip

cd ..
zip -r code.zip res.zip src test README.md USEME.md
rm -r res.zip
