import Carpark.*;

import org.omg.CORBA.*;
import java.io.*;

public class PayStationImpl extends PayStationPOA
{
    private String machine_name;

    public void reset() {
    }

    public void turn_on() {
    }

    public void turn_off() {
    }

    public int return_cash_total() {
        return 0;
    }

    public String machine_name() {
        return "machine_name";
    }
}
