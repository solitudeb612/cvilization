package cn.swust.indigo.mce.service;

import cn.swust.indigo.mce.entity.dto.SiteTree;
import cn.swust.indigo.mce.entity.vo.SiteVo;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import cn.swust.indigo.mce.entity.po.Site;
import io.swagger.models.auth.In;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 站点
 *
 * @author lhz
 * @date 2023-02-24 17:01:59
 */
@Service
public interface SiteService extends IService<Site> {

    /**
     * 将站点归纳到部门下后查看所有部门树
     *
     * @return 部门树(包括站点)
     */
    List<SiteTree> selectTreeAll(Integer department_type);

    /**
     * @param department_type
     * @return
     */
    List<SiteTree> selectCheckedTreeAll(Integer department_type);

    IPage<SiteVo> getSiteVoList(@Param("page") Page<SiteVo> page,
                                @Param("longitude") String longitude,
                                @Param("latitude") String latitude,
                                @Param("sitePy") String sitePy,
                                @Param("leaderName") String leaderName);


    SiteVo getSite(Integer id);

    List<SiteVo> getAll();
}
