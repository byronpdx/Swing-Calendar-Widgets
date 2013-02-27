package com.byronpdx.swing;

import org.joda.time.DateMidnight;

public class Person {
	private String name;
	private DateMidnight dob;
	private int age;

	public Person(String name, DateMidnight dob, int age) {
		super();
		this.name = name;
		this.dob = dob;
		this.age = age;
	}

	/**
	 * Get name
	 * 
	 * @return
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		System.out.println(this.name + "->" + name);
		this.name = name;
	}

	public DateMidnight getDob() {
		return dob;
	}

	public void setDob(DateMidnight dob) {
		System.out.println(this.dob != null ? this.dob.toString("MM/dd/yyyy")
				: "null" + "->" + dob != null ? dob.toString("MM/dd/yyyy")
						: "null");
		this.dob = dob;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		System.out.println(this.age + " -> " + age);
		this.age = age;
	}

	@Override
	public String toString() {
		StringBuffer buff = new StringBuffer();
		buff.append("Person:{");
		buff.append(" name:").append(name);
		buff.append(" dob:").append(dob);
		buff.append(" age:").append(age);
		buff.append("}");
		return buff.toString();
	}
}
