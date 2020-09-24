package com.tr.alg.jarExecution;

import com.tr.alg.domain.Result;
import com.tr.alg.domain.Term;
import com.tr.alg.service.AnsjSeg;
import com.tr.alg.service.AnsjSegImpl;
import com.tr.alg.library.StopLibrary;
import com.tr.alg.splitword.DicAnalysis;
import org.nlpcn.commons.lang.tire.domain.Forest;
import org.nlpcn.commons.lang.tire.domain.Value;
import org.nlpcn.commons.lang.tire.library.Library;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * 说明
 * <p>
 *
 * @author tanrui
 */
public class AnsjSegPython {

    /**
     * ansj seg init
     *
     * @return
     */
    public static AnsjSeg init() {
        AnsjSeg ansjSeg = null;
        try {
            ansjSeg = AnsjSegImpl.getSingleton();
        } catch (IOException e) {
            System.out.println("load ansj seg w2vModel failed. " + e);
        }
        return ansjSeg;
    }

    /**
     * 初始化自定义词典
     *
     * @param path
     * @return
     */
    public static Forest insertUserDic(String path) {
        Forest userDic = new Forest();
        BufferedReader br;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(path), "utf-8"));
            String line;
            while ((line = br.readLine()) != null) {
                String[] seg = line.split("\t");
                String word = seg[0];
                if (seg.length == 3) {
                    Library.insertWord(userDic, new Value(word, seg[1], seg[2]));
                } else if (seg.length == 2) {
                    Library.insertWord(userDic, new Value(word, seg[1], "2000"));
                } else if (seg.length == 1) {
                    Library.insertWord(userDic, new Value(word, "user", "2000"));
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("load user dic failed. " + e);
        }
        return userDic;
    }

    /**
     * 精准分词
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis（不去重）；2-ToAnalysis（去重）；3-IndexAnalysis；4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizer(AnsjSeg ansjSeg, String text, String type) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text);
        } else {
            terms = ansjSeg.textTokenizer(text, type);
        }
        List<List<String>> list = new ArrayList<>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }

    /**
     * 激活停用词典
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis（不去重）；2-ToAnalysis（去重）；3-IndexAnalysis；4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizerStop(AnsjSeg ansjSeg, String text, String type) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text).recognition(StopLibrary.get());
        } else {
            terms = ansjSeg.textTokenizer(text, type).recognition(StopLibrary.get());
        }
        List<List<String>> list = new ArrayList<>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }

    /**
     * 添加自定义词典
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis（不去重）；2-ToAnalysis（去重）；3-IndexAnalysis；4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizerUser(AnsjSeg ansjSeg, String text, String type, Forest... userDic) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text, userDic);
        } else {
            terms = ansjSeg.textTokenizerUser(text, type, userDic);
        }
        List<List<String>> list = new ArrayList<>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }

    /**
     * 添加自定义词典并激活停用词典
     *
     * @param ansjSeg
     * @param text
     * @param type    1-ToAnalysis（不去重）；2-ToAnalysis（去重）；3-IndexAnalysis；4-DicAnalysis
     * @return
     */
    public static List<List<String>> textTokenizerUserStop(AnsjSeg ansjSeg, String text, String type, Forest... userDic) {
        Result terms;
        if (type.equals("4")) {
            terms = DicAnalysis.parse(text, userDic).recognition(StopLibrary.get());
        } else {
            terms = ansjSeg.textTokenizerUser(text, type, userDic).recognition(StopLibrary.get());
        }
        List<List<String>> list = new ArrayList<>();
        for (Term t : terms) {
            List<String> tmpList = new ArrayList<>();
            tmpList.add(t.getName());
            tmpList.add(t.getNatureStr());
            list.add(tmpList);
        }
        return list;
    }
}
