#!/bin/bash
rm -r code.zip
cd res/PPMImages
for file in *.ppm; do zip ${file%.*}.zip $file; done
zip -r res.zip *.zip
mv res.zip ../..
rm -r *.zip
cd ../..
zip -r code.zip res.zip src test
rm -r res.zip
