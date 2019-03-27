import Company.*;
import Carpark.VehicleEvent;

import org.omg.CORBA.*;
import java.io.*;

public class CompanyHQImpl extends CompanyHQPOA
{
    private CompanyHQServer parent;

    public CompanyHQImpl(CompanyHQServer parentGUI) {
        parent = parentGUI;
    }

    public void raise_alarm(VehicleEvent event) {
        parent.addMessage(event.registration_number);
    }

    public void register_local_server(String server_name, String server_ior) {
    }
}
