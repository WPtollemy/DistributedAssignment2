package Carpark;

/**
* Carpark/DateTimeHolder.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 24 March 2019 17:28:02 o'clock GMT
*/

public final class DateTimeHolder implements org.omg.CORBA.portable.Streamable
{
  public Carpark.DateTime value = null;

  public DateTimeHolder ()
  {
  }

  public DateTimeHolder (Carpark.DateTime initialValue)
  {
    value = initialValue;
  }

  public void _read (org.omg.CORBA.portable.InputStream i)
  {
    value = Carpark.DateTimeHelper.read (i);
  }

  public void _write (org.omg.CORBA.portable.OutputStream o)
  {
    Carpark.DateTimeHelper.write (o, value);
  }

  public org.omg.CORBA.TypeCode _type ()
  {
    return Carpark.DateTimeHelper.type ();
  }

}
