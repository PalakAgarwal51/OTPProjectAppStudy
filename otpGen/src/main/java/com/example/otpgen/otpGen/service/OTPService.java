package com.example.otpgen.otpGen.service;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.example.otpgen.otpGen.dao.otpDao;
import com.example.otpgen.otpGen.entities.CustomerResponse;
import com.example.otpgen.otpGen.entities.OTPBean;
import com.example.otpgen.otpGen.exception.NoResourceException;
import com.example.otpgen.otpGen.exception.OTPTime;


@Service
public class OTPService implements OTPServiceInterface {
	@Value("${app.gen}")
	public int gOtp;
	@Value("${app.val}")
	public int vOtp;
    @Autowired
	private EmailServiceImplementation emailServiceImplementation;
    
	
	@Override
	public String generateOTP() {
		long otp;
		Random random=new Random();
		otp=100000+random.nextInt(900000);
		return String.valueOf(otp);		
	}
	@Autowired
	private otpDao otpDao;

	public OTPBean generatedOTP(OTPBean otpEntities) throws NoResourceException, OTPTime {
		OTPBean otp=this.otpDao.findById(otpEntities.getUid()).orElse(null);
		if(otp!=null)
		{
			if(((System.currentTimeMillis()-otp.getTimestamp())/60000)<gOtp)
			{
				throw new OTPTime();
			}
		}
		 if(otpEntities.getChannelName().equalsIgnoreCase("email")) {
	        	Pattern emailPattern = Pattern.compile("[a-zA-Z0-9[!#$%&'()*+,/\\-_\\.\"]]+@[a-zA-Z0-9[!#$%&'()*+,/\\-_\"]]+\\.[a-zA-Z0-9[!#$%&'()*+,/\\-_\"\\.]]+");   
	        	    Matcher m = emailPattern.matcher(otpEntities.getUid());
	        	        if(!m.matches()) {
	        throw new NoResourceException("Email not valid");
	        }
	        }
	        else if(otpEntities.getChannelName().equalsIgnoreCase("sms")) {
	        	if(!otpEntities.getUid().matches("[0-9]="))
	        	throw new NoResourceException("Needs to be a number for sms");
	        	if(otpEntities.getUid().length()!=10) {
		        	throw new NoResourceException("Number length is not equal to `0");
	        	}
	        }
	        else {
	        	throw new NoResourceException("Enter valid channel name either email or sms");
	        }
		otpEntities.setOtp(generateOTP());
		System.out.println(otpEntities.getOtp());
		otpEntities.setTimestamp();
		 
		 System.out.println(otpEntities);
		 if(otpEntities.getChannelName().equalsIgnoreCase("email"))
		 {
			 try {
		 emailServiceImplementation.sendSimpleMessage(otpEntities.getUid(), "OTP", otpEntities.getOtp());
		 this.otpDao.save(otpEntities);

			 }
			 catch(Exception e)
			 {
				 throw new NoResourceException("EMAIL CANT BE SENT");
			 }
		 
		 }
		 return otpEntities;
	}

	@Override
	public ResponseEntity<OTPBean> validateOTP(String uid,String otp)throws NoResourceException {
		long timestamp=System.currentTimeMillis();
		OTPBean otpBean=otpDao.findById(uid).orElseThrow(()->new NoResourceException("no such user exist"));
		
        String channelNameStored=otpBean.getChannelName();
       
        
		long timestampStored=otpBean.getTimestamp();
		if((timestamp-timestampStored)/60000<vOtp)
		{
			String otpStored=otpBean.getOtp();
			if(otpStored.equals(otp))
			{
				return ResponseEntity.ok().body(otpBean);
			}
			throw new NoResourceException("Otp invalid");
			
		}
		else {
			throw new NoResourceException("TimeStampExceeded ");
			//return ResponseEntity.badRequest().body(otpBean);}
	}
		
}

	}