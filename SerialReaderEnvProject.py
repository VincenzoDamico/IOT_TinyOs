import serial
import re

from Publisher.mqttPublisher import MQTTpublisher, addr_broker, port, topic
import time

SERIAL_PORT = 'COM3'
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
            humidity_raw = int(match.group(3))  
            luminosity = int(match.group(4))
            timestamp = int(match.group(5))

            temperature_float = float(temp_raw) / 10.0
            humidity = ((0.0405 + (-2.8E-6 * humidity_raw)) * humidity_raw - 4)
            hum_round = round(humidity, 1)
            
            key = (timestamp, node_id)

            parsed_data = {
                "temperature": temperature_float,
                "humidity": hum_round,
                "luminosity": luminosity
            }
            return key, parsed_data
        except ValueError as e:
            print(f"Errore di conversione dei dati dalla riga: {line} - {e}")
            return None, None
    return None, None

def leggi_e_processa_dati_seriale():

    publisherMQTT = MQTTpublisher(addr_broker,port,topic)

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

                        timestamp, node_id = key #per estrarre i due valori

                        timestamp = int(time.time())

                        payloadPyt = {
                            "idSensor": node_id,
                            "timestamp": timestamp,
                            **parsed_data
                        }

                        publisherMQTT.sendMessage(payloadPyt)
                        
                        print(f"Aggiunto al dizionario: Chiave={key}, Dati={parsed_data}")
                    print("-" * 50)

    except serial.SerialException as e:
        print(f"Errore nella connessione seriale: {e}")
    except KeyboardInterrupt:
        print("\nLettura interrotta dall'utente.")

if __name__ == "__main__":
    leggi_e_processa_dati_seriale()
