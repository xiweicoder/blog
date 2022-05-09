package com.lfs.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.lfs.constants.SystemConstants;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.Link;
import com.lfs.domain.vo.LinkVo;
import com.lfs.mapper.LinkMapper;
import com.lfs.service.LinkService;
import com.lfs.utils.BeanCopyUtils;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 友链(Link)表服务实现类
 *
 * @author makejava
 * @since 2022-05-07 14:32:34
 */
@Service("linkService")
public class LinkServiceImpl extends ServiceImpl<LinkMapper, Link> implements LinkService {

    //    友链
    @Override
    public ResponseResult getAllLink() {
//        查询所有审核通过的
        LambdaQueryWrapper<Link> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Link::getStatus, SystemConstants.LINK_STATUS_NORMAL);
        List<Link> linkList = list(queryWrapper);
//        转换成vo
        List<LinkVo> vs = BeanCopyUtils.copyBeanList(linkList, LinkVo.class);
        return ResponseResult.okResult(vs);
    }
}

