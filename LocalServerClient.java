import Carpark.*;
import Company.*;
import config.orb_config;

import java.io.*;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;

public class LocalServerClient
{
    static public void main(String[] args) {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(orb_config.returnArgs(), null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Find location from args if exists
            String location = "";
            for (int i = 0; i < args.length; i++) {
                if (args[i].equals("-location")) {
                    location = args[i+1];
                }
            }

            // Create the local server servant object
            LocalServerImpl localServant = new LocalServerImpl(orb, location);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(localServant);
            LocalServer cref = LocalServerHelper.narrow(ref);

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

            // bind the local server object in the Naming service
            String name = "localServer" + location;
            NameComponent[] localServer = nameService.to_name(name);
            nameService.rebind(localServer, cref);

            //  wait for invocations from clients
            orb.run();
        } catch(Exception e) {
            System.err.println(e);
        }
    }
}
