setTimeout(function(){
        Java.perform(function () {

            var TM = Dalvik.use("java.io.File");

            TM.exists.implementation = function () {
                send("Called - canRead()");
                console.log(this.path['value']);
                var file_path = this.path['value'];
                var root_locations = ['/bin/su','/xbin/su','Superuser.apk','busybox','/sdcard/test'];
                for (i = 0; i < root_locations.length; i++) {
                        console.log(" Comparing " + root_locations[i] + " with "+file_path);

                        if(root_locations[i] === file_path){
                            console.log('lalal');
                            return false;
                        }
                }
            return true;
            };

        });

    },0);