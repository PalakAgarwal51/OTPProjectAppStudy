package com.example.otpgen.otpGen;

import static org.junit.Assert.assertTrue;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.otpgen.otpGen.service.OTPService;
@ExtendWith(SpringExtension.class)
@SpringBootTest
public class OtpServiceTest {
	@Test
	@DisplayName(" OTP length")
	public void generateOtp()
	{
		OTPService otpService=new OTPService();
		String expected=otpService.generateOTP();
		assertTrue(expected.length()==6);
		
	}
	@Test
	@DisplayName("Number format")
	public void generateOtp2()
	{
		OTPService otpService=new OTPService();
		String expected=otpService.generateOTP();
		assertTrue(expected.matches("[0-9]+"));		
	}

}
