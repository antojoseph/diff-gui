setTimeout(function(){
        Dalvik.perform(function () {
            var TM = Dalvik.use("android.os.Debug");
            TM.isDebuggerConnected.implementation = function () {
                send("Called - isDebuggerConnected()");
            return false;
            };
        });
},0);