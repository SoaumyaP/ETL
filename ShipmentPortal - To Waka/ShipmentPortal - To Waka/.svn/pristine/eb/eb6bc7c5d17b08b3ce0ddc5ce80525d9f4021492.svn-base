package com.csfe.common;

public class CSFEException extends Exception {
    
	public CSFEException() {
		super();
		// TODO Auto-generated constructor stub
	}

	public CSFEException(String arg0, Throwable arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
	}

	public CSFEException(String arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	public CSFEException(Throwable arg0) {
		super(arg0);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		String err = super.toString() + "\n";
		StackTraceElement[] traceEle = super.getStackTrace();
		if(traceEle != null) {
			for(int i=0; i < traceEle.length; i++) {
				err += "\tat" + traceEle[i].toString() + "\n";
			}
		}
		return err;
	}
}
