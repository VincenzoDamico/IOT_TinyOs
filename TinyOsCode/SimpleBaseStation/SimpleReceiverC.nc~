
#include <printf.h>
#include <string.h>
#include "SensorMsg.h"
module SimpleReceiverC{

  uses {
    interface Boot;
    interface Leds;
    interface SplitControl as RadioControl;

    interface Packet as RadioPacket;
    interface AMPacket as RadioAMPacket;

    interface Packet as SerialPacket;
    interface AMPacket as SerialAMPacket;

    interface Receive;
    interface AMSend;
    interface SplitControl as SerialControl;

  }

}

implementation{
  bool radioON = FALSE;
  bool serialON = FALSE;
  bool busy = FALSE; 

  SensorMsgStr* mymsg;
  message_t serialMsg;


  event void Boot.booted() {
    call RadioControl.start();
    call SerialControl.start();
   
  }
  event void SerialControl.startDone(error_t err){
	if (err == SUCCESS) {
		serialON = TRUE;
	}
	else {
		call SerialControl.start();
	}
  }

  event void SerialControl.stopDone(error_t err){ }

  event void RadioControl.startDone(error_t err){
	if (err == SUCCESS) {
		radioON = TRUE;
        	call Leds.led1On();
	}
	else {
		call RadioControl.start();
	}
  }

  event void RadioControl.stopDone(error_t err){
	if (err == SUCCESS) {
		radioON = FALSE;
        	call Leds.led1Off();
	}
	else {
		call RadioControl.stop();
	}
  }
  event message_t* Receive.receive(message_t* msg, void* payload, uint8_t len) {
        SensorMsgStr* toSend;
	error_t sendErr;
        call Leds.led0On();
 	if (radioON && len == sizeof(SensorMsgStr)) {
        	
 		mymsg = (SensorMsgStr*)payload;

		call Leds.led2Off();
        	if (serialON && !busy) {
            		printf("------------------\n"); 		
			printf("\n id: %d \t Temp: %d \t Hum: %d \t Lum: %d \t TimeStamp: %lu \t\n",mymsg->nodeid,mymsg->temperature,mymsg->humidity,mymsg->luminosity,(unsigned long)mymsg->timestamp);
	    		printfflush();
			
            		toSend = (SensorMsgStr*)call SerialPacket.getPayload(&serialMsg, sizeof(SensorMsgStr));
	   		if (toSend!=NULL){
            			memcpy(toSend, mymsg, sizeof(SensorMsgStr));
            			sendErr=call AMSend.send(TOS_BCAST_ADDR, &serialMsg, sizeof(SensorMsgStr));
               			if (sendErr == SUCCESS){
					busy=TRUE;
					call Leds.led2On();
				}else{ 
					printf("Error\n");
				}
	    		}
        	}
		
	}
        call Leds.led0Off();
	return msg;
  }
  event void AMSend.sendDone(message_t* m, error_t error){
	if (error == SUCCESS) {
		busy = FALSE;
		call Leds.led2Off();
	}
  }

}
