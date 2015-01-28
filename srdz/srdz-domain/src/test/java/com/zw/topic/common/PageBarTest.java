package com.zw.topic.common;


import com.zw.srdz.domain.Page;

import junit.framework.TestCase;

public class PageBarTest extends TestCase{

	public void testAllPage(){
		Page page = new Page(1);
		page.setAllCount(21);
		
		assertEquals(page.getAllPage(), 3);
	}
	public void testAllPage1(){
		Page page = new Page(1);
		page.setAllCount(5);
		
		assertEquals(page.getAllPage(), 1);
	}
	public void testAllPage2(){
		Page page = new Page(1);
		page.setAllCount(10);
		
		assertEquals(page.getAllPage(), 1);
	}
	
	public void testStart(){
		Page page = new Page(1);
		
		assertEquals(page.getStart(),0);
	}
	
	public void testStart1(){
		Page page = new Page(2);
		
		assertEquals(page.getStart(),10);
	}
	
	public void testStart2(){
		Page page = new Page(5);
		
		assertEquals(page.getStart(),40);
	}
	
	public void testEnd(){
		Page page = new Page(5);
		
		assertEquals(page.getEnd(),50);
	}
	
	public void testPageBar(){
		
		Page page = new Page(1);
		page.setAllCount(34);
		
		System.out.println(page.getPagebars());
	}
	
	public void testPageBar1(){
		
		Page page = new Page(1);
		page.setAllCount(51);
		
		System.out.println(page.getPagebars());
	}
	
	public void testPageBar2(){
		
		Page page = new Page(5);
		page.setAllCount(51);
		
		System.out.println(page.getPagebars());
	}
	
	public void testPageBar3(){
		
		Page page = new Page(6);
		page.setAllCount(51);
		
		System.out.println(page.getPagebars());
	}
	
	public void testPageBar4(){
		
		Page page = new Page(10);
		page.setAllCount(168);
		
		System.out.println(page.getPagebars());
	}
	
	public void testPageBar5(){
		
		Page page = new Page(9);
		page.setAllCount(168);
		
		System.out.println(page.getPagebars());
	}
}
