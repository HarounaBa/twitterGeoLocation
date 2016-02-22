package org.harouna.model;

import java.io.IOException;

import twitter4j.Twitter;

public class MainHashTags {

	public static void main(String[] args) throws IOException {

		String hashTag = "Paris";
		HashTags hashTags = new HashTags();
		Twitter twitter = hashTags.authentification();
		System.out.println(hashTags.getGeoLocation(hashTag));

	}
}
