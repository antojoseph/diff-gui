# diff-gui

Uses flask for a web framework , jinja for templates , redis for message que , server side push for real time updates and some js .

start webserver with the following command :

      gunicorn diff-gui:app --worker-class gevent --bind 127.0.0.1:80

If it complains , make sure you have all the python dependencies installed !

anto$ cat  requirements.txt 

      flask 
      jinja2 
      flask_sse
      frida
      requests
      json

you can use pip install module_name to install all of these dependencies 

Install redis , and start it by typing 
      redis-server
in the console

Download frida server for arm android from frida.re and push it to the device

      adb push frida-server /data/local/tmp
      adb shell
      cd /data/local/tmp
      ./frida-server


execute 
      frida-ps -U 
to make sure you have a working installation , the command should list all the processes running on the device / emulator



Now , you can start instrumenting with the avaliable modules !

Happy Hacking :)

Initial Screen  - Enter Package name here !
![alt tag](https://raw.githubusercontent.com/antojoseph/diff-gui/master/readme_images/screen1.png)

Select the Module you want to use , or add modules by just adding js scripts to modules folder
![alt tag](https://raw.githubusercontent.com/antojoseph/diff-gui/master/readme_images/screen2.png)

Run the script or make any changes in the IDE and get Results in the same screen !
![alt tag](https://raw.githubusercontent.com/antojoseph/diff-gui/master/readme_images/screen3.png)

You can also do native hooking as shown below !
![alt tag](https://raw.githubusercontent.com/antojoseph/diff-gui/master/readme_images/screen4.png)
