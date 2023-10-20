package cn.swust.indigo.mce.utils;

import cn.hutool.extra.pinyin.PinyinUtil;
import cn.hutool.extra.pinyin.engine.pinyin4j.Pinyin4jEngine;
import net.sourceforge.pinyin4j.PinyinHelper;
import net.sourceforge.pinyin4j.format.HanyuPinyinCaseType;
import net.sourceforge.pinyin4j.format.HanyuPinyinOutputFormat;
import net.sourceforge.pinyin4j.format.HanyuPinyinToneType;
import net.sourceforge.pinyin4j.format.HanyuPinyinVCharType;
import net.sourceforge.pinyin4j.format.exception.BadHanyuPinyinOutputFormatCombination;

import java.util.Arrays;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

public class NameUtil {
    public static String getPinyin(String string){
        if(string==null || "".equals(string)){
            return string;
        }
        if(string.length()>=16){
            string=string.substring(0,15);
        }
        HanyuPinyinOutputFormat format = new HanyuPinyinOutputFormat();
        format.setCaseType(HanyuPinyinCaseType.UPPERCASE);// 大小写
        format.setToneType(HanyuPinyinToneType.WITHOUT_TONE);// 不加声调
        format.setVCharType(HanyuPinyinVCharType.WITH_U_AND_COLON);// 'ü' 使用 "v" 代替
        Pinyin4jEngine engine = new Pinyin4jEngine(format);

        return engine.getPinyin(string,"");
    }

    private NameUtil() {
    }
}

