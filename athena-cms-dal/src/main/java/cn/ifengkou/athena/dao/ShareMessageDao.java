package cn.ifengkou.athena.dao;

import cn.ifengkou.athena.model.ShareMessage;
import org.springframework.stereotype.Repository;

/**
 * Created by Administrator on 2017/3/13.
 */
@Repository
public interface ShareMessageDao {
    ShareMessage getMessageByUserName(String name);
    ShareMessage getMessageAll();
    int add(ShareMessage message);
    int delete(long id);
}
