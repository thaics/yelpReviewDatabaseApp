package yelpReviewDatabaseApp;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

@JsonIgnoreProperties(ignoreUnknown=true)
public class Business {
	public String business_id;
	public String name;
	public String address;
	public String city;
	public String state;
	public String postal_code;
	public Float latitude;
	public Float longitude;
	public Float stars;
	public Integer review_count;
	public String is_open;
	
	@JsonIgnoreProperties({"categories"})
	public String categories;
	
	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories.replace("categories", "").replace("'", "''");
	}
	
	public final String getBusiness_id() {
		return business_id;
	}
	public final void setBusiness_id(String business_id) {
		this.business_id = business_id;
	}
	public final String getName() {
		return name;
	}
	public final void setName(String name) {
		this.name = name.replace("'", "''");
	}
	public final String getAddress() {
		return address;
	}
	public final void setAddress(String address) {
		this.address = address.replace("'", "''");
	}
	public final String getCity() {
		return city;
	}
	public final void setCity(String city) {
		this.city = city.replace("'", "''");
	}
	public final String getState() {
		return state;
	}
	public final void setState(String state) {
		this.state = state.replace("'", "''");
	}
	public final String getPostal_code() {
		return postal_code;
	}
	public final void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}
	public final Float getLatitude() {
		return latitude;
	}
	public final void setLatitude(Float latitude) {
		this.latitude = latitude;
	}
	public final Float getLongitude() {
		return longitude;
	}
	public final void setLongitude(Float longitude) {
		this.longitude = longitude;
	}
	public final Float getStars() {
		return stars;
	}
	public final void setStars(Float stars) {
		this.stars = stars;
	}
	public final Integer getReview_count() {
		return review_count;
	}
	public final void setReview_count(Integer review_count) {
		this.review_count = review_count;
	}
	public final String getIs_open() {
		return is_open;
	}
	public final void setIs_open(String is_open) {
		this.is_open = is_open.replace("'", "''");
	}
	
//	public String keywordString() {
//		StringBuilder sb= new StringBuilder();
//		for(String keyword: categories) {
//			sb.append(keyword);
//			sb.append(",");
//		}
//		if(sb.length() > 1) sb.deleteCharAt(sb.length()-1);
//		return sb.toString();
//	}
	public String toString() {
		return "('" + this.business_id + "', "
				+ "'" + this.name + "', "
				+ "'" + this.address + "', "
				+ "'" + this.city + "', "
				+ "'" + this.state + "', "
				+ "'" + this.postal_code + "', "
				+ "'" + this.latitude + "', "
				+ "'" + this.longitude + "', "
				+ "'" + this.stars + "', "
				+ "'" + this.review_count + "', "
				+ "'" + this.is_open + "', "
				+ "'" + this.categories + "', "
				+ "'0')";
	}
}
