package com.tr.alg.jarExecution;

import com.tr.alg.service.AnsjSeg;
import org.nlpcn.commons.lang.tire.domain.Forest;

import java.io.IOException;
import java.util.List;

/**
 * 说明
 * <p>
 *
 * @author tanrui
 */
public class AnsjSegPythonTest {
    public static void main(String[] args) throws IOException {
        String text = "做超跌反弹更适合短线操作，好股如好酒，越守越醇。";
        AnsjSeg ansjSeg = AnsjSegPython.init();
        String userDicPath = "D:\\java_project\\java-ansj-seg-master\\src\\main\\java\\com\\zy\\alg\\jarExecution\\userDic.txt";
        Forest userDic = AnsjSegPython.insertUserDic(userDicPath);
//        List<List<String>> terms = AnsjSegPython.textTokenizer(ansjSeg, text, "1");
        List<List<String>> terms = AnsjSegPython.textTokenizerUser(ansjSeg, text, "1", userDic);
        System.out.println(terms);
    }
}
