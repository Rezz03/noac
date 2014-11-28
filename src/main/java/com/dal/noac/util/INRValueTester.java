package com.dal.noac.util;

import java.util.ArrayList;
import java.util.List;

public class INRValueTester {
	
	public final static double minVal = 2.1;
	public final static double maxVal = 30.4;
	private Double inrValue1;
	private Double inrValue2;
	private Double inrValue3;
	private Double inrValue4;
	private Double inrValue5;
	private Double inrValue6;
	private Double inrValue7;
	private Double inrValue8;
	
	public INRValueTester(){
		inrValue1 = 0.0;
		inrValue2 = 0.0;
		inrValue3 = 0.0;
		inrValue4 = 0.0;
		inrValue5 = 0.0;
		inrValue6 = 0.0;
		inrValue7 = 0.0;
		inrValue8 = 0.0;
	}

	public Double getInrValue1() {
		return inrValue1;
	}

	public void setInrValue1(Double inrValue1) {
		this.inrValue1 = inrValue1;
	}

	public Double getInrValue2() {
		return inrValue2;
	}

	public void setInrValue2(Double inrValue2) {
		this.inrValue2 = inrValue2;
	}

	public Double getInrValue3() {
		return inrValue3;
	}

	public void setInrValue3(Double inrValue3) {
		this.inrValue3 = inrValue3;
	}

	public Double getInrValue4() {
		return inrValue4;
	}

	public void setInrValue4(Double inrValue4) {
		this.inrValue4 = inrValue4;
	}

	public Double getInrValue5() {
		return inrValue5;
	}

	public void setInrValue5(Double inrValue5) {
		this.inrValue5 = inrValue5;
	}

	public Double getInrValue6() {
		return inrValue6;
	}

	public void setInrValue6(Double inrValue6) {
		this.inrValue6 = inrValue6;
	}

	public Double getInrValue7() {
		return inrValue7;
	}

	public void setInrValue7(Double inrValue7) {
		this.inrValue7 = inrValue7;
	}

	public Double getInrValue8() {
		return inrValue8;
	}

	public void setInrValue8(Double inrValue8) {
		this.inrValue8 = inrValue8;
	}

	public List getInrValues(){
		ArrayList<Double> list = new ArrayList<Double>();
		list.add(inrValue1);
		list.add(inrValue2);
		list.add(inrValue3);
		list.add(inrValue4);
		list.add(inrValue5);
		list.add(inrValue6);
		list.add(inrValue7);
		list.add(inrValue8);
		
		return list;
	}
}
