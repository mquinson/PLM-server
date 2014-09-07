package models;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import play.db.ebean.Model;

@Entity
public class Student extends Model {
	private static final long serialVersionUID = 1L;

	@Id
	public String hashedUuid;
	
	public String name;
	
	public String mail;

	public static Finder<String, Student> find = new Finder<String, Student>(String.class, Student.class);

	public Student(String name, String mail, String hashedUuid) {
		this.hashedUuid = hashedUuid;
		this.name = name;
		this.mail = mail;
	}


	public static Student authenticate(String uuid) {
		return find.where().eq("uuid", uuid).findUnique();
	}


    public static int count() {
        return find.findRowCount();
    }
	
	public static List<Student> all() {
	  return find.all();
	}

	public static void create(Student student) {
		System.out.println("Student :\n"
				+ "name : " + student.name +"\n"
				+ "hashedUuid : "+ student.hashedUuid);
		student.save();
	}
	
	public static void delete(String hashedUuid, String s) {
		find.byId(hashedUuid).delete();
	}
	
}
