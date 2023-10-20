package cn.swust.indigo.admin.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.swust.indigo.admin.entity.dto.DeptTree;
import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.po.SysDeptRelation;
import cn.swust.indigo.admin.entity.vo.QSysDept;
import cn.swust.indigo.admin.entity.vo.SysDeptVo;
import cn.swust.indigo.admin.entity.vo.SystemDepartmentType;
import cn.swust.indigo.admin.entity.vo.TreeUtil;
import cn.swust.indigo.admin.mapper.SysDeptMapper;
import cn.swust.indigo.admin.mapper.SysDeptRelationMapper;
import cn.swust.indigo.admin.service.SysDeptRelationService;
import cn.swust.indigo.admin.service.SysDeptService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

/**
 * <p>
 * 部门管理 服务实现类
 * </p>
 */
@Service
@AllArgsConstructor

public class SysDeptServiceImpl extends ServiceImpl<SysDeptMapper, SysDept> implements SysDeptService {
    private final SysDeptRelationService sysDeptRelationService;
    private final SysDeptRelationMapper sysDeptRelationMapper;

    @Autowired
    private final SysDeptMapper sysDeptMapper;

    /**
     * 添加信息部门
     *
     * @param dept 部门
     * @return
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean saveDept(SysDept dept) {
//        更新部门表
        //        查询领导管辖几个部门，最多一个。
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getLeaderId, dept.getLeaderId());
        Integer count = sysDeptMapper.selectCount(queryWrapper);
        if (count >= 1) {
            return false;
        }
        this.save(dept);
        // 更新关系表
        sysDeptRelationService.insertDeptRelation(dept);
        return Boolean.TRUE;
    }


    /**
     * 删除部门
     *
     * @param id 部门 ID
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean removeDeptById(Integer id) {
        //级联删除部门
        List<Integer> idList = sysDeptRelationService
                .list(Wrappers.<SysDeptRelation>query().lambda()
                        .eq(SysDeptRelation::getAncestor, id))
                .stream()
                .map(SysDeptRelation::getDescendant)
                .collect(Collectors.toList());
//        idList.add(id);
        if (CollUtil.isNotEmpty(idList)) {
            this.removeByIds(idList);
        }
        //删除部门级联关系
        sysDeptRelationService.deleteAllDeptRealtion(id);
        return Boolean.TRUE;
    }

    /**
     * 更新部门
     *
     * @param sysDept 部门信息
     * @return 成功、失败
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public Boolean updateDeptById(SysDept sysDept) {
        //查询领导管辖几个部门，最多一个
        SysDept oldDept = this.getById(sysDept.getDeptId());
        int countFlag = 0;
        if (oldDept.getLeaderId()==sysDept.getLeaderId())
            countFlag = 1;

        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getLeaderId, sysDept.getLeaderId());
        Integer count = sysDeptMapper.selectCount(queryWrapper);
        if (count > countFlag) {
            return false;
        }
        //更新部门状态
        SysDeptRelation sysDeptRelation = sysDeptRelationMapper.selectOne(Wrappers.<SysDeptRelation>query().lambda()
                .eq(SysDeptRelation::getDescendant, sysDept.getDeptId())
                .eq(SysDeptRelation::getAncestor, sysDept.getParentId()));
        if (sysDeptRelation != null)
            this.updateById(sysDept);
            //更新部门关系
        else {
            this.updateById(sysDept);
            SysDeptRelation relation = new SysDeptRelation();
            relation.setAncestor(sysDept.getParentId());
            relation.setDescendant(sysDept.getDeptId());
            sysDeptRelationService.updateDeptRealtion(relation);
        }
        return Boolean.TRUE;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Page<SysDeptVo> selectPage(QSysDept qSysDept) {
        if (ObjectUtil.isNull(qSysDept.getLeaderName())) {
            qSysDept.setLeaderName("");
        }
        if (ObjectUtil.isNull(qSysDept.getName())) {
            qSysDept.setName("");
        }
        if (ObjectUtil.isNull(qSysDept.getDepartmentPy())) {
            qSysDept.setDepartmentPy("");
        }
        qSysDept.setDepartmentPy(qSysDept.getDepartmentPy() + "%");
        qSysDept.setLeaderName(qSysDept.getLeaderName() + "%");
        qSysDept.setName(qSysDept.getName() + "%");
        Page<SysDeptVo> sysDeptVoPage = new Page<>(qSysDept.getIndex(), qSysDept.getSize());
        Page<SysDeptVo> deptPageVo = sysDeptMapper.getDeptPageVoWithName(sysDeptVoPage, qSysDept.getLeaderName(), qSysDept.getName(), qSysDept.getDepartmentPy());
        System.out.println(deptPageVo.getRecords());

        return deptPageVo;
    }

    /**
     * 返回被此user管理的所有部门，及其以下的部门
     *
     * @param userId 用户ID
     * @return 树形列表
     */
    @Override
    public List<DeptTree> selectTreeById(Integer userId) {
        List<DeptTree> deptTrees = selectTreeByType(SystemDepartmentType.ALL);

        ArrayList<DeptTree> res = new ArrayList<>();

        LinkedList<DeptTree> linkedList = new LinkedList<>(deptTrees);
        // 使用层次遍历去获得
        while (!linkedList.isEmpty()) {
            DeptTree head = linkedList.peekFirst();
            linkedList.removeFirst();
            if (head.getUserId() == userId) {
                res.add(head);
            } else {
                for (Object child : head.getChildren()) {
                    linkedList.addLast((DeptTree) child);
                }
            }
        }
        return res;
    }

