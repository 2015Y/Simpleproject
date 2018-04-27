package pojo;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "S_USER")
public class User implements Serializable {
	/**
	 * serialVersionUID 描述:
	 */
	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false, length = 11)
	private String id;
	@Column(name = "name", nullable = true, length = 18)
	private String name;
	@Column(name = "age", nullable = true, length = 11)
	private String age;

	public void setId(String id) {
		this.id = id;
	}

	public String getId() {
		return id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getAge() {
		return age;
	}

	@Override
	public String toString() {
		return "[id=" + id + ", name=" + name + ", age=" + age + "]";
	}

}
