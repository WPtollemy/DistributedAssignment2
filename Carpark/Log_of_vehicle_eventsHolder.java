package Carpark;


/**
* Carpark/Log_of_vehicle_eventsHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 24 March 2019 17:28:02 o'clock GMT
*/

public final class Log_of_vehicle_eventsHolder implements org.omg.CORBA.portable.Streamable
{
  public Carpark.VehicleEvent value[] = null;

  public Log_of_vehicle_eventsHolder ()
  {
  }

  public Log_of_vehicle_eventsHolder (Carpark.VehicleEvent[] initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Carpark.Log_of_vehicle_eventsHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Carpark.Log_of_vehicle_eventsHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Carpark.Log_of_vehicle_eventsHelper.type ();
  }

}
