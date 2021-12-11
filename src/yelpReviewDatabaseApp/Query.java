package yelpReviewDatabaseApp;

public class Query {
	String city = null;
	String state = null;
	String keywords = null;
	String k = null; 
	
	public final String getCity() {
		return city;
	}

	public final String getState() {
		return state;
	}

	public final String getKeywords() {
		return keywords;
	}

	public final String getK() {
		return k;
	}

	public final void setCity(String city) {
		String[] data = city.split(":");
		if(data.length > 1) this.city = data[1];
	}

	public final void setState(String state) {
		String[] data = state.split(":");
		if(data.length > 1) this.state = data[1];
	}

	public final void setKeywords(String keywords) {
		String[] data = keywords.split(":");
		if(data.length > 1) this.keywords = data[1];
	}

	public final void setK(String k) {
		String[] data = k.split(":");
		if(data.length > 1) this.k = data[1];
	}

	public String buildQuery() {
		StringBuilder query = new StringBuilder("SELECT name, net_sentiment FROM business WHERE ");
		boolean firstCondition = true;
		if(city != null) {
			query.append("city = '" + this.city + "' ");
			firstCondition = false;
		}
		if(state != null) {
			if(!firstCondition) query.append("AND ");
			query.append("state = '" + this.state + "' ");
		}
		if(keywords != null) {
			if(!firstCondition) query.append("AND ");
			String[] listOfKeywords = keywords.split(",");
			for(int i = 0; i < listOfKeywords.length; i++) {
				String keyword = listOfKeywords[i];
				query.append("keywords LIKE '%" + keyword + "%' ");
				if(i != listOfKeywords.length -1) query.append("AND ");
			}
		}
		query.append("ORDER BY net_sentiment DESC LIMIT " + this.k);
		return query.toString();
	}
}
