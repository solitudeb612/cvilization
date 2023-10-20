package cn.swust.indigo.mce.entity.dto;

import lombok.Data;

/**
 * @author Binary cat
 * @date 2023/3/7 16:24
 */
@Data
public class CheckedSiteTree extends SiteTree{
    private boolean checked = false;
}
