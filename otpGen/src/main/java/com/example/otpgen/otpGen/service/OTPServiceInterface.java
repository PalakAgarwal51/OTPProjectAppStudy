package com.example.otpgen.otpGen.service;

import org.springframework.http.ResponseEntity;

import com.example.otpgen.otpGen.entities.CustomerResponse;
import com.example.otpgen.otpGen.entities.OTPBean;
import com.example.otpgen.otpGen.exception.NoResourceException;
import com.example.otpgen.otpGen.exception.OTPTime;

public interface OTPServiceInterface {

	public String generateOTP();

	public OTPBean generatedOTP(OTPBean otpentity) throws NoResourceException,OTPTime;

	public ResponseEntity<OTPBean> validateOTP(String uid, String otp) throws NoResourceException;
	


}
