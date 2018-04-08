package com.github.chengheaven.book.bean;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * @author Heaven_Cheng Created on 2018/1/1.
 */
public class BookData implements Serializable {

    @SerializedName("count")
    private int count;
    @SerializedName("start")
    private int start;
    @SerializedName("total")
    private int total;
    @SerializedName("books")
    private List<BookBean> books;

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getStart() {
        return start;
    }

    public void setStart(int start) {
        this.start = start;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public List<BookBean> getBooks() {
        return books;
    }

    public void setBooks(List<BookBean> books) {
        this.books = books;
    }

    public static class BookBean implements Serializable {

        @SerializedName("rating")
        private RatingBean rating;
        @SerializedName("subtitle")
        private String subtitle;
        @SerializedName("pubdate")
        private String pubDate;
        @SerializedName("origin_title")
        private String originTitle;
        @SerializedName("image")
        private String image;
        @SerializedName("binding")
        private String binding;
        @SerializedName("catalog")
        private String catalog;
        @SerializedName("pages")
        private String pages;
        @SerializedName("images")
        private ImagesBean images;
        @SerializedName("alt")
        private String alt;
        @SerializedName("id")
        private String id;
        @SerializedName("publisher")
        private String publisher;
        @SerializedName("isbn10")
        private String isbn10;
        @SerializedName("isbn13")
        private String isbn13;
        @SerializedName("title")
        private String title;
        @SerializedName("url")
        private String url;
        @SerializedName("alt_title")
        private String altTitle;
        @SerializedName("author_intro")
        private String authorIntro;
        @SerializedName("summary")
        private String summary;
        @SerializedName("series")
        private SeriesBean series;
        @SerializedName("price")
        private String price;
        @SerializedName("ebook_url")
        private String bookUrl;
        @SerializedName("ebook_price")
        private String bookPrice;
        @SerializedName("author")
        private List<String> author;
        @SerializedName("tags")
        private List<TagsBean> tags;
        @SerializedName("translator")
        private List<String> translator;

        public RatingBean getRating() {
            return rating;
        }

        public void setRating(RatingBean rating) {
            this.rating = rating;
        }

        public String getSubtitle() {
            return subtitle;
        }

        public void setSubtitle(String subtitle) {
            this.subtitle = subtitle;
        }

        public String getPubDate() {
            return pubDate;
        }

        public void setPubDate(String pubDate) {
            this.pubDate = pubDate;
        }

        public String getOriginTitle() {
            return originTitle;
        }

        public void setOriginTitle(String originTitle) {
            this.originTitle = originTitle;
        }

        public String getImage() {
            return image;
        }

        public void setImage(String image) {
            this.image = image;
        }

        public String getBinding() {
            return binding;
        }

        public void setBinding(String binding) {
            this.binding = binding;
        }

        public String getCatalog() {
            return catalog;
        }

        public void setCatalog(String catalog) {
            this.catalog = catalog;
        }

        public String getPages() {
            return pages;
        }

        public void setPages(String pages) {
            this.pages = pages;
        }

        public ImagesBean getImages() {
            return images;
        }

        public void setImages(ImagesBean images) {
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

        public String getPublisher() {
            return publisher;
        }

        public void setPublisher(String publisher) {
            this.publisher = publisher;
        }

        public String getIsbn10() {
            return isbn10;
        }

        public void setIsbn10(String isbn10) {
            this.isbn10 = isbn10;
        }

        public String getIsbn13() {
            return isbn13;
        }

        public void setIsbn13(String isbn13) {
            this.isbn13 = isbn13;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getAltTitle() {
            return altTitle;
        }

        public void setAltTitle(String altTitle) {
            this.altTitle = altTitle;
        }

        public String getAuthorIntro() {
            return authorIntro;
        }

        public void setAuthorIntro(String authorIntro) {
            this.authorIntro = authorIntro;
        }

        public String getSummary() {
            return summary;
        }

        public void setSummary(String summary) {
            this.summary = summary;
        }

        public SeriesBean getSeries() {
            return series;
        }

        public void setSeries(SeriesBean series) {
            this.series = series;
        }

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getBookUrl() {
            return bookUrl;
        }

        public void setBookUrl(String bookUrl) {
            this.bookUrl = bookUrl;
        }

        public String getBookPrice() {
            return bookPrice;
        }

        public void setBookPrice(String bookPrice) {
            this.bookPrice = bookPrice;
        }

        public List<String> getAuthor() {
            return author;
        }

        public void setAuthor(List<String> author) {
            this.author = author;
        }

        public List<TagsBean> getTags() {
            return tags;
        }

        public void setTags(List<TagsBean> tags) {
            this.tags = tags;
        }

        public List<String> getTranslator() {
            return translator;
        }

        public void setTranslator(List<String> translator) {
            this.translator = translator;
        }

        public static class RatingBean implements Serializable {

            @SerializedName("max")
            private int max;
            @SerializedName("numRaters")
            private int numRaters;
            @SerializedName("average")
            private String average;
            @SerializedName("min")
            private int min;

            public int getMax() {
                return max;
            }

            public void setMax(int max) {
                this.max = max;
            }

            public int getNumRaters() {
                return numRaters;
            }

            public void setNumRaters(int numRaters) {
                this.numRaters = numRaters;
            }

            public String getAverage() {
                return average;
            }

            public void setAverage(String average) {
                this.average = average;
            }

            public int getMin() {
                return min;
            }

            public void setMin(int min) {
                this.min = min;
            }
        }

        public static class ImagesBean implements Serializable {

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

        public static class SeriesBean implements Serializable {

            @SerializedName("id")
            private String id;
            @SerializedName("title")
            private String title;

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }

        public static class TagsBean implements Serializable {

            @SerializedName("count")
            private int count;
            @SerializedName("name")
            private String name;
            @SerializedName("title")
            private String title;

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getTitle() {
                return title;
            }

            public void setTitle(String title) {
                this.title = title;
            }
        }
    }
}
