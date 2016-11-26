package aye.model.main;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by reid on 2016/11/26.
 */

public class MainDaily implements Serializable{
    private static final long serialVersionUID = 1L;

    public String date;
    public List<Story> stories;
    public List<Story> top_stories;

    public static class Story implements Serializable{
        public int id;
        public int type;
        @SerializedName("ga_prefix")
        public String prefix;
        public String title;
        public String image;
        public List<String> images;

        @Override
        public String toString() {
            return "Story{" +
                    "id=" + id +
                    ", type=" + type +
                    ", prefix='" + prefix + '\'' +
                    ", title='" + title + '\'' +
                    ", image='" + image + '\'' +
                    ", images=" + images +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MainDaily{" +
                "date='" + date + '\'' +
                ", stories=" + stories +
                ", top_stories=" + top_stories +
                '}';
    }
}
