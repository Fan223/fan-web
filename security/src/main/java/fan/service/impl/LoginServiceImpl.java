package fan.service.impl;

import fan.dto.LoginDTO;
import fan.lang.Response;
import fan.pojo.entity.UserDO;
import fan.pojo.vo.UserVO;
import fan.service.CaptchaService;
import fan.service.LoginService;
import fan.service.UserService;
import fan.utils.SysMapStruct;
import org.springframework.stereotype.Service;

import jakarta.annotation.Resource;
import java.util.Map;

/**
 * 登录实现类
 *
 * @author Fan
 * @since 2023/7/7 15:14
 */
@Service
public class LoginServiceImpl implements LoginService {

    @Resource
    private CaptchaService captchaService;

    @Resource
    private UserService userService;

    @Override
    public Response<Map<String, String>> getCaptcha() {
        return captchaService.getCaptcha("fan-web");
    }

    @Override
    public Response<UserVO> login(LoginDTO loginDTO) {
        // 校验验证码
        Response<Boolean> verify = captchaService.verifyCaptcha(loginDTO.getToken(), loginDTO.getCaptcha());
        Boolean captcha = verify.getData();
        if (Boolean.FALSE.equals(captcha)) {
            return Response.fail(verify.getMessage(), null);
        }

        UserDO userDO = userService.verifyUser(loginDTO.getUsername());
        if (null == userDO) {
            return Response.fail("用户名不存在", null);
        } else if (!userDO.getPassword().equals(loginDTO.getPassword())) {
            return Response.fail("用户名或密码错误", null);
        }

        return Response.success("登录成功", SysMapStruct.INSTANCE.transUser(userDO));
    }
}
