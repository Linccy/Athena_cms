package cn.ifengkou.athena.controller;

import cn.ifengkou.athena.common.id.IdGen;
import cn.ifengkou.athena.common.id.UUIDTools;
import cn.ifengkou.athena.common.util.MD5Utils;
import cn.ifengkou.athena.controller.common.JsonDto;
import cn.ifengkou.athena.controller.exception.IllegalException;
import cn.ifengkou.athena.model.User;
import cn.ifengkou.athena.service.UserService;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * Created by Sloong on 2015/11/24.
 */
@Controller
@RequestMapping("user")
public class UserController {
    @Resource
    UserService userService;

    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpServletRequest request) {
        User user = (User) request.getSession().getAttribute("user");
        if (user != null) {
            return "redirect:/";
        } else {
            return "login";
        }
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public String login(HttpServletRequest request, String loginName, String pass) throws IOException, IllegalException {
        User user = userService.getUserByLoginName(loginName);

        //TODO:混合校验
        //String kaptcha = (String)request.getSession().getAttribute("kaptcha");

        if (user != null && pass.equals(user.getPass())) {
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(user);
            return json;
        } else {
            throw new IllegalException("user '" + loginName + "' is not exists or password is error");
        }
//        request.setAttribute("message", "登录失败");
//        return "login";
    }

    @RequestMapping(value = "register", method = RequestMethod.POST)
    @ResponseBody
    public String register(HttpServletRequest request, String loginName, String pass, String name) throws IOException, IllegalException {
        if (userService.getUserByLoginName(loginName) == null && pass != null) {
            User user = new User();
            user.setId(IdGen.genId());
            user.setLoginName(loginName);
            user.setName(name);
            user.setPass(pass);
            user.setAccessToken(UUIDTools.uuid());
            userService.add(user);

            JsonDto jsonDto = new JsonDto(true, "register successed");
            return jsonDto.toJsonStr();
        } else {
            throw new IllegalException("user '" + loginName + "' already  exist");
        }
    }

    @RequestMapping(value = "change_pwd", method = RequestMethod.POST)
    @ResponseBody
    public String change_pwd(HttpServletRequest request, String loginName, String pass, String newpass) throws IOException, IllegalException {
        User user = userService.getUserByLoginName(loginName);
        if (user != null) {
            if (user.getPass().equals(pass) && newpass != null) {
                user.setPass(newpass);
                userService.update(user);
                JsonDto jsonDto = new JsonDto(true, "change_pwd successed");
                return jsonDto.toJsonStr();
            } else {
                throw new IllegalException("pass is error");
            }
        } else {
            throw new IllegalException("user is un exist");
        }
    }

    @RequestMapping(value = "change_user_information", method = RequestMethod.POST)
    public String change_user_information(HttpServletRequest request, String userjson) throws IOException, IllegalException {
        ObjectMapper mapper = new ObjectMapper();
        User user = mapper.convertValue(userjson, User.class);
        User userOnService = userService.getUserByLoginName(user.getLoginName());
        if (userOnService != null && user.getPass().equals(userOnService.getPass())) {
            userOnService.setAvatar(user.getAvatar());
            userOnService.setEmail(user.getEmail());
            JsonDto jsonDto = new JsonDto(true, "change_pwd successed");
            return jsonDto.toJsonStr();
        }
        throw new IllegalException("");
    }
}
