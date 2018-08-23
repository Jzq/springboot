package com.jzq.modules.sys.controller;

import com.jzq.common.utils.CacheUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.cache.CacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class SwithSkinController {

    private static Logger log = LoggerFactory.getLogger(SwithSkinController.class);
    @Autowired
    private CacheManager cacheManager;

    @RequestMapping("/switchSkin/view")
    public String view(){
        return "admin/sys/sysSwitchSkin";
    }

    /**
     * 切换主题
     */
    @RequestMapping(value = "switchSkin/{skinName}")
    public String switchSkin(@PathVariable String skinName) {
        if (StringUtils.isNotBlank(skinName) && !"select".equals(skinName)){
            CacheUtils.putSysCache("skinName",skinName);
            return "redirect:/index";
        }
        return "modules/sys/sysSwitchSkin";
    }
}
