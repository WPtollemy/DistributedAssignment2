package Carpark;

/**
* Carpark/VehicleEventHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Wednesday, 27 March 2019 15:18:06 o'clock GMT
*/

public final class VehicleEventHolder implements org.omg.CORBA.portable.Streamable
{
  public Carpark.VehicleEvent value = null;

  public VehicleEventHolder ()
  {
  }

  public VehicleEventHolder (Carpark.VehicleEvent initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Carpark.VehicleEventHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Carpark.VehicleEventHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Carpark.VehicleEventHelper.type ();
  }

}
