package com.scheakur.suri;

import com.scheakur.suri.impl.Base62Encoder;
import com.scheakur.suri.impl.InMemoryDataStore;
import com.scheakur.suri.impl.SequentialIdGenerator;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Suri {

	private final DataStore ds;
	private final IdEncoder encoder;
	private final IdGenerator idGenerator;

	public Suri() {
		this(new InMemoryDataStore(), new Base62Encoder(), new SequentialIdGenerator(0));
	}

	public Suri(DataStore ds, IdEncoder encoder, IdGenerator idGenerator) {
		this.ds = ds;
		this.encoder = encoder;
		this.idGenerator = idGenerator;
	}

	public String shorten(String uri) {
		Optional<Long> id = ds.getId(uri);
		if (id.isPresent()) {
			return encoder.encode(id.get());
		}

		long newId = idGenerator.newId();
		String s = encoder.encode(newId);
		ds.store(newId, uri, s);
		return s;
	}

	public Optional<String> lengthen(String shortUri) {
		return ds.getUri(shortUri);
	}

}