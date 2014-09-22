package com.scheakur.suri;

import java.util.Optional;

public interface DataStore {

	Optional<Long> getId(String uri);

	Optional<String> getUri(String shortUri);

	void store(long id, String uri, String shortUri);

}
