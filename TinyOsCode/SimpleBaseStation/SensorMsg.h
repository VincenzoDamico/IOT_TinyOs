#ifndef SENSORMSG_H
#define SENSORMSG_H

enum {
  AM_SENSOR_MESSAGE = 6,//identifico il tipo di messaggio che stai mandando
};

//nx è la rappresensetazione big-endian, little-endian  
// indica modalità differenti usate dai calcolatori per immagazzinare all'interno della memoria dati di dimensione superiore al byte (es. word, dword, qword).
// Generally, an external type is the same as a normal type except that it has nx or nxle_ preceding it
 //nx_uint16_t val;  A big-endian 16-bit value msb->lsb
// nxle_uint32_t otherVal;  A little-endian 32-bit value  lsb->msb
typedef nx_struct SensorMsgStr {
  nx_uint16_t nodeid; 
  nx_uint16_t temperature;
  nx_uint16_t humidity;
  nx_uint16_t luminosity;
  nx_uint32_t timestamp;
} SensorMsgStr;


#endif

