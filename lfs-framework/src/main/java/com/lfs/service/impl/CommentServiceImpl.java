package com.lfs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfs.constants.SystemConstants;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.Comment;
import com.lfs.domain.vo.CommentVo;
import com.lfs.domain.vo.PageVo;
import com.lfs.enums.AppHttpCodeEnum;
import com.lfs.exception.SystemException;
import com.lfs.mapper.CommentMapper;
import com.lfs.service.CommentService;
import com.lfs.service.UserService;
import com.lfs.utils.BeanCopyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * 评论表(Comment)表服务实现类
 *
 * @author CodeShark
 * @since 2022-05-08 15:15:54
 */
@Service("commentService")
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Autowired
    private UserService userService;


    @Override
    public ResponseResult commentList(String commentType, Long articleId, Integer pageNum, Integer pageSize) {
//        查询对应文章的根评论

//        对articleId进行判断
        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();

//        只有文章评论为0 的时候才加这个判断
        queryWrapper.eq(SystemConstants.ARTICLE_COMMENT.equals(commentType), Comment::getArticleId, articleId);

//        一级评论时间晚的在前面，评论的评论时间早的在前面
        queryWrapper.orderByDesc(Comment::getCreateTime);

//        根评论 root = -1
        queryWrapper.eq(Comment::getRootId, SystemConstants.ROOT_COMMENT);

//        评论类型
        queryWrapper.eq(Comment::getType, commentType);


//        分页查询
        Page<Comment> page = new Page<>(pageNum, pageSize);
        page(page, queryWrapper);

//        调用方法实现转化，返回值有两个字段值没有
        List<CommentVo> commentVoList = toCommentVoList(page.getRecords());


//        查询所有根评论的子评论，并赋值给对应的属性，从一个
        for (CommentVo commentVo : commentVoList) {
//            查询对应的子评论
            List<CommentVo> children = getChildren(commentVo.getId());
//            赋值
            commentVo.setChildren(children);
        }

        return ResponseResult.okResult(new PageVo(commentVoList, page.getTotal()));
    }

    //    添加评论
    @Override
    public ResponseResult addComment(Comment comment) {
//        从token中获取信息添加到表中
//        评论内容不能为空
        if (!StringUtils.hasText(comment.getContent())) {
            throw new SystemException(AppHttpCodeEnum.CONTENT_NOT_NULL);
        }
        save(comment);

        return ResponseResult.okResult();
    }

    /**
     * 根据根评论的ID查询对应的子评论的集合
     *
     * @param id 根评论Id
     * @return
     */
    private List<CommentVo> getChildren(Long id) {

        LambdaQueryWrapper<Comment> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Comment::getRootId, id);
        queryWrapper.orderByAsc(Comment::getCreateTime);
        List<Comment> comments = list(queryWrapper);

        List<CommentVo> commentVos = toCommentVoList(comments);
        return commentVos;

    }

    //    Page -> CommentVo   Page中的数据本身是从Comment中获取的，缺少返回的两个字段值 toCommentUserName 和 username
//    把Page转换成CommentVo 再通过遍历中用 评论创建人 和 所回复的目标评论的userid 查询
    private List<CommentVo> toCommentVoList(List<Comment> list) {
        List<CommentVo> commentVos = BeanCopyUtils.copyBeanList(list, CommentVo.class);

//        遍历vo集合
        for (CommentVo commentVo : commentVos) {
//            1、通过createBy查询用户的昵称并赋值
            String nickName = userService.getById(commentVo.getCreateBy()).getNickName();
            commentVo.setUsername(nickName);

//           2、通过toCommentUserId查询用户的昵称并赋值
//            如果toCommentUserId不为 -1 才进行查询
//            给子级单独添加的字段
//            子级返回内容时，会多一个username
            if (commentVo.getToCommentId() != -1) {
                String toCommentName = userService.getById(commentVo.getToCommentUserId()).getNickName();
                commentVo.setToCommentUserName(toCommentName);
            }
        }
        return commentVos;
    }
}

