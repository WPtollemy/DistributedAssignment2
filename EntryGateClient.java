import Carpark.*;
import config.orb_config;

import java.util.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import org.omg.CORBA.*;
import org.omg.CosNaming.*;

public class EntryGateClient extends JFrame
{
    //ORB Components / local server
    LocalServer localServer;
    String location = "";

    //JSwing Components
    private JTextField registrationNumIn;

    public EntryGateClient(String location) {
        this.location = location;
        initOrb();
        initGUIComponents();
        this.setSize(450,150);
        pack();
    }

    private void initOrb() {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(orb_config.returnArgs(), null);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                return;
            }

            // Use NamingContextExt instead of NamingContext. This is
            // part of the Interoperable naming Service.
            NamingContextExt nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                return;
            }

            // resolve the local server object reference in the Naming service
            String name = "localServer" + location;
            localServer = LocalServerHelper.narrow(nameService.resolve_str(name));

        } catch(Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }

    private void initGUIComponents() {
        setTitle("Entry Gate UI");

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North Panel to select topics
        JPanel jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout ());

        //Center Panel to view comments
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new GridLayout (2, 2, 5, 5));

        JLabel regLabel = new JLabel();
        regLabel.setText ("Registration Number: ");
        jPanel2.add (regLabel);

        registrationNumIn = new JTextField (12);
        registrationNumIn.setText ("");
        jPanel2.add (registrationNumIn);

        //South Panel to add comments
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        JButton vehicleEntryButton = new JButton();
        vehicleEntryButton.setText("Enter vehicle");
        vehicleEntryButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                registerVehicleIn(evt);
            }
        }  );

        jPanel3.add(vehicleEntryButton);

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    private void registerVehicleIn(java.awt.event.ActionEvent evt) {

        Calendar cal = Calendar.getInstance();
        String  hour = Integer.toString(cal.get(Calendar.HOUR_OF_DAY));
        String  time = hour + cal.get(Calendar.MINUTE);
        int  intTime = Integer.parseInt(time);

        try {
            EventType     type = EventType.from_int(0);
            DateTime      date = new DateTime(intTime, cal.get(Calendar.DATE));
            String      regNum = registrationNumIn.getText();
            VehicleEvent event = new VehicleEvent(type, date, regNum);

            localServer.vehicle_in(event);

        } catch(Exception e) {
            System.err.println("Exception");
            System.err.println(e);
        }
    }

    public static void main( String[] args ) {
        // Find location from args if exists
        String location = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-location")) {
                location = args[i+1];
            }
        }

        EntryGateClient entryGate = new EntryGateClient(location);
        entryGate.setVisible(true);
    }
}
