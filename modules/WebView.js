setTimeout(function(){
        Dalvik.perform(function () {

            var WebView = Dalvik.use("android.webkit.WebView");

            WebView.loadUrl.overload("java.lang.String").implementation = function (s) {

                send(s.toString());

                this.loadUrl.overload("java.lang.String").call(this, s);

            };

        });

    },0);