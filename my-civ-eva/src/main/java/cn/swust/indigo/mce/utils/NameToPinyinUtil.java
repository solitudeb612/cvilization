package cn.swust.indigo.mce.utils;

import cn.hutool.extra.pinyin.engine.pinyin4j.Pinyin4jEngine;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;

public class NameToPinyinUtil {
    public static String getPinyinFirst(String string) {
        if (string == null || "".equals(string)) {
            return string;
        }
        if (string.length() >= 10) {
            string = string.substring(0, 10);
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.LOWERCASE);// 大小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不加声调
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// 'ü' 使用 "v" 代替
        Pinyin4jEngine engine = new Pinyin4jEngine(format);
        String[] stringArr = engine.getPinyin(string, " ").split(" ");
        StringBuffer stringBuffer = new StringBuffer();
        for (String s : stringArr) {
            char[] chars = s.toCharArray();
            if (chars.length > 0) {
                if ((chars[0] >= 'a' && chars[0] <= 'z') || (chars[0] >= 'A' && chars[0] <= 'Z')) {
//                    int index = stringBuffer.indexOf(String.valueOf(chars[0]));
//                    if (index < 0) {
                        stringBuffer.append(chars[0]);
                }
            }
        }
        return stringBuffer.toString();
    }
}

