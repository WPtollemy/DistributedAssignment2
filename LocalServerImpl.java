import Carpark.*;

import org.omg.CORBA.*;
import java.io.*;

public class LocalServerImpl extends LocalServerPOA
{
    public String location() {
        return "car park location";
    }

    public VehicleEvent[] log() {
        return new VehicleEvent[1];
    }

    public void vehicle_in(VehicleEvent e) {
        System.out.println("vehicle marked as in");
    }

    public void vehicle_out(VehicleEvent e) {
        System.out.println("vehicle marked as out");
    }

    public boolean vehicle_in_car_park(String registration_number) {
        System.out.println("vehicle is in car park");
        return true;
    }

    public int return_cash_total() {
        return 10;
    }

    public void add_entry_gate(String gate_name, String gate_ior) {
    }

    public void add_exit_gate(String gate_name, String gate_ior) {
    }

    public void add_pay_station(String station_name, String startion_ior) {
    }
}
