package com.wechat.message.response;

/**
 * @author gkl
 * 语音消息
 */
public class VoiceMessage extends BaseMessage {
	//语音
	private Voice Voice;

	public Voice getVoice() {
		return Voice;
	}

	public void setVoice(Voice voice) {
		Voice = voice;
	}
}

/**
 * 语音model
 */
class Voice{
	//媒体文件ID
	private String MediaId;

	public String getMediaId() {
		return MediaId;
	}

	public void setMediaId(String mediaId) {
		MediaId = mediaId;
	}
}