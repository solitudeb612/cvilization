package cn.swust.indigo.mce.controller;

import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.bo.LoginUser;
import cn.swust.indigo.admin.entity.dto.UserInfo;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.entity.po.Token;
import cn.swust.indigo.admin.entity.vo.UserVO;
import cn.swust.indigo.admin.mapper.SysRoleMapper;
import cn.swust.indigo.admin.service.SysUserService;
import cn.swust.indigo.admin.service.TokenService;
import cn.swust.indigo.common.core.constants.CacheConstants;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.common.security.util.SecurityUtils;
import cn.swust.indigo.mce.entity.vo.*;
import cn.swust.indigo.mce.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import java.util.List;

/**
 * 用户
 */
@RestController
@RequestMapping("/user")
@Api(value = "user", tags = "用户管理包含：注册，审核，登录")
public class UserController {
    @Autowired
    public UserService userService;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private CacheManager cacheManager;
    @Autowired
    private SysRoleMapper sysRoleMapper;

    private static final PasswordEncoder ENCODER = new BCryptPasswordEncoder();


    @Autowired
    public SysUserService sysUserService;
    @ApiOperation(value = "获取站点的id和对应内容以及部门的id和对应内容")
    @GetMapping("getSiteAndDept")

    public R getSiteAndDept(){
        SysUser sysUser = SecurityUtils.getUser().getSysUser();
        Integer leaderId = sysUser.getUserId();
        return userService.getSiteAndDept(leaderId);
    }


    @ApiOperation(value = "用户注册", notes = "用户注册")
    @PostMapping("/register")
    @Deprecated
    public R register(@Valid @RequestBody UserVo userVo){
        return  userService.register(userVo);
    }
    @ApiOperation(value = "领导查看自己手下的注册情况 status不可用 0;可用 1;锁定2;申请中 3;申请不成功 4",
            notes = "领导查看自己手下的注册情况status不可用 0;可用 1;锁定2;申请中 3;申请不成功 4")
    @PostMapping("/findMyUserAll")
    @Deprecated
    public R findMyUserAll(int status){
        SysUser sysUser = SecurityUtils.getUser().getSysUser();
        Integer userId = sysUser.getUserId();
//        List<Integer> deptIdByUserIds = userService.findDeptIdByUserIds(userId);
        return userService.findMyUserAll(userId,status,sysUser.getUsername());
    }

    @ApiOperation(value = "领导审批自己手下的注册", notes ="领导审批自己手下的注册")
    @PostMapping("/examine")
    @Deprecated
    public R examineMyUser(@RequestParam("userIds") List<Integer> userIds,
                           @RequestParam("status")int status,HttpServletRequest request){
        if(userIds == null || userIds.size() == 0 || (status != 0 && status != 1 && status != 2 && status != 3 && status != 4 )){
        return R.failed("非法操作");
        }

       return userService.updateByUserIdsAndStatus(userIds,status);
    }

    @ApiOperation(value = "微信用户绑定实地考察用户", notes ="微信用户绑定实地考察用户")
    @PutMapping("/binding")
    public R bindingMyUser(@RequestParam("userName") String userName, @RequestParam("passWord") String passWord,
                           @RequestParam("openId") String openId){
        SysUser userByUsername = userService.getUserByUsername(userName);
        if(userByUsername != null && ENCODER.matches(passWord,userByUsername.getPassword())){
            if(userByUsername.getWxOpenId() != null){
                return R.failed("该用户已被绑定");
            }
            UserVo userVo = userService.getById(userByUsername.getUserId());
            userVo.setWxOpenId(openId);
            userService.updateById(userVo);
            return R.ok("绑定成功");
        }
        return R.failed("用户名密码输入错误");
    }

