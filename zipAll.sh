#!/bin/bash

for file in res/PPMImages/*.ppm; do zip ${file%.*}.zip $file; done
zip res.zip res/PPMImages/*.zip
rm res/PPMImages/*.zip
zip code.zip res.zip src test
rm res.zip
