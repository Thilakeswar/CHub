package org.Builders;

import java.math.BigDecimal;

public class Student
{
	private final String name;
	private final String rollNo;
	private final String grade;
	private final String phoneNo;
	private final BigDecimal average;

	private Student(Builder builder)
	{
		this.name = builder.name;
		this.rollNo = builder.rollNo;
		this.grade = builder.grade;
		this.phoneNo = builder.phoneNo;
		this.average = builder.average;
	}

	public String getName()
	{
		return name;
	}

	public String getRollNo()
	{
		return rollNo;
	}

	public String getGrade()
	{
		return grade;
	}

	public BigDecimal getAverage()
	{
		return average;
	}

	public static class Builder
	{
		private String name;
		private String rollNo;
		private String grade;

		private String phoneNo;
		private BigDecimal average;

		public Builder setName(String name)
		{
			this.name = name;
			return this;
		}

		public Builder setRollNo(String rollNo)
		{
			this.rollNo = rollNo;
			return this;
		}

		public Builder setPhoneNo(String phoneNo)
		{
			this.phoneNo = phoneNo;
			return this;
		}

		public Builder setGrade(String grade)
		{
			this.grade = grade;
			return this;
		}

		public Builder setAverage(BigDecimal average)
		{
			this.average = average;
			return this;
		}

		public Student build()
		{
			return new Student(this);
		}
	}
}
