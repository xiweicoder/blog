package com.lfs.utils;

import com.lfs.domain.entity.Article;
import com.lfs.domain.vo.HotArticleVo;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

//bean工具类
public class BeanCopyUtils {
    private BeanCopyUtils() {

    }

    //    通过传入的参数来确定需要创建的vo对象   字节码的反射方式
    public static <V> V copyBean(Object source, Class<V> clazz) {
//        创建目标对象
        V result = null;
        try {
            result = clazz.newInstance();
//            实现属性的copy
            BeanUtils.copyProperties(source, result);
            //        返回结果
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    public static <O,V> List<V> copyBeanList(List<O> list, Class<V> clazz) {
        return list.stream()
//                这个流中就是转换完的对象
                .map(o -> copyBean(o, clazz))
                .collect(Collectors.toList());
    }


    public static void main(String[] args) {
        Article article = new Article();
        article.setId(1L);
        article.setTitle("ss");
        HotArticleVo hotArticleVo = copyBean(article, HotArticleVo.class);
        System.out.println(hotArticleVo);
    }


}
