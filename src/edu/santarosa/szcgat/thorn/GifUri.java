package edu.santarosa.szcgat.thorn;

public class GifUri {

	private long id;
	private String uri;

	public GifUri(long id, String uri) {
		this.id = id;
		this.uri = uri;
	}

	public long getId() {
		return id;
	}

	public String getUri() {
		return uri;
	}

	@Override
	public String toString() {
		return uri;
	}

}
