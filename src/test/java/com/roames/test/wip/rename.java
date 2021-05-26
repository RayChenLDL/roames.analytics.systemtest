package com.roames.test.wip;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class rename {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

        File videofile = new File("C:\\Learning\\testJ");
        File[] tempList = videofile.listFiles();
        String videoFileName, videoFileNameMain;
        int dotPos, slashPos;
        for (int i = 0; i < tempList.length; i++) {
            if (tempList[i].isFile()) {
//                  System.out.println("文     件：" + tempList[i]);
                videoFileName = tempList[i].toString();
                File videoFile = new File(videoFileName);
                slashPos = videoFileName.lastIndexOf("\\");
                dotPos = videoFileName.indexOf(".");
                videoFileNameMain = videoFileName.substring(slashPos+1, dotPos) + ".";

                String pathname = "C:\\Learning\\list.txt"; // �?对路径或相对路径都�?�以，这里是�?对路径，写入文件时演示相对路径  
                File filename = new File(pathname); // �?读�?�以上路径的input。txt文件  
                InputStreamReader reader = new InputStreamReader( new FileInputStream(filename)); // 建立一个输入�?对象reader  
                BufferedReader br = new BufferedReader(reader); // 建立一个对象，它把文件内容转�?计算机能读懂的语言  
                String line = "";  
                line = br.readLine();  
                while (line != null) {  
                    line = br.readLine(); // 一次读入一行数�?�  
                    
                    if (line.startsWith(videoFileNameMain)) {
                    	System.out.println(line);
                    	File newVideoFile = new File("C:\\Learning\\testJ\\"+ line+".mp4");
                    	videoFile.renameTo(newVideoFile);
                    	break;
                    }
                } 
                br.close();

            }
        }

	}

}
