# ood-ime
IME Program for NU CS3500


#Commands to load example image, modify it and save the output

Run IME main method
````
load res/PPMImages/testOG.ppm OG
brighten 10 OG OG-brighter
vertical-flip OG OG-vertical
horizontal-flip OG OG-horizontal
value-component OG OG-value
save res/Test/OG-brighter.ppm OG-brighter
save res/Test/OG-vertical.ppm OG-vertical
save res/Test/OG-horizontal.ppm OG-horizontal
save res/Test/OG-value.ppm OG-value
quit