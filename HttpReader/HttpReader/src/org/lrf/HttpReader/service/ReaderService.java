package org.lrf.HttpReader.service;

public interface ReaderService {
	/**
	 * 
	 * @param listUrl 新闻列表的地址
	 * @param listWrapperId 新闻列表页面的HTML标签Id名 (id或class 其中一项必填，另一项填null)
	 * @param detailWrapperId 新闻列表详情页面的HTML标签Id名(id或class 其中一项必填，另一项填null)
	 * @param listWrapperClass 新闻列表页面的HTML标签class名(id或class 其中一项必填，另一项填null)
	 * @param detailWrapperClass 新闻列表详情页面的HTML标签class名(id或class 其中一项必填，另一项填null)
	 * @param charset 编码格式  默认gb2312
	 */
	public void saveTodayHtml(
			String listUrl,
			String listWrapperId,
			String detailWrapperId,
			String listWrapperClass,
			String detailWrapperClass,
			String charset);
}
