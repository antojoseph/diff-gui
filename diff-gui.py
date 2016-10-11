from flask import *
from jinja2 import Environment
from os import listdir
from os.path import isfile, join,dirname,realpath

import os
import sys
import frida
import requests
import json
import getopt
import socket
import redis


# default variables
port=8000
modulePath=os.path.dirname(os.path.realpath(__file__))+"/modules/"
listenAddress="127.0.0.1"
device = None
red = redis.StrictRedis()

HTML = """{{title}}"""

app = Flask(__name__)


'''
landing page, ask for the remote address of the device, if no remote adresse juste let the input empty 
'''
@app.route('/')
def hello_world():
    return render_template('intro.html')

'''
show list of running process on the device
'''
@app.route('/packages',methods=['GET'])
def show_packages():
    global device
    try:
        remote = request.args.get('remote')
        if device == None:
            if remote != None:        
                # check remote ip address
                try:
                    socket.inet_aton(remote)
                    print "adding remote device to device manager"
                    frida.get_device_manager().add_remote_device(remote)
                    print "getting remote device"
                    device=frida.get_remote_device()
                except socket.error:
                    return render_template('intro.html')
            else:
                device = frida.get_usb_device()

        # get list of apps
        packages=device.enumerate_processes()
        print packages
    except frida.ServerNotRunningError :
        return render_template('error.html',error="cannot connect to remote :(")
    return render_template('packages_list.html',
                           packages=packages)


def event_stream():
    pubsub = red.pubsub()
    pubsub.subscribe('diffdroid')
    print "JUST SUBSCRIBE TO DIFFDROID"
    for message in pubsub.listen():
        print "EVENT_STREAM : ",message
        yield 'data: %s\n\n' % message['data']


@app.route('/stream')
def stream():
    return Response(event_stream(),
                          mimetype="text/event-stream")


def get_messages_from_js(message,data):
    red.publish('diffdroid', message['payload'])
    print "JUST_PUBLISHED : ", message['payload']
    #sse.publish({"message": json.dumps(message['payload'])}, type='greeting')
    #r = requests.post("http://127.0.0.1:8000/ding", data=json.dumps(message['payload']), headers=head)

def start_frida(x,bleeh):
    global device
    print device
    process = device.attach(bleeh)

    script = process.create_script(x)
    script.on('message',get_messages_from_js)
    script.load()

@app.route('/hack')
def hack():
    module = request.args.get('module')
    
    txt = open(modulePath+module,'r').read()
    return render_template('main.html',
                           content=txt)


@app.route('/lol',methods=['GET', 'POST'])
def lol():
    try:
        blah = request.get_json()
        bleeh = request.args.get('package_name')
        if blah is None:
             return "boo"
        else:
            x = Environment().from_string(HTML).render(title=request.get_json())
            start_frida(x,bleeh)
            
            response = jsonify({"message":"ok"})
            response.status_code = 200
            return response
    except Exception as e:
        print e.message, e.args


@app.route('/list')
def list():
    bleeh = request.args.get('package_name')
    filess = [f for f in listdir(modulePath) if isfile(join(modulePath, f))]
    return render_template("list.html",
                           posts=filess,
                           package_name = bleeh)

@app.route('/update')
def update():
    return render_template("update.html")


def main(argv):
    global modulePath
    global listenAddress
    global port
    try:
        opts, args = getopt.getopt(argv[1:],"hp:a:",["help","port=","address="])
    except getopt.GetoptError:
        print "Usage:"
        print argv[0] + ' -p <port>'
        sys.exit(2)
    for opt, arg in opts:
        print "OPT ",opt
        if opt in ('-h',"--help"):
            print "Usage:"
            print argv[0] + ' -p <port>'
            sys.exit()
        elif opt in ("-p", "--port"):
            port = arg
        elif opt in ("-a","--address"):
            listenAddress= arg

    app.run(host=listenAddress,port=port,threaded=True)


if __name__ == '__main__':
    main(sys.argv)
