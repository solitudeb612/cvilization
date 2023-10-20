package cn.swust.indigo.mce.service;

import cn.swust.indigo.admin.entity.po.SysUser;
import cn.swust.indigo.mce.entity.vo.UserVo;
import cn.swust.indigo.common.core.util.R;
import cn.swust.indigo.mce.entity.vo.UserVo;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;


public interface UserService extends IService<UserVo> {
    R register(UserVo userVo);

    List<Integer> findDeptIdByUserIds(Integer userId);

    R findMyUserAll(int leadId,int status,String username);

    R updateByUserIdsAndStatus(List<Integer> userIds, int status);

    SysUser getUserByUsername(String username);

    R getSiteAndDept(Integer userId);
}
