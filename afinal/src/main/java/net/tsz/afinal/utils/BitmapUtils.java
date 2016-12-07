package net.tsz.afinal.utils;

/**
 * Created by LiuWeiJie on 2015/7/25 0025.
 * Email:1031066280@qq.com
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.os.StatFs;
import android.util.Log;

import java.io.File;

public class BitmapUtils {
    private static final String TAG = "BitmapCommonUtils";
    private static final long POLY64REV = -7661587058870466123L;
    private static final long INITIALCRC = -1L;
    private static long[] sCrcTable = new long[256];

    static {
        for (int i = 0; i < 256; ++i) {
            long part = (long) i;

            for (int j = 0; j < 8; ++j) {
                long x = ((int) part & 1) != 0 ? -7661587058870466123L : 0L;
                part = part >> 1 ^ x;
            }

            sCrcTable[i] = part;
        }

    }

    public BitmapUtils() {
    }

    public static File getDiskCacheDir(Context context, String uniqueName) {
        String cachePath = "mounted".equals(Environment.getExternalStorageState()) ? getExternalCacheDir(context).getPath() : context.getCacheDir().getPath();
        return new File(cachePath + File.separator + uniqueName);
    }

    public static int getBitmapSize(Bitmap bitmap) {
        return bitmap.getRowBytes() * bitmap.getHeight();
    }

    public static File getExternalCacheDir(Context context) {
        String cacheDir = "/Android/data/" + context.getPackageName() + "/cache/";
        return new File(Environment.getExternalStorageDirectory().getPath() + cacheDir);
    }

    public static long getUsableSpace(File path) {
        try {
            StatFs e = new StatFs(path.getPath());
            return (long) e.getBlockSize() * (long) e.getAvailableBlocks();
        } catch (Exception var2) {
            Log.e("BitmapCommonUtils", "获取 sdcard 缓存大小 出错，请查看AndroidManifest.xml 是否添加了sdcard的访问权限");
            var2.printStackTrace();
            return -1L;
        }
    }

    public static byte[] getBytes(String in) {
        byte[] result = new byte[in.length() * 2];
        int output = 0;
        char[] var6;
        int var5 = (var6 = in.toCharArray()).length;

        for (int var4 = 0; var4 < var5; ++var4) {
            char ch = var6[var4];
            result[output++] = (byte) (ch & 255);
            result[output++] = (byte) (ch >> 8);
        }

        return result;
    }

    public static boolean isSameKey(byte[] key, byte[] buffer) {
        int n = key.length;
        if (buffer.length < n) {
            return false;
        } else {
            for (int i = 0; i < n; ++i) {
                if (key[i] != buffer[i]) {
                    return false;
                }
            }

            return true;
        }
    }

    public static byte[] copyOfRange(byte[] original, int from, int to) {
        int newLength = to - from;
        if (newLength < 0) {
            throw new IllegalArgumentException(from + " > " + to);
        } else {
            byte[] copy = new byte[newLength];
            System.arraycopy(original, from, copy, 0, Math.min(original.length - from, newLength));
            return copy;
        }
    }

    public static byte[] makeKey(String httpUrl) {
        return getBytes(httpUrl);
    }

    public static final long crc64Long(String in) {
        return in != null && in.length() != 0 ? crc64Long(getBytes(in)) : 0L;
    }

    public static final long crc64Long(byte[] buffer) {
        long crc = -1L;
        int k = 0;

        for (int n = buffer.length; k < n; ++k) {
            crc = sCrcTable[((int) crc ^ buffer[k]) & 255] ^ crc >> 8;
        }

        return crc;
    }
}

