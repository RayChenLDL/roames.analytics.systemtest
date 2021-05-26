package com.roames.test.utilities;

import java.io.File;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.ObjectTagging;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.Tag;
import com.roames.test.base.TestcaseBase;
import com.roames.test.base.TestcaseBaseAPI;
import com.roames.test.constant.TestType;

public class AWSUtilities {
	
	public String clientRegion;
	public String bucketName;
	public String AWSAccessKey;
	public String AWSSecretKey;
	public String AWSS3URL;
	
	public AWSUtilities(TestType testtype) {
		try {
			switch (testtype) {
			case GUI:
				AWSS3URL = TestcaseBase.config.getString("AWS_S3_URL");
				clientRegion = TestcaseBase.config.getString("AWS_Region");
				bucketName = TestcaseBase.config.getString("AWS_S3_Bucket_Name");
				AWSAccessKey = TestcaseBase.encrypter.decrypt(TestcaseBase.config.getString("AWSAccessKey"));
				AWSSecretKey = TestcaseBase.encrypter.decrypt(TestcaseBase.config.getString("AWSSecretKey"));
				break;
			case API:
				AWSS3URL = TestcaseBaseAPI.config.getString("AWS_S3_URL");
				clientRegion = TestcaseBaseAPI.config.getString("AWS_Region");
				bucketName = TestcaseBaseAPI.config.getString("AWS_S3_Bucket_Name");
				AWSAccessKey = TestcaseBaseAPI.encrypter.decrypt(TestcaseBaseAPI.config.getString("AWSAccessKey"));
				AWSSecretKey = TestcaseBaseAPI.encrypter.decrypt(TestcaseBaseAPI.config.getString("AWSSecretKey"));
				break;	
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
		
	public String uploadFileToS3(String path, String testcaseName) {
        
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        String S3filename = testcaseName + "_" + timestamp.toString().replace(":", "_").replace(" ", "_").replace(".", "_") + ".pdf";
        
        String testRailURLDisplayed = AWSS3URL + bucketName + "/" + S3filename;

        try {        	
        	AWSCredentials credentials = new BasicAWSCredentials(AWSAccessKey, AWSSecretKey);
        			  
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();        
            
            // Upload a file as a new object with ContentType and title specified.
            PutObjectRequest request = new PutObjectRequest(bucketName, S3filename, new File(path + testcaseName + ".pdf"))      
            		.withCannedAcl(CannedAccessControlList.PublicRead);

            // Set metadata
            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType("plain/text");
            metadata.addUserMetadata("Author", "Roames Test Automation");
            metadata.addUserMetadata("FileType", "PDF");
            request.setMetadata(metadata);
                        
            // Set tags
            List<Tag> tags = new ArrayList<Tag>();
            tags.add(new Tag("Author", "Roames Test Automation"));
            tags.add(new Tag("Usage", "Test Artifact"));
            request.setTagging(new ObjectTagging(tags));
            
            s3Client.putObject(request);
        }
        catch(AmazonServiceException e) {
            e.printStackTrace();
        }
        catch(SdkClientException e) {
            e.printStackTrace();
        }	
        
        return testRailURLDisplayed;
	}
	
	public boolean checkS3ObjectExist(String mys3object) {

        try {        	
        	AWSCredentials credentials = new BasicAWSCredentials(AWSAccessKey, AWSSecretKey);
        			  
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard()
                    .withRegion(clientRegion)
                    .withCredentials(new AWSStaticCredentialsProvider(credentials))
                    .build();        
            
            boolean exists = s3Client.doesObjectExist(bucketName, mys3object);
            if (exists) {
            	System.out.println("Object \"" + bucketName + "/" + mys3object + "\" exists!");
            	return true;
            }
            else {
            	System.out.println("Object \"" + bucketName + "/" + mys3object + "\" does not exist!");
            	return false;
            }            
        }
        catch(AmazonServiceException e) {
            e.printStackTrace();
            return false;
        }
        catch(SdkClientException e) {
            e.printStackTrace();
            return false;
        }	
	}
}


