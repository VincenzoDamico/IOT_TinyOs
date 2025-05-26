import tkinter as tk

class Root(tk.Tk):
    def __init__(self):
        super().__init__()

        self.geometry("1350x650+15+20")
        
        self.configure(background="midnightblue")
        self.title("IoT Project")
        #self.overrideredirect(True)  # Rimuove barra di sistema e tutta la finestra rendendola impossibile far il resize
        self.resizable(True, True) #non funzina per via dell'istruzione prima overrideredirect

        # --- AREA CONTENUTO SCORREVOLE ---
        container = tk.Frame(self)
        container.pack(fill=tk.BOTH, expand=True)

        # Canvas + Scrollbar
        canvas = tk.Canvas(container, bg="midnightblue", highlightthickness=0)   # elimina i bordi highlightthickness=0
        scrollbar = tk.Scrollbar(container, orient="vertical", command=canvas.yview)

        self.scrollable_frame = tk.Frame(canvas, bg="midnightblue")

        self.scrollable_frame.bind(
            "<Configure>",
            lambda e: canvas.configure(scrollregion=canvas.bbox("all"))
        )

        canvas.create_window((0, 0), window=self.scrollable_frame, anchor="nw")
        canvas.configure(yscrollcommand=scrollbar.set)

        canvas.pack(side="left", fill="both", expand=True)
        scrollbar.pack(side="right", fill="y")

        Initial_message=tk.Frame(self.scrollable_frame, bg="midnightblue")

        tk.Label(  Initial_message, text="Waiting for the sensor devices to connect....", bg="midnightblue",
              font=("System", 30, "bold"), fg="ghostwhite").grid(
            row=0, column=0, padx=50, pady=(70, 35))
        Initial_message.pack(fill='both',expand=True)


        self.spacer = tk.Frame(self.scrollable_frame, height=100, bg="midnightblue")
        self.spacer.pack(side="bottom")


    def get_main_content(self):
        return self.scrollable_frame
