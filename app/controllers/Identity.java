package controllers;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.persistence.PersistenceException;

import models.Student;
import play.mvc.Controller;
import play.mvc.Result;

public class Identity extends Controller {
	
	public static Result index() {
		return ok("You called the index method of the Git controller.");
	}

	public static Result linkIdentity(String username, String UUID, String mail) {
		String s;
		
		s = "Username : "+ username + "\nUUID : " + UUID +"\nMail : " + mail + "\n";
		
		System.out.println(s);
		
		String hashUUID = hashed(UUID);
		
		Student stu = new Student(username, mail, hashUUID);
		try {
			stu.save();
			stu.saveManyToManyAssociations("courses");
		} catch(PersistenceException ex) {
			s += "\n"
				+ "ERROR executing DML bindLog[] error[Unique index or primary key violation: PRIMARY_KEY_B ON PUBLIC.STUDENT(UUID)"
				+ "\n";
		}
		
		return ok(
			//views.html.students.render(Student.all())
			views.html.linkOk.render()
			);
	}
	
	public static Result linkForm(String UUID) {
		return ok(
			views.html.identity.render(UUID)
			);
	}
	
	// Helper methods
	public static String hashed(String input) {
		StringBuffer sb = new StringBuffer();
		try {
			MessageDigest mDigest = MessageDigest.getInstance("SHA1");
			byte[] result = mDigest.digest(input.getBytes());
			for (int i = 0; i < result.length; i++) {
				sb.append(Integer.toString((result[i] & 0xff) + 0x100, 16).substring(1));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

}
