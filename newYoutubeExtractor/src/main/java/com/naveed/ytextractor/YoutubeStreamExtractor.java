package com.naveed.ytextractor;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Looper;
import android.text.Html;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonParser;
import com.naveed.ytextractor.model.PlayerResponse;
import com.naveed.ytextractor.model.Response;
import com.naveed.ytextractor.model.YoutubeMedia;
import com.naveed.ytextractor.model.YoutubeMeta;
import com.naveed.ytextractor.utils.HTTPUtility;
import com.naveed.ytextractor.utils.LogUtils;
import com.naveed.ytextractor.utils.RegexUtils;
import com.naveed.ytextractor.utils.Utils;

import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.DataNode;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class YoutubeStreamExtractor extends AsyncTask<String,Void,Void> {




	Map<String,String> Headers=new HashMap<>();



	List<YoutubeMedia> adaptiveMedia=new ArrayList<>();
	List<YoutubeMedia> muxedMedia=new ArrayList<>();
	
	String regexItag=("(?<=itag=).*");
	String regexUrl=("(?<=url=).*");
	String regexQualitylabel="(?<=quality_label=).*";
	String regexType="(?<=mimeType=).*";
	String regexResSize="(?<=size=).*";
	String regexBitrate="(?<=bitrate=).*";
	String regexExtension="(?<=(video|audio)/).*?(?=\\;)";
	String regexCodec="(?<=(codecs=)\").*?(?=\")";
	String regextypeVideoAudio=".*?(?=/)";
	String regexYtshortLink="(http|https)://(www\\.|)youtu.be/.*";
	String regexPageLink = ("(http|https)://(www\\.|m.|)youtube\\.com/watch\\?v=(.+?)( |\\z|&)");
	String regexFindReason="(?<=(class=\"message\">)).*?(?=<)";
	String regexPlayerJson="(?<=ytplayer.config\\s=).*?((\\}(\n|)\\}(\n|))|(\\}))(?=;)";
	ExtractorListner listener;
	private ExtractorException Ex;
	List<String> reasonUnavialable=Arrays.asList(new String[]{"This video is unavailable on this device.","Content Warning","who has blocked it on copyright grounds."});
	Handler han=new Handler(Looper.getMainLooper());
	private YoutubeMeta YTVideoMeta;
	private Response response;
    private PlayerResponse playerResponse;

	private boolean isLive;
	private boolean useCipher;

	private String jsonResponse;


	public YoutubeStreamExtractor(ExtractorListner EL) {
		this.listener = EL;
		Headers.put("Accept-Language", "en");
	}

	public void setHeaders(Map<String, String> headers) {
		Headers = headers;
	}

	public Map<String, String> getHeaders() {
		return Headers;
	}

	public void Extract(String VideoId) {
		this.execute(VideoId);
	}
	
	

	@Override
	protected void onPostExecute(Void result) {
		if (Ex != null) {
			listener.onExtractionGoesWrong(Ex, "exception", jsonResponse);
		} else
			listener.onExtractionDone(adaptiveMedia, muxedMedia, YTVideoMeta);

	}

	@Override
	protected void onPreExecute() {
		Ex = null;
		adaptiveMedia.clear();
		muxedMedia.clear();

	}

	@Override
	protected void onCancelled() {
		if (Ex != null) {
			listener.onExtractionGoesWrong(Ex, "cancelled", jsonResponse);
		}	
	}



	@Override
	protected Void doInBackground(String[] ids)  {

		String Videoid=Utils.extractVideoID(ids[0]);
		
        String jsonBody = null;

        try {
			String body = HTTPUtility.downloadPageSource("https://www.youtube.com/watch?v=" + Videoid + "&has_verified=1&bpctr=9999999999", Headers);
			jsonResponse = body;
			jsonBody = parsePlayerConfig(body);
			if (jsonBody == null || jsonBody.isEmpty()){
				parseJson(body);
			}
			else {
				parseJson(jsonBody);
			}
			parseUrls();
		}
        catch (Exception e) {
			LogUtils.log(Arrays.toString(e.getStackTrace()));// e.toString());
			Ex = new ExtractorException("Error While getting Youtube Data:" + e.getMessage());
			this.cancel(true);
		}

		return null;
	}

	private String parsePlayerConfig(String body) throws ExtractorException {

		if(Utils.isListContain(reasonUnavialable,RegexUtils.matchGroup(regexFindReason,body))){
			throw new ExtractorException(RegexUtils.matchGroup(regexFindReason,body));
		}
		
		if(body.contains("ytplayer.config")){
			return RegexUtils.matchGroup(regexPlayerJson,body);
		}else{
			throw new ExtractorException("This Video is unavialable");
		}
	
	}

	private void parseUrls() {

		try {
			if (!isLive) {
				parseAdaptiveUrls();	
				parseMuxedUrls();
			}
		}
		catch (IOException e) {
			Ex = new ExtractorException(e.getMessage());
			this.cancel(true);
		}
	}

	private void parseMuxedUrls() throws IOException {
		if (response == null) return;
		String url_encoded_fmt_stream_map=response.getArgs().getUrlEncodedFmtStreamMap();
		if (url_encoded_fmt_stream_map == null || url_encoded_fmt_stream_map.isEmpty())return;
		String[] rawUrls=url_encoded_fmt_stream_map.split(",");


		for (int x=0;x < rawUrls.length;x++) {

			YoutubeMedia media=new YoutubeMedia();

			String[] Decodedurl= rawUrls[x].split("&");
			for (String part:Decodedurl) {

				if (part.startsWith("url=")) {
					media.setUrl(URLDecoder.decode(RegexUtils.matchGroup(regexUrl, part)));
				}
				if (part.startsWith("s=") & useCipher) {
					try {
						media.setDechiperedSig(CipherManager.dechiperSig(URLDecoder.decode(part.replace("s=", "")), response.getAssets().getJs()));
					}
					catch (Exception e){
						Log.e("Youtube", "Exception while getting js", e);
						media.setDechiperedSig("");
					}
				}
				if (part.startsWith("size=")) {		
					media.setResSize(RegexUtils.matchGroup(regexResSize, part));
				}
				if (part.startsWith("bitrate=")) {		
					media.setBitrate(RegexUtils.matchGroup(regexBitrate, part));
				}
				if (part.startsWith("itag=")) {		
					media.setItag(RegexUtils.matchGroup(regexItag, part));
				}
				if (part.startsWith("quality_label=")) {		
					media.setResolution(RegexUtils.matchGroup(regexQualitylabel, part));
				}
				if (part.startsWith("type=")) {		
					String type=URLDecoder.decode(RegexUtils.matchGroup(regexType, part));

					media.setExtension(RegexUtils.matchGroup(regexExtension, type));
					media.setCodec(RegexUtils.matchGroup(regexCodec, type));
					media.setIsAudioOnly(false);
					media.setIsVideoOnly(false);					
					media.setIsMuxed(true);
				}		

			}
			muxedMedia.add(media);

		}

		muxedMedia =Utils.filterInvalidLinks(muxedMedia);
	}


	private void parseAdaptiveUrls() throws IOException {

		//Response.AdaptiveFormats[] adaptive_fmts=response.getArgs().getadaptiveFormats();
		PlayerResponse.AdaptiveFormats[] adaptive_fmts=playerResponse.getStreamingData().getadaptiveFormats();

        PlayerResponse.Formats[] formats;

        if (adaptive_fmts == null || adaptive_fmts.length <= 0){
            formats = playerResponse.getStreamingData().getFormats();

            /*String[] rawUrls=adaptive_fmts.split(",");*/

            for (int x=0;x < formats.length;x++) {

                YoutubeMedia media=new YoutubeMedia();

                PlayerResponse.Formats format = formats[x];

                if (!format.getUrl().isEmpty()) {
                    media.setUrl(URLDecoder.decode(format.getUrl(), "utf-8"));
                }
                /*if (part.startsWith("s=") & useCipher) {
                    media.setDechiperedSig(CipherManager.dechiperSig(URLDecoder.decode(part.replace("s=", "")), response.getAssets().getJs()));
                }
                if (part.startsWith("size=")) {
                    media.setResSize(RegexUtils.matchGroup(regexResSize, part));
                }
                if (part.startsWith("bitrate=")) {
                    media.setBitrate(RegexUtils.matchGroup(regexBitrate, part));
                }
                if (part.startsWith("itag=")) {
                    media.setItag(RegexUtils.matchGroup(regexItag, part));
                }*/
                if (!format.getQualityLabel().isEmpty()) {
                    media.setResolution(format.getQualityLabel());
                }
                media.setIsVideoOnly(false);
                media.setIsAudioOnly(false);
                media.setIsMuxed(true);
                /*if (part.startsWith("type=")) {
                    String type=URLDecoder.decode(RegexUtils.matchGroup(regexType, part));
                    media.setExtension(RegexUtils.matchGroup(regexExtension, type));
                    media.setCodec(RegexUtils.matchGroup(regexCodec, type));
                    if (RegexUtils.matchGroup(regextypeVideoAudio, type).equals("audio")) {
                        media.setIsAudioOnly(true);
                        media.setIsVideoOnly(false);
                    } else {
                        media.setIsAudioOnly(false);media.setIsVideoOnly(true);
                    }

                }*/
                adaptiveMedia.add(media);
            }
        }
        else {
            //String[] rawUrls=adaptive_fmts[0].getAdaptiveFormats().split(",");

            for (int x=0;x < adaptive_fmts.length;x++) {

                YoutubeMedia media=new YoutubeMedia();
				PlayerResponse.AdaptiveFormats adaptiveFormats = adaptive_fmts[x];

				if (adaptiveFormats.getUrl() != null && !adaptiveFormats.getUrl().isEmpty()) {
					media.setUrl(URLDecoder.decode(adaptiveFormats.getUrl(), "utf-8"));
				}
				if (adaptiveFormats.getSignatureCipher() != null && !adaptiveFormats.getSignatureCipher().isEmpty() && useCipher) {
					try {
						media.setDechiperedSig(CipherManager.dechiperSig(URLDecoder.decode(adaptiveFormats.getSignatureCipher(), "utf-8"), response.getAssets().getJs()));
					}
					catch (Exception e){
						Log.e("Youtube", "Exception while getting js", e);
						media.setDechiperedSig("");
					}
				}
				if (adaptiveFormats.getSize() != null && !adaptiveFormats.getSize().isEmpty()) {
					media.setResSize(adaptiveFormats.getSize());
				}
				if (adaptiveFormats.getBitrate() != null && !adaptiveFormats.getBitrate().isEmpty()) {
					media.setBitrate(adaptiveFormats.getBitrate());
				}
				if (adaptiveFormats.getItag() != null && !adaptiveFormats.getItag().isEmpty()) {
					media.setItag(adaptiveFormats.getItag());
				}
				if (adaptiveFormats.getQualityLabel() != null && !adaptiveFormats.getQualityLabel().isEmpty()) {
					media.setResolution(adaptiveFormats.getQualityLabel());
				}
				if (adaptiveFormats.getMimeType() != null && !adaptiveFormats.getMimeType().isEmpty()) {
					String type=URLDecoder.decode(adaptiveFormats.getMimeType(), "utf-8");
					media.setExtension(RegexUtils.matchGroup(regexExtension, type));
					media.setCodec(RegexUtils.matchGroup(regexCodec, type));
					if (RegexUtils.matchGroup(regextypeVideoAudio, type).contains("audio")) {
						media.setIsAudioOnly(true);
						media.setIsVideoOnly(false);
					} else {
						media.setIsAudioOnly(false);
						media.setIsVideoOnly(true);
					}

				}
                adaptiveMedia.add(media);
            }
        }
        //adaptiveMedia =Utils.filterInvalidLinks(adaptiveMedia);
	}



	private void parseJson(String body) throws Exception {
		try {
			JsonParser parser=new JsonParser();

			Document document = Jsoup.parse(body);
			Elements scriptElements = document.getElementsByTag("script");
			boolean initialResponseFound = false;

			String initialResponse = "";
			List<DataNode> dataNodes;

			if (scriptElements.size() > 0){
				for (Element element : scriptElements){
					dataNodes = element.dataNodes();
					if (dataNodes.size() > 0){
						for (DataNode node : dataNodes){
							if (node.getWholeData().startsWith("var ytInitialPlayerResponse")){
								initialResponse = node.getWholeData();
								initialResponseFound = true;
								break;
							}
						}
					}
					if (initialResponseFound){
						break;
					}
				}
			}

			if (!initialResponse.isEmpty()){
				initialResponse = initialResponse.replace("var ytInitialPlayerResponse = ", "");
				initialResponse = initialResponse.substring(0, initialResponse.lastIndexOf(";"));

				JSONObject initialResponseObject = new JSONObject(initialResponse);

				playerResponse=new GsonBuilder().serializeNulls().create().fromJson(initialResponseObject.toString() ,PlayerResponse.class);
			}

			if (body != null && !body.isEmpty() && !body.equalsIgnoreCase("null")){
				response=new GsonBuilder().serializeNulls().create().fromJson(parser.parse(body),Response.class);
			}

			/*if (body != null && !body.isEmpty() && !body.equalsIgnoreCase("null")){
				response=new GsonBuilder().serializeNulls().create().fromJson(parser.parse(body),Response.class);
			}

			if (response != null && response.getArgs() != null && response.getArgs().getPlayerResponse() != null){
				playerResponse=new GsonBuilder().serializeNulls().create().fromJson(Html.fromHtml(response.getArgs().getPlayerResponse()).toString(),PlayerResponse.class);
			}
			else {
				playerResponse=new GsonBuilder().serializeNulls().create().fromJson(body,PlayerResponse.class);
			}*/

			if (playerResponse != null){
				YTVideoMeta=playerResponse.getVideoDetails();
				//LogUtils.log(response.getAssets().getJs());
				if (YTVideoMeta.getisLive()  ||  ( YTVideoMeta.getIsLiveContent()&& playerResponse.getStreamingData().getHlsManifestUrl()!=null)) {
					isLive=true;
				}
				useCipher=YTVideoMeta.getUseChiper();
				//useCipher = true;
				if(isLive)parseLiveUrls(playerResponse.getStreamingData());
			}
		}
		catch (Exception e){
			Log.e("Youtube", "Exception while getting json", e);

			Document document = Jsoup.parse(body);
			Elements scriptElements = document.getElementsByTag("script");
			boolean initialResponseFound = false;

			String initialResponse = "";
			List<DataNode> dataNodes;

			if (scriptElements.size() > 0){
				for (Element element : scriptElements){
					dataNodes = element.dataNodes();
					if (dataNodes.size() > 0){
						for (DataNode node : dataNodes){
							if (node.getWholeData().startsWith("var ytInitialPlayerResponse")){
								initialResponse = node.getWholeData();
								initialResponseFound = true;
								break;
							}
						}
					}
					if (initialResponseFound){
						break;
					}
				}
			}

			if (!initialResponse.isEmpty()){
				initialResponse = initialResponse.replace("var ytInitialPlayerResponse = ", "");
				initialResponse = initialResponse.substring(0, initialResponse.lastIndexOf(";"));

				JSONObject initialResponseObject = new JSONObject(initialResponse);

				playerResponse=new GsonBuilder().serializeNulls().create().fromJson(initialResponseObject.toString() ,PlayerResponse.class);
			}

			if (playerResponse != null){
				YTVideoMeta=playerResponse.getVideoDetails();
				//LogUtils.log(response.getAssets().getJs());
				if (YTVideoMeta.getisLive()  ||  ( YTVideoMeta.getIsLiveContent()&& playerResponse.getStreamingData().getHlsManifestUrl()!=null)) {
					isLive=true;
				}
				useCipher=YTVideoMeta.getUseChiper();
				//useCipher = true;
				if(isLive)parseLiveUrls(playerResponse.getStreamingData());
			}
		}
	}
	

	private void parseLiveUrls( PlayerResponse.StreamingData data) throws Exception {
		
		if(data.getHlsManifestUrl()==null){
			throw new ExtractorException("No link for hls video");
		}
		
		LogUtils.log(data.getHlsManifestUrl());
		String hlsPageSource=HTTPUtility.downloadPageSource(data.getHlsManifestUrl());

		String regexhlsLinks="(https://manifest.googlevideo.com/).*?((?=\\#)|\\z| )";
		List<String> Livelinks= RegexUtils.getAllMatches(regexhlsLinks, hlsPageSource);
		//List<String> linksInfo=re
		for (String s:Livelinks) {
			YoutubeMedia media=new YoutubeMedia();
			media.setUrl(s);
			media.setIsMuxed(true);
			muxedMedia.add(media);
			//LogUtils.log(s);
		}

	}

	public interface ExtractorListner {
		void onExtractionGoesWrong(ExtractorException e, String message, String data);
		void onExtractionDone(List<YoutubeMedia> adativeStream, List<YoutubeMedia> muxedStream, YoutubeMeta meta);
	}
}
