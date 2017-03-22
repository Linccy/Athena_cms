package cn.ifengkou.athena.controller.api;

import cn.ifengkou.athena.controller.common.JsonDto;
import cn.ifengkou.athena.controller.exception.IllegalException;
import cn.ifengkou.athena.model.ShareMessage;
import cn.ifengkou.athena.service.ShareMessageService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.List;

/**
 * Created by Administrator on 2017/3/13.
 */
@Controller
@RequestMapping("sharemessage")
public class ShareMessageController {
    @Resource
    ShareMessageService shareMessageService;


    @RequestMapping(value = "getmessagebyname", method = RequestMethod.POST)
    @ResponseBody
    public String getMessageByName(HttpServletRequest request, String name) throws IOException, IllegalException {
        ShareMessage shareMessage = shareMessageService.getMessageByUserName(name);
        if (shareMessage != null) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(shareMessage);
            return json;
        } else {
            throw new IllegalException("no shus as by" + name);
        }
    }

    @RequestMapping(value = "getsharemessageall", method = RequestMethod.GET)
    @ResponseBody
    public String getMessageAll(HttpServletRequest request) throws IOException, IllegalException {
        List<ShareMessage> shareMessageList = shareMessageService.getMessageAll();
        if (shareMessageList != null) {
            JsonDto jsonDto = new JsonDto(true, "successed", shareMessageList);
            return jsonDto.toJsonStr();
        } else {
            throw new IllegalException("sharemessage is empty");
        }
    }

    @RequestMapping(value = "addsharemessage", method = RequestMethod.POST)
    @ResponseBody
    public String addShareMessage(HttpServletRequest request, String sharemessagejsonstr) throws IOException, IllegalException {
        ObjectMapper mapper = new ObjectMapper();
        //不用date
        ShareMessage shareMessage = mapper.readValue(sharemessagejsonstr, ShareMessage.class);
        int status = shareMessageService.add(shareMessage);
        if (status > 0) {
            JsonDto jsonDto = new JsonDto(true, "shared successed");
            return jsonDto.toJsonStr();
        } else {
            throw new IllegalException("mistake share");
        }
    }
}
