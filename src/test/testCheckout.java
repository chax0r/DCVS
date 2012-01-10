package test;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import mgr.info.MetaLog;
import client.Checkout;

public class testCheckout {
	
	public static void main(String [] args){
		
		Checkout.start(args);
	}
}
