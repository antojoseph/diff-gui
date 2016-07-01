setTimeout(function(){
        Java.perform(function () {
            var TM = Dalvik.use("java.io.File");
            TM.exists.implementation = function () {
                send("Called - canRead()"+this.path['value']);
                console.log(this.path['value']);
                var result = this.exists.apply(this);
            return result;
            };

        });

},0);