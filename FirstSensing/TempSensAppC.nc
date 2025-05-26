configuration TempSensAppC {
}

implementation {

	components TempSensC, MainC, LedsC;
	components new TimerMilliC() as Timer;
	components new SensirionSht11C() as TemperatureDriver;

	TempSensC.Boot -> MainC;
	TempSensC.Leds -> LedsC;
	TempSensC.SensorTimer -> Timer;
	TempSensC.Temperature -> TemperatureDriver.Temperature;

}
