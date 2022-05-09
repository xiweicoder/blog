package com.lfs.domain.vo;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleDetailVo extends ArticleListVo {
    //    文章内容
    private String content;
}
