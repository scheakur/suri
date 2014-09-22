package com.scheakur.suri;

import com.scheakur.suri.impl.Base62Encoder;
import com.scheakur.suri.impl.InMemoryDataStore;
import com.scheakur.suri.impl.SequentialIdGenerator;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class Suri {

	private final IdEncoder encoder;
	private final DataStore ds;
	private final IdGenerator idGenerator;

	public Suri() {
		this.encoder = new Base62Encoder();
		this.ds = new InMemoryDataStore();
		this.idGenerator = new SequentialIdGenerator(0);
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