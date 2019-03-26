package Carpark;


/**
* Carpark/VehicleEvent.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Tuesday, 26 March 2019 13:26:25 o'clock GMT
*/

public final class VehicleEvent implements org.omg.CORBA.portable.IDLEntity
{
  public Carpark.EventType eventType = null;
  public Carpark.DateTime date = null;
  public String registration_number = null;

  public VehicleEvent ()
  {
  } // ctor

  public VehicleEvent (Carpark.EventType _eventType, Carpark.DateTime _date, String _registration_number)
  {
    eventType = _eventType;
    date = _date;
    registration_number = _registration_number;
  } // ctor

} // class VehicleEvent
