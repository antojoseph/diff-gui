Dalvik.perform(function () {
             var TextToDeObfuscate= "2MI1KO";  // obfuscated data goes here 
             var ClassToHook = Dalvik.use("com.jni.anto.kalip.MainActivity");
             var Result = ClassToHook.unobfuscate(TextToDeObfuscate);
             send("[*] Tex to original: " + getResult);
         });
