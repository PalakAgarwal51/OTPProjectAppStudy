package com.example.otpgen.otpGen;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import com.example.otpgen.otpGen.entities.OTPBean;
import com.example.otpgen.otpGen.exception.NoResourceException;
import com.example.otpgen.otpGen.service.OTPService;

public class ServiceImplementationTest {
	@Autowired
	private OTPService otpService;
	@Test
	@DisplayName("Generate OTP")
	public void generateOTP()
	{
		NoResourceException exception = (NoResourceException) assertThrows(NoResourceException.class, () -> otpService.generatedOTP(new OTPBean("shaktigupta931@gmail.com","123abc", "emai", "951689",1637086769324L)));
        assertEquals("Enter valid channel name either email or sms",exception.getMessage());
        
	}

}
