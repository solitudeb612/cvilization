package cn.swust.indigo.admin.service;


import cn.swust.indigo.admin.entity.dto.DeptTree;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.vo.QSysDept;
import cn.swust.indigo.admin.entity.vo.SysDeptVo;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 部门管理 服务类
 * </p>
 */
@Service
public interface SysDeptService extends IService<SysDept> {

    /**
     * 查询部门树菜单
     *
     * @return 树
     */
    List<DeptTree> selectTreeByType(int departmentType);

    /**
     * 添加信息部门
     *
     * @param sysDept
     * @return
     */
    Boolean saveDept(SysDept sysDept);

    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    Boolean removeDeptById(Integer id);

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    Boolean updateDeptById(SysDept sysDept);


    Page<SysDeptVo> selectPage(QSysDept qSysDept);

    /**
     * 返回被此user管理的所有部门，及其以下的部门
     * @param userId 用户ID
     * @return 树形列表
     */
    List<DeptTree> selectTreeById(Integer userId);

    /**
     * 设置部门的的LeaderId
     * @param userId
     * @param departmentId
     */
    boolean setDepartmentLeader(Integer userId, Integer departmentId);

    /**
     * 删除部门和其子部门
     * @param departmentId 部门Id
     * @return 是否成功
     */
    boolean removeByIdAndSubTree(Integer departmentId);

    /**
     * 根据用户ID获得当前用户管理的部门
     * @param userId 用户id
     * @return 部门信息
     */
    SysDept getDeptByUserId(Integer userId);
}
