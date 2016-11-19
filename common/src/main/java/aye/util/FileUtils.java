package aye.util;

import android.database.Cursor;
import android.net.Uri;
import android.os.StatFs;
import android.provider.MediaStore;
import android.text.TextUtils;

import java.io.File;

import aye.content.Contexts;

/**
 * Created by reid on 2016/11/19.
 */

public class FileUtils {
    private static final String TAG = FileUtils.class.getSimpleName();

    /**
     * 获取指定路径下可用空间
     * @param dir 指定路径
     * @return 可用空间
     */
    public static long getAvailableBytes(String dir) {
        if (TextUtils.isEmpty(dir)) return 0;

        try {
            StatFs stat = new StatFs(dir);
            return ((long) stat.getAvailableBlocks() * (long) stat.getBlockSize());
        } catch (RuntimeException ex) {
            return 0;
        }
    }

    /**
     * 判断文件是否存在
     */
    public static boolean fileExists(String url){
        if(!TextUtils.isEmpty(url)){
            Uri uri = Uri.parse(url);
            String filename = null;
            if(!TextUtils.isEmpty(uri.getScheme()) &&
                    uri.getScheme().equals("content")){
                filename = getRealFilePathFromContentUri(uri);
            }else{
                filename = url.replace("file://", "");
            }
            if(!TextUtils.isEmpty(filename)){
                File file = new File(filename);
                return file.exists();
            }
        }
        return false;
    }

    /**
     * 根据ContentUri获取真实文件路径
     */
    private static String getRealFilePathFromContentUri(Uri contentUri) {
        try{
            String[] columns = new String[]{MediaStore.Video.Media.DATA};
            Cursor cursor = Contexts.getContext().getContentResolver().query(contentUri, columns, null, null, null);
            if (cursor == null) {
                return null;
            }
            int index = cursor.getColumnIndexOrThrow(MediaStore.Video.Media.DATA);
            cursor.moveToFirst();
            String result = cursor.getString(index);
            cursor.close();
            return result;
        }catch (Throwable t) {
            return null;
        }
    }

    /**
     * 刪除指定目錄下所有文件，包含該路径
     */
    public static void delDir(File dir) {
        try {
            if(dir != null) {
                delAllFiles(dir);
                dir.delete();
            }
        } catch (Throwable t) {
            Logger.e(TAG, "delete dir error:　" + t.getMessage());
        }
    }

    /**
     * 刪除指定目錄下所有文件，包含該路径
     */
    public static void delDir(String dir) {
        delDir(new File(dir));
    }

    /**
     * 刪除指定目錄下所有文件，不包含該路径
     */
    public static void delAllFiles(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return;
        }
        String dirFullName = dir.getAbsolutePath();

        String[] fileList = dir.list();
        File tempFile = null;
        try{
            for (int i = 0; i < fileList.length; i++) {
                if (dirFullName.endsWith(File.separator)) {
                    tempFile = new File(dirFullName + fileList[i]);
                } else {
                    tempFile = new File(dirFullName + File.separator + fileList[i]);
                }
                if (tempFile.isFile()) {
                    tempFile.delete();
                }
                if (tempFile.isDirectory()) {
                    delAllFiles(dirFullName + "/" + fileList[i]);
                    delDir(dirFullName + "/" + fileList[i]);
                }
            }
        }catch (Throwable t){
            Logger.e(TAG, "delete all files error: " + t.getMessage());
        }
    }

    /**
     * 刪除指定目錄下所有文件，不包含該路径
     */
    public static void delAllFiles(String dir) {
        delAllFiles(new File(dir));
    }
}
