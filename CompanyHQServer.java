import Company.*;
import Carpark.*;
import config.orb_config;

import java.io.*;
import java.awt.*;
import javax.swing.*;

import org.omg.CORBA.*;
import org.omg.PortableServer.*;
import org.omg.PortableServer.POA;
import org.omg.CosNaming.*;

public class CompanyHQServer extends JFrame
{
    //ORB Components etc
    private NamingContextExt nameService;

    private JPanel jPanel1; 
    private JTextArea textarea;
    private JScrollPane scrollpane;
    private JComboBox<String> serverList;

    public CompanyHQServer() {
        initOrb();
        initGUIComponents();
        this.setSize(500,500);
        pack();
    }
    
    public void initOrb() {
        try {
            // Initialize the ORB
            ORB orb = ORB.init(orb_config.returnArgs(), null);

            // get reference to rootpoa & activate the POAManager
            POA rootpoa = POAHelper.narrow(orb.resolve_initial_references("RootPOA"));
            rootpoa.the_POAManager().activate();

            // Create the company HQ servant object
            CompanyHQImpl companyHQServant = new CompanyHQImpl(this);

            // get object reference from the servant
            org.omg.CORBA.Object ref = rootpoa.servant_to_reference(companyHQServant);
            CompanyHQ cref = CompanyHQHelper.narrow(ref);

            // Get a reference to the Naming service
            org.omg.CORBA.Object nameServiceObj = orb.resolve_initial_references ("NameService");
            if (nameServiceObj == null) {
                return;
            }

            // Use NamingContextExt which is part of the Interoperable
            // Naming Service (INS) specification.
            nameService = NamingContextExtHelper.narrow(nameServiceObj);
            if (nameService == null) {
                return;
            }

            // bind the company HQ object in the Naming service
            String name = "companyHQ";
            NameComponent[] companyHQ = nameService.to_name(name);
            nameService.rebind(companyHQ, cref);

            //  wait for invocations from clients
            //orb.run();

        } catch(Exception e) {
            System.err.println(e);
        }
    }

    public void initGUIComponents() {
        setTitle("Company HQ UI");

        Container cp = getContentPane();
        cp.setLayout (new BorderLayout ());

        //North Panel to select topics
        jPanel1 = new JPanel();
        jPanel1.setLayout (new FlowLayout ());

        serverList = new JComboBox<String>();
        jPanel1.add(serverList);

        JButton totalsButton = new JButton();
        totalsButton.setText("Cash total");
        totalsButton.addActionListener (new java.awt.event.ActionListener () {
            public void actionPerformed (java.awt.event.ActionEvent evt) {
                getCashTotal(evt);
            }
        }  );

        jPanel1.add(totalsButton);

        //Center Panel to view comments
        JPanel jPanel2 = new JPanel();
        jPanel2.setLayout (new FlowLayout());

        textarea = new JTextArea(20,25);
        scrollpane = new JScrollPane(textarea);
        jPanel2.add(scrollpane);

        //South Panel to add comments
        JPanel jPanel3 = new JPanel();
        jPanel3.setLayout (new FlowLayout ());

        cp.add (jPanel1, "North");
        cp.add (jPanel2, "Center");
        cp.add (jPanel3, "South");
    }

    protected void addMessage(String message) {
        textarea.append(message + "\n");
    }

    protected void registerLocalServer(String server_name) {
        serverList.addItem(server_name);
        jPanel1.revalidate();
    }

    private void getCashTotal(java.awt.event.ActionEvent evt) {
        try {
        String     selectedItem = "localServer" + (String)serverList.getSelectedItem();
        LocalServer localServer = LocalServerHelper.narrow(nameService.resolve_str(selectedItem));
        int amountTotal = localServer.return_cash_total();


        //default title and icon
        JOptionPane.showMessageDialog(this,
            "Total cash earned for " + selectedItem + ": " + amountTotal);
        } catch (Exception e) {
            System.err.println(e);
        }
    }

    static public void main(String[] args) {
        CompanyHQServer server = new CompanyHQServer();
        server.setVisible(true);
    }
}
