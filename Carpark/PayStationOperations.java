package Carpark;


/**
* Carpark/PayStationOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Tuesday, 26 March 2019 13:26:25 o'clock GMT
*/

public interface PayStationOperations 
{
  String machine_name ();
  void turn_on ();
  void turn_off ();
  void reset ();
  int return_cash_total ();
} // interface PayStationOperations
