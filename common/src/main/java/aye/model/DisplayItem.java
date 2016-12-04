package aye.model;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Created by reid on 2016/11/26.
 */

public class DisplayItem<T> implements Serializable {
    private static final long serialVersionUID = 1L;

    public String id;
    public String title;
    @SerializedName("sub_title")
    public String subTitle;
    public String desc;
    public String extra;

    public UI ui;
    public Target target;
    public Hint hint;
    public ImageGroup images;

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static class UI extends HashMap<String, String> implements Serializable {
        private static final long serialVersionUID = 1L;

        public String name() {
            return get("name");
        }

        public int id() {
            return getInt(get("id"), -1);
        }

        public int rows() {
            return getInt(get("rows"), 1);
        }

        public float ratio() {
            return getFloat(get("ratio"), 1.0f);
        }

        public boolean divider() {
            return getBoolean(get("divider"), false);
        }

    }

    public static class Hint extends LinkedHashMap<String, String> implements Serializable {
        private static final long serialVersionUID = 1L;

        public String left() {
            return get("left");
        }

        public String center() {
            return get("center");
        }

        public String right() {
            return get("right");
        }

        public String toString() {
            return "hint left:" + left() + " center:" + center() + " right:" + right();
        }
    }

    public static class Image implements Serializable {
        private static final long serialVersionUID = 1L;

        public String url;
        public String color;
        public boolean round;
        public List<String> urls;
    }

    public static class ImageGroup extends HashMap<String, Image> implements Serializable {
        private static final long serialVersionUID = 1L;

        public Image back() {
            return get("back");
        }

        public Image icon() {
            return get("icon");
        }

        public Image text() {
            return get("text");
        }

        public Image thumbnail() {
            return get("thumbnail");
        }

        public Image poster() {
            return get("poster");
        }

        public Image normal() {
            return get("normal");
        }

        public Image pressed() {
            return get("pressed");
        }

        public Image left_top_corner() {
            return get("left_top_corner");
        }

        public Image right_top_corner() {
            return get("right_top_corner");
        }
    }

    public static class Target implements Serializable {
        private static final long serialVersionUID = 1L;

        public String entity;
        public Params params;
        public String url;

        public Target() {
            params = new Params();
        }

        public String toString() {
            return "url: " + url + " entity:" + entity + " params:" + params;
        }

        public static class Params extends HashMap<String, Object> implements Serializable {
            private static final long serialVersionUID = 1L;

            public String getString(Object obj) {
                try {
                    if (obj != null) {
                        if (obj instanceof String) return (String) obj;
                        else return obj.toString();
                    }
                } catch (Exception ne) {
                    Log.d("DisplayItem", ne.getMessage());
                }
                return null;
            }

            public String getString(String key) {
                try {
                    if (key != null) {
                        return (String) get(key);
                    }
                } catch (Exception ne) {
                    Log.d("DisplayItem", ne.getMessage());
                }
                return null;
            }
        }
    }


    public static int getInt(String str, int def) {
        if (TextUtils.isEmpty(str)) {
            return def;
        }

        int res = def;
        try {
            res = Integer.parseInt(str);
        } catch (Exception ne) {
        }
        return res;
    }

    public static float getFloat(String str, float def) {
        if (TextUtils.isEmpty(str)) {
            return def;
        }

        float res = def;
        try {
            res = Float.parseFloat(str);
        } catch (Exception ne) {
        }
        return res;
    }

    public static long getLong(String str, long def) {
        if (TextUtils.isEmpty(str)) {
            return def;
        }

        long res = def;
        try {
            res = Long.parseLong(str);
        } catch (Exception ne) {
        }
        return res;
    }

    public static boolean getBoolean(String str, boolean def) {
        if (TextUtils.isEmpty(str)) {
            return def;
        }

        boolean res = def;
        try {
            res = "1".equalsIgnoreCase(str) || "true".equalsIgnoreCase(str);
        } catch (Exception ne) {
        }
        return res;
    }
}
