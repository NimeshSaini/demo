package com.naveed.ytextractor.model;

public class PlayerResponse {
    private PlayabilityStatus playabilityStatus;
    private StreamingData streamingData;
    private YoutubeMeta videoDetails;

    public void setPlayabilityStatus(PlayabilityStatus playabilityStatus) {
        this.playabilityStatus = playabilityStatus;
    }

    public PlayabilityStatus getPlayabilityStatus() {
        return playabilityStatus;
    }

    public void setStreamingData(StreamingData streamingData) {
        this.streamingData = streamingData;
    }

    public StreamingData getStreamingData() {
        return streamingData;
    }

    public void setVideoDetails(YoutubeMeta videoDetails) {
        this.videoDetails = videoDetails;
    }

    public YoutubeMeta getVideoDetails() {
        return videoDetails;
    }


    public class StreamingData {

        private String hlsManifestUrl;
        private long expiresInSeconds;
        private Formats[] formats;
        private AdaptiveFormats[] adaptiveFormats;

        public void setExpiresInSeconds(long expiresInSeconds) {
            this.expiresInSeconds = expiresInSeconds;
        }

        public long getExpiresInSeconds() {
            return expiresInSeconds;
        }

        public void setHlsManifestUrl(String hlsManifestUrl) {
            this.hlsManifestUrl = hlsManifestUrl;
        }

        public String getHlsManifestUrl() {
            return hlsManifestUrl;
        }

        public Formats[] getFormats() {
            return formats;
        }

        public void setFormats(Formats[] formats) {
            this.formats = formats;
        }

        public AdaptiveFormats[] getadaptiveFormats() {
            return adaptiveFormats;
        }

        public void setadaptiveFormats(AdaptiveFormats[] adaptiveFormats) {
            this.adaptiveFormats = adaptiveFormats;
        }
    }

    public class PlayabilityStatus {
        private String status;
        private boolean playableInEmbed;

        public void setStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }

        public void setPlayableInEmbed(boolean playableInEmbed) {
            this.playableInEmbed = playableInEmbed;
        }

        public boolean isPlayableInEmbed() {
            return playableInEmbed;
        }
    }

    public class Formats {
        private String url;
        private String qualityLabel;
        private String signatureCipher;
        private String mimeType;

        public String getSignatureCipher() {
            return signatureCipher;
        }

        public void setSignatureCipher(String signatureCipher) {
            this.signatureCipher = signatureCipher;
        }

        public String getQualityLabel() {
            return qualityLabel;
        }

        public String getUrl() {
            return url;
        }

        public void setQualityLabel(String qualityLabel) {
            this.qualityLabel = qualityLabel;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }
    }

    public class AdaptiveFormats {
        private String url;
        private String qualityLabel;
        private String s;
        private String size;
        private String bitrate;
        private String itag;
        private String mimeType;
        private String codec;
        private String signatureCipher;

        public String getSignatureCipher() {
            return signatureCipher;
        }

        public void setSignatureCipher(String signatureCipher) {
            this.signatureCipher = signatureCipher;
        }

        public String getCodec() {
            return codec;
        }

        public void setCodec(String codec) {
            this.codec = codec;
        }

        public String getS() {
            return s;
        }

        public void setS(String s) {
            this.s = s;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public String getBitrate() {
            return bitrate;
        }

        public void setBitrate(String bitrate) {
            this.bitrate = bitrate;
        }

        public String getItag() {
            return itag;
        }

        public void setItag(String itag) {
            this.itag = itag;
        }

        public String getMimeType() {
            return mimeType;
        }

        public void setMimeType(String mimeType) {
            this.mimeType = mimeType;
        }

        public String getQualityLabel() {
            return qualityLabel;
        }

        public String getUrl() {
            return url;
        }

        public void setQualityLabel(String qualityLabel) {
            this.qualityLabel = qualityLabel;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}


