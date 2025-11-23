package com.mohit.EMS;

import java.util.Scanner;

public class EmployeeApp 
{

    private static EmployeeService service;
    private static Scanner sc;

    public static void main(String[] args) throws Exception 
    {
    	
        sc = new Scanner(System.in);
        System.out.print("Enter DB Root Password: ");
        String pass = sc.next();
        service = new EmployeeService(pass);

        boolean loop = true;
        while (loop) 
        {
        	
            System.out.println("\n===== Employee Management =====");
            System.out.println("1. Add Employee");
            System.out.println("2. Show All Employees");
            System.out.println("3. Search Employee by ID");
            System.out.println("4. Search Employees by Name");
            System.out.println("5. Search Employees by Department");
            System.out.println("6. Update Employee");
            System.out.println("7. Delete Employee");
            System.out.println("0. Exit");
            System.out.print("Choose Option: ");
            String option = sc.next();
            int op = Integer.parseInt(option);


            switch (op) 
            {
                
                case 1 : service.addEmployee();
                break;
                case 2 : service.showAllEmployees();
                break;
                case 3 : service.searchById();
                break;
                case 4 : service.searchByName();
                break;
                case 5 : service.searchByDepartment();
                break;
                case 6 : service.updateEmployee();
                break;
                case 7 : service.deleteEmployee();
                break;
                case 0 : { 
                			System.out.println("Goodbye!"); loop = false; 
                		  }
                break;
                default : System.out.println("❌ Invalid Option!");
            }
            
        }
    }
}

