# === MODEL ===
class SensorModel:
    def __init__(self):
        self.data = {}  # (timestamp, id) -> sensor values

    def update_data(self, key, values):
        self.data[key] = values

    def get_all_data(self):
        return self.data
