package mvp.com.zmvp.data;

import android.content.Context;
import android.content.SharedPreferences;

public class PreferencesMgr {
    public static final String FILE_NAME = "mvp.com.zmvp";
    /**
     * 使用过引导
     */
    public static final String PREF_GUIDE_USED = "guide.used";

    private static class HolderBuilder {
        static final PreferencesMgr instance = new PreferencesMgr();
    }

    private PreferencesMgr() {

    }

    public static PreferencesMgr getInstance() {
        return HolderBuilder.instance;
    }


    // 获取String类型
    public String getString(Context context, String key) {
        return getString(context, key, "");
    }

    // 获取String类型
    public String getString(Context context, String key, String def) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getString(key, def);
    }

    // 获取int类型
    public int getInt(Context context, String key) {
        return getInt(context, key, 0);
    }

    // 获取int类型
    public int getInt(Context context, String key, int def) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getInt(key, def);
    }

    // 获取long类型
    public long getLong(Context context, String key) {
        return getLong(context, key, 0);
    }

    // 获取long类型
    public long getLong(Context context, String key, long def) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getLong(key, def);
    }

    // 获取boolean类型
    public boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    // 获取boolean类型
    public boolean getBoolean(Context context, String key, boolean def) {
        return context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).getBoolean(key, def);
    }

    // 设置String类型
    public void putString(Context context, String key, String value) {
        SharedPreferences.Editor ed = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        ed.putString(key, value);
        ed.commit();
    }

    // 设置Int类型
    public void putInt(Context context, String key, int value) {
        SharedPreferences.Editor ed = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        ed.putInt(key, value);
        ed.commit();
    }

    // 设置Long类型
    public void putLong(Context context, String key, long value) {
        SharedPreferences.Editor ed = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        ed.putLong(key, value);
        ed.commit();
    }

    // 设置Boolean类型
    public void putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor ed = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        ed.putBoolean(key, value);
        ed.commit();
    }


    public void remove(Context context, String key) {
        SharedPreferences.Editor ed = context.getSharedPreferences(FILE_NAME, Context.MODE_PRIVATE).edit();
        ed.remove(key).commit();
    }

}
