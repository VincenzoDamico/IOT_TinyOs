import paho.mqtt.client as mqtt
import json

addr_broker = "localhost"
port = 1883
topic = "sensors/telosB"

class MQTTpublisher:
    def __init__(self, addr_broker, port, topic):
        self.addr_broker = addr_broker
        self.port = port
        self.topic = topic

        self.client = mqtt.Client()
        self.client.connect(self.addr_broker, self.port, 60)

    
    def sendMessage(self, payload:dict):
        payloadInJSON = json.dumps(payload)
        self.client.publish(self.topic, payloadInJSON)

        print("Message sent: ", payloadInJSON)