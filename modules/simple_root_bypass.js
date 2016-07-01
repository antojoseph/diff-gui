setTimeout(function(){
                    Dalvik.perform(function () {

                        var TM = Dalvik.use("com.jni.anto.kalip.MainActivity");

                        TM.isPhoneRooted.implementation = function () {

                            send("Called - isPhoneRooted()");

                            return false;

                        };

                    });

},0);