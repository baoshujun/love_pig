package com.lovepig.engine;

import java.util.ArrayList;

import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;

import com.lovepig.main.Application;
import com.lovepig.main.Configs;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.NewsDetailModel;
import com.lovepig.model.NewsGalleryModel;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;
import com.lovepig.widget.TlcyDialog.TlcyDialogListener;

public class NewsEngine extends BaseEngine {
	private static final int LIMIT_PUSHNEWS=20;
	private static final String LogTag = "NewsEngine";
	public static int NEWS_LIMIT_LENGTH = 20;
	private static String GET_NEWS = "news/list?";
	private static String GET_NEWS_DETAILS = "news/detail?newsId=";
	private static String PUSH_NEWS = "news/pushNews?";
	private static String VERSION_UPDATE = "dload/update";
		
	OnlineNewsManager manager;
	getNewsTask mGetNewsTask;
	getMoreNewsTask mGetMoreNewsTask;
	GetNewsDetailTask mGetNewsDetail;
	int RequestDataSize = 20;// 请求的数据条数
	// 获取评论引擎
//	private HttpEngine httpEngine = null;
	private int catId;
	private int newsId;

	public NewsEngine(OnlineNewsManager manager) {
		super(manager);
		this.manager = manager;
	}

	/**
	 * 获取最新新闻
	 * 
	 * @param catId
	 *            :分类id
	 * @param limit每次获取的长度
	 * @param maxId
	 *            ：上次获取新闻的做大id，第一次传0
	 */
	public void refreshNews(int catId, int limit, int maxId) {
		this.catId = catId;
		StringBuilder mStrBuilder = new StringBuilder("catId=");
		mStrBuilder.append(catId).append("&limit=").append(limit)
				.append("&maxId=").append(maxId);
		mGetNewsTask = new getNewsTask();
		mGetNewsTask.execute(mStrBuilder.toString());
	}

	/**
	 * 获取新闻详情
	 * 
	 * @param id
	 * @param type
	 */
	public void fetchNewsDetail(int id) {
		newsId = id;
		String params = GET_NEWS_DETAILS + id;
		mGetNewsDetail = new GetNewsDetailTask();
		mGetNewsDetail.execute(params);
	}

	/**
	 * 获取最新详情
	 * 
	 */
	class GetNewsDetailTask extends AsyncTask<String, Void, NewsDetailState> {
		boolean isStop;

		@Override
		protected NewsDetailState doInBackground(String... params) {
			String result = httpRequestThisThread(1, params[0], false);
			if (isStop) {
				return null;
			} else {
				LogInfo.LogOut("result:" + result);
				NewsDetailState rs = ParseHttp2(result, 0);
				return rs;
			}
		}

		@Override
		protected void onPostExecute(NewsDetailState result) {
			if (!isStop && result != null) {
				if (result.code!=null&&result.code.equals("hasnews")) {
					manager.showNewsDetails(result.newsDetail);
				} else if (result.code.equals("neterror")) {
					// 网络错误
					manager.ShowNewsError("网络不可用,请检查您的网络！");
				} else {
					// 服务器正常返回但没内容
					manager.ShowNewsError(result.code);
				}
			} else {
			}
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}

	/**
	 * 加载更多新闻
	 * 
	 * @param fontSize
	 * @param type
	 */
	public void moreNews(int catId, int limit, int maxId) {
		this.catId = catId;
		StringBuilder mStrBuilder = new StringBuilder("catId=");
		mStrBuilder.append(catId).append("&limit=").append(limit)
				.append("&maxId=").append(maxId);
		mGetMoreNewsTask = new getMoreNewsTask();
		mGetMoreNewsTask.execute(mStrBuilder.toString());
	}

	/**
	 * 停止网络
	 */
	public void StopTask() {
		if (mGetNewsTask != null) {
			mGetNewsTask.stop();
			mGetNewsTask = null;
		}
		if (mGetMoreNewsTask != null) {
			mGetMoreNewsTask.stop();
			mGetMoreNewsTask = null;
		}
		manager.CancelRefresh();
	}

	/**
	 * 获取最新新闻
	 * 
	 * @author DCH
	 * 
	 */
	class getNewsTask extends AsyncTask<String, Void, NewsState> {
		boolean isStop;

		@Override
		protected NewsState doInBackground(String... params) {
			String result = httpRequestThisThread(1, GET_NEWS + params[0],
					false);
			if (isStop) {
				return null;
			} else {
				LogInfo.LogOut("result:" + result);
				manager.dbEngine.deleteNewsByTypeID(catId);
				NewsState rs = ParseHttpNews(result, 0);
				if (rs!=null&&rs.code!=null&&rs.code.equals("hasnews") && !isStop) {
					manager.SetLatestNews(rs.newslist);
				}
				return rs;
			}
		}

