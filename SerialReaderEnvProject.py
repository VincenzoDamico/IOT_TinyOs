import serial
import re
import datetime

SERIAL_PORT = 'COM4'
BAUD_RATE = 115200

sensor_data_dict = {}

pattern = re.compile(
    r"id.*?:\s*.*?(\d+).*?"
    r"Temp.*?:\s*.*?(\d+).*?"
    r"Hum.*?:\s*.*?(\d+).*?"
    r"Lum.*?:\s*.*?(\d+).*?"
    r"TimeStamp.*?:\s*.*?(\d+).*?"
)

def parse_sensor_line(line):
    match = pattern.search(line)
    if match:
        try:
            node_id = int(match.group(1))
            temp_raw = int(match.group(2))
            humidity = int(match.group(3))
            luminosity = int(match.group(4))
            timestamp = int(match.group(5))

            temperature_float = float(temp_raw) / 10.0

            key = (timestamp, node_id)

            parsed_data = {
                "temperature": temperature_float,
                "humidity": humidity,
                "luminosity": luminosity
            }
            return key, parsed_data
        except ValueError as e:
            print(f"Errore di conversione dei dati dalla riga: {line} - {e}")
            return None, None
    return None, None

def leggi_e_processa_dati_seriale():
    try:
        with serial.Serial(SERIAL_PORT, BAUD_RATE, timeout=1) as ser:
            print(f"Connesso a {SERIAL_PORT} a {BAUD_RATE} bps.")
            print("In attesa di dati... Premi Ctrl+C per uscire.")
            print("-" * 50)

            while True:
                line = ser.readline().decode('utf-8', errors='ignore').strip()

                if line:
                    print(f"Ricevuto: {line}")

                    key, parsed_data = parse_sensor_line(line)

                    if key and parsed_data:
                        sensor_data_dict[key] = parsed_data
                        print(f"Aggiunto al dizionario: Chiave={key}, Dati={parsed_data}")
                    print("-" * 50)

    except serial.SerialException as e:
        print(f"Errore nella connessione seriale: {e}")
    except KeyboardInterrupt:
        print("\nLettura interrotta dall'utente.")

if __name__ == "__main__":
    leggi_e_processa_dati_seriale()
