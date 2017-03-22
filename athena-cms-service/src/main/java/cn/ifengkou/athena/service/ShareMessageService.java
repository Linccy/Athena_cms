package cn.ifengkou.athena.service;

import cn.ifengkou.athena.model.ShareMessage;

import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */
public interface ShareMessageService {
    ShareMessage getMessageByUserName(String name);

    List<ShareMessage> getMessageAll();

    int add(ShareMessage message);

    int delete(long id);
}
