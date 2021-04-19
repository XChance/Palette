from flask import Flask, render_template, request, redirect, make_response
from palette import Palette
from PIL import Image
import os
import time
import random

app = Flask(__name__,template_folder='./web',static_folder='./web/static')
palette = Palette()

filename = None

# images 9 - 16 were taken from pexels.com
# images 1 - 8 were taken from google images
# some of images 1 - 8 may be copyrighted or something
# i do not care
images = []
images.append("web/static/images/img1.jpg")
images.append("web/static/images/img2.jpg")
images.append("web/static/images/img3.jpg")
images.append("web/static/images/img5.jpg")
images.append("web/static/images/img6.jpg")
images.append("web/static/images/img7.jpg")
images.append("web/static/images/img8.jpg")
images.append("web/static/images/img9.jpg")
images.append("web/static/images/img10.jpg")
images.append("web/static/images/img11.jpg")
images.append("web/static/images/img12.jpg")
images.append("web/static/images/img13.jpg")
images.append("web/static/images/img14.jpg")
images.append("web/static/images/img15.jpg")
images.append("web/static/images/img16.jpg")

@app.route('/', methods=['GET', 'POST'])
def index():
    global palette, filename

    if filename is not None and os.path.exists("web/static/images/uploads/" + filename):
        os.remove("web/static/images/uploads/" + filename)
        
    filename = None

    if request.method == 'POST':
        if request.files:
            image = request.files["inputFile"]
            filename = image.filename

            if not validImage():
                return redirect(request.url)

            image.save(os.path.join("web/static/images/uploads", filename))
            colors = palette.getPalette(image)
            r = make_response(render_template('index.html.j2', colors=colors, srcFile="static/images/uploads/" + filename))
            r.headers.set('Cache-Control', 'public, max-age=15, s-maxage=180')
            return r

    i = random.randrange(len(images))
    colors = palette.getPalette(images[i])
    srcFile = images[i].replace("web", "")
    r = make_response(render_template('index.html.j2', colors=colors, srcFile=srcFile))
    r.headers.set('Cache-Control', 'public, max-age=15, s-maxage=180')
    return r

def validImage():
    global filename

    if not "." in filename:
        return False

    extension = filename.rsplit(".", 1)[1]
    validExtensions = ["PNG", "JPG", "JPEG"]

    if extension.upper() in validExtensions:
        return True
    else:
        return False

if __name__ == '__main__':
    # app.run(debug=True, host='0.0.0.0', port=int(os.environ.get('PORT', 8080)))
    app.run(debug=True, host='localhost', port=int(os.environ.get('PORT', 8080)))