package com.naveed.ytextractor.model;


public class Response {

	private Args args;
	private Assets assets;
	
	
	public Args getArgs() {
		return args;
	}

	public void setArgs(Args args) {
		this.args = args;
	}

	public Assets getAssets() {
		return assets;
	}

	public void setAssets(Assets assets) {
		this.assets = assets;
	}


public class Args {

	private AdaptiveFormats[] adaptiveFormats;
	private String player_response;
	private String url_encoded_fmt_stream_map;
	
	public AdaptiveFormats[] getadaptiveFormats() {
		return adaptiveFormats;
	}

	public void setadaptiveFormats(AdaptiveFormats[] adaptiveFmts) {
		this.adaptiveFormats = adaptiveFmts;
	}

	public String getPlayerResponse() {
		return player_response;
	}

	public void setPlayerResponse(String playerResponse) {
		this.player_response = playerResponse;
	}

	public String getUrlEncodedFmtStreamMap() {
		return url_encoded_fmt_stream_map;
	}

	public void setUrlEncodedFmtStreamMap(String urlEncodedFmtStreamMap) {
		this.url_encoded_fmt_stream_map = urlEncodedFmtStreamMap;
	}

	
	

}

	public class AdaptiveFormats {
		private String adaptiveFormats;

		public String getAdaptiveFormats() {
			return adaptiveFormats;
		}

		public void setAdaptiveFormats(String adaptiveFormats) {
			this.adaptiveFormats = adaptiveFormats;
		}
	}


public class Assets {

	private String js;

	public String getJs() {
		if(js.startsWith("http")&&js.contains("youtube.com")){
			return js;
		}else return "https://youtube.com"+js;
	}

	public void setJs(String js) {
		this.js = js;
	}

}
}
	
	
