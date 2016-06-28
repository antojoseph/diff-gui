from flask import Flask,render_template,request
from jinja2 import Environment
from os import listdir
from os.path import isfile, join
from flask_sse import sse
import sys
import frida
import requests
import json

HTML = """{{title}}"""

app = Flask(__name__)
app.config["REDIS_URL"] = "redis://localhost"
app.register_blueprint(sse, url_prefix='/stream')

def get_messages_from_js(message,data):
    print(message)
    print (message['payload'])
    head = {'content-type': 'application/json'}
    r = requests.post("http://127.0.0.1/ding", data=json.dumps(message['payload']), headers=head)



def start_frida(x,bleeh):
    process = frida.get_usb_device().attach(bleeh)
    script = process.create_script(x)
    script.on('message',get_messages_from_js)
    script.load()
    #sys.stdin.read()


@app.route('/')
def hello_world():
    #return 'Enter the packagename as package_name=com.jni.anto.kalip in the URL'
    return render_template('intro.html')

@app.route('/hack')
def hack():
    username = request.args.get('username')
    mypath = "/Users/anto/PycharmProjects/diff-gui/modules/"
    txt = open(mypath+username,'r').read()
    return render_template('main.html',
                           content=txt)


@app.route('/lol',methods=['GET', 'POST'])
def lol():
    try:
        blah = request.get_json()
        bleeh = request.args.get('package_name')
        if blah is None:
            return "Booooo!"
        else:
            x = Environment().from_string(HTML).render(title=request.get_json())
            #write_data_to_file_and_exec(x)
            start_frida(x,bleeh)
            print x
            print bleeh
            return "started frida"
    except Exception as e:
        print e.message, e.args


@app.route('/list')
def list():
        bleeh = request.args.get('package_name')
        mypath = "/Users/anto/PycharmProjects/diff-gui/modules"
        filess = [f for f in listdir(mypath) if isfile(join(mypath, f))]
        return render_template("list.html",
                posts=filess,
                package_name = bleeh)

@app.route('/update')
def update():
    return render_template("update.html")

@app.route('/ding',methods=['GET', 'POST'])
def ding():
        print "received data from python!!"
        username = request.get_json()
        print username
        sse.publish({"message": username}, type='greeting')
        return "Message sent!"




if __name__ == '__main__':
    app.run()
