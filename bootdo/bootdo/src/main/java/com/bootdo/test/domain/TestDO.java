package com.bootdo.test.domain;

import java.io.Serializable;



/**
 * 
 * 
 * @author chglee
 * @email 1992lcg@163.com
 * @date 2017-09-26 10:54:46
 */
public class TestDO implements Serializable {
	private static final long serialVersionUID = 1L;
	
	//主键id
	private Integer testId;
	//名字
	private String testName;
	//年龄
	private Integer testAge;
	//地址
	private String testAddr;

	/**
	 * 设置：主键id
	 */
	public void setTestId(Integer testId) {
		this.testId = testId;
	}
	/**
	 * 获取：主键id
	 */
	public Integer getTestId() {
		return testId;
	}
	/**
	 * 设置：名字
	 */
	public void setTestName(String testName) {
		this.testName = testName;
	}
	/**
	 * 获取：名字
	 */
	public String getTestName() {
		return testName;
	}
	/**
	 * 设置：年龄
	 */
	public void setTestAge(Integer testAge) {
		this.testAge = testAge;
	}
	/**
	 * 获取：年龄
	 */
	public Integer getTestAge() {
		return testAge;
	}
	/**
	 * 设置：地址
	 */
	public void setTestAddr(String testAddr) {
		this.testAddr = testAddr;
	}
	/**
	 * 获取：地址
	 */
	public String getTestAddr() {
		return testAddr;
	}
}
