package com.scheakur.suri.impl;

import com.scheakur.suri.DataStore;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public class InMemoryDataStore implements DataStore {

	private ConcurrentHashMap<String, Long> uri2id;
	private ConcurrentHashMap<String, String> short2uri;

	public InMemoryDataStore() {
		this.uri2id = new ConcurrentHashMap<>();
		this.short2uri = new ConcurrentHashMap<>();
	}

	@Override
	public Optional<Long> getId(String uri) {
		return Optional.ofNullable(uri2id.get(uri));
	}

	@Override
	public Optional<String> getUri(String shortUri) {
		return Optional.ofNullable(short2uri.get(shortUri));
	}

	@Override
	public void store(long id, String uri, String shortUri) {
		uri2id.put(uri, id);
		short2uri.put(shortUri, uri);
	}

}
