import serial
import re
import threading
from datetime import datetime, timedelta


class SensorController:
    def __init__(self, model, view, root):
        self.model = model
        self.view = view
        self.root = root
        self.first_timestamp = {}

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

    def timeCalculation(self, node_id,timestamp):

        if node_id not in self.first_timestamp:
            self.first_timestamp[node_id] = timestamp
            self.view.add_sensor_frame(node_id)

        base_time = self.first_timestamp[node_id]

        if ( base_time >=  timestamp) :
            self.first_timestamp[node_id] = timestamp
            base_time = self.first_timestamp[node_id]

        now = datetime.now()
        delta = timedelta(milliseconds= (timestamp - base_time))
        time = now + delta
        return time.strftime("%H:%M:%S")
    

    def parse_line(self, line):
        match = self.pattern.search(line)
        if match:
            try:
                node_id = int(match.group(1))
                temp_raw = int(match.group(2))
                humidity_raw = int(match.group(3))
                luminosity = int(match.group(4))
                timestamp = int(match.group(5))
                

                temperature = temp_raw / 10.0
                humidity=  (0.0405+(-2.8E-6*humidity_raw))*humidity_raw -4 #https://emesystems.com/OLDSITE/OL2sht1x.htm la conversione lo presa da qua 
                hum_round=round(humidity , 1)

                time=self.timeCalculation(node_id,timestamp)
            
                key = (time, node_id)

                self.model.update_data(key, {
                    "temperature": temperature,
                    "humidity": hum_round,
                    "luminosity": luminosity
                })

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
                
