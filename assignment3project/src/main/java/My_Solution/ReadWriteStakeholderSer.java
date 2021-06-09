/**
 * File Name: ReadWriteStakeholderSer.java
 * Module: ADP262S 
 * Task Name: Assignment3
 * Name: Brandon Kruger
 * Student Number: 216049245
 * Student Email: 216049245@mycput.ac.za
 *
 */
package My_Solution;

/**
 *
 * @author Brandon
 */
//Importing required classes
import za.ac.cput.assignment3project.Customer;
import za.ac.cput.assignment3project.Supplier;
import java.time.LocalDate;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.io.IOException;
import java.util.ArrayList;
import java.io.FileInputStream;
import java.util.Collections;
import java.io.File;
import java.util.Locale;
import java.io.EOFException;
import java.io.PrintWriter;

public class ReadWriteStakeholderSer
{
    private final String readWriteOutStakeholder = "Stakeholder.ser";
    
    //Writes content to files
    FileWriter writeFile;
    PrintWriter printFile;
    
    // Reads objects from .ser
    FileInputStream fileInput;
    ObjectInputStream fileOutput;
    
    public void openSerializedFile(String filename)
    {
        try
        {
            writeFile = new FileWriter(new File(filename));
            printFile = new PrintWriter(writeFile);
            System.out.println(filename + " This file has been successfully created");
            
        } catch (IOException ioe)
        {
            System.exit(1);
        }
    }
    
    // read customer objects from .ser and add to the ArrayList
    private ArrayList<Customer> cust()
    {
        ArrayList<Customer> custList = new ArrayList<>();
        
        try
        {
            fileInput = new FileInputStream(new File(readWriteOutStakeholder));
            fileOutput = new ObjectInputStream(fileInput);
            
             
            while (true)
            {
                Object obj = fileOutput.readObject();
                
                if (obj instanceof Customer)
                {
                    custList.add((Customer) obj);
                }
            }
            
        } catch (EOFException eofException)
        {
            
        } catch (IOException | ClassNotFoundException e)
        {
           System.exit(1);
            
        } finally
        {
            try
            {
                fileInput.close();
                fileOutput.close();
                
            } catch (IOException e)
            {
            }
        }
        
        {
            // Sorting arrayList
            Collections.sort(custList,
                    (Customer a1, Customer a2) -> 
                            a1.getStHolderId().compareTo(a2.getStHolderId())
            );
        }
        
        return custList;
    }
    
    private void writeCustomerFile()
    {
        String borderLineOne = "======================= CUSTOMERS =========================\n";
        String outputStructure = "%s\t%-10s\t%-10s\t%-10s\t%-10s\n";
        String borderLineTwo = "===========================================================\n";
        
        try
        {   
            printFile.print(borderLineOne);
            printFile.printf(outputStructure, "ID", "Name", "Surname", 
       "Date Of Birth", "Age");
            printFile.print(borderLineTwo);
            
            for (int i = 0; i < cust().size(); i++)
            {   
                printFile.printf(outputStructure,
                        cust().get(i).getStHolderId(),
                        cust().get(i).getFirstName(),
                        cust().get(i).getSurName(),
                        formatDate(cust().get(i).getDateOfBirth()),
                        Birthday(cust().get(i).getDateOfBirth())
                );
            }
            
            printFile.printf(
                    "\nNumber of customers who are able to rent: %d", 
                    ableToRent());
            
            printFile.printf(
                    "\nNumber of customers who are unable to rent: %d", 
                    unableToRent());
            
        } catch (Exception e)
        {
        }
    }
    
    private String formatDate(String dob)
    {
        // Formatting date
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(
                "dd MMM yyyy", 
                Locale.ENGLISH);
        // Parses date of birth
        LocalDate parseDob = LocalDate.parse(dob); 

        // Returns the formatted date.
        return parseDob.format(formatter);
    }
    
    private int Birthday(String dob)
    {
        // Fetches the current date.
        ZonedDateTime todayDate = ZonedDateTime.now(); 
        int currentYear = todayDate.getYear();
        
        // Parses the date of birth.
        LocalDate parseDob = LocalDate.parse(dob); 
        int birthdayYear  = parseDob.getYear();
        
        // Return the customers age.
        return currentYear - birthdayYear;
    }
    
    private int ableToRent()
    {
        int ableToRent = 0;
        
        for (int i = 0; i < cust().size(); i++)
        {
            // Check if you're able to rent.
            if (cust().get(i).getCanRent())
            {
                ableToRent += i++;
            }
        }
        
        return ableToRent;
    }
    
    private int unableToRent()
    {
        int unableToRent = 0;
        
        for (int i = 0; i < cust().size(); i++)
        {
            // Check if you're able to rent.
            if (!cust().get(i).getCanRent())
            {
                unableToRent += i++;
            }
        }
        return unableToRent;
    }
    
    // Read from supplier .ser and add to arrayList
    private ArrayList<Supplier> suppList()
    {
        ArrayList<Supplier> suppliers = new ArrayList<>();
        
        try
        {
            fileInput = new FileInputStream(new File(readWriteOutStakeholder));
            fileOutput = new ObjectInputStream(fileInput);
            
            
            while (true)
            {
                Object obj = fileOutput.readObject();
                
                if (obj instanceof Supplier)
                {
                    suppliers.add((Supplier) obj);
                }
            }
            
        } catch (EOFException eofException)
        {
            
        } catch (IOException | ClassNotFoundException e)
        {
        } finally
        {
            try
            {
                fileInput.close();
                fileOutput.close();
                
            } catch (IOException e)
            {
            }
        }
        
        {
            // sort arrayList ascending according to the supplier name
            Collections.sort(
                    suppliers, 
                    (Supplier b1, Supplier b2) -> 
                            b1.getName().compareTo(b2.getName())
            );
        }
        
        return suppliers;
    }
    
    private void writeSupplierFile()
    {
        String borderLineOne = "======================= SUPPLIERS ============================\n";
        String outputStructure = "%s\t%-20s\t%-10s\t%-10s\n";
        String borderLineTwo = "===========================================================\n";
        
        try
        {
            printFile.print(borderLineOne);
            printFile.printf(outputStructure, "ID", "Name", "Manufacturer",
       "Description");
            printFile.print(borderLineTwo);
            for (int i = 0; i < suppList().size(); i++)
            {
                printFile.printf(outputStructure,
                        suppList().get(i).getStHolderId(),
                        suppList().get(i).getName(),
                        suppList().get(i).getProductType(),
                        suppList().get(i).getProductDescription()
                );
            }
            
        } catch (Exception e)
        {
        }
    }
    
    //Closing the file
    public void closeFile(String filename)
    {
        try
        {
            writeFile.close();
            printFile.close();

        } catch (IOException ex)
        {
        }
    }
    
   //Main Method where everything is executed.
    public static void main(String[] args)
    {
        ReadWriteStakeholderSer rws = new ReadWriteStakeholderSer();
        rws.openSerializedFile("customerOutFile.txt");
        rws.writeCustomerFile();
        rws.closeFile("customerOutFile.txt");
        rws.openSerializedFile("supplierOutFile.txt");
        rws.writeSupplierFile();
        rws.closeFile("supplierOutFile.txt");
    }
}
