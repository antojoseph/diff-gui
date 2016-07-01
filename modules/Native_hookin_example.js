Interceptor.attach(Module.findExportByName("libc.so" , "open"), {
    onEnter: function(args) {
        args[1] = ptr(0);
        send(Memory.readCString(args[0])+","+args[1]);
    },
    onLeave:function(retval){

    } });
