package com.scheakur.suri;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Suri {

	private final Encoder encoder;
	private final DataStore ds;
	private final IdGenerator idGenerator;

	public Suri() {
		this.encoder = new Encoder();
		this.ds = new DataStore();
		this.idGenerator = new IdGenerator(0);
	}

	class Encoder {

		String[] chars = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHUJKLMNOPQRSTUVWXYZ".split("");

		public String encode(long id) {
			if (id <= 62) {
				return chars[(int) id];
			}
			return encode(id / 62) + chars[(int) (id % 62)];
		}

	}

	class IdGenerator {

		private AtomicLong idSequence;

		public IdGenerator(long start) {
			this.idSequence = new AtomicLong(start);
		}

		public long newId() {
			return idSequence.addAndGet(1);
		}

	}

	class DataStore {

		private ConcurrentHashMap<String, Long> uri2id;
		private ConcurrentHashMap<String, String> short2uri;

		public DataStore() {
			this.uri2id = new ConcurrentHashMap<>();
			this.short2uri = new ConcurrentHashMap<>();
		}

		public Optional<Long> getId(String uri) {
			return Optional.ofNullable(uri2id.get(uri));
		}

		public Optional<String> getUri(String shortUri) {
			return Optional.ofNullable(short2uri.get(shortUri));
		}

		public void store(long id, String uri, String shortUri) {
			uri2id.put(uri, id);
			short2uri.put(shortUri, uri);
		}

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