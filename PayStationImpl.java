import Carpark.*;

import java.io.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class PayStationImpl extends PayStationPOA
{
    private ORB orb;
    private LocalServer localServer;

    private String machine_name;
    private int cash_total = 0;

    public PayStationImpl(ORB orb_value, String location) {
        orb = orb_value;
        machine_name = location;

        try {
            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                return;
            }

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                return;
            }

            // resolve the local server object reference in the Naming service
            String name = "localServer" + location;
            localServer = LocalServerHelper.narrow(nameService.resolve_str(name));
        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public void reset() {
    }

    public void turn_on() {
    }

    public void turn_off() {
    }

    public int return_cash_total() {
        return cash_total;
    }

    public String machine_name() {
        return machine_name;
    }
    
    public void storeTicket(Ticket t) {
        cash_total += t.stay_length;
        localServer.ticket_created(t);
    }
}