    @PostMapping(value = "/login")
    @ApiParam(required = true, value = "登录信息")
    @ApiOperation(value = "登录")
    public R login(@RequestBody User user, HttpServletRequest request) {
        try { 
            UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(user.getUsername(),
                    user.getPassword());
            Authentication authentication = authenticationManager.authenticate(authRequest);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            UserInfo loginUser = SecurityUtils.getUser();
            if(!loginUser.getSysUser().getStatus().equals("1")) {
                session.removeAttribute("SPRING_SECURITY_CONTEXT");
                Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
                cache.evict(loginUser.getSysUser().getUsername());
                if (loginUser.getSysUser().getStatus().equals("0")) {
                    return R.failed("该用户不可用");
                } else if (loginUser.getSysUser().getStatus().equals("2")) {
                    return R.failed("该用户被锁定");
                } else if (loginUser.getSysUser().getStatus().equals("3")) {
                    return R.failed("该用户正在申请中");
                } else if (loginUser.getSysUser().getStatus().equals("4")) {
                    return R.failed("该用户申请不成功");
                }
            }
            Token token = tokenService.saveToken(loginUser.getSysUser().getUserId(), loginUser.getSysUser().getUsername());
            String roleName = sysRoleMapper.findRoleNameByUserId(token.getUserId());
            TokenVo tokenVo = new TokenVo(token.getUserId(),token.getUsername(),token.getAccessToken(),token.getRefreshToken(),roleName);
            return R.ok(tokenVo);
        } catch (LockedException e) {
            return R.failed("用户被锁定。");
        } catch (DisabledException ex) {
            return R.failed("用户被撤销。");
        } catch (AuthenticationException ex) {
            return R.failed("用户名密码错误。");
        }
    }

    @PostMapping(value = "/wx/login")
    @ApiParam(required = true, value = "微信登录信息")
    @ApiOperation(value = "微信登录信息")
    public R wxLogin(@RequestBody WxLogin user, HttpServletRequest request) {
        try {
            HttpSession session = request.getSession();
            session.setAttribute("SPRING_SECURITY_CONTEXT", SecurityContextHolder.getContext());
            UserInfo loginUser = sysUserService.getByWxOpenId(user.getOpenId());
            if(loginUser == null){
                return R.failed("该用户尚未绑定");
            }
            if(!loginUser.getSysUser().getStatus().equals("1")) {
                session.removeAttribute("SPRING_SECURITY_CONTEXT");
                Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
                cache.evict(loginUser.getSysUser().getUsername());
                if (loginUser.getSysUser().getStatus().equals("0")) {
                    return R.failed("该用户不可用");
                } else if (loginUser.getSysUser().getStatus().equals("2")) {
                    return R.failed("该用户被锁定");
                } else if (loginUser.getSysUser().getStatus().equals("3")) {
                    return R.failed("该用户正在申请中");
                } else if (loginUser.getSysUser().getStatus().equals("4")) {
                    return R.failed("该用户申请不成功");
                }
            }
            Cache cache = cacheManager.getCache(CacheConstants.USER_DETAILS);
            cache.put(loginUser.getSysUser().getUsername(),  new LoginUser(loginUser));

            Token token = tokenService.saveToken(loginUser.getSysUser().getUserId(), loginUser.getSysUser().getUsername());
            String roleName = sysRoleMapper.findRoleNameByUserId(token.getUserId());
            TokenVo tokenVo = new TokenVo(token.getUserId(),token.getUsername(),token.getAccessToken(),token.getRefreshToken(),roleName);
            return R.ok(tokenVo);
        } catch (LockedException e) {
            return R.failed("用户被锁定。");
        } catch (DisabledException ex) {
            return R.failed("用户被撤销。");
        } catch (AuthenticationException ex) {
            return R.failed("用户名密码错误。");
        }
    }


    @PutMapping("/untie/id")
    @ApiOperation(value = "使用用户的ID解绑微信")
    public R untieWXUserById(@RequestBody UserId userId){
        return sysUserService.uniteByUserId(userId.getUserId()) ? R.ok("解绑完成") : R.failed("解绑失败！可能是不存在这个用户，或服务器延迟，请稍等。");
    }
}
