package com.scheakur.suri;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SuriTest {

	@Test
	public void test() throws Exception {
		Suri suri = new Suri();
		String s1 = suri.shorten("http://scheakur.com");
		String s2 = suri.shorten("http://blog.scheakur.com");

		assertThat(suri.lengthen(s1), is("http://scheakur.com"));
		assertThat(suri.lengthen(s2), is("http://blog.scheakur.com"));
	}

}