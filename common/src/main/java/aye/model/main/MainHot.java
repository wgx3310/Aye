package aye.model.main;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

/**
 * Created by reid on 2016/11/27.
 */

public class MainHot implements Serializable{
    private static final long serialVersionUID = 1L;

    public List<Recent> recent;

    public static class Recent implements Serializable{
        @SerializedName("news_id")
        public String id;
        public String url;
        public String thumbnail;
        public String title;

        @Override
        public String toString() {
            return "Recent{" +
                    "id='" + id + '\'' +
                    ", url='" + url + '\'' +
                    ", thumbnail='" + thumbnail + '\'' +
                    ", title='" + title + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "MainHot{" +
                "recent=" + recent +
                '}';
    }
}
