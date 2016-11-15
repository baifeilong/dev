!(function(doc, win) {
  var docEle = doc.documentElement,
      gbody = document.getElementsByTagName("body")[0]
  evt = "onorientationchange" in window ? "orientationchange" : "resize",
      fn = function() {
          var width = docEle.clientWidth;
          width && (docEle.style.fontSize = 100 * (width / 640) + "px");
      };
  win.addEventListener(evt, fn, false);
  doc.addEventListener("DOMContentLoaded", fn, false);
  gbody.style.display = "block";
  }(document, window));