setTimeout(function(){
        Java.perform(function () {

            var TM = Java.use("com.jni.anto.kalip.MainActivity");
            TM.dude.overload("java.lang.String").implementation = function (a) {
                send("Called - open()"+a.toString());
                this.dude.overload("java.lang.String").call(this,"hi");
            };


        });

},0);