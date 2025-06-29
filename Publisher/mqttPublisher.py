import paho.mqtt.client as mqtt
import json


class MQTTpublisher:
    def __init__(self, addr_broker, port, topic,keepalive):
        self.addr_broker = addr_broker
        self.port = port
        self.topic = topic
        self.keepalive = keepalive
        self.client = mqtt.Client()
        
        self.client.connect(self.addr_broker, self.port, keepalive)

    
    def sendMessage(self, payload:dict):
        payloadInJSON = json.dumps(payload)
        self.client.publish(self.topic, payloadInJSON)

        print("Message sent: ", payloadInJSON)