package com.scheakur.suri;

import com.scheakur.suri.impl.Base62Encoder;
import com.scheakur.suri.impl.InMemoryDataStore;
import com.scheakur.suri.impl.SequentialIdGenerator;

import java.util.Optional;

public class Suri {

	private final String origin;
	private final DataStore ds;
	private final IdEncoder encoder;
	private final IdGenerator idGenerator;

	public Suri(String origin) {
		this(origin, new InMemoryDataStore(), new Base62Encoder(), new SequentialIdGenerator(0));
	}

	public Suri(String origin, DataStore ds, IdEncoder encoder, IdGenerator idGenerator) {
		this.origin = origin.endsWith("/") ? origin : origin + "/";
		this.ds = ds;
		this.encoder = encoder;
		this.idGenerator = idGenerator;
	}

	public String shorten(String uri) {
		Optional<Long> id = ds.getId(uri);
		if (id.isPresent()) {
			return asUrl(encoder.encode(id.get()));
		}

		long newId = idGenerator.newId();
		String s = encoder.encode(newId);
		ds.store(newId, uri, s);
		return asUrl(s);
	}

	private String asUrl(String s) {
		return origin + s;
	}

	public Optional<String> lengthen(String shortUri) {
		String s = shortUri.replaceFirst(origin, "");
		return ds.getUri(s);
	}

}