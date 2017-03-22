package cn.ifengkou.athena.service.impl;

import cn.ifengkou.athena.common.id.IdGen;
import cn.ifengkou.athena.dao.ShareMessageDao;
import cn.ifengkou.athena.model.ShareMessage;
import cn.ifengkou.athena.service.ShareMessageService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */
@Service
public class ShareMessageImpl implements ShareMessageService {
    @Resource
    ShareMessageDao shareMessageDao;
    @Override
    public ShareMessage getMessageByUserName(String name) {
        return shareMessageDao.getMessageByUserName(name);
    }

    @Override
    public List<ShareMessage> getMessageAll() {
        return shareMessageDao.getMessageAll();
    }

    @Override
    public int add(ShareMessage message) {
        message.setId(IdGen.genId());
        return shareMessageDao.add(message);
    }

    @Override
    public int delete(long id) {
        return 0;
    }
}
