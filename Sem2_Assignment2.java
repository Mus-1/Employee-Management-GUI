/** ***********************************************************************************************
 *  Course_Name – Assignment 2                                                                                                                                *
 *
 *  I declare that this assignment is my own work in accordance with Humber Academic Policy.        *
 *
 *  No part of this assignment has been copied manually or electronically from any other source       *
 *
 *  (including web sites) or distributed to other students/social media.                                                       *
 *
 *  Name: Mustafa Udegadhwala    Student ID: N01414702   Date: 16-2-2022  		          *
 *
 * ************************************************************************************************ */
package sem2_assignment2;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

/**
 *
 * @author musta
 */
public class Sem2_Assignment2 extends JFrame {

    public Sem2_Assignment2() {
        setTitle("Customer Management Appliction ");
        setSize(1000, 800);
        createTabbedPane();
    }

    private void createTabbedPane() {
        JTabbedPane tabbedPane = new JTabbedPane();
        //creating new JPannel
        JPanel panel1 = new JPanel();
        //Setting Background color of Jpanel
        panel1.setBackground(Color.LIGHT_GRAY);
        //Creating the required labels for first tab
        JLabel customerId = new JLabel("Customer Id:");
        JLabel customerName = new JLabel("Customer Name:");
        JLabel customerPhone = new JLabel("Customer Phone");
        JLabel customerEmail = new JLabel("Customer Email:");
        JLabel customerPostalCode = new JLabel("Customer Postal code:");
        JLabel errorlabel = new JLabel("");

        //Setting Font color and size of Labels of Panel 1
        customerId.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerName.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerPhone.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerEmail.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerPostalCode.setFont(new Font("Oswald", Font.PLAIN, 24));

        //Creating JTextFields for First Pannel
        JTextField customerIdField = new JTextField(10);
        JTextField customerNameField = new JTextField(10);
        JTextField customerPhoneField = new JTextField(10);
        JTextField customerEmailField = new JTextField(10);
        JTextField customerPostalCodeField = new JTextField(10);

        //Creating JButtons for First Pannel
        JButton submitButton = new JButton("Write To File");
        JButton clearButton = new JButton("Clear fields");

        ActionListener clear = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                customerIdField.setText("");
                customerNameField.setText("");
                customerPhoneField.setText("");
                customerEmailField.setText("");
                customerPostalCodeField.setText("");
                errorlabel.setText("");
            }
        };
        clearButton.addActionListener(clear);

        tabbedPane.addTab("Insert Data", null, panel1, "This is Tab1");
        tabbedPane.setMnemonicAt(0, KeyEvent.VK_1); // tab index,(alt+1)

        //creating panel on tab 2
        JPanel panel2 = new JPanel();
        tabbedPane.addTab("Modify Data", null, panel2, "This is Tab2");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_2); // tab index,(alt+2)

        //creating panel on tab 3
        JPanel panel3 = new JPanel();
        tabbedPane.addTab("Display list of customers", null, panel3, "This is Tab");
        tabbedPane.setMnemonicAt(1, KeyEvent.VK_3); // tab index,(alt+2)

        add(tabbedPane, BorderLayout.CENTER);

        //Action Listener for Inserting Data
        ActionListener InsertData = new ActionListener() {
            public void actionPerformed(ActionEvent e) {

                errorlabel.setText("");
                //storing data from elements to variables
                String strId = customerIdField.getText();
                String strName = customerNameField.getText();
                String strPhone = customerPhoneField.getText();
                String strPostalCode = customerPostalCodeField.getText();
                String strEmail = customerEmailField.getText();
                int flag = 0;
                //declaring file path for writing
                File file = new File("C:\\Users\\musta\\Desktop\\Assignment2_data\\Data.dat");
                //Validating if the field are not empty
                if (strId.equals("") || strName.equals("") || strPhone.equals("")
                        || strPostalCode.equals("") || strEmail.equals("")) {
                    errorlabel.setText("Please fill details in all the fields");
                } else {
                    try (DataInputStream input = new DataInputStream(new FileInputStream(file));) {
                        int intStrId = Integer.parseInt(strId);
                        //Validating There are no Duplicate CustomerId
                        while (true) {
                            if (intStrId == input.readInt()) {
                                flag = 1;
                                break;
                            }
                            input.readUTF();
                            input.readUTF();
                            input.readUTF();
                            input.readUTF();
                        }
                    } catch (IOException ex) {
                        System.out.println(ex);
                    }
                    if (flag == 0) {
                        try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file, true));) {
                            int intStrId = Integer.parseInt(strId);
                            //Writing Data to File if customerId is not Duplicate
                            output.writeInt(intStrId);
                            output.writeUTF(strName);
                            output.writeUTF(strPhone);
                            output.writeUTF(strEmail);
                            output.writeUTF(strPostalCode + "\n");
                        } //Catching Exception
                        catch (Exception ep) {
                            System.out.println(ep);
                        }
                    } else {
                        errorlabel.setText("Customer Id Already Exists");
                    }

                }

            }

        };
        //adding action listener to clear fields button
        submitButton.addActionListener(InsertData);
        //setting layout of the elements
        panel1.setLayout(new GridLayout(7, 2));

        //addidng elements to the JPanel.
        panel1.add(customerId);
        panel1.add(customerIdField);
        panel1.add(customerName);
        panel1.add(customerNameField);
        panel1.add(customerPhone);
        panel1.add(customerPhoneField);
        panel1.add(customerEmail);
        panel1.add(customerEmailField);
        panel1.add(customerPostalCode);
        panel1.add(customerPostalCodeField);
        panel1.add(submitButton);
        panel1.add(clearButton);
        panel1.add(errorlabel);

        //Adding Buttons to Panel3
        JButton submitButton3 = new JButton("Display List Of Customers");
        JButton clearButton3 = new JButton("Clear Fields");
        JTextField resultField = new JTextField("");
        //Action Listener to Display all Data from file
        ActionListener FetchData = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //declaring file path for reading
                File file = new File("C:\\Users\\musta\\Desktop\\Assignment2_data\\Data.dat");

                try (DataInputStream input = new DataInputStream(new FileInputStream(file));) {

                    int count = 0;
                    while (true) {
                        if (count % 5 == 0) {
                            System.out.println("count");
                            System.out.println(count);
                            resultField.setText(resultField.getText());
                        }
//                      //Reading Data from file and setting it to JTextField
                        resultField.setText(resultField.getText() + input.readInt() + "     ");
                        resultField.setText(resultField.getText() + input.readUTF() + "     ");
                        resultField.setText(resultField.getText() + input.readUTF() + "     ");
                        resultField.setText(resultField.getText() + input.readUTF() + "     ");
                        resultField.setText(resultField.getText() + input.readUTF() + "     ");
                    }
                    //Exception Handeling
                } catch (EOFException ex) {
                    System.out.println("All the data were read");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        };
        //adding action listener to clear fields button
        submitButton3.addActionListener(FetchData);
        //setting layout of the elements
        panel3.setLayout(new GridLayout(3, 1));

        //addidng elements to the JPanel.
        panel3.add(submitButton3);
        panel3.add(resultField);
        panel3.add(clearButton3);

        //setting layout of the elements
        panel2.setLayout(
                new GridLayout(7, 2));

        //addidng action listener to clear fields
        ActionListener clear3 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                resultField.setText("");

            }
        };
        clearButton3.addActionListener(clear3);

        //Creating Jlabels
        JLabel customerId2 = new JLabel("Customer Id:");
        JLabel customerName2 = new JLabel("Customer Name:");
        JLabel customerPhone2 = new JLabel("Customer Phone");
        JLabel customerEmail2 = new JLabel("Customer Email:");
        JLabel customerPostalCode2 = new JLabel("Customer Postal code:");
        JLabel errorlabel2 = new JLabel("");

        //Changing font of Jlabels
        customerId2.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerName2.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerPhone2.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerEmail2.setFont(new Font("Oswald", Font.PLAIN, 24));
        customerPostalCode2.setFont(new Font("Oswald", Font.PLAIN, 24));

        //Creating JTextFields
        JTextField fetchCustomerIdField = new JTextField(10);
        JTextField fetchCustomerNameField = new JTextField(10);
        JTextField fetchCustomerPhoneField = new JTextField(10);
        JTextField fetchCustomerEmailField = new JTextField(10);
        JTextField fetchCustomerPostalCodeField = new JTextField(10);

        //Creating Jbuttons
        JButton submitButton2 = new JButton("Modify");
        JButton FetchRecordButton = new JButton("Fetch Record");
        JButton clearButton2 = new JButton("Clear Fields");

        //Adding Elements to Panel
        panel2.add(customerId2);
        panel2.add(fetchCustomerIdField);
        panel2.add(customerName2);
        panel2.add(fetchCustomerNameField);
        panel2.add(customerPhone2);
        panel2.add(fetchCustomerPhoneField);
        panel2.add(customerEmail2);
        panel2.add(fetchCustomerEmailField);
        panel2.add(customerPostalCode2);
        panel2.add(fetchCustomerPostalCodeField);
        panel2.add(FetchRecordButton);
        panel2.add(submitButton2);
        panel2.add(clearButton2);
        panel2.add(errorlabel2);

        //Action Listener to Fetch Records
        ActionListener FetchRecordListener = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //declaring file path for reading
                File file = new File("C:\\Users\\musta\\Desktop\\Assignment2_data\\Data.dat");
                int Id = Integer.parseInt(fetchCustomerIdField.getText());
                try (DataInputStream input = new DataInputStream(new FileInputStream(file));) {
                    //Reading Records from File And setting To field in JtextField of Pannel 2
                    while (true) {
                        if ((input.readInt() == Id)) {
                            fetchCustomerNameField.setText(input.readUTF());
                            fetchCustomerPhoneField.setText(input.readUTF());
                            fetchCustomerEmailField.setText(input.readUTF());
                            fetchCustomerPostalCodeField.setText(input.readUTF());

                        }

                    }

                } //Exception Handeling
                catch (EOFException ex) {
                    System.out.println("All the data were read");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }
        };
        //adding action listener to button
        FetchRecordButton.addActionListener(FetchRecordListener);

        //Creating Action Listener to Modify Data
        ActionListener ModifyData = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                //declaring file path for reading
                File file = new File("C:\\Users\\musta\\Desktop\\Assignment2_data\\Data.dat");
                File file2 = new File("C:\\Users\\musta\\Desktop\\Assignment2_data\\Data2.dat");

                //Reading The first file and Writing Content to second File
                try {
                    FileReader fr = new FileReader("C:\\Users\\musta\\Desktop\\Assignment2_data\\Data.dat");
                    BufferedReader br = new BufferedReader(fr);
                    FileWriter fw = new FileWriter("C:\\Users\\musta\\Desktop\\Assignment2_data\\Data2.dat");
                    String s;
                    //Skipping the Matched Record
                    while ((s = br.readLine()) != null) { // read a line
                        System.out.println("Trim: " + s.trim());
                        System.out.println("LINE: " + s);
                        System.out.println("Original " + fetchCustomerIdField.getText());
                        String check = s.trim();
                        if (check.startsWith(fetchCustomerIdField.getText())) {
                            System.out.println("Not Copied: " + s);
                        } else {
                            System.out.println("LINE Copied: " + s);
                            fw.write(s); // write to output file
                            fw.flush();
                        }
                    }
                    br.close();
                    fw.close();
                    System.out.println("file copied");
                    if (file.delete()) //returns Boolean value  
                    {
                        System.out.println(file.getName() + " deleted");   //getting and printing the file name  
                        file2.renameTo(file);

                    } else {
                        System.out.println("failed");
                    }
                } catch (IOException ep) {
                    System.out.println(ep);

                }
                try (DataOutputStream output = new DataOutputStream(new FileOutputStream(file, true));) {
                    //Fetching the data from Fields and storing to variables
                    String modID = fetchCustomerIdField.getText();
                    String modName = fetchCustomerNameField.getText();
                    String modPhone = fetchCustomerPhoneField.getText();
                    String modEmail = fetchCustomerEmailField.getText();
                    String modPostal = fetchCustomerPostalCodeField.getText();

                    //Writing the data to file
                    output.writeUTF(modID);
                    output.writeUTF(modName);
                    output.writeUTF(modPhone);
                    output.writeUTF(modEmail);
                    output.writeUTF(modPostal);
                    //Handeling Exception
                } catch (EOFException ex) {
                    System.out.println("All the data were read");
                } catch (IOException ex) {
                    System.out.println(ex);
                }
            }

        };
        submitButton2.addActionListener(ModifyData);

        panel2.setLayout(
                new GridLayout(7, 2));

        //action Listener to clear fields
        ActionListener clear2 = new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                fetchCustomerIdField.setText("");
                fetchCustomerNameField.setText("");
                fetchCustomerPhoneField.setText("");
                fetchCustomerEmailField.setText("");
                fetchCustomerPostalCodeField.setText("");

            }
        };
        clearButton2.addActionListener(clear2);

    }
}
