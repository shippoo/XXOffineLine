package com.donson.utils;

import java.io.Closeable;

public class UtilsIO {
	
	public static void closeAllQuietly(Closeable... closeables){
		try{
			if(closeables!=null&& closeables.length>0){
				for(Closeable closeable:closeables){
					if(closeable!=null){
						closeable.close();
					}
				}
			}
			
		}catch(Exception ioe){
			
		}
	}

}
