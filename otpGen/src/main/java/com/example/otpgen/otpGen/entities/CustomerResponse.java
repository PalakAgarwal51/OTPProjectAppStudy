package com.example.otpgen.otpGen.entities;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


	
	public class CustomerResponse {
		private OTPBean otpBean;
		private String message;
		public CustomerResponse(OTPBean otpBean, String message) {
			super();
			this.otpBean = otpBean;
			this.message = message;
		}
		
		public CustomerResponse() {
			super();
		}

		public OTPBean getOtpBean() {
			return otpBean;
		}
		public void setOtpBean(OTPBean otpBean) {
			this.otpBean = otpBean;
		}
		public String getMessage() {
			return message;
		}
		public void setMessage(String message) {
			this.message = message;
		}
		
		
		
	}


