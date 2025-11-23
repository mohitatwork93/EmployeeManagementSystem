package com.mohit.EMS;

import java.time.LocalDate;

class Employee 
{
    private int id;
    private String name;
    private String department;
    private String phone;
    private LocalDate dob;

    public Employee() {}
    
    public Employee(String name, String department, String phone, LocalDate dob) 
	{
        this.name = name;
        this.department = department;
        this.phone = phone;
        this.dob = dob;
    }

    public void setId(int id) 
	{ 
		this.id = id; 
	}
    public int getId() 
	{ 
		return this.id; 
	}

    public void setName(String name) 
	{ 
		this.name = name; 
	}
    public String getName() 
	{ 
		return this.name; 
	}

    public void setDepartment(String department) 
	{ 
		this.department = department; 
	}
    public String getDepartment() 
	{ 
		return this.department; 
	}

    public void setPhone(String phone) 
	{ 
		this.phone = phone; 
	}
    public String getPhone() 
	{ 
		return this.phone; 
	}

    public void setDob(LocalDate dob) 
	{ 
		this.dob = dob; 
	}
    public LocalDate getDob() 
	{ 
		return this.dob; 
	}
    
    public String toString()
    {
    	return id + " || " +name + " || " + department ;
    }
	
}