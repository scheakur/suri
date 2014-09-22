package com.scheakur.suri.impl;

import com.scheakur.suri.IdGenerator;

import java.util.concurrent.atomic.AtomicLong;

public class SequentialIdGenerator implements IdGenerator {

	private AtomicLong idSequence;

	public SequentialIdGenerator(long start) {
		this.idSequence = new AtomicLong(start);
	}

	@Override
	public long newId() {
		return idSequence.addAndGet(1);
	}

}
