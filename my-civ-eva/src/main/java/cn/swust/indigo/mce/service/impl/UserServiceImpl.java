package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.admin.mapper.SysDeptMapper;
import cn.swust.indigo.admin.mapper.SysUserMapper;
import cn.swust.indigo.admin.service.SysDeptService;
import cn.swust.indigo.admin.service.SysUserService;
import cn.swust.indigo.common.core.util.PinYinUtils;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.vo.Department;
import cn.swust.indigo.mce.entity.vo.MangeLocals;
import cn.swust.indigo.mce.entity.vo.Site;
import cn.swust.indigo.mce.entity.vo.UserVo;
import cn.swust.indigo.mce.mapper.UserMapper;
import cn.swust.indigo.mce.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServiceImpl  extends ServiceImpl<UserMapper, UserVo> implements UserService{
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private SysUserMapper sysUserMapper;
    @Autowired
    private  PasswordEncoder passwordEncoder;
    @Autowired
    private SysDeptMapper sysDeptMapper;
    @Override
    @Transactional
    public R register(UserVo userVo) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,userVo.getUsername());
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        if(sysUser!=null){
            return R.failed("该用户已存在");
        }
        String encode = passwordEncoder.encode(userVo.getPassword());
        userVo.setPassword(encode);
        userVo.setUserPy();
        int count = userMapper.insertUser(userVo);
        SysUser newRegisterUser = sysUserMapper.selectOne(wrapper);
        userVo.setUserId(newRegisterUser.getUserId());
        int i = userMapper.insertSysUserRole(userVo);
        if(count == 1 && i == 1){
            return R.ok("注册申请已经提交");
        }else {
            return R.failed("注册失败");
        }
    }
    @Override
    public List<Integer> findDeptIdByUserIds(Integer userId) {
        List<Integer> depIdByLeaderId = sysDeptMapper.getDepIdByLeaderId(userId);
        return depIdByLeaderId;
    }
    @Override
    public R findMyUserAll(int leaderId ,int status,String username) {
        if(username.equals("admin")){
            LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
            wrapper.eq(SysUser::getStatus,status);
            return R.ok(sysUserMapper.selectList(wrapper));
        }
        List<SysUser> myUserAll= userMapper.findMyUserAll(leaderId,status);
        return R.ok(myUserAll);
    }

    @Override
    public R updateByUserIdsAndStatus(List<Integer> userIds, int status) {
        userMapper.updateByUserIdsAndStatus(userIds,status);
       return  R.ok("修改成功");
    }

    @Override
    public SysUser getUserByUsername(String username) {
        LambdaQueryWrapper<SysUser> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysUser::getUsername,username);
        SysUser sysUser = sysUserMapper.selectOne(wrapper);
        return sysUser;
    }

    @Override
    public R getSiteAndDept(Integer leaderId) {
        List<Site> sites =  userMapper.getSiteIdAndName(leaderId);
        List<Department> departments = userMapper.getDepartmentIdAndName(leaderId);
        MangeLocals mangeLocals = new MangeLocals(departments,sites);
        return  R.ok(mangeLocals);
    }
}
