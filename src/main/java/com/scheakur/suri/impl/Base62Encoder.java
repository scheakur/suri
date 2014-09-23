package com.scheakur.suri.impl;

import com.scheakur.suri.IdEncoder;

public class Base62Encoder implements IdEncoder {

	private static final String[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHUJKLMNOPQRSTUVWXYZ".split("");

	@Override
	public String encode(long id) {
		if (id <= 62) {
			return chars[(int) id];
		}
		return encode(id / 62) + chars[(int) (id % 62)];
	}

}
