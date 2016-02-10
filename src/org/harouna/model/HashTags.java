package org.harouna.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import twitter4j.GeoLocation;
import twitter4j.Place;
import twitter4j.Query;
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

	private static final int MAX_QUERIES= 9;
	private static final int TWEETS_PER_QUERY	= 100;
	//private static final String HASH_TAG	= "#NationalPizzaDay";

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

	public List<GeoLocation> getGeoLocation(String hashtag){
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

				Query q = new Query(hashtag + " +exclude:retweets");
				q.setCount(TWEETS_PER_QUERY);	// How many tweets, max, to retrieve 
				//q.resultType("recent");	// Get all tweets 

				//q.setLang("en");

				if (maxID != -1) {
					q.setMaxId(maxID - 1); 
				} 

				QueryResult r = twitter.search(q);

				if (r.getTweets().size() == 0) { 
					break;	// Nothing? We must be done
				} 

				List<Status> listStatus = r.getTweets();
				for (Status s: listStatus)	{ //	Increment our count of tweets retrieved 

					Place place = s.getPlace();
					System.out.println("PLACE" + place);
					System.out.println("STATUS : " + s.getGeoLocation());
					if(s.getGeoLocation() == null){
						System.out.println("GEOLOCATION NULL");
					}
					else{
						listGeoLocation.add(s.getGeoLocation());					
					}
					totalTweets++; //	Keep track of the lowest tweet ID. If you do not do this, you cannot retrieve multiple //	blocks of tweets... 
					if (maxID == -1 || s.getId() < maxID) {
						maxID = s.getId();
					} //	Do something with the tweet.... 
					System.out.printf("At %s, @%-20s said: %s\n", s.getCreatedAt().toString(), s.getUser().getScreenName(), cleanText(s.getText()));
				}
				searchTweetsRateLimit = r.getRateLimitStatus();
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

	/**
	 * Methode permettant de recuperer la latitude
	 * @param listGeo
	 * @return
	 */
	public List<Double> getLat(List<GeoLocation> listGeo){

		List<Double> listeLatitude = new ArrayList <Double>(); 

		for(int i = 0 ; i <  listGeo.size(); i++){
			listeLatitude.add(listGeo.get(i).getLatitude());
		}
		return  listeLatitude;
	}


	/**
	 * Methode permettant de recuperer la longitude
	 * @param listGeo
	 * @return
	 */
	public List<Double> getLong(List<GeoLocation> listGeo){

		List<Double> listeLongitude = new ArrayList <Double>(); 

		for(int i = 0 ; i <  listGeo.size(); i++){
			listeLongitude.add(listGeo.get(i).getLongitude());
		}
		return  listeLongitude;		
	}
	public static void main(String[] args) {
		/*	// TODO Auto-generated method stub
		int	totalTweets = 0;
		long maxID = -1;

		ConfigurationBuilder cb = new ConfigurationBuilder();

		cb.setDebugEnabled(true)
		.setOAuthConsumerKey(OAUTH_CONSUMER1)
		.setOAuthConsumerSecret(OAUTH_CONSUMER2)
		.setOAuthAccessToken(ACCESS_TOKEN1)
		.setOAuthAccessTokenSecret(ACCESS_TOKEN2)
		.setIncludeMyRetweetEnabled(false);

		TwitterFactory factory = new TwitterFactory(cb.build());
		Twitter twitter = factory.getInstance();

		try {
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

				Query q = new Query(HASH_TAG + " +exclude:retweets");
				q.setCount(TWEETS_PER_QUERY);	// How many tweets, max, to retrieve 
				//q.resultType("recent");	// Get all tweets 

				q.setLang("en");

				if (maxID != -1) {
					q.setMaxId(maxID - 1); 
				} 

				QueryResult r = twitter.search(q);

				if (r.getTweets().size() == 0) { 
					break;	// Nothing? We must be done
				} 
				// Loop through all the tweets... 
				List<Status> listStatus = r.getTweets();
				for (Status s: listStatus)	{ //	Increment our count of tweets retrieved 

					Place place = s.getPlace();
					System.out.println("PLACE : " +place);
					System.out.println(s.getUser().getScreenName());
					System.out.println("STATUS : " + s.getGeoLocation());
//					if(place.getBoundingBoxCoordinates() == null){
//						System.out.println("Null");
//					}
//					else{
//					GeoLocation[][] box = place.getBoundingBoxCoordinates();

					totalTweets++; //	Keep track of the lowest tweet ID. If you do not do this, you cannot retrieve multiple //	blocks of tweets... 
					if (maxID == -1 || s.getId() < maxID) {
						maxID = s.getId();
					} //	Do something with the tweet.... 
					System.out.printf("At %s, @%-20s said: %s\n", s.getCreatedAt().toString(), s.getUser().getScreenName(), cleanText(s.getText()));

//					for(int i = 0; i<r.getTweets().size() ;i++){
//
//						for(int j=0 ; j< r.getTweets().size() ;j++){
//
//							System.out.println("\n==========TEST Latitude =============== " + s.getUser().getName() + " : "+ box[i][j].getLatitude()); 
//							System.out.println("==========TEST:Longitude =============== " + s.getUser().getName() + ": "  + box[i][j].getLongitude()); 
//
//						}
//					}
				}
				searchTweetsRateLimit = r.getRateLimitStatus();
				}
//			}
		}
		catch (Exception e) {
			//	Catch all -- you're going to read the stack trace and figure out what needs to be done to fix it 
			System.out.println("That didn't work well...wonder why?"); 
			e.printStackTrace(); 
		}
		System.out.printf("\n\nA total of %d tweets retrieved\n", totalTweets);
		 */

		String hashTag = "#Paris";
		HashTags hashTags = new HashTags();
		Twitter twitter = hashTags.authentification();
		System.out.println(hashTags.getGeoLocation(hashTag));
		System.out.println(hashTags.getLat(hashTags.getGeoLocation(hashTag)));
		//System.out.println(hashTags.getGeoLocation(hashTag).get(0));
	}
}
