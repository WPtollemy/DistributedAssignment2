package Carpark;


/**
* Carpark/EventTypeHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Sunday, 24 March 2019 17:28:02 o'clock GMT
*/

abstract public class EventTypeHelper
{
  private static String  _id = "IDL:Carpark/EventType:1.0";

  public static void insert (org.omg.CORBA.Any a, Carpark.EventType that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static Carpark.EventType extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_enum_tc (Carpark.EventTypeHelper.id (), "EventType", new String[] { "Entry", "Exit"} );
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static Carpark.EventType read (org.omg.CORBA.portable.InputStream istream)
  {
    return Carpark.EventType.from_int (istream.read_long ());
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, Carpark.EventType value)
  {
    ostream.write_long (value.value ());
  }

}
