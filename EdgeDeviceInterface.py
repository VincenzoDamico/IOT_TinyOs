
from Controller.MainController import SensorController
from Models.MainModels import SensorModel
from View.MainView import SensorView
from View.root import Root

if __name__ == "__main__":
    app = Root()
    model = SensorModel()
    view = SensorView(app)
    controller = SensorController(model, view, app)
    app.mainloop()
    controller.running = False
