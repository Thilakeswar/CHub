package org.Builders;

import java.math.BigDecimal;

import org.junit.jupiter.api.Assertions;

public class UtilityTester
{

	//@Test
	public void testBuilderGeneration() throws Exception
	{
		Student student = new Student.Builder()
			.setName("John")
			.setPhoneNo("9898989898")
			.setGrade("VI")
			.setAverage(new BigDecimal(92.3))
			.setRollNo("VI23")
			.build();
		Assertions.assertEquals(true, student != null);
	}
}
