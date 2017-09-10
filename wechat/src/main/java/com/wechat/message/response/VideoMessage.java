package com.wechat.message.response;

/**
 * @author gkl
 * 视频消息
 */
public class VideoMessage extends BaseMessage{
	//视频
	private Video Video;

	public Video getVideo() {
		return Video;
	}

	public void setVideo(Video video) {
		Video = video;
	}
}

/**
 * 视频model
 */
class Video{
	//媒体文件ID
	private String MediaId;
	//缩略图的媒体ID
	private String ThumbMediaId;
	
	public String getMediaId() {
		return MediaId;
	}
	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
	public String getThumbMediaId() {
		return ThumbMediaId;
	}
	public void setThumbMediaId(String thumbMediaId) {
		ThumbMediaId = thumbMediaId;
	}
}