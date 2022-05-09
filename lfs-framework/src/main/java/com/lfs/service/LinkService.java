package com.lfs.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.lfs.domain.ResponseResult;
import com.lfs.domain.entity.Link;


/**
 * 友链(Link)表服务接口
 *
 * @author makejava
 * @since 2022-05-07 14:32:34
 */
public interface LinkService extends IService<Link> {

    //    友链
    ResponseResult getAllLink();
}