    @Override
    public boolean setDepartmentLeader(Integer userId, Integer departmentId) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getLeaderId, userId);
        Integer count = sysDeptMapper.selectCount(queryWrapper);
        if (count > 1) {
            return false;
        }
        SysDept sysDept = sysDeptMapper.selectById(departmentId);
        if (ObjectUtil.isNotNull(sysDept)) {
            sysDept.setLeaderId(userId);
            sysDeptMapper.updateById(sysDept);
            return true;
        }
        return false;
    }

    @Override
    public boolean removeByIdAndSubTree(Integer departmentId) {
        List<DeptTree> deptTrees = selectTreeByType(SystemDepartmentType.ALL);

        LinkedList<DeptTree> linkedList = new LinkedList<>(deptTrees);

        DeptTree now = null;
        // 使用层次遍历去获得此节点
        while (!linkedList.isEmpty()) {
            DeptTree head = linkedList.peekFirst();
            linkedList.removeFirst();
            if (Objects.equals(head.getDeptId(), departmentId)) {
                now = head;
                break;
            } else {
                for (Object child : head.getChildren()) {
                    linkedList.addLast((DeptTree) child);
                }
            }
        }

        // 如果找到此此节点，那么删除此节点及其子节点
        if (ObjectUtil.isNotNull(now)) {
            ArrayList<Integer> deleteIds = new ArrayList<>();
            LinkedList<DeptTree> deleteList = new LinkedList<>(CollectionUtil.toList(now));
            while (!deleteList.isEmpty()) {
                DeptTree head = deleteList.peekFirst();
                deleteList.removeFirst();
                deleteIds.add(head.getDeptId());
                for (Object child : head.getChildren()) {
                    deleteList.addLast((DeptTree) child);
                }
            }
            sysDeptMapper.deleteBatchIds(deleteIds);
            return true;
        } else {
            return false;
        }

    }

    /**
     * 根据用户ID获得当前用户管理的部门
     * @param userId 用户id
     * @return 部门信息
     */
    @Override
    public SysDept getDeptByUserId(Integer userId) {
        LambdaQueryWrapper<SysDept> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(SysDept::getLeaderId, userId);
        return sysDeptMapper.selectOne(queryWrapper);
    }


    /**
     * 根据部门类型返回相应的树
     *
     * @return 树
     */
    @Override
    @Transactional(rollbackFor = Exception.class)
    public List<DeptTree> selectTreeByType(int departmentType) {
        List<SysDept> sysDepts = null;
        if (departmentType == SystemDepartmentType.ALL) {
            // 选取所有的节点
            sysDepts = sysDeptMapper.selectList(new QueryWrapper<SysDept>().orderByDesc("sort"));
        } else {
            // 仅选取当前所选部门的节点
            sysDepts = sysDeptMapper.selectList(new QueryWrapper<SysDept>().eq("department_type", departmentType).orderByDesc("sort"));
        }
        return getDeptTree(sysDepts);
    }


    /**
     * 构建部门树
     *
     * @param depts 部门
     * @return
     */
    private List<DeptTree> getDeptTree(List<SysDept> depts) {
        List<DeptTree> treeList = depts.stream()
                .filter(dept -> !dept.getDeptId().equals(dept.getParentId()))
                .sorted(Comparator.comparingInt(SysDept::getSort))
                .map(dept -> {
                    DeptTree node = new DeptTree();
                    node.setId(dept.getDeptId());
                    node.setDeptId(node.getId());
                    node.setParentId(dept.getParentId());
                    node.setName(dept.getName());
                    node.setType(dept.getType());
                    node.setUserId(dept.getLeaderId());
                    node.setDepartmentPy(dept.getDepartmentPy());
                    return node;
                }).collect(Collectors.toList());
        return TreeUtil.build(treeList, 0);
    }
}
