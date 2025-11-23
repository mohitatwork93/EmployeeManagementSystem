package com.mohit.EMS;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

class EmployeeDB 
{
    private String url = "jdbc:mysql://localhost:3306/employee_db";				// static final
    private String user = "root";
    private String pass = "Mohit12@34";

    public EmployeeDB(String pass) 
	{
        this.pass = pass;
    }

    private Connection getConnection() throws Exception 
	{
        Class.forName("com.mysql.cj.jdbc.Driver");
        return DriverManager.getConnection(url, user, pass);
    }

    public boolean addEmployee(Employee emp) throws Exception 
	{
        String query = "INSERT INTO employees(name, department, phone, dob) VALUES(?,?,?,?)";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query, Statement.RETURN_GENERATED_KEYS)) 
		{
			ps.setString(1, emp.getName());
			ps.setString(2, emp.getDepartment());
			ps.setString(3, emp.getPhone());
			ps.setDate(4, Date.valueOf(emp.getDob()));
			int rows = ps.executeUpdate();
			if (rows > 0) 
			{
				try (ResultSet rs = ps.getGeneratedKeys()) 
				{
					
					if (rs.next()) 
					{
						emp.setId(rs.getInt(1));
					}
				}
			}
            return rows > 0;
        }
    }

    public List<Employee> getAllEmployees() throws Exception 
	{
		
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM employees";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query);
             ResultSet rs = ps.executeQuery()) 
		{
            while (rs.next()) 
			{
                Employee emp = new Employee();
                emp.setId(rs.getInt("e_id"));
                emp.setName(rs.getString("name"));
                emp.setDepartment(rs.getString("department"));
                emp.setPhone(rs.getString("phone"));
                emp.setDob(rs.getDate("dob").toLocalDate());
                list.add(emp);
            }
        } 
        return list;
    }

    public Employee getEmployeeById(int id) throws Exception 
	{
        String query = "SELECT * FROM employees WHERE e_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) 
		{
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) 
			{
                if (rs.next()) 
				{
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("e_id"));
                    emp.setName(rs.getString("name"));
                    emp.setDepartment(rs.getString("department"));
                    emp.setPhone(rs.getString("phone"));
                    emp.setDob(rs.getDate("dob").toLocalDate());
                    return emp;
                }
            }
        }
        return null;
    }

    public List<Employee> searchEmployeesByName(String name) throws Exception 
	{
		
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE name LIKE ?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) 
		{
            ps.setString(1, "%" + name + "%");
            try (ResultSet rs = ps.executeQuery()) 
			{
                while (rs.next()) 
				{
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("e_id"));
                    emp.setName(rs.getString("name"));
                    emp.setDepartment(rs.getString("department"));
                    emp.setPhone(rs.getString("phone"));
                    emp.setDob(rs.getDate("dob").toLocalDate());
                    list.add(emp);
                }
            }
        }
        return list;
    }

    public List<Employee> searchEmployeesByDepartment(String dept) throws Exception 
	{
		
        List<Employee> list = new ArrayList<>();
        String query = "SELECT * FROM employees WHERE department=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) 
		{
            ps.setString(1, dept);
            try (ResultSet rs = ps.executeQuery()) 
			{
                while (rs.next()) 
				{
                    Employee emp = new Employee();
                    emp.setId(rs.getInt("e_id"));
                    emp.setName(rs.getString("name"));
                    emp.setDepartment(rs.getString("department"));
                    emp.setPhone(rs.getString("phone"));
                    emp.setDob(rs.getDate("dob").toLocalDate());
                    list.add(emp);
                }
            }
        }
        return list;
    }

    public boolean updateEmployee(Employee emp) throws Exception 
	{
        String query = "UPDATE employees SET name=?, department=?, phone=?, dob=? WHERE e_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(query)) 
		{
            ps.setString(1, emp.getName());
            ps.setString(2, emp.getDepartment());
            ps.setString(3, emp.getPhone());
            ps.setDate(4, Date.valueOf(emp.getDob()));
            ps.setInt(5, emp.getId());
            return ps.executeUpdate() > 0;
        }
    }

    public boolean deleteEmployee(int id) throws Exception 
	{
		
        String sql = "DELETE FROM employees WHERE e_id=?";
        try (Connection con = getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) 
		{
            ps.setInt(1, id);
			
            return ps.executeUpdate() > 0;
        }
    }
}
