from tkinter import Frame, Label, StringVar
from PIL import Image, ImageTk

class SensorFrame(Frame):
    def __init__(self, master=None):
        super().__init__(master)
        self.pack(fill='both', expand=True)
        self.configure(bg="midnightblue")

        self.sensorID = StringVar()
        self.temp = StringVar()
        self.hum = StringVar()
        self.lum = StringVar()
        self.time = StringVar()

    # Sensor ID
        Label(self, text="SensorID:", bg="midnightblue",
              font=("System", 17, "bold"), fg="ghostwhite").grid(
            row=0, column=0, padx=50, pady=(70, 35))

    
        Label(self, textvariable=self.sensorID, bg="midnightblue",
              font=("System", 25, "bold"), fg="ghostwhite").grid(
            row=0, column=0, padx=(150,0), pady=(30, 0))

        # Temperature
        Label(self, text="Temperature", bg="midnightblue",
              font=("System", 17, "bold"), fg="ghostwhite").grid(
            row=1, column=1, sticky="w", padx=20, pady=2)

        temp_img = Image.open(r"Utility\Images\temp.jpg").resize((225, 125))
        self.im_Temper = ImageTk.PhotoImage(temp_img)

        Label(self, image=self.im_Temper, bd=0, textvariable=self.temp,
              compound="center", font=("System", 22, "bold"),
              fg="lavender", bg="midnightblue").grid(row=2, column=1, sticky="w", padx=20, pady=1.5)

        # Humidity
        Label(self, text="Humidity", bg="midnightblue",
              font=("System", 17, "bold"), fg="ghostwhite").grid(
            row=1, column=3, sticky="w", padx=20, pady=2)

        hum_img = Image.open(r"Utility\Images\hum.jpg").resize((225, 125))
        self.im_Hum = ImageTk.PhotoImage(hum_img)

        Label(self, image=self.im_Hum, bd=0, textvariable=self.hum,
              compound="center", font=("System", 22, "bold"),
              fg="lavender", bg="midnightblue").grid(row=2, column=3, sticky="w", padx=20, pady=1.5)

        # Luminosity
        Label(self, text="Luminosity", bg="midnightblue",
              font=("System", 17, "bold"), fg="ghostwhite").grid(
            row=1, column=5, sticky="w", padx=20, pady=2)

        lum_img = Image.open(r"Utility\Images\lum.jpg").resize((225, 125))
        self.im_Lum = ImageTk.PhotoImage(lum_img)

        Label(self, image=self.im_Lum, bd=0, textvariable=self.lum,
              compound="center", font=("System", 22, "bold"),
              fg="lavender", bg="midnightblue").grid(row=2, column=5, sticky="w", padx=20, pady=1.5)
        

        # Timestamp
        Label(self, text="Timestamp", bg="midnightblue",
              font=("System", 17, "bold"), fg="ghostwhite").grid(
            row=1, column=7, sticky="w", padx=20, pady=2)

        time_img = Image.open(r"Utility\Images\timS.jpg").resize((225, 125))
        self.im_TimeS = ImageTk.PhotoImage(time_img)

        Label(self, image=self.im_TimeS, bd=0, textvariable=self.time,
              compound="center", font=("System", 22, "bold"),
              fg="lavender", bg="midnightblue").grid(row=2, column=7, sticky="w", padx=20, pady=1.5)