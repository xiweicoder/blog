package com.lfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.Comment;


/**
 * 评论表(Comment)表服务接口
 *
 * @author makejava
 * @since 2022-05-08 15:15:54
 */
public interface CommentService extends IService<Comment> {

    //    评论表
    ResponseResult commentList(Long articleId, Integer pageNum, Integer pageSize);

    //    添加评论
    ResponseResult addComment(Comment comment);
}

