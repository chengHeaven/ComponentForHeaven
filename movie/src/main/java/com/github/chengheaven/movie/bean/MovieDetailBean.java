package com.github.chengheaven.movie.bean;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/**
 * @author Heaven_Cheng Created on 2017/12/28.
 */
public class MovieDetailBean {

    @SerializedName("rating")
    private RatingBean rating;
    @SerializedName("reviews_count")
    private int reviews_count;
    @SerializedName("wish_count")
    private int wish_count;
    @SerializedName("douban_site")
    private String douban_site;
    @SerializedName("year")
    private String year;
    @SerializedName("images")
    private AvatarsBean images;
    @SerializedName("alt")
    private String alt;
    @SerializedName("id")
    private String id;
    @SerializedName("mobile_url")
    private String mobile_url;
    @SerializedName("title")
    private String title;
    @SerializedName("do_count")
    private Object do_count;
    @SerializedName("share_url")
    private String share_url;
    @SerializedName("seasons_count")
    private Object seasons_count;
    @SerializedName("schedule_url")
    private String schedule_url;
    @SerializedName("episodes_count")
    private Object episodes_count;
    @SerializedName("collect_count")
    private int collect_count;
    @SerializedName("current_season")
    private Object current_season;
    @SerializedName("original_title")
    private String original_title;
    @SerializedName("summary")
    private String summary;
    @SerializedName("subtype")
    private String subtype;
    @SerializedName("comments_count")
    private int comments_count;
    @SerializedName("ratings_count")
    private int ratings_count;
    @SerializedName("countries")
    private List<String> countries;
    @SerializedName("genres")
    private List<String> genres;
    @SerializedName("casts")
    private List<CastsBean> casts;
    @SerializedName("directors")
    private List<CastsBean> directors;
    @SerializedName("aka")
    private List<String> aka;

    public RatingBean getRating() {
        return rating;
    }

    public void setRating(RatingBean rating) {
        this.rating = rating;
    }

    public int getReviews_count() {
        return reviews_count;
    }

    public void setReviews_count(int reviews_count) {
        this.reviews_count = reviews_count;
    }

    public int getWish_count() {
        return wish_count;
    }

    public void setWish_count(int wish_count) {
        this.wish_count = wish_count;
    }

    public String getDouban_site() {
        return douban_site;
    }

    public void setDouban_site(String douban_site) {
        this.douban_site = douban_site;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public AvatarsBean getImages() {
        return images;
    }

    public void setImages(AvatarsBean images) {
        this.images = images;
    }

    public String getAlt() {
        return alt;
    }

    public void setAlt(String alt) {
        this.alt = alt;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMobile_url() {
        return mobile_url;
    }

    public void setMobile_url(String mobile_url) {
        this.mobile_url = mobile_url;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Object getDo_count() {
        return do_count;
    }

    public void setDo_count(Object do_count) {
        this.do_count = do_count;
    }

    public String getShare_url() {
        return share_url;
    }

    public void setShare_url(String share_url) {
        this.share_url = share_url;
    }

    public Object getSeasons_count() {
        return seasons_count;
    }

    public void setSeasons_count(Object seasons_count) {
        this.seasons_count = seasons_count;
    }

    public String getSchedule_url() {
        return schedule_url;
    }

    public void setSchedule_url(String schedule_url) {
        this.schedule_url = schedule_url;
    }

    public Object getEpisodes_count() {
        return episodes_count;
    }

    public void setEpisodes_count(Object episodes_count) {
        this.episodes_count = episodes_count;
    }

    public int getCollect_count() {
        return collect_count;
    }

    public void setCollect_count(int collect_count) {
        this.collect_count = collect_count;
    }

    public Object getCurrent_season() {
        return current_season;
    }

    public void setCurrent_season(Object current_season) {
        this.current_season = current_season;
    }

    public String getOriginal_title() {
        return original_title;
    }

    public void setOriginal_title(String original_title) {
        this.original_title = original_title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getSubtype() {
        return subtype;
    }

    public void setSubtype(String subtype) {
        this.subtype = subtype;
    }

    public int getComments_count() {
        return comments_count;
    }

    public void setComments_count(int comments_count) {
        this.comments_count = comments_count;
    }

    public int getRatings_count() {
        return ratings_count;
    }

    public void setRatings_count(int ratings_count) {
        this.ratings_count = ratings_count;
    }

    public List<String> getCountries() {
        return countries;
    }

    public void setCountries(List<String> countries) {
        this.countries = countries;
    }

    public List<String> getGenres() {
        return genres;
    }

    public void setGenres(List<String> genres) {
        this.genres = genres;
    }

    public List<CastsBean> getCasts() {
        return casts;
    }

    public void setCasts(List<CastsBean> casts) {
        this.casts = casts;
    }

    public List<CastsBean> getDirectors() {
        return directors;
    }

    public void setDirectors(List<CastsBean> directors) {
        this.directors = directors;
    }

    public List<String> getAka() {
        return aka;
    }

    public void setAka(List<String> aka) {
        this.aka = aka;
    }

    public static class RatingBean {
        /**
         * max : 10
         * average : 9.6
         * stars : 50
         * min : 0
         */

        @SerializedName("max")
        private int max;
        @SerializedName("average")
        private double average;
        @SerializedName("stars")
        private String stars;
        @SerializedName("min")
        private int min;

        public int getMax() {
            return max;
        }

        public void setMax(int max) {
            this.max = max;
        }

        public double getAverage() {
            return average;
        }

        public void setAverage(double average) {
            this.average = average;
        }

        public String getStars() {
            return stars;
        }

        public void setStars(String stars) {
            this.stars = stars;
        }

        public int getMin() {
            return min;
        }

        public void setMin(int min) {
            this.min = min;
        }
    }

    public static class AvatarsBean {
        /**
         * small : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
         * large : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
         * medium : https://img3.doubanio.com/view/photo/s_ratio_poster/public/p480747492.webp
         */

        @SerializedName("small")
        private String small;
        @SerializedName("large")
        private String large;
        @SerializedName("medium")
        private String medium;

        public String getSmall() {
            return small;
        }

        public void setSmall(String small) {
            this.small = small;
        }

        public String getLarge() {
            return large;
        }

        public void setLarge(String large) {
            this.large = large;
        }

        public String getMedium() {
            return medium;
        }

        public void setMedium(String medium) {
            this.medium = medium;
        }
    }

    public static class CastsBean {
        /**
         * alt : https://movie.douban.com/celebrity/1054521/
         * avatars : {"small":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","large":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp","medium":"https://img3.doubanio.com/view/celebrity/s_ratio_celebrity/public/p17525.webp"}
         * name : 蒂姆·罗宾斯
         * id : 1054521
         */

        @SerializedName("alt")
        private String alt;
        @SerializedName("avatars")
        private AvatarsBean avatars;
        @SerializedName("name")
        private String name;
        @SerializedName("id")
        private String id;

        public String getAlt() {
            return alt;
        }

        public void setAlt(String alt) {
            this.alt = alt;
        }

        public AvatarsBean getAvatars() {
            return avatars;
        }

        public void setAvatars(AvatarsBean avatars) {
            this.avatars = avatars;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }
    }
}
