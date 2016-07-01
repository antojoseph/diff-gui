Dalvik.perform(function () {
    var Activity = Dalvik.use("android.app.Activity");
    Activity.onResume.implementation = function () {
        send("onResume() called ");
        this.onResume();
    };
});