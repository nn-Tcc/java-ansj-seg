# ansj分词项目

## 项目说明
```
基于ansj分词版本5.1.6修改。<url>http://github.com/NLPchina/ansj_seg</url>
```
## 修改部分
* 抛弃使用CRF模型针对未登录词的识别方法，待增加和优化，即原始项目NlpAnalysis.class
* 词典配置方式调整为统一类加载，详见AnsjSegImpl.class
* 封装统一接口AnsjSeg.class,避免使用不同分词方法，重复加载模型
* 接口AnsjSeg.class配置通信锁，避免不同服务同时调用接口
* 增加使用jvm启动代码，供python调用
#### 词典
* 词典统一路径为../resources/，同时解耦亚洲和外国人名识别方法（详见ToAnalysis.class）
* 新增机构名词典company.dic
* 原始项目default.dic为用户自定义词典，并未调整加载方式，建议不调整词典内部词库，可为初始通用核心词典
* uesrdefine.dic为自定义词典，可定义为垂直领域自定义词典，支持多用户词典同时使用

## 准备工作

1、 jar包：
````
需要jar包：org.nlpcn-1.7.7
````

## 接口说明

0、快速调用方法：
````
    public static void main(String[] args) throws IOException {
            String text = "做超跌反弹更适合短线操作，好股如好酒，越守越醇。";
            AnsjSeg ansjSeg = AnsjSegPython.init();
            String userDicPath = "D:\\java_project\\java-ansj-seg-master\\src\\main\\java\\com\\zy\\alg\\jarExecution\\userDic.txt";
            Forest userDic = AnsjSegPython.insertUserDic(userDicPath);
    //        List<List<String>> terms = AnsjSegPython.textTokenizer(ansjSeg, text, "1");
            List<List<String>> terms = AnsjSegPython.textTokenizerUser(ansjSeg, text, "1", userDic);
            System.out.println(terms);
        }
````

1、默认分词方法：
````
    /**
     * @param query     原句子
     * @param typescene 1.精准分词-不去重;2.精准分词-去重;3.索引分词
     * @return
     */
    Result textTokenizer(String query, String typescene);
````

2、自定义分词方法：
````
    /**
     * @param query
     * @param typescene 1.精准分词-不去重;2.索引分词
     * @param forests   用户自定义词典
     * @return
     */
    Result textTokenizerUser(String query, String typescene, Forest... forests);
````
