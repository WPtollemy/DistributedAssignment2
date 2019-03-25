import Carpark.*;

import org.omg.CORBA.*;
import java.io.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class ClientTemplate
{
    public static void main( String[] args ) {
        try {
            // Initialize the ORB
            System.out.println("Initializing the ORB");
            ORB orb = ORB.init(args, null);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                System.out.println("nameServiceObj = null");
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                System.out.println("nameService = null");
                return;
            }

            // resolve the Count object reference in the Naming service
            // basically get the server
            String serverName = "serverName";
            LocalServer counter = LocalServerHelper.narrow(nameService.resolve_str(serverName));

            // start calling functions
            // blah blah blah

        } catch(Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }
}
