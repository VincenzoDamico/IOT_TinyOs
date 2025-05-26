#define SAMPLING_TIME 1024

module TempSensC {
	uses interface Boot;
	uses interface Timer<TMilli> as SensorTimer;
	uses interface Leds;	
	uses interface Read<uint16_t> as Temperature;
}

implementation {

	event void Boot.booted() {
		call SensorTimer.startPeriodic(SAMPLING_TIME); // 1024 ticks = 1 sec
	}

	uint8_t counter = 0;

	event void SensorTimer.fired() {
		call Temperature.read();			
	}

	event void Temperature.readDone(error_t code, uint16_t data) {
		uint16_t dataCelsius = 0;
		call Leds.led1On();		
		if(code == SUCCESS)  {
			dataCelsius = -39+0.01*data;			
			if (dataCelsius >= 25) {
				call Leds.led0On();			
				call Leds.led2Off();
			}
			else {
				call Leds.led0Off();			
				call Leds.led2On();
			}
		}
	}

	

}
