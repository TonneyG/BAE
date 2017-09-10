package com.wechat.message.response;

/**
 * @author gkl
 * 图片消息
 */
public class ImageMessage extends BaseMessage{
	//图片
	private Image Image;

	public Image getImage() {
		return Image;
	}

	public void setImage(Image Image) {
		this.Image = Image;
	}
}

/**
 * 图片model
 */
class Image{
	//媒体文件ID
	private String MediaId;
}
