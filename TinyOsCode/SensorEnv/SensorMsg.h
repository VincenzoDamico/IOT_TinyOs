#ifndef SENSORMSG_H
#define SENSORMSG_H

enum {
  AM_SENSOR_MESSAGE = 6,//identifico il tipo di messaggio che stai mandando
};


typedef nx_struct SensorMsgStr {
  nx_uint16_t nodeid; 
  nx_uint16_t temperature;
  nx_uint16_t humidity;
  nx_uint16_t luminosity;
  nx_uint32_t timestamp;
} SensorMsgStr;


#endif