		@Override
		protected void onPostExecute(NewsState result) {
			if (!isStop && result != null) {
				if (result.code!=null&&result.code.equals("hasnews")) {
					manager.SetLatestNews(result.newslist);
					manager.getNewsComplete(0);
					if (result.hasBtn != null) {
						// 有更多按钮
						manager.SetMoreBtn(true);
					} else {
						// 没有更多按钮
						manager.SetMoreBtn(false);
					}
				} else if (result.code!=null&&result.code.equals("neterror")) {
					// 网络错误
					manager.ShowNewsError("网络不可用,请检查您的网络！");
				} else {
					// 服务器正常返回但没内容
					manager.ShowNewsError(result.code);
				}
			} else {
				// 网络错误
				manager.ShowNewsError("网络不可用,请检查您的网络！");
			}
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}

	/**
	 * 解析新闻数据
	 * 
	 * @param result
	 * @param flag
	 * @return
	 */
	private NewsState ParseHttpNews(String result, int flag) {
		Json[] newsarray = null;
		NewsState newsState = new NewsState();
		if (result != null) {
			Json json = new Json(result);
			if (json.getString("status").equals("1")) {
				NewsModel news;
				ArrayList<NewsModel> topList = new ArrayList<NewsModel>();
				newsarray = json.getJsonArray("news");
				int L = newsarray.length;
				if (L > 0) {
					newsState.code = "hasnews";// 有新闻
				}
				if (L == OnlineNewsManager.DEFAULT_NEW_LENGTH) {
					newsState.hasBtn = "hasmorebtn";// 有更多按钮
				}
				for (int i = 0; i < newsarray.length; i++) {
					news = new NewsModel();
					news.paserJson(newsarray[i]);
					manager.dbEngine.saveNews(news, catId);
					if (news.top) {
						topList.add(news);
					} else {
						newsState.newslist.add(news);
					}
				}
				if (topList.size()>0) {
				    NewsModel model = topList.get(0);
				    model.top = true;
				    model.topNews = topList;
				    LogInfo.LogOut(LogTag, "topNews size:" + topList.size());
				    newsState.newslist.add(0, model);
                }
			} else {
				newsState.code = json.getString("errorCode");// 服务器正常返回，但没数据
			}
		} else {
			newsState.code = "neterror";// 网络异常
		}
		return newsState;
	}

	/**
	 * 加载更多
	 * 
	 */
	class getMoreNewsTask extends AsyncTask<String, Void, NewsState> {
		boolean isStop;

		@Override
		protected NewsState doInBackground(String... params) {
			String result = httpRequestThisThread(1, GET_NEWS + params[0],
					false);
			if (isStop) {
				return null;
			} else {
				return ParseHttpNews(result, 1);
			}
		}

		@Override
		protected void onPostExecute(NewsState result) {
			if (!isStop) {
				if (result.code!=null&&result.code.equals("hasnews")) {
					for (NewsModel news : result.newslist) {
						manager.onLoadoldMoreNews(news);
					}
					manager.getNewsComplete(1);
					// manager.ShowDetail();
					if (result.hasBtn != null) {
						// 有更多按钮
						manager.SetMoreBtn(true);
					} else {
						// 没有更多按钮
						manager.SetMoreBtn(false);
					}
				} else if (result.code!=null&&result.code.equals("neterror")) {
					// 网络错误
					manager.ShowNewsError2("网络不可用,请检查您的网络！");
				} else {
					// 服务器正常返回但没内容
					manager.ShowNewsError2(result.code);
					manager.SetMoreBtn(false);
				}

			}
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}

	}

	/**
	 * 解析新闻详情信息
	 * 
	 * @param result
	 * @param flag
	 * @return
	 */
	private NewsDetailState ParseHttp2(String result, int flag) {
		NewsDetailState newsDetailState = new NewsDetailState();
		if (result != null) {
			Json json = new Json(result);
			if (json.getInt("status") == 1) {
				String temp = json.getString("detail");
				if (temp != null) {
					newsDetailState.code = "hasnews";// 有新闻
				}
				NewsDetailModel model = new NewsDetailModel();
				model.paserJson(new Json(temp));
				model.id = newsId;
				newsDetailState.newsDetail = model;
			} else {
				newsDetailState.code = json.getString("errorCode");// 服务器正常返回，但没数据
			}
		} else {
			newsDetailState.code = "neterror";// 网络异常
		}
		return newsDetailState;
	}
	class NewsState {
		public ArrayList<NewsModel> newslist = new ArrayList<NewsModel>();// 新闻数据
		public String code;// hasnews 表示有新闻 neterror 表示网络异常
		public String hasBtn;// hasmore 表示有更多按钮
	}

