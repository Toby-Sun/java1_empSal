/**
 * Description: This program uses loops, file I/O and methods to process employee salary data and generate a small report.
 * The input files are employee (Employee.csv) and employee salary (EmpSal2021.csv).
 * When the user enters the full path of two files and the employee's name, the program will find the EmpID for that employee in two files.
 * Finally,the program will produce the salary schedule for that employee,
 * including Month, Gross Salary, Tax, CPP, EI, Net Salary and total amount of these items.
 * 
 * Name: Christopher Ashe, Drew Temple-Smith, Ho Chun Sun
 * Date: 14/11/2021
 */
import java.util.*;
import java.io.IOException;
import java.io.*; 
public class EmpSal {
    //main method: takes inputs, initializes tasks, calls subprograms 
    public static void main (String [] args) throws IOException {
        //create scanner
        Scanner in = new Scanner (System.in);
        System.out.print("Enter employee filename (full path): ");
        String fileEmployee = in.nextLine();
        File myFile = new File(fileEmployee);
        System.out.print("Enter employee salary filename (full path): ");
        String fileSalary = in.nextLine();
        File myFile2 = new File(fileSalary);
        System.out.print("Enter employee last name: ");
        String lastName = in.nextLine();
        System.out.print("Enter employee first name: ");
        String firstName = in.nextLine();
        //get empID
        String empID = lookUpEID(firstName, lastName); //invoke lookUpEID method
        if(empID.equals("")){
            System.out.println("No Employee information found for: " + firstName + " " + lastName);
        }else{
            //invoke printSalary method
            printSalary(empID, firstName, lastName);

        }
    }
    //lookupemployeeID method: Reads specified file for specified user input, returns corresponding employee ID data.
    public static String lookUpEID (String firstName, String lastName) throws IOException {
        java.io.File myFile = new java.io.File("c:\\temp\\Employee.csv");
        Scanner inFile = new Scanner(myFile);
        //read in header info
        //read and dicard header line
        inFile.nextLine();
        //used to input a whole line of data
        String data = "";
        String [] items = null;
        String empid = "";
        //while more data
        while(inFile.hasNextLine()) {
            //read entire line
            data = inFile.nextLine();
            //split line into component parts
            items = data.split(",");
            //find first name
            String first = (items[0]);
            String last = (items[1]);

            //match name
            if ((firstName.equalsIgnoreCase(first)) && (lastName.equalsIgnoreCase(last))) {
                empid = (items[3]);
            }

        }
        return empid;
    }
    //printemployeeSalary method: Takes employeeID and reads specified file for corresponding Salary information and prints salary.
    public static void printSalary (String emp, String firstName, String lastName) throws IOException {
        java.io.File myFile = new java.io.File("c:\\temp\\EmpSal2021.csv");
        Scanner inFile = new Scanner(myFile);
        //read in header info
        //read and dicard header line
        inFile.nextLine();
        //used to input a whole line of data
        String data = "";
        String [] items = null;
        String eID ="";
        String monthSal = "";
        String fedTax = "";
        String abTax = "";
        String cpp = "";
        String ei = "";
        //while more data
        while(inFile.hasNextLine()) {
            //read entire line
            data = inFile.nextLine();
            //split line into component parts
            items = data.split(",");
            //fing empid
            eID = (items[0]);
            if(emp.equals(eID)){
                monthSal = (items[1]);
                fedTax = (items[2]);
                abTax = (items[3]);
                cpp = (items[4]);
                ei = (items[5]);
            }
        }

        //invoke paySchedual
        paySchedual(emp, firstName, lastName, monthSal, fedTax, abTax, cpp, ei);
    }
    //Print Payschedual
    public static void paySchedual (String emp, String firstName, String lastName, String monthSal, String fedTax, String abTax, String cpp, String ei) throws IOException {
        //turn strings into doubles
        double MonthSal = Double.parseDouble(monthSal);
        double FedTax = Double.parseDouble(fedTax);
        double AbTax = Double.parseDouble(abTax);
        double Cpp = Double.parseDouble(cpp);
        double Ei = Double.parseDouble(ei);
        //declare the maximum of Cpp and Ei
        final double CPP_MAX = 3166.45;
        final double EI_MAX = 889.54;

        //calculate total tax
        double totalTax = FedTax + AbTax;
        //declare the net salary
        double netSalary = 0;
        //declare the variable for total-item
        double grossSalary = 0;
        double grossTotalTax = 0;
        double totalCpp = 0;
        double totalEi = 0;
        double totalNetSalary = 0;
        
        //print the header of output
        System.out.println("");
        System.out.println("Salary schedule for " + firstName.toUpperCase() + " " + lastName.toUpperCase() + " " + "(" + emp +")");
        System.out.println("            Gross                                             Net");
        System.out.println("Month      Salary         Tax         CPP          EI      Salary");
        System.out.println("-----------------------------------------------------------------");
        //create a loop from January to December
        for (int month = 1; month < 13; month++){
            //sum up the monthly Salary,Tax, CPP, EI to total-item
            grossSalary += MonthSal;
            grossTotalTax += totalTax;
            totalCpp += Cpp;
            totalEi += Ei;
            //create a if-statement for CPP and EI, so the amount of CPP and EI will not exceed the maximum
            if (totalCpp <= CPP_MAX){
                Cpp = Cpp;
            }
            else {
                Cpp = Cpp - (totalCpp - CPP_MAX);
                if (Cpp < 0){
                    Cpp = 0;
                }
                totalCpp = CPP_MAX;
            }
            if (totalEi <= EI_MAX) {
                Ei = Ei;
            }
            else {
                Ei = Ei - (totalEi - EI_MAX);
                if (Ei < 0){
                    Ei = 0;
                }
                totalEi = EI_MAX;
            }
            //declare monthly net salary
            netSalary = MonthSal - totalTax - Cpp - Ei;
            //declare total net salary
            totalNetSalary += netSalary;
            //print the monthly results
            System.out.printf("%5s%,12.2f%,12.2f%,12.2f%,12.2f%,12.2f%n", month, MonthSal, totalTax, Cpp, Ei, netSalary);
        }
        System.out.println("-----------------------------------------------------------------");
        //print the total amounts of the items
        System.out.printf("%5s%,12.2f%,12.2f%,12.2f%,12.2f%,12.2f%n", "Total", grossSalary, grossTotalTax, totalCpp, totalEi, totalNetSalary);
        System.out.println();
    }
}
