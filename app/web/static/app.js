let inputFile = document.getElementById("inputFile");
const imagePreview = document.getElementById("imagePreview");
let image = imagePreview.querySelector(".image-display-image");
let imageText = imagePreview.querySelector(".image-display-text");

let button = document.getElementById("submit-button");
button.disabled = true;
var colors = '{{ colors[0] }}';

inputFile.addEventListener("change", function(){
    const file = this.files[0];
    if(file) {
        const reader = new FileReader();
        
        imageText.style.display = "none";
        image.style.display = "block";

        reader.addEventListener("load", function() {
            image.setAttribute("src", this.result);
            button.disabled = false;
        });

        reader.readAsDataURL(file);

    }else{
        imageText.style.display = null;
        image.style.display = null;
        button.disabled = true;
    }
});


// TAKEN FROM
// https://stackoverflow.com/questions/400212/how-do-i-copy-to-the-clipboard-in-javascript
function fallbackCopyTextToClipboard(text) {
    var textArea = document.createElement("textarea");
    textArea.value = text;

    // Avoid scrolling to bottom
    textArea.style.top = "0";
    textArea.style.left = "0";
    textArea.style.position = "fixed";

    document.body.appendChild(textArea);
    textArea.focus();
    textArea.select();

    try {
        var successful = document.execCommand('copy');
        var msg = successful ? 'successful' : 'unsuccessful';
        console.log('Fallback: Copying text command was ' + msg);
    } catch (err) {
        console.error('Fallback: Oops, unable to copy', err);
    }

    document.body.removeChild(textArea);
}
function copyTextToClipboard(text) {
    if (!navigator.clipboard) {
        fallbackCopyTextToClipboard(text);
        return;
    }
    navigator.clipboard.writeText(text).then(function() {
        console.log('Async: Copying to clipboard was successful!');
    }, function(err) {
        console.error('Async: Could not copy text: ', err);
    });
}

var c1 = document.getElementById("c1"), 
c2 = document.getElementById("c2"), 
c3 = document.getElementById("c3"), 
c4 = document.getElementById("c4"), 
c5 = document.getElementById("c5"), 
c6 = document.getElementById("c6"), 
c7 = document.getElementById("c7"), 
c8 = document.getElementById("c8");

c1.addEventListener('click', function(event) {
    copyTextToClipboard(c1.innerText);
});

c2.addEventListener('click', function(event) {
    copyTextToClipboard(c2.innerText);
});

c3.addEventListener('click', function(event) {
    copyTextToClipboard(c3.innerText);
});

c4.addEventListener('click', function(event) {
    copyTextToClipboard(c4.innerText);
});

c5.addEventListener('click', function(event) {
    copyTextToClipboard(c5.innerText);
});

c6.addEventListener('click', function(event) {
    copyTextToClipboard(c6.innerText);
});

c7.addEventListener('click', function(event) {
    copyTextToClipboard(c7.innerText);
});

c8.addEventListener('click', function(event) {
    copyTextToClipboard(c8.innerText);
});