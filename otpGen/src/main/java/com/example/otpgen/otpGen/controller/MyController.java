package com.example.otpgen.otpGen.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.otpgen.otpGen.entities.CustomerResponse;
import com.example.otpgen.otpGen.entities.OTPBean;
import com.example.otpgen.otpGen.exception.NoResourceException;
import com.example.otpgen.otpGen.exception.OTPTime;
import com.example.otpgen.otpGen.service.OTPServiceInterface;

import org.apache.logging.log4j.Logger;
import org.apache.coyote.http11.Http11AprProtocol;
import org.apache.logging.log4j.LogManager;


@RestController
public class MyController {
	
	Logger logger = LogManager.getLogger(MyController.class);
	


	@Autowired
	private OTPServiceInterface otpService;

	@GetMapping("/genOtp")
	public String generateOtp() {
		
		return this.otpService.generateOTP();
	}
	@PostMapping("/OTPBean")
	public ResponseEntity<?> generatedOtp(@RequestBody OTPBean otpentity) throws NoResourceException, OTPTime {
		
		logger.info("generated otp for ->"+ otpentity);
		
		try{
			OTPBean otpBean=otpService.generatedOTP(otpentity);
			return ResponseEntity.ok().body(new CustomerResponse(otpBean,"DETAILS OF USER"));
		}catch(Exception e)
		{
			return ResponseEntity.badRequest().body(new CustomerResponse(null,"OTP CANNOT BE GENERATED"));
		}
	
	}
	@GetMapping("/validate/{uid}/{otp}")
	public ResponseEntity<OTPBean>validateOtp(@PathVariable String uid,@PathVariable String otp) throws NoResourceException{
		return this.otpService.validateOTP(uid,otp);
		
	}

}
