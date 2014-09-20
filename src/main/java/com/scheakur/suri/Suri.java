package com.scheakur.suri;

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
			this.uri2id = new ConcurrentHashMap<String, Long>();
			this.short2uri = new ConcurrentHashMap<String, String>();
		}

		public long newId() {
			return this.id.addAndGet(1);
		}

		public boolean hasUri(String uri) {
			return uri2id.containsKey(uri);
		}

		public long getId(String uri) {
			return uri2id.get(uri);
		}

		public String getUri(String shortUri) {
			return short2uri.get(shortUri);
		}

		public void store(long id, String uri, String shortUri) {
			uri2id.put(uri, id);
			short2uri.put(shortUri, uri);
		}

	}

	public String shorten(String uri) {
		if (ds.hasUri(uri)) {
			long id = ds.getId(uri);
			return encoder.encode(id);
		}

		long id = ds.newId();
		String s = encoder.encode(id);
		ds.store(id, uri, s);
		return s;
	}

	public String lengthen(String shortUri) {
		return ds.getUri(shortUri);
	}

}