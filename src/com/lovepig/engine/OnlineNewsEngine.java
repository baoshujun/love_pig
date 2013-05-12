package com.lovepig.engine;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.os.AsyncTask;

import com.lovepig.main.R;
import com.lovepig.manager.OnlineNewsManager;
import com.lovepig.model.NewsCommentModel;
import com.lovepig.model.NewsGalleryModel;
import com.lovepig.model.NewsModel;
import com.lovepig.pivot.BaseEngine;
import com.lovepig.pivot.HttpEngine;
import com.lovepig.utils.Json;
import com.lovepig.utils.LogInfo;

public class OnlineNewsEngine extends BaseEngine {
	public static int NEWS_LIMIT_LENGTH = 20;
	private static String GET_NEWS = "news/list?";
	private static String GET_NEWS_DETAILS = "news/detail?newsId=2";
	OnlineNewsManager manager;
	getNewsTask mGetNewsTask;
	getMoreNewsTask mGetMoreNewsTask;
	getNewsDetailTask mGetNewsDetail;
	int RequestDataSize = 20;// 请求的数据条数
	// 获取评论引擎
	private HttpEngine httpEngine = null;
	private int catId;

	public OnlineNewsEngine(OnlineNewsManager manager) {
		super(manager);
		this.manager = manager;
	}

