import time
import math
from PIL import Image, ImageEnhance

class Palette:
    # Get Image
    image = None

    # Get arr of all pixels in image
    pixels = []
    buckets = []
    paletteSize = 8
    palette = []
    colorChannel = 0

    def getPalette(self, image):
        start = end = 0
        start = time.time()
        self.init(image)
        end = time.time()
        print(f"init(): WAS {end - start}")

        start = time.time()
        self.colorChannel = self.getGreatestChannelRange()
        end = time.time()
        print(f"greatestColorChannel(): WAS {end - start}")

        start = time.time()
        self.mergeSort(self.pixels)
        end = time.time()
        print(f"mergeSort(): WAS {end - start}")

        self.colorSplit(0, len(self.pixels) - 1, self.paletteSize)
        self.averageBuckets()
        return self.palette

    def reset(self):
        self.image = None
        self.pixels = []
        self.buckets = []
        self.palette = []

    # Initialize
    def init(self, image):
        # Open image
        self.reset()
        self.image = Image.open(image)

        # MAYBE BECAUSE THIS MAKES THE COLORS PRETTIER
        contrast = ImageEnhance.Contrast(self.image)
        self.image = contrast.enhance(1.25)
        brightness = ImageEnhance.Brightness(self.image)
        self.image = brightness.enhance(1.075)

        # Make the image 1/16 of normal size
        # Surprisingly has little to no effect on palette output
        self.image = self.image.resize((self.image.width//4, self.image.height//4))

        # Make a list of pixels
        self.pixels = list(self.image.getdata())

    # Find which color channel (R, G, B) has the greatest range by comparing all pixels
    def getGreatestChannelRange(self):
        # i can literally do this w numpy
        # ranges = np.ptp(self.pixels, axis=0)
        # but apparently thats slower than this?? 

        ranges = []
        for i in range(3):
            min, max = 255, 0
            for pixel in self.pixels:
                if (pixel[i] < min):
                    min = pixel[i]
                if (pixel[i] > max):
                    max = pixel[i]
            ranges.append(max - min)

        if ranges[0] >= ranges[1] and ranges[0] >= ranges[2]:
            return 0
        if ranges[1] > ranges[2]:
            return 1
        else:
            return 2

    # Sort pixels by color channel w greatest range in ascending order
    def mergeSort(self, pixels):
        if len(pixels) > 1:
            mid = len(pixels) // 2
            L = pixels[:mid]
            R = pixels[mid:]

            self.mergeSort(L)
            self.mergeSort(R)

            i = j = k = 0

            while i < len(L) and j < len(R):
                if L[i][self.colorChannel] < R[j][self.colorChannel]:
                    pixels[k] = L[i]
                    i += 1
                else:
                    pixels[k] = R[j]
                    j += 1
                k += 1

            while i < len(L):
                pixels[k] = L[i]
                i += 1
                k += 1

            while j < len(R):
                pixels[k] = R[j]
                j += 1
                k += 1

    # Split list into equal 'buckets'
    def colorSplit(self, left, right, depth):
        # Find middle point
        mid = (left + right) // 2

        # Recursive - if the depth is not at 1, then split at the middle point and call colorSplit() again
        # if depth is at 1, add everything from the left point to the right point into a bucket then return
        if (depth > 1):
            self.colorSplit(left, mid, depth // 2)
            self.colorSplit(mid + 1, right, depth // 2)
        else:
            self.buckets.append(self.pixels[left:right + 1])

    # Sum the square of all color (R,G,B) values, then find the average and sqrt()
    # I read somewhere that doing the square thing increases precision and I think it checks out
    # Anyway the values you end up getting and adding to palette are the colors
    def averageBuckets(self):
        r = g = b = 0

        for bucket in self.buckets:
            for color in bucket:
                r += color[0] * color[0]
                g += color[1] * color[1]
                b += color[2] * color[2]

            r //= len(bucket)
            g //= len(bucket)
            b //= len(bucket)

            r = int(math.sqrt(r))
            g = int(math.sqrt(g))
            b = int(math.sqrt(b))

            self.palette.append((r, g, b))
