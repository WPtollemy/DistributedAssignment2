package Carpark;


/**
* Carpark/Ticket.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from CarPark.idl
* Wednesday, 27 March 2019 15:18:06 o'clock GMT
*/

public final class Ticket implements org.omg.CORBA.portable.IDLEntity
{
  public Carpark.DateTime startDate = null;
  public int stay_length = (int)0;
  public String registration_number = null;

  public Ticket ()
  {
  } // ctor

  public Ticket (Carpark.DateTime _startDate, int _stay_length, String _registration_number)
  {
    startDate = _startDate;
    stay_length = _stay_length;
    registration_number = _registration_number;
  } // ctor

} // class Ticket
