import serial
import re
import datetime
import tkinter as tk
from PIL import Image, ImageTk

# Ilenia contribution

def creationInterface():
    window = tk.Tk()     #main window
    window.geometry("1350x650+15+20")      #dimensions
    window.resizable(True,True)     #resizable window
    window.title("IoT Project")
    window.configure(background="midnightblue")

    window.overrideredirect(True)  # Rimuove barra di sistema


    def move_window(event):
        window.geometry(f'+{event.x_root}+{event.y_root}')

    def ripristina():
        window.state("normal")

    def maximize():
        window.state("zoomed")

    def close():
        window.destroy()


    # --- Barra personalizzata ---
    bar = tk.Frame(window, bg="navy", relief='raised', bd=0, height=30)
    bar.pack(fill=tk.X, side=tk.TOP)

    # Permette di spostare la finestra trascinando la barra
    bar.bind("<B1-Motion>", move_window)

    # Titolo personalizzato
    titolo = tk.Label(bar, text="IoT Project", bg="navy", fg="white", font=("Arial", 11, "bold"))
    titolo.pack(side=tk.LEFT, padx=10)

    #Button close
    btn_close = tk.Button(bar, text="✕", bg="navy", fg="white", bd=0, font=("Arial", 10), command=close)
    btn_close.pack(side=tk.RIGHT, padx=4)

    #Button maximize
    btn_max = tk.Button(bar, text="🗖", bg="navy", fg="white", bd=0, font=("Arial", 14), command=maximize)
    btn_max.pack(side=tk.RIGHT, padx=4)

    #Button minimize
    btn_min = tk.Button(bar, text="-", bg="navy", fg="white", bd=0, font=("Arial", 14), command=ripristina)
    btn_min.pack(side=tk.RIGHT, padx=4)

    #Content window
    content = tk.Frame(window, bg="midnightblue")
    content.pack(fill=tk.BOTH, expand=True)


  #  leggi_e_processa_dati_seriale()
 #   key,values =next(iter(sensor_data_dict.items()))
 #   
    ###Sensor1
    lb = tk.Label(content,text="ID_sensor1",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=0,column=0,padx=50,pady=(70,35))
    
    #Temperature
    lb = tk.Label(content, text="Temperature",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=1,column=1,sticky="w",padx=20,pady=2)

    img_Temp = Image.open(r"Utility\Images\temp.jpg")
    img_Temp = img_Temp.resize((225,125))
    im_Temper = ImageTk.PhotoImage(img_Temp)

    lb = tk.Label(content, image=im_Temper,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=2, column=1,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="24°C    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    #Humidity
    lb = tk.Label(content, text="Humidity",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=1,column=3,sticky="w",padx=20,pady=2)

    img_Hum = Image.open(r"Utility\Images\hum.jpg")
    img_Hum = img_Hum.resize((225,125))
    im_Hum = ImageTk.PhotoImage(img_Hum)

    lb = tk.Label(content, image=im_Hum,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=2, column=3,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="valHum    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    #Luminosity
    lb = tk.Label(content, text="Luminosity",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=1,column=5,sticky="w",padx=20,pady=2)

    img_Lum = Image.open(r"Utility\Images\lum.jpg")
    img_Lum = img_Lum.resize((225,125))
    im_Lum = ImageTk.PhotoImage(img_Lum)

    lb = tk.Label(content, image=im_Lum,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=2, column=5,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="valLum    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    #Timestamp
    lb = tk.Label(content, text="Timestamp",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=1,column=7,sticky="w",padx=20,pady=2)

    img_TimeS = Image.open(r"Utility\Images\timS.jpg")
    img_TimeS = img_TimeS.resize((225,125))
    im_TimeS = ImageTk.PhotoImage(img_TimeS)

    lb = tk.Label(content, image=im_TimeS,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=2, column=7,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="time    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    #####
    ##Sensor2
    lb = tk.Label(content,text="ID_sensor2",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=4,column=0,padx=50,pady=(70,35))

    #Temperature
    lb = tk.Label(content, text="Temperature",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=5,column=1,sticky="w",padx=20,pady=2)

    img_Temp2 = Image.open(r"Utility\Images\temp.jpg")
    img_Temp2 = img_Temp2.resize((225,125))
    im_Temper2 = ImageTk.PhotoImage(img_Temp2)

    lb = tk.Label(content, image=im_Temper2,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=6, column=1,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="valTemp    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    #Humidity
    lb = tk.Label(content, text="Humidity",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=5,column=3,sticky="w",padx=20,pady=2)

    img_Hum2 = Image.open(r"Utility\Images\hum.jpg")
    img_Hum2 = img_Hum2.resize((225,125))
    im_Hum2 = ImageTk.PhotoImage(img_Hum2)

    lb = tk.Label(content, image=im_Hum2,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=6, column=3,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="valHum    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    #Luminosity
    lb = tk.Label(content, text="Luminosity",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=5,column=5,sticky="w",padx=20,pady=2)

    img_Lum2 = Image.open(r"Utility\Images\lum.jpg")
    img_Lum2 = img_Lum2.resize((225,125))
    im_Lum2 = ImageTk.PhotoImage(img_Lum2)

    lb = tk.Label(content, image=im_Lum2,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=6, column=5,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="valLum    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    #Timestamp
    lb = tk.Label(content, text="Timestamp",bg="midnightblue",font=("System",17,"bold"),fg="ghostwhite")
    lb.grid(row=5,column=7,sticky="w",padx=20,pady=2)

    img_TimeS2 = Image.open(r"Utility\Images\timS.jpg")
    img_TimeS2 = img_TimeS2.resize((225,125))
    im_TimeS2 = ImageTk.PhotoImage(img_TimeS2)

    lb = tk.Label(content, image=im_TimeS2,bd=0,text="",compound="center",font=("System",22,"bold"),fg="lavender")
    lb.grid(row=6, column=7,sticky="w",padx=20,pady=1.5)
    #aggiorna()     
    lb.config(text="time    ", bd=0, bg="midnightblue")           ###in questa riga va richiamata la nostra

    window.mainloop()


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

            line = ser.readline().decode('utf-8', errors='ignore').strip()

            while True:
                if line:
                    print(f"Ricevuto: {line}")
                    key, parsed_data = parse_sensor_line(line)


                    if key and parsed_data:
                        sensor_data_dict[key] = parsed_data
                        print(f"Aggiunto al dizionario: Chiave={key}, Dati={parsed_data}")
                        return key, parsed_data
                    print("-" * 50)


    except serial.SerialException as e:
        print(f"Errore nella connessione seriale: {e}")

   
def aggiorna(key, parsed_data):
    time_stamp, node_id=key;
    val=[]
    for flag,value in parsed_data:
        val.append(value)
    #if (node_id==1):
        

    temp_raw = leggi_valore()
    lb.config(text=temp_raw)
    window.after(5000, aggiorna)
    

if __name__ == "__main__":
    creationInterface()
    leggi_e_processa_dati_seriale()
