package com.mohit.EMS;

import java.util.List;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

class EmployeeService 
{

    private final EmployeeDB db;
    private final Scanner sc;

    public EmployeeService(String dbPassword) throws Exception 
    {
        db = new EmployeeDB(dbPassword);
        sc = new Scanner(System.in);
    }

    
    
    private void printEmployees(List<Employee> employees) 
    {
        System.out.println();
        System.out.printf("%-10s %-18s %-15s %-15s %-15s%n", "ID", "Name", "Department", "Phone", "DOB");
        System.out.println("-------------------------------------------------------------------------");
        
        for (Employee e : employees) 
        {
            System.out.printf("%-10d %-18s %-15s %-15s %-15s%n",
                    e.getId(), e.getName(), e.getDepartment(), e.getPhone(), e.getDob());
        }
    }

    private String inputPhone() 
    {
        sc.nextLine(); // consume leftover newline
        while (true) 
        {
            System.out.print("Enter Phone (10 digits): ");
            String phone = sc.nextLine();
            
            if (phone != null && phone.length() == 10) 
            {
                try {
                    Long.parseLong(phone);  // will throw if not numeric
                    return phone;           // valid 10-digit number
                } 
                catch (NumberFormatException e) {
                	System.out.println("❌ Invalid phone number!(phone number should be 10 digits long)");
                }
            }
            
        }
    }

    private LocalDate inputDob() 
    {
        while (true) 
        {
            System.out.print("Enter DOB (yyyy-MM-dd): ");       // ISO-8601 format
            String dateOfBirth = sc.nextLine();
            try 
            { 
            	LocalDate dob = LocalDate.parse(dateOfBirth);		// DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); return LocalDate.parse(dateOfBirth, formatter);
            	return dob;
            }
            catch (DateTimeParseException e) 
            { 
            	System.out.println("❌ Invalid date format!");
            }
        }
    }

    private String inputNonEmpty(String field) {
        while (true) {
            System.out.print("Enter " + field + ": ");
            String value = sc.next();
            if (!value.isBlank()) return value;
            System.out.println("❌ " + field + " cannot be empty!");
        }
    }

    
    // ---------- CRUD Operations ----------
    public void addEmployee() {
        try 
        {
            String name = inputNonEmpty("Name");
            String dept = inputNonEmpty("Department");
            String phone = inputPhone();
            LocalDate dob = inputDob();

            Employee emp = new Employee(name, dept, phone, dob);
            
            if (db.addEmployee(emp))
            {
            	System.out.println("Success !! Employee Added with ID: " + emp.getId());
            }
        } 
        catch (Exception e) 
        {
        	System.out.println("❌ Error: " + e.getMessage()); 
        }
    }

    public void showAllEmployees() {
        try 
        {
            List<Employee> list = db.getAllEmployees();
            if (list.isEmpty())
            {
            	System.out.println("Sorry... No Employees Found!");
            }
            else
            {
            	printEmployees(list);
            }
        } 
        catch (Exception e) 
        {
        	System.out.println("❌ Error: " + e.getMessage()); 
        }
    }

    public void searchById() {
        try {
            System.out.print("Enter Employee ID: ");
            int id = sc.nextInt();
            Employee emp = db.getEmployeeById(id);
            if (emp == null)
            {
            	System.out.println("Sorry... Employee Not Found!");
            }
            else
            {
            	printEmployees(List.of(emp));
            }
            sc.nextLine();
        } catch (Exception e) { System.out.println("❌ Error: " + e.getMessage()); }
    }

    public void searchByName() {
        try {
            
            String name = inputNonEmpty("Name");
            List<Employee> list = db.searchEmployeesByName(name);
            if (list.isEmpty()) System.out.println("Sorry... No Employees Found!");
            else printEmployees(list);
        } catch (Exception e) { System.out.println("❌ Error: " + e.getMessage()); }
    }

    public void searchByDepartment() {
        try {
            
            String dept = inputNonEmpty("Department");
            List<Employee> list = db.searchEmployeesByDepartment(dept);
            if (list.isEmpty()) System.out.println("Sorry... No Employees Found!");
            else printEmployees(list);
        } catch (Exception e) { System.out.println("❌ Error: " + e.getMessage()); }
    }

    public void updateEmployee() {
        try {
            System.out.print("Enter Employee ID to Update: ");
            int id = sc.nextInt();
            Employee emp = db.getEmployeeById(id);
            if (emp == null) { System.out.println("Sorry... Employee Not Found!"); return; }

            sc.nextLine();
            System.out.print("Enter New Name (" + emp.getName() + "): ");
            String name = sc.nextLine(); if (!name.isEmpty()) emp.setName(name);

            System.out.print("Enter New Department (" + emp.getDepartment() + "): ");
            String dept = sc.nextLine(); if (!dept.isEmpty()) emp.setDepartment(dept);

            System.out.print("Enter New Phone (" + emp.getPhone() + "): ");
            String phone = sc.nextLine(); if (!phone.isEmpty() && phone.matches("\\d{10}")) emp.setPhone(phone);

            System.out.print("Enter New DOB (" + emp.getDob() + "): ");
            String dob = sc.nextLine();
            if (!dob.isEmpty()) {
                try { emp.setDob(LocalDate.parse(dob)); }
                catch (DateTimeParseException ex) { System.out.println("Sorry... Invalid date, keeping old DOB."); }
            }

            if (db.updateEmployee(emp)) System.out.println("✅ Employee Updated!");
            else System.out.println("Sorry... Update Failed!");
        } catch (Exception e) { System.out.println("❌ Error: " + e.getMessage()); }
    }

    public void deleteEmployee() {
        try {
            System.out.print("Enter Employee ID to Delete: ");
            int id = sc.nextInt();
            if (db.deleteEmployee(id)) System.out.println("✅ Employee Deleted!");
            else System.out.println("Sorry... Delete Failed!");
        } catch (Exception e) { System.out.println("❌ Error: " + e.getMessage()); }
    }
}

