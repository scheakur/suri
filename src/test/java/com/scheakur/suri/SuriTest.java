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
		String s3 = suri.shorten("http://scheakur.com");

		assertThat(suri.lengthen(s1).get(), is("http://scheakur.com"));
		assertThat(suri.lengthen(s2).get(), is("http://blog.scheakur.com"));
		assertThat(s3, is(s1));
	}

}
