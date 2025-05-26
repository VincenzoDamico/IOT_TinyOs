import tkinter as tk
from View.sensorFrame import SensorFrame

class SensorView:
    def __init__(self, root):
        self.root = root
        self.sensor_frames = {}  # node_id -> frame

    def add_sensor_frame(self, node_id):
        frame = SensorFrame(master=self.root.get_main_content())
        frame.pack(fill=tk.X, side=tk.TOP, padx=30, pady=15)
        self.sensor_frames[node_id] = frame
        frame.sensorID.set(f"{node_id}")
        return frame

    def update_sensor_frame(self, node_id, temperature, humidity, luminosity, timestamp):
        frame = self.sensor_frames.get(node_id)
        if frame:
            frame.temp.set(f"{temperature} °C")
            frame.hum.set(f"{humidity} %")
            frame.lum.set(f"{luminosity} lx")
            frame.time.set(str(timestamp))

