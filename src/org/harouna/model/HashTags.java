package org.harouna.model;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
import twitter4j.Query.ResultType;
import twitter4j.QueryResult;
import twitter4j.RateLimitStatus;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class HashTags {

	public static String ACCESS_TOKEN1 = "4716889402-B1IhsglcILYQkNhudgOLBgU2mA8OsxhObAmyW8k";
	public static String ACCESS_TOKEN2 = "U2SrGwJAXypLeWYmZPiR3VJfraA4lveIIOYM27qLjQcFu";
	public static String OAUTH_CONSUMER1 = "2SNP2Do8lLavSUfu2BdYf4PH5";
	public static String OAUTH_CONSUMER2 = "Xzfrz8NeDuvZn65Kz3IU8S9rBzVRYUv8wnDCoKi6Bgl731Nwfj";

	private static final int MAX_QUERIES= 30;
	private static final int TWEETS_PER_QUERY	= 100;


	public HashTags(){

	}

	public static String cleanText(String text) {
		text = text.replace("\n", "\\n");
		text = text.replace("\t", "\\t");
		return text; 
	} 

	public static Twitter authentification(){
		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(OAUTH_CONSUMER1)
		.setOAuthConsumerSecret(OAUTH_CONSUMER2)
		.setOAuthAccessToken(ACCESS_TOKEN1)
		.setOAuthAccessTokenSecret(ACCESS_TOKEN2)
		.setIncludeMyRetweetEnabled(false);

		TwitterFactory factory = new TwitterFactory(cb.build());
		Twitter twitter = factory.getInstance();
		return twitter;
	}

	public List<GeoLocation> getGeoLocation(String hashtag) throws IOException{
		int	totalTweets = 0;
		long maxID = -1;

		Twitter twitter = authentification();
		List<GeoLocation> listGeoLocation = new ArrayList<GeoLocation>();

		GeoLocation geoLocation = new GeoLocation(0, 0);

		try{
			Map<String, RateLimitStatus> rateLimitStatus = twitter.getRateLimitStatus("search");
			RateLimitStatus searchTweetsRateLimit = rateLimitStatus.get("/search/tweets");
			System.out.printf("You have %d calls remaining out of %d, Limit resets in %d seconds\n",
					searchTweetsRateLimit.getRemaining(),
					searchTweetsRateLimit.getLimit(),
					searchTweetsRateLimit.getSecondsUntilReset());

			for (int queryNumber=0;queryNumber < MAX_QUERIES; queryNumber++){
				System.out.printf("\n\n!!! Starting loop %d\n\n", queryNumber);

				if (searchTweetsRateLimit.getRemaining() == 0) {
					System.out.printf("!!! Sleeping for %d seconds due to rate limits\n", searchTweetsRateLimit.getSecondsUntilReset());

					Thread.sleep((searchTweetsRateLimit.getSecondsUntilReset()+2) * 1000l);
				}

				Query query = new Query(hashtag + " +exclude:retweets");
				query.setCount(TWEETS_PER_QUERY);

				ResultType resultType = ResultType.recent;
				query.setResultType(resultType);

				if (maxID != -1) {
					query.setMaxId(maxID - 1); 
				} 

				QueryResult result = twitter.search(query);

				if (result.getTweets().size() == 0) { 
					break;
				} 

				List<Status> listStatus = result.getTweets();
				for (Status s: listStatus)	{ 

					Place place = s.getPlace();
					System.out.println("PLACE" + place);
					System.out.println("GEOLOCATION : " + s.getGeoLocation());
					if(s.getGeoLocation() == null){
						System.out.println("GEOLOCATION NULL");
					}
					else{
						listGeoLocation.add(s.getGeoLocation());	
					}

					totalTweets++;  

					if (maxID == -1 || s.getId() < maxID) {
						maxID = s.getId();
					} 

					System.out.printf("At %s, @%-20s said: %s\n", s.getCreatedAt().toString(), s.getUser().getScreenName(), cleanText(s.getText()));
				}

				searchTweetsRateLimit = result.getRateLimitStatus();
			}
		}
		catch(Exception e){
			System.out.println("Error : " + e);
			e.printStackTrace();
		}
		return listGeoLocation;
	}

	public void showGeolocation(List<GeoLocation> listGeo){
		System.out.println("LISTE :" + listGeo);
	}

	public List<Double> getLat(List<GeoLocation> listGeo){

		List<Double> listeLatitude = new ArrayList <Double>(); 

		for(int i = 0 ; i <  listGeo.size(); i++){
			listeLatitude.add(listGeo.get(i).getLatitude());
		}
		return  listeLatitude;
	}

	public List<Double> getLong(List<GeoLocation> listGeo){

		List<Double> listeLongitude = new ArrayList <Double>(); 

		for(int i = 0 ; i <  listGeo.size(); i++){
			listeLongitude.add(listGeo.get(i).getLongitude());
		}
		return  listeLongitude;		
	}

}
