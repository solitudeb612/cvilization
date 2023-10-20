package cn.swust.indigo.mce.entity.dto;

import cn.swust.indigo.admin.entity.dto.DeptTree;
import cn.swust.indigo.mce.entity.po.Site;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.ArrayList;
import java.util.Objects;

/**
 * @author Binary cat
 * @date 2023/3/5 13:36
 */
@Data
@ApiModel("")
public class SiteTree extends DeptTree {
    private ArrayList<Site> sites = new ArrayList<>();
}
