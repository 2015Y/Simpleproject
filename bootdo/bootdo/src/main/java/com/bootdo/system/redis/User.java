package com.bootdo.system.redis;

import java.io.Serializable;

/**
 * Created by on 2017/2/24.
 */
@SuppressWarnings({ "unused", "serial" })
public class User implements Serializable {

	private static final long serialVersionUId = 1L;

	private String id;
	private String name;

	public User() {
		super();
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return "User{" + "id='" + id + '\'' + ", name='" + name + '\'' + '}';
	}
}