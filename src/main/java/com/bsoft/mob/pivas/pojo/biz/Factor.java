package com.bsoft.mob.pivas.pojo.biz;

import com.bsoft.mob.pivas.domain.hrp.KSDM;

import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * SF01查询条件列表
 * Created by huangy on 2015-05-04.
 */
public class Factor {

    /**
     * 病区列表<id, mkmc>
     */
    public TreeMap<String, String> bqs;

    /**
     * 批次列表<id, mkmc>
     */
    public TreeMap<String, String> pcs;

    /**
     * 配制方式列表<id, mkmc>
     */
    public TreeMap<String, String> pzfs;

    /**
     * 主药类型列表<id, mkmc>
     */
    public TreeMap<String, String> zylx;


}
