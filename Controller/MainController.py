import serial
import re
import threading
from datetime import datetime, timedelta


class SensorController:
    def __init__(self, publisherMQTT, view, root):
        self.publisherMQTT = publisherMQTT
        self.view = view
        self.root = root
        self.previous_uptime = {}

        self.pattern = re.compile(
            r"id.*?:\s*.*?(\d+).*?"
            r"Temp.*?:\s*.*?(\d+).*?"
            r"Hum.*?:\s*.*?(\d+).*?"
            r"Lum.*?:\s*.*?(\d+).*?"
            r"TimeStamp.*?:\s*.*?(\d+).*?"
        )

        self.serial = None
        self.running = True
        self.thread = threading.Thread(target=self.read_serial_loop, daemon=True)
        self.thread.start()

    def timeCalculation(self, node_id,uptime):
        now = datetime.now()

        if node_id not in self.previous_uptime:
            self.previous_uptime[node_id] =uptime
            self.view.add_sensor_frame(node_id)
            return now.strftime("%Y-%m-%d\n%H:%M:%S")
        
        previous_uptime=self.previous_uptime[node_id]

        if ( uptime <=  previous_uptime) :
            self.previous_uptime[node_id] =uptime
            return now.strftime("%Y-%m-%d\n%H:%M:%S")

        generetionTime = uptime-previous_uptime

        time = now-timedelta(milliseconds= (generetionTime))
        self.previous_uptime[node_id] =uptime

        return time.strftime("%Y-%m-%d\n%H:%M:%S")
    

    def parse_line(self, line):
        match = self.pattern.search(line)
        if match:
            try:
                node_id = int(match.group(1))
                temp_raw = int(match.group(2))
                humidity_raw = int(match.group(3))
                luminosity = int(match.group(4))
                uptime = int(match.group(5))
                

                temperature = temp_raw / 10.0
                humidity=  (0.0405+(-2.8E-6*humidity_raw))*humidity_raw -4 #https://emesystems.com/OLDSITE/OL2sht1x.htm la conversione lo presa da qua 
                hum_round=round(humidity , 1)

                time=self.timeCalculation(node_id,uptime)
            
                parsed_data = {
                    "temperature": temperature,
                    "humidity": hum_round,
                    "luminosity": luminosity
                }

                payloadPyt = {
                            "idSensor": node_id,
                            "timestamp": time.replace("\n","_"),
                            **parsed_data
                        }
                #sostituisco il model con influxdb collegato via node red
                self.publisherMQTT.sendMessage(payloadPyt)

                self.root.after(0, self.view.update_sensor_frame, node_id, temperature, hum_round, luminosity, time )

            except Exception as e:
                print(f"Errore nel parsing: {e}")

    def read_serial_loop(self):
        try:
            self.serial = serial.Serial('COM7', 115200, timeout=1)
            print("Seriale connesso. In attesa di dati... per interrompere la connesione premere Ctrl+c")
        except Exception as e:
            print(f"Errore apertura seriale: {e}")
            return             

        while self.running:
            try:
                line = self.serial.readline().decode('utf-8', errors='ignore').strip()
                if line:                
                    print(line)
                    self.parse_line(line)
            except Exception as e:
                print(f"Errore lettura seriale: {e}")
                
