package com.shnu.vueblog.util;
/**
 * @author Legendary_Hu
 * @Description:None
 * @date 2022/3/18 15:15
 */

import com.shnu.vueblog.shiro.AccountProfile;
import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;

/**
 * @program: vueblog
 *
 * @description:
 *
 * @author: Legendary_Hu
 *
 * @create: 2022-03-18 15:15
 **/
public class ShiroUtil {
    public static AccountProfile getProfile(){
        return (AccountProfile) SecurityUtils.getSubject().getPrincipal();
    }

}
