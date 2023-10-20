package cn.swust.indigo.admin.controller;

import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.po.Token;
import cn.swust.indigo.admin.entity.vo.UserVO;
import cn.swust.indigo.admin.service.SysUserService;
import cn.swust.indigo.admin.service.TokenService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.constants.CommonConstants;
import cn.swust.indigo.common.core.constants.SecurityConstants;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.AuthUtils;
import cn.swust.indigo.common.security.util.SecurityUtils;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.AllArgsConstructor;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@Api(value = "/token", tags = "登录认证访问")
public class TokenController {

    private final RedisTemplate redisTemplate;
    private AuthenticationManager authenticationManager;
    private TokenService tokenService;
    private final SysUserService userService;

//    @PostMapping(value = "/user/login")
//    @ApiParam(required = true, value = "登录信息")
//    @ApiOperation(value = "登录")
//    public R login(String username, String password, String captcha, String randomStr, HttpServletRequest request) {
//
//        // 验证码校验
////        if (!this.checkCaptcha(captcha, randomStr)) {
////            return R.failed("验证码错误");
////        }
//        try {
//            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(username,
//                    password);
//            Authentication authentication = authenticationManager.authenticate(authRequest);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//            HttpSession session = request.getSession();
//            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
//            UserInfo loginUser = SecurityUtils.getUser();
//            Token token = tokenService.saveToken(loginUser.getSysUser().getUserId(), loginUser.getSysUser().getUsername());
//            return R.ok(token);
//        } catch (LockedException e) {
//            return R.failed("用户被锁定。");
//        } catch (DisabledException ex) {
//            return R.failed("用户被撤销。");
//        } catch (AuthenticationException ex) {
//            return R.failed("用户名密码错误。");
//        }
//    }

    @PostMapping(value = "/loginOut")
    @ApiParam(required = true, value = "退出登录信息")
    @ApiOperation(value = "退出登录")
    public R logout(HttpServletRequest request) {
        String token = AuthUtils.extractAndDecodeHeader(request);
        tokenService.deleteToken(token);
        return R.ok("退出登录成功。");
    }

    @ApiOperation(value = "获取验证码")
    @GetMapping(value = "/getcaptcha", produces = MediaType.IMAGE_PNG_VALUE)
    public @ResponseBody
    byte[] getCaptcha(String randomStr) throws IOException {
        LineCaptcha lineCaptcha = CaptchaUtil.createLineCaptcha(200, 100);
        String code = lineCaptcha.getCode();

//        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.opsForValue().set(CommonConstants.DEFAULT_CODE_KEY + randomStr, code
                , SecurityConstants.CODE_TIME, TimeUnit.SECONDS);

        ByteArrayOutputStream bao = new ByteArrayOutputStream();
        try {
            ImageIO.write(lineCaptcha.getImage(), "png", bao);
            return bao.toByteArray();
        } catch (IOException e) {
            return null;
        }
    }

    /**
     * 获取当前用户全部信息
     *
     * @return 用户信息
     */
    @ApiOperation(value = "获取当前用户全部信息", notes = "获取当前用户全部信息")
    @GetMapping(value = "/user/info")
    public R info() {
        String username = SecurityUtils.getUser().getSysUser().getUsername();
        SysUser user = userService.findSysUserByUserName(username);
        if (user == null) {
            return R.failed(null, "获取当前用户信息失败");
        }
        return R.ok(userService.findUserInfo(user));
    }

//    /**
//     * 获取当前用户top菜单
//     * @return top菜单
//     */
//    @ApiOperation(value = "获取当前用户top菜单", notes = "获取当前用户顶部菜单")
//    @GetMapping(value = "/user/topMenu")
//    public R getTopMenu() {
//
//        return R.ok( );
//    }
//    /**
//     * 获取当前用户子菜单
//     * @return 用户信息子菜单信息
//     */
//    @ApiOperation(value = "获取当前用户子菜单", notes = "获取当前用户子菜单")
//    @GetMapping(value = "/user/menu")
//    public R getSubMenu(String type) {
//
//        return R.ok( );
//    }


    /*
    检验验证码
    */
    protected boolean checkCaptcha(String captcha, String randomStr) {
        String key = CommonConstants.DEFAULT_CODE_KEY + randomStr;
//        redisTemplate.setKeySerializer(new StringRedisSerializer());
        if (!redisTemplate.hasKey(key)) {
            return false;
        } else {
            return true;
        }
    }
}
