package com.github.chengheaven.book.data;

import com.github.chengheaven.book.bean.BookData;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;
import rx.Observable;

/**
 * @author Heaven・Cheng Created on 17/4/18.
 */

public interface ApiService {

    /**
     * 根据tag获取图书
     *
     * @param tag   搜索关键字
     * @param count 一次请求的数目 最多100
     */

    @GET("v2/book/search")
//    Call<ResponseBody> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);
    Observable<BookData> getBook(@Query("tag") String tag, @Query("start") int start, @Query("count") int count);

    @GET("v2/book/{id}")
//    Call<ResponseBody> getBookDetail(@Path("id") String id);
    Observable<BookData.BookBean> getBookDetail(@Path("id") String id);

    @GET("search/query/{content}/category/{type}/count/{per}/page/{page}")
    Call<ResponseBody> getSearchData(@Path("content") String content, @Path("type") String type, @Path("per_page") int per, @Path("page") int page);
}
