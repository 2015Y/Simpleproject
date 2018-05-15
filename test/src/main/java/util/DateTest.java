package util;

import java.time.Instant;
import java.time.LocalDate;
import java.util.Date;

public class DateTest {
	public static void main(String[] args) {
		System.out.println(LocalDate.now().toString());
		System.out.println(Date.from(Instant.now()).toString());
		System.out.println(new Date().toString());
		for (int i = 0; i <=100; i++) {
			System.out.println((i&3)+"<---->"+(i%4));
		}
	}
}