	/**
	 * 更新（获取）新闻类型
	 */
	public void updategrally() {
		Json j = new Json(0);
		new UpdateGalleryTask().execute(j.toString());
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
		this.catId=catId;
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
	public void fetchNewsDetail(int id, int type) {
		// Json j = new Json(0);
		// j.put("typeId", type);
		// j.put("flag", "0");
		// j.put("id", id);
		// j.put("size", RequestDataSize);
		mGetNewsDetail = new getNewsDetailTask();
		mGetNewsDetail.execute();
	}

	/**
	 * 获取最新详情
	 * 
	 * @author DCH
	 * 
	 */
	class getNewsDetailTask extends AsyncTask<String, Void, NewsState> {
		boolean isStop;

		@Override
		protected NewsState doInBackground(String... params) {
			String result = httpRequestThisThread(1, GET_NEWS_DETAILS);
			if (isStop) {
				return null;
			} else {
				LogInfo.LogOut("result:" + result);
				NewsState rs = ParseHttp1(result, 0);
				// if (rs.code.equals("hasnews") && !isStop) {
				// manager.SaveNews(rs.newslist);
				// }
				if (rs.code.equals("hasnews") && !isStop) {
					// manager.SetLatestNews(rs.newslist);
				}
				return rs;
			}
		}

		@Override
		protected void onPostExecute(NewsState result) {
			if (!isStop && result != null) {
				if (result.code.equals("hasnews")) {
					// manager.SetLatestNews(result.newslist);
					// // manager.SaveNews();
					// manager.getNewsComplete(0);
					// manager.ShowDetail();
					// if (result.hasBtn != null) {
					// // 有更多按钮
					// manager.SetMoreBtn(true);
					// } else {
					// // 没有更多按钮
					// manager.SetMoreBtn(false);
					// }
					manager.showNewsDetails(result.newslist);
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
	 * @param id
	 * @param type
	 */
	public void moreNews(int catId, int limit, int maxId) {
		this.catId=catId;
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
	 * 更新新闻类型
	 * 
	 * @author DCH
	 * 
	 */
	class UpdateGalleryTask extends
			AsyncTask<String, Void, ArrayList<NewsGalleryModel>> {

		@Override
		protected ArrayList<NewsGalleryModel> doInBackground(String... params) {
			return getGalleryItem(httpRequestThisThread(1, ""));
		}

		@Override
		protected void onPostExecute(ArrayList<NewsGalleryModel> result) {
			manager.getGrally(result);
		}
	}

	/**
	 * 解析新闻类型列表
	 */
	private ArrayList<NewsGalleryModel> getGalleryItem(String result) {
		ArrayList<NewsGalleryModel> gallery = null;
		int count = 0;
		if (result != null) {
			Json json = new Json(result);
			if (json.getString("status").equals("1")) {
				NewsGalleryModel gModel;
				Json[] galleryarray = json.getJsonArray("news");
				if (galleryarray == null || galleryarray.length == 0) {
					return gallery;
				}
				gallery = new ArrayList<NewsGalleryModel>();
				for (int i = 0; i < galleryarray.length; i++) {
					gModel = new NewsGalleryModel();
					gModel.id = galleryarray[i].getInt("id");
					gModel.checked = galleryarray[i].getInt("checked");
					gModel.indexNum = galleryarray[i].getInt("indexNum");
					gModel.name = galleryarray[i].getString("name");
					gallery.add(gModel);

				}
				Collections.sort(gallery, new Comparator<NewsGalleryModel>() {
					@Override
					public int compare(NewsGalleryModel object1,
							NewsGalleryModel object2) {
						if (object1.indexNum > object2.indexNum) {
							return 1;
						}
						return -1;
					}
				});
			}
		}
		return gallery;
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
			String result = httpRequestThisThread(1, GET_NEWS + params[0]);
			if (isStop) {
				return null;
			} else {
				LogInfo.LogOut("result:" + result);
				manager.dbEngine.deleteNewsByTypeID(catId);
				NewsState rs = ParseHttp1(result, 0);
				if (rs.code.equals("hasnews") && !isStop) {
					manager.SaveNews(rs.newslist);
				}
				if (rs.code.equals("hasnews") && !isStop) {
					manager.SetLatestNews(rs.newslist);
				}
				return rs;
			}
		}

		@Override
		protected void onPostExecute(NewsState result) {
			if (!isStop && result != null) {
				if (result.code.equals("hasnews")) {
					manager.SetLatestNews(result.newslist);
					// manager.SaveNews();
					manager.getNewsComplete(0);
					//manager.ShowDetail();
					if (result.hasBtn != null) {
						// 有更多按钮
						manager.SetMoreBtn(true);
					} else {
						// 没有更多按钮
						manager.SetMoreBtn(false);
					}
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
	 * 加载更多
	 * 
	 * @author DCH
	 * 
	 */
	class getMoreNewsTask extends AsyncTask<String, Void, NewsState> {
		boolean isStop;

		@Override
		protected NewsState doInBackground(String... params) {
			 String result = httpRequestThisThread(1, GET_NEWS +
			 params[0]);
			 if (isStop) {
			    return null;
			} else {
				return ParseHttp1(result, 1);
			}
		}

		@Override
		protected void onPostExecute(NewsState result) {
			if (!isStop) {
				if (result.code.equals("hasnews")) {
					for (NewsModel news : result.newslist) {
						manager.onLoadoldMoreNews(news);
					}
					manager.getNewsComplete(1);
//					manager.ShowDetail();
					if (result.hasBtn != null) {
						// 有更多按钮
						manager.SetMoreBtn(true);
					} else {
						// 没有更多按钮
						manager.SetMoreBtn(false);
					}
				} else if (result.code.equals("neterror")) {
					// 网络错误
					manager.ShowNewsError("网络不可用,请检查您的网络！");
				} else {
					// 服务器正常返回但没内容
					manager.ShowNewsError(result.code);
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
	 * 解析新闻数据
	 * 
	 * @param result
	 * @param flag
	 * @return
	 */
	private NewsState ParseHttp1(String result, int flag) {
		Json[] newsarray = null;
		NewsState newsState = new NewsState();
		if (result != null) {
			Json json = new Json(result);
			if (json.getString("status").equals("1")) {
				NewsModel news;
				ArrayList<NewsModel> topList=new ArrayList<NewsModel>();
				newsarray = json.getJsonArray("news");
				int L = newsarray.length;
				if (L > 0) {
					newsState.code = "hasnews";// 有新闻
				}
				if (L == OnlineNewsManager.DEFAULT_NEW_LENGTH) {
					newsState.hasBtn = "hasmorebtn";// 有更多按钮
				}
				for (int i = 0; i < newsarray.length; i++) {
					LogInfo.LogOut("news = " + newsarray.length);
					news = new NewsModel();
					news.paserJson(newsarray[i]);
					manager.dbEngine.saveNews(news, catId);
                   if (news.top==1) {
                	   topList.add(news);
				   }else{
					   newsState.newslist.add(news);
				   }

				  }
				NewsModel model=new NewsModel();
				model.top=1;
				model.topNews=topList;
				newsState.newslist.addAll(0, topList);
			} else {
				newsState.code = json.getString("msg");// 服务器正常返回，但没数据
			}
		} else {
			newsState.code = "neterror";// 网络异常
		}
		return newsState;
	}

	class getNewsTask1 extends AsyncTask<String, Void, ArrayList<NewsModel>> {
		boolean isStop = false;

		@Override
		protected ArrayList<NewsModel> doInBackground(String... params) {
			// String result = httpRequestThisThread(1, Configs.getNewsAction +
			// params[0]);
			// if (isStop) {
			return null;
			// } else {
			// return ParseHttp(result, 0);
			// }
		}

		@Override
		protected void onPostExecute(ArrayList<NewsModel> lastnews) {
			if (!isStop) {
				manager.onLaterNews(lastnews);
			}
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}

	private ArrayList<NewsModel> ParseHttp(String response, int type) {
		ArrayList<NewsModel> lastnews = null;
		Json[] newsarray = null;
		if (response != null) {
			Json json = new Json(response);
			if (json.getString("status").equals("1")) {
				lastnews = new ArrayList<NewsModel>();
				NewsModel news;
				newsarray = json.getJsonArray("news");
				for (int i = 0; i < newsarray.length; i++) {
					// LogInfo.LogOut("news = " + newsarray.length);
					// news = new NewsModel();
					// news.id = newsarray[i].getInt("id");
					// news.createTime = newsarray[i].getString("createTime");
					// news.title =
					// newsarray[i].getString("title").replace("\r",
					// "").replace("\n", "");
					// news.summary = newsarray[i].getString("intro");
					// news.details = newsarray[i].getString("content");
					// news.commentNum = newsarray[i].getInt("commentSize");
					// news.isTop = newsarray[i].getInt("isTop") == 1;
					// if (news.summary != null) {// 简介段首自动缩进
					// news.summary = "        " + news.summary;
					// news.summary = news.summary.replaceAll("\n",
					// "\n        ");
					// }
					// // if (news.details != null) {// 所有段首自动缩进
					// // news.details = "        " + news.details;
					// // news.details = news.details.replaceAll("\n",
					// // "\n        ");
					// // }
					// if (newsarray[i].has("publisher")) {
					// news.editor = newsarray[i].getString("publisher");
					// }
					// if (newsarray[i].has("picUrl")) {
					// news.iconPath = newsarray[i].getString("picUrl");
					// }
					// if (newsarray[i].has("picInfo")) {
					// news.picintro = newsarray[i].getString("picInfo");
					// }
					// if (newsarray[i].has("bigPic")) {
					// news.imgPath = newsarray[i].getString("bigPic");
					// }
					// if (newsarray[i].has("commentSize")) {
					// news.commentSize = newsarray[i].getInt("commentSize");
					// }
					// lastnews.add(news);
					// if (type == 1) {// 更多
					// manager.onLoadoldMoreNews(news);
					// }
					// LogInfo.LogOut("OnlineNewsEngine", "name:" + news.id);
				}
			} else {

			}
		}
		return lastnews;
	}

	class getMoreNewsTask1 extends
			AsyncTask<String, Void, ArrayList<NewsModel>> {
		boolean isStop = false;

		@Override
		protected ArrayList<NewsModel> doInBackground(String... params) {
			// String result = httpRequestThisThread(1, Configs.getNewsAction +
			// params[0]);
			// if (isStop) {
			// return null;
			// } else {
			// return ParseHttp(result, 1);
			// }
			return null;
		}

		@Override
		protected void onPostExecute(ArrayList<NewsModel> lastnews) {
			if (!isStop) {
				if (lastnews != null && lastnews.size() != RequestDataSize) {
					manager.onOldNewsNoMore(lastnews);
				} else {
					manager.onOldNews(lastnews);
				}
			}
		}

		public void stop() {
			isStop = true;
			cancel(isStop);
		}
	}

	class NewsState {
		public ArrayList<NewsModel> newslist = new ArrayList<NewsModel>();// 新闻数据
		public String code;// hasnews 表示有新闻 neterror 表示网络异常
		public String hasBtn;// hasmore 表示有更多按钮
	}

	public void sentNewsComments(NewsCommentModel newsCommentModel) {
		if (httpEngine != null) {
			httpEngine.cancelRequest();
		}
		// 判断一下网络是否可用

		httpEngine = new HttpEngine(manager);
		String params = newsCommentModel.updateCommentParams(0).toString();
		httpEngine.setListener(new HttpEngine.HttpEngineListener() {

			@Override
			public void onPreHttp() {
				manager.showLoading(
						manager.context.getString(R.string.loading),
						new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								httpEngine.cancelRequest();
							}
						});
			}

			@Override
			public void onPostHttp(String result) {
				manager.dismissLoading();
				if (!result.equals("ok")) {
					if (result.equals("nologin")) {
					} else {
						manager.showAlert(result);
					}

				}
			}

			@Override
			public String onParseHttp(String response) {
				String msg = null;
				Json json = new Json(response);
				switch (json.getInt("result")) {
				case 0:// 失败

					return "发表评论失败";
				case 1:// 成功
					manager.sendMessage(manager.obtainMessage(
							OnlineNewsManager.STATE_SENT_COMMENT_SUC,
							json.getString("time")));
					return "ok";

				case 2:// 审核中
					return "发表评论成功,审核中.....";
				default:
					break;
				}
				return msg;
			}

		});
		// httpEngine.httpRequestNewThread(1, Configs.sentNewsCommentsAction +
		// params);

	}

	public void getNewsComments(int firstNum, int newSId) {
		if (httpEngine != null) {
			httpEngine.cancelRequest();
		}
		// 判断一下网络是否可用

		httpEngine = new HttpEngine(manager);
		Json json = new Json(0);
		json.put("newsId", newSId);
		json.put("firstSize", firstNum);
		json.put("maxSize", 20);
		String params = json.toString();
		httpEngine.setListener(new HttpEngine.HttpEngineListener() {

			@Override
			public void onPreHttp() {
				manager.showLoading(
						manager.context.getString(R.string.loading),
						new OnCancelListener() {
							@Override
							public void onCancel(DialogInterface dialog) {
								httpEngine.cancelRequest();
							}
						});
			}

			@Override
			public void onPostHttp(String result) {
				manager.dismissLoading();
				if (!result.equals("ok")) {
					if (result.equals("nologin")) {
					} else {
						manager.showAlert(result);
					}

				}
			}

			@Override
			public String onParseHttp(String response) {
				String msg = null;
				Json json = new Json(response);
				ArrayList<NewsCommentModel> datas = new ArrayList<NewsCommentModel>();
				switch (json.getInt("result")) {
				case 0:// 失败
					return "获取评论失败";
				case 1:// 成功
					int count = json.getInt("count");
					Json[] jsonArray = json.getJsonArray("comments");
					if (jsonArray.length > 0) {
						for (int i = 0; i < jsonArray.length; i++) {
							datas.add(new NewsCommentModel(count)
									.parserJsonForComments(jsonArray[i]));

						}
					}
					manager.sendMessage(manager.obtainMessage(
							OnlineNewsManager.STATE_SENT_COMMENT_LIST, count,
							0, datas));
					return "ok";
				default:
					break;
				}
				return msg;
			}

		});
		// httpEngine.httpRequestNewThread(1, Configs.getNewsComment + params);

	}

	public ArrayList<NewsGalleryModel> getNewsGalleryModels() {
		ArrayList<NewsGalleryModel> mNewsGallerModels = new ArrayList<NewsGalleryModel>();
		mNewsGallerModels.add(new NewsGalleryModel(1, 1, "头条"));
		mNewsGallerModels.add(new NewsGalleryModel(2, 0, "行业"));
		mNewsGallerModels.add(new NewsGalleryModel(3, 0, "企业"));
		mNewsGallerModels.add(new NewsGalleryModel(3, 0, "市场"));
		mNewsGallerModels.add(new NewsGalleryModel(5, 0, "会讯"));

		return mNewsGallerModels;
	}

}
