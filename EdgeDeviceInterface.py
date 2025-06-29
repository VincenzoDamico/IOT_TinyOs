from Controller.MainController import SensorController
from View.MainView import SensorView
from View.root import Root
from Publisher.mqttPublisher import MQTTpublisher

if __name__ == "__main__":
    
    addr_broker = "localhost"
    port = 1883
    topic = "sensors/telosB"
    keepalive=60
    app = Root()
    view = SensorView(app)
    publisherMQTT= MQTTpublisher( addr_broker, port, topic,keepalive)
    controller = SensorController(publisherMQTT, view, app)
    try:
        app.mainloop()
    except KeyboardInterrupt:
        print("\nLettura interrotta dall'utente")
    controller.running = False