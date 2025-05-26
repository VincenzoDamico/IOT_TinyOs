
configuration SensingAppC{
}

implementation{ 
	components SensingC, MainC, LedsC;
	components  PrintfC, SerialStartC; 
	components new TimerMilliC() as Timer;
	components new SensirionSht11C() as Sensor;
	components new HamamatsuS1087ParC() as Lumi;
	components UserButtonC;
        components LocalTimeMilliC;

	components ActiveMessageC;
	components new AMSenderC(AM_SENSOR_MESSAGE);
	

	
	SensingC.Boot -> MainC.Boot;
	SensingC.ReadTemp -> Sensor.Temperature;
	SensingC.ReadHumidity -> Sensor.Humidity;
	SensingC.ReadLumi -> Lumi;
	SensingC.Timer -> Timer;
        SensingC.Leds -> LedsC;
	SensingC.Get-> UserButtonC;
	SensingC.Notify -> UserButtonC;

	SensingC.RadioControl -> ActiveMessageC;
	SensingC.Packet -> AMSenderC;
	SensingC.AMPacket -> AMSenderC;
	SensingC.AMSend -> AMSenderC;
        SensingC.LocalTime -> LocalTimeMilliC;


	

}
