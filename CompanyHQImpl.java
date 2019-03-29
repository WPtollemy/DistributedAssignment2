import Company.*;
import Carpark.VehicleEvent;

import org.omg.CORBA.*;
import java.io.*;
import java.util.*;

public class CompanyHQImpl extends CompanyHQPOA
{
    private CompanyHQServer parent;

    private ArrayList<String> localServers = new ArrayList<String>();

    public CompanyHQImpl(CompanyHQServer parentGUI) {
        parent = parentGUI;
    }

    public void raise_alarm(VehicleEvent event) {
        parent.addMessage("Car left owing money: " + event.registration_number);
    }

    public void register_local_server(String server_name, String server_ior) {
        localServers.add(server_name);
        parent.registerLocalServer(server_name);
    }
}
