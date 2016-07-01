setTimeout(function(){
        Dalvik.perform(function () {
            var TM = Dalvik.use("android.telephony.TelephonyManager");
            TM.getDeviceId.implementation = function () {
                send("Called - deviceID() , returning 007");
                return "007";
            };
        });
},0);