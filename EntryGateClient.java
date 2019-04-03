import Carpark.*;
import config.orb_config;

import java.util.*;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;

public class EntryGateClient extends JFrame
{
    //ORB Components / local server
    private LocalServer localServer;
    private String location = "";
    private String id = "";
    private EntryGateImpl entryGateServant;

    //JSwing Components
    private JTextField registrationNumIn;

    public EntryGateClient(String location, String id) {
        this.location = location;
        this.id = id;
        initOrb();
        initGUIComponents();
        this.setSize(500,500);
        pack();
    }

    private void initOrb() {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(orb_config.returnArgs(), null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create the pay station servant object
            String entryGateName = "entryGate" + location + id;
            entryGateServant = new EntryGateImpl(entryGateName);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(entryGateServant);
            EntryGate cref = EntryGateHelper.narrow(ref);

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

            // bind the entry gate object in the Naming service
            NameComponent[] entryGate = nameService.to_name(entryGateName);
            nameService.rebind(entryGate, cref);

            localServer.add_entry_gate(entryGateName, "null");

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
        String id = "";
        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-location")) {
                location = args[i+1];
            } else if (args[i].equals("-id")) {
                id = args[i+1];
            }
        }

        EntryGateClient entryGate = new EntryGateClient(location, id);
        entryGate.setVisible(true);
    }
}
