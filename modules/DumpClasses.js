setTimeout(function(){Java.enumerateLoadedClasses({onMatch: function(className) {send(className);},onComplete:function(){send("done");}});},0);
