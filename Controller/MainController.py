import serial
import re
import threading

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

    def parse_line(self, line):
        match = self.pattern.search(line)
        if match:
            try:
                node_id = int(match.group(1))
                temp_raw = int(match.group(2))
                humidity = int(match.group(3))
                luminosity = int(match.group(4))
                timestamp = int(match.group(5))

                temperature = temp_raw / 10.0

                if node_id not in self.first_timestamp:
                    self.first_timestamp[node_id] = timestamp
                    self.view.add_sensor_frame(node_id)

                base_time = self.first_timestamp[node_id]
                key = (timestamp - base_time, node_id)

                self.model.update_data(key, {
                    "temperature": temperature,
                    "humidity": humidity,
                    "luminosity": luminosity
                })

                self.root.after(0, self.view.update_sensor_frame, node_id, temperature, humidity, luminosity, timestamp)

            except Exception as e:
                print(f"Errore nel parsing: {e}")

    def read_serial_loop(self):
        try:
            self.serial = serial.Serial('COM7', 115200, timeout=1)
            print("Seriale connesso. In attesa di dati...")
        except Exception as e:
            print(f"Errore apertura seriale: {e}")
            return             

        while self.running:
            try:
                line = self.serial.readline().decode('utf-8', errors='ignore').strip()
                print(line)
                if line:
                    self.parse_line(line)
            except Exception as e:
                print(f"Errore lettura seriale: {e}")
            except KeyboardInterrupt:
             print("\nLettura interrotta dall'utente.")