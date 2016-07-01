Java.perform(function(){
            var Classee = Java.use("com.jni.anto.kalip.MainActivity");
            Classee.dude.overloads.forEach(m => { m.implementation = function(){ send("Called ");
            m.apply(this, arguments);
            }})
            });
