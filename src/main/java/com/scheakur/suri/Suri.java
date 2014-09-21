package com.scheakur.suri;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

public class Suri {

	private final Encoder encoder;
	private final DataStore ds;

	public Suri() {
		this.encoder = new Encoder();
		this.ds = new DataStore();
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

	class DataStore {

		private AtomicLong id;
		private ConcurrentHashMap<String, Long> uri2id;
		private ConcurrentHashMap<String, String> short2uri;

		public DataStore() {
			this.id = new AtomicLong(0);
			this.uri2id = new ConcurrentHashMap<>();
			this.short2uri = new ConcurrentHashMap<>();
		}

		public long newId() {
			return this.id.addAndGet(1);
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

		long newId = ds.newId();
		String s = encoder.encode(newId);
		ds.store(newId, uri, s);
		return s;
	}

	public Optional<String> lengthen(String shortUri) {
		return ds.getUri(shortUri);
	}

}