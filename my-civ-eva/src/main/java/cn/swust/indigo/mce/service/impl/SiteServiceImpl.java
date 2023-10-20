package cn.swust.indigo.mce.service.impl;

import cn.swust.indigo.admin.entity.po.SysDept;
import cn.swust.indigo.admin.entity.vo.SystemDepartmentType;
import cn.swust.indigo.admin.entity.vo.TreeUtil;
import cn.swust.indigo.admin.mapper.SysDeptMapper;
import cn.swust.indigo.admin.service.SysDeptService;
import cn.swust.indigo.common.core.constants.CommonConstants;
import cn.swust.indigo.common.minio.service.MinioTemplate;
import cn.swust.indigo.mce.entity.dto.CheckedSiteTree;
import cn.swust.indigo.mce.entity.dto.SiteTree;
import cn.swust.indigo.mce.entity.po.CheckedSite;
import cn.swust.indigo.mce.entity.vo.SiteVo;
import cn.swust.indigo.mce.mapper.SitePageMapper;
import cn.swust.indigo.mce.service.SiteService;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import cn.swust.indigo.mce.entity.po.Site;
import cn.swust.indigo.mce.mapper.SiteMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 站点
 *
 * @author lhz
 * @date 2023-02-24 17:01:59
 */
@Service
@AllArgsConstructor
public class SiteServiceImpl extends ServiceImpl<SiteMapper, Site> implements SiteService {
    private final SitePageMapper sitePageMapper;

    @Autowired
    SysDeptService sysDeptService;

    @Autowired
    SysDeptMapper sysDeptMapper;

    @Autowired
    SiteMapper siteMapper;

    MinioTemplate minioTemplate;

    /**
     * 将站点归纳到部门下后查看所有部门树，需要选择类型
     * @return 部门树(包括站点)
     */
    @Override
    public List<SiteTree> selectTreeAll(Integer department_type) {
        LambdaQueryWrapper<SysDept> queryWrapper = null;
        // 选择树中站点的类型，如果是全部那么就是null查询
        if(!department_type.equals(SystemDepartmentType.ALL)){
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysDept::getType, department_type);
        }
        List<Site> sites = siteMapper.selectList(null);
        List<SysDept> sysDepts = sysDeptMapper.selectList(queryWrapper);
        HashMap<Integer, ArrayList<Site>> integerArrayListHashMap = new HashMap<>();
        for (Site site : sites) {
            if (!integerArrayListHashMap.containsKey(site.getLeaderId())) {
                integerArrayListHashMap.put(site.getDepartmentId(),new ArrayList<>());
            }
            integerArrayListHashMap.get(site.getDepartmentId()).add(site);
        }

        List<SiteTree> treeList = sysDepts.stream()
                .filter(dept -> !dept.getDeptId().equals(dept.getParentId()))
                .map(dept -> {
                    SiteTree node = new SiteTree();
                    node.setId(dept.getDeptId());
                    node.setParentId(dept.getParentId());
                    node.setName(dept.getName());
                    node.setType(dept.getType());
                    node.setUserId(dept.getLeaderId());
                    node.setSites(integerArrayListHashMap.get(node.getDeptId()));
                    return node;
                }).collect(Collectors.toList());

        return TreeUtil.build(treeList, 0);
    }

    @Override
    public List<SiteTree> selectCheckedTreeAll(Integer department_type) {
        LambdaQueryWrapper<SysDept> queryWrapper = null;
        // 选择树中站点的类型，如果是全部那么就是null查询
        if(!department_type.equals(SystemDepartmentType.ALL)){
            queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(SysDept::getType, department_type);
        }
        List<Site> oldSites = siteMapper.selectList(null);
        // 从父类生成 checkerSites
        ArrayList<CheckedSite> checkedSites = new ArrayList<>();
        for (Site oldSite : oldSites) {
            CheckedSite temp = new CheckedSite();
            BeanUtils.copyProperties(oldSite, temp);
            checkedSites.add(temp);
        }
        // 从父类生成 checkerSites
        List<SysDept> sysDepts = sysDeptMapper.selectList(queryWrapper);

        HashMap<Integer, ArrayList<Site>> integerArrayListHashMap = new HashMap<>();
        for (CheckedSite site : checkedSites) {
            if (!integerArrayListHashMap.containsKey(site.getLeaderId())) {
                integerArrayListHashMap.put(site.getDepartmentId(),new ArrayList<>());
            }
            integerArrayListHashMap.get(site.getDepartmentId()).add(site);
        }

        List<SiteTree> treeList = sysDepts.stream()
                .filter(dept -> !dept.getDeptId().equals(dept.getParentId()))
                .map(dept -> {
                    CheckedSiteTree node = new CheckedSiteTree();
                    node.setParentId(dept.getParentId());
                    node.setId(dept.getDeptId());
                    node.setName(dept.getName());
                    node.setType(dept.getType());
                    node.setUserId(dept.getLeaderId());
                    node.setSites(integerArrayListHashMap.get(node.getDeptId()));
                    return node;
                }).collect(Collectors.toList());

        return TreeUtil.build(treeList, 0);
    }

    @Override
    public  IPage<SiteVo> getSiteVoList(@Param("page") Page<SiteVo> page, @Param("longitude") String longitude, @Param("latitude") String latitude, @Param("sitePy") String sitePy, @Param("leaderName") String leaderName) {
        if(sitePy != null && siteMapper.selectList(new QueryWrapper<Site>().like("site_py",sitePy)).isEmpty()){
            return null;
        }
        return siteMapper.getSiteVoList(page, longitude, latitude, sitePy, leaderName);
    }

    @Override
    public SiteVo getSite(Integer id) {
        SiteVo siteVoById = siteMapper.getSiteVoById(id);
        siteVoById.setPicUrl(minioTemplate.getObjectURL(CommonConstants.BUCKET_NAME, siteVoById.getThumbnail(), 7200));
        return siteVoById;
    }

    @Override
    public List<SiteVo> getAll() {
        List<SiteVo> all = siteMapper.getAll();
        Iterator<SiteVo> it = all.listIterator();
        while(it.hasNext()){
            SiteVo next = it.next();
            next.setPicUrl(minioTemplate.getObjectURL(CommonConstants.BUCKET_NAME, next.getThumbnail(), 7200));
        }
        return all;
    }
}
