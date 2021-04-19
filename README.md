# Palette

This program takes an image as input, and returns the 8 main colors in that image.

This was done using Median Cut Color Quantization, which essentially limits an image to a small amount of colors.
It's been very helpful in providing a color palette for my own art through the use of reference images.

I initially wrote this in Java, though this seemed like a perfect opportunity to make a simple dynamic website.
So I rewrote the program in Python, and used Flask, Cloud Run, and Firebase Hosting to get my feet wet with web development.

You can find the website here: https://palette-website.web.app/

The palettes aren't always perfect, but I hope they work for you!
