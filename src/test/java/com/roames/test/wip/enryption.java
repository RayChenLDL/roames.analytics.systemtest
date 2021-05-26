package com.roames.test.wip;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import com.roames.test.utilities.AESEnryption;


	public class enryption {

	    public static void main(String args []) throws Exception
	    {
	        String data = "bt6G$cgFc5h3";

	        AESEnryption encrypter = new AESEnryption();

	        String encrypted = encrypter.encrypt(data);

	        System.out.println("Encrypted String: " + encrypted);

	        //String decrypted = encrypter.decrypt(data);

	        //System.out.println("Decrypted String: " + decrypted);
	    }
	}
