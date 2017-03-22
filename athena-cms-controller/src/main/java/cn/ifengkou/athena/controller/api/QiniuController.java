package cn.ifengkou.athena.controller.api;

import cn.ifengkou.athena.controller.common.JsonDto;
import cn.ifengkou.athena.controller.exception.IllegalException;
import cn.ifengkou.athena.model.ShareMessage;
import com.qiniu.util.Auth;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.util.JSONPObject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/22.
 */
@Controller
@RequestMapping("parameter")
public class QiniuController {
    @RequestMapping(value = "gettoken", method = RequestMethod.POST)
    @ResponseBody
    public String getMessageByName(HttpServletRequest request, String username) throws IOException, IllegalException {
        String accessKey = "VXGWN-JZZ4aYDllehZrZyhYVoNxzQS9Tk3n7L-ik";
        String secretKey = "BRWUksUG75fTNkuQlli6z0JjD2mmYj8Z3bcrB8hv";    // 密钥配置
        String bucket = "sharefoods";
        Auth auth = Auth.create(accessKey, secretKey);
        String upToken = auth.uploadToken(bucket);
        if (upToken != null) {
            JsonDto jsonDto = new JsonDto(true, "successed", upToken);
            return jsonDto.toJsonStr();
        } else {
            throw new IllegalException("get token field");
        }
    }
}
