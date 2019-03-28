import Carpark.*;
import Company.*;

import java.io.*;
import java.util.*;
import java.text.SimpleDateFormat;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;

public class LocalServerImpl extends LocalServerPOA
{
    //ORB Components etc
    private ORB orb;
    private String location;
    private CompanyHQ companyHQServer;
    private NamingContextExt nameService;

    private ArrayList<String> regNums = new ArrayList<String>();
    private ArrayList<Ticket> tickets = new ArrayList<Ticket>();
    private ArrayList<String> payStationNames = new ArrayList<String>();

    public LocalServerImpl(ORB orb_val, String location) {
        orb = orb_val;
        this.location = location;

        try {
            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                return;
            }

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                return;
            }

            // resolve the local server object reference in the Naming service
            String companyName = "companyHQ";
            companyHQServer = CompanyHQHelper.narrow(nameService.resolve_str(companyName));
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public String location() {
        return location;
    }

    public VehicleEvent[] log() {
        return new VehicleEvent[1];
    }

    public void vehicle_in(VehicleEvent e) {
        System.out.println("vehicle marked as in");
        regNums.add(e.registration_number);
    }

    public void vehicle_out(VehicleEvent e) {
        System.out.println("vehicle marked as out");
        regNums.remove(e.registration_number);

        boolean ticketFound = false;
        for(Ticket ticket : tickets){
            if(ticket.registration_number != null &&
                    ticket.registration_number.contains(e.registration_number)) {
                // Check date time for ticket is in time 
                ticketFound = true;
                checkTicketInTime(ticket, e);
            }
        }

        if (!ticketFound) {
            companyHQServer.raise_alarm(e);
        }
    }
    
    //One of the most ineffective method I've ever created
    //Please re do if get time
    private void checkTicketInTime(Ticket ticket, VehicleEvent e) {
        //Current time
        Calendar currentDate = Calendar.getInstance();

        //Expected leave time 
        //Set date to when they got ticket
        Date date = new Date();
        try {
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HHmm");
            String time = Integer.toString(ticket.startDate.time);
            date = simpleDateFormat.parse(time);
        } catch (Exception exc) {
            System.err.println(exc);
        }

        Calendar cal1 = Calendar.getInstance();
        cal1.set(Calendar.DATE, ticket.startDate.date);
        cal1.set(Calendar.HOUR_OF_DAY, date.getHours());
        cal1.set(Calendar.MINUTE, date.getMinutes());
        long t = cal1.getTimeInMillis();

        // Add stay length + 5 minutes
        int endStayTime = ticket.stay_length * 360000;
        endStayTime += (5 * 6000);
        Date expectedLeaveDate=new Date(t + (10 * endStayTime));

        // Check the user leaves in time for ticket
        if (currentDate.getTime().after(expectedLeaveDate)) {
            companyHQServer.raise_alarm(e);
        }

    }

    public void ticket_created(Ticket ticket) {
        tickets.add(ticket);
    }

    public boolean vehicle_in_car_park(String registration_number) {
        if (regNums.contains(registration_number))
            return true;

        return false;
    }

    public int return_cash_total() {
        int cash_total = 0;

        //Loop through stored paystations and get cash total
        try {
            // resolve the pay station object references in the Naming service
            String name = "payStation" + location;
            PayStation payStation = PayStationHelper.narrow(nameService.resolve_str(name));
            cash_total += payStation.return_cash_total();
        } catch (Exception e) {
            //Do nothing atm
        }

        return cash_total;
    }

    public void add_entry_gate(String gate_name, String gate_ior) {
    }

    public void add_exit_gate(String gate_name, String gate_ior) {
    }

    public void add_pay_station(String station_name, String startion_ior) {
    }
}
