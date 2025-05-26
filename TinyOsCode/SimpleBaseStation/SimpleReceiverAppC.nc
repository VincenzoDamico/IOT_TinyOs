
configuration SimpleReceiverAppC {
}
implementation{ 
	components SimpleReceiverC, MainC, LedsC;
	components  PrintfC, SerialStartC; 



	components SerialActiveMessageC;
	components ActiveMessageC;
	components new AMReceiverC(AM_SENSOR_MESSAGE);
	components new AMSenderC(AM_SENSOR_MESSAGE) as SerialSender;

      	SimpleReceiverC.SerialControl -> SerialActiveMessageC;

	

        SimpleReceiverC.RadioControl -> ActiveMessageC;
	SimpleReceiverC.Boot -> MainC.Boot;
        SimpleReceiverC.Leds -> LedsC;
	SimpleReceiverC.AMSend -> SerialSender;
        SimpleReceiverC.Receive-> AMReceiverC;

	SimpleReceiverC.RadioPacket -> AMReceiverC;
	SimpleReceiverC.RadioAMPacket -> AMReceiverC;

	SimpleReceiverC.SerialPacket -> SerialSender;
	SimpleReceiverC.SerialAMPacket -> SerialSender;

}
