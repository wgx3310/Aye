package aye.util;

import android.content.Context;
import android.graphics.Typeface;
import android.support.annotation.NonNull;

import java.lang.reflect.Field;

/**
 * Created by reid on 2016/11/19.
 */

public class FontUtils {
    public static void resetTypefaceDefaultFont(@NonNull String name, @NonNull Context context, @NonNull String assetPath){
        resetTypefaceDefaultFont(name, Typeface.createFromAsset(context.getAssets(), assetPath));
    }

    public static void resetTypefaceDefaultFont(@NonNull String name, @NonNull Typeface typeface){
        modifyTypefaceField(name, typeface);
    }

    private static void modifyTypefaceField(@NonNull String fieldName, Object value) {
        try {
            Field defaultField = Typeface.class.getDeclaredField(fieldName);
            defaultField.setAccessible(true);
            defaultField.set(null, value);

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
