import Carpark.*;

import org.omg.CORBA.*;
import java.io.*;

public class EntryGateImpl extends EntryGatePOA
{
    private String machine_name;

    public void reset() {
    }

    public void turn_on() {
    }

    public void turn_off() {
    }

    public String machine_name() {
        return "machine_name";
    }
}