	class NewsDetailState {
		public NewsDetailModel newsDetail = new NewsDetailModel();// 新闻数据
		public String code;// hasnews 表示有新闻 neterror 表示网络异常
		public String hasBtn;// hasmore 表示有更多按钮
	}
	/**
	 * 新闻分类
	 * @return
	 */
	public ArrayList<NewsGalleryModel> getNewsGalleryModels() {
		ArrayList<NewsGalleryModel> mNewsGallerModels = new ArrayList<NewsGalleryModel>();
		mNewsGallerModels.add(new NewsGalleryModel(1, 1, "头条"));
		mNewsGallerModels.add(new NewsGalleryModel(2, 0, "行业"));
		mNewsGallerModels.add(new NewsGalleryModel(3, 0, "企业"));
		mNewsGallerModels.add(new NewsGalleryModel(3, 0, "市场"));
		mNewsGallerModels.add(new NewsGalleryModel(5, 0, "会讯"));

		return mNewsGallerModels;
	}
	
	/**
	 * 新闻轮询
	 * 
	 * news/pushNews (新增)新闻推送 param: catId limit maxId time
	 */
	public void pushNews(int catId,int maxId,String time){
		StringBuilder mStrBuilder = new StringBuilder("catId=");
		mStrBuilder.append(catId).append("&limit=").append(LIMIT_PUSHNEWS)
				.append("&maxId=").append(maxId).append("&time=").append(System.currentTimeMillis());
		PushNewsTask mPushNewsTask = new PushNewsTask();
		mPushNewsTask.execute(mStrBuilder.toString());
		
	}
	/**
	 * 获取最新新闻
	 * 
	 * 
	 */
	class PushNewsTask extends AsyncTask<String, Void, NewsState> {
		boolean isStop;

		@Override
		protected NewsState doInBackground(String... params) {
//			String result = httpRequestThisThread(1, PUSH_NEWS + params[0],
//					false);
//			if (isStop) {
				return null;
//			} else {
//				LogInfo.LogOut("result:" + result);
//				manager.dbEngine.deleteNewsByTypeID(catId);
//				NewsState rs = ParseHttpNews(result, 0);
//				if (rs.code.equals("hasnews") && !isStop) {
//					manager.SetLatestNews(rs.newslist);
//				}
//				return rs;
//			}
		}

		@Override
		protected void onPostExecute(NewsState result) {
//			if (!isStop && result != null) {
//				if (result.code.equals("hasnews")) {
//					manager.SetLatestNews(result.newslist);
//					manager.getNewsComplete(0);
//					if (result.hasBtn != null) {
//						// 有更多按钮
//						manager.SetMoreBtn(true);
//					} else {
//						// 没有更多按钮
//						manager.SetMoreBtn(false);
//					}
//				} else if (result.code.equals("neterror")) {
//					// 网络错误
//					manager.ShowNewsError("网络不可用,请检查您的网络！");
//				} else {
//					// 服务器正常返回但没内容
//					manager.ShowNewsError(result.code);
//				}
//			} else {
//			}
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}

// public String updateVersion(){
//	String result= httpRequestThisThread(1, VERSION_UPDATE, false);
//	if (result!=null) {
//		Json json = new Json(result);
//		String versionNub=json.getString("version");
//		if (versionNub.compareTo(Configs.VERSION_NO)<0) {
//			return json.getString("url");
//		}
//	}
//	
//	return null;
// }
	
 /**
	 * 获取最新新闻
	 * 
	 * 
	 */
	class VersionUpdateTask extends AsyncTask<String, Void, String> {
		boolean isStop;

		@Override
		protected String doInBackground(String... params) {
			String result= httpRequestThisThread(1, VERSION_UPDATE, false);
			if (result!=null) {
				Json json = new Json(result);
				String versionNub=json.getString("version");
				LogInfo.LogOut("versionNub:"+versionNub+" v:"+Configs.VERSION_NO+versionNub.compareTo(Configs.VERSION_NO));
				if (versionNub.compareTo(Configs.VERSION_NO)>0) {
					return json.getString("url");
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(final String result) {
			if (result!=null) {
				manager.showAlert("有新版本，是否更新？", new TlcyDialogListener() {
					
					@Override
					public void onClick() {
						  Uri uri = Uri.parse(result);
                          Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                          Application.application.startActivity(intent);
						
					}
				}, null);
			}
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}
	
	public void updateVersion(){
		VersionUpdateTask mPushNewsTask = new VersionUpdateTask();
		mPushNewsTask.execute();
	}
}
