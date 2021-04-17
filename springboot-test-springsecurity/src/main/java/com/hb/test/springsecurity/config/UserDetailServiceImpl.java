package com.hb.test.springsecurity.config;

import com.hb.test.springsecurity.model.Permission;
import com.hb.test.springsecurity.model.User;
import com.hb.test.springsecurity.model.UserPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户信息
 *
 * @author Mr.Huang
 * @version v0.1, 2020/6/1 15:23, create by huangbiao.
 */
@Service("userDetailsService")
public class UserDetailServiceImpl implements UserDetailsService {

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        User user = getUserByUserName(s);
        System.out.println("用户信息：" + user);
        List<String> roleList = getRoleListByRoleIdArr(s);
        System.out.println("角色信息：" + user);
        List<Permission> permissionList = getPermissionListByPermissionIdArr(s);
        System.out.println("权限信息：" + user);
        return new UserPrincipal(user, roleList, permissionList);
    }

    /**
     * 根据用户名获取用户信息
     *
     * @param userName
     *            用户名
     * @return 用户信息
     */
    public static User getUserByUserName(String userName) {
        Map<String, User> userMap = new HashMap<>();
        userMap.put("zhangsan",
            new User(123L, "zhangsan", "$2a$10$DBYNsfDsGsjIDmL/LL7uku1JJvMAESHwFbyfNNvINYPiFxOfCXq0q"));// 123
        userMap.put("lisi", new User(456L, "lisi", "$2a$10$furPmcpdd5uKPwkJ3HEN8OuMNfqPKEkG8Ci9XII2K5.Jwdti9FHQi")); // 456
        return userMap.get(userName);
    }

    /**
     * 根据用户名获取角色集合
     *
     * @param roleId
     *            角色ID
     * @return 角色集合
     */
    public static List<String> getRoleListByRoleIdArr(String... roleId) {
        Map<String, List<String>> roleMap = new HashMap<>();
        roleMap.put("zhangsan", Arrays.asList("r1"));
        roleMap.put("lisi", Arrays.asList("r2"));
        return roleMap.get(roleId[0]);
    }

    /**
     * 根据用户名获取角色集合
     *
     * @param permissionId
     *            权限ID
     * @return 权限集合
     */
    public static List<Permission> getPermissionListByPermissionIdArr(String... permissionId) {
        Map<String, List<Permission>> permissionMap = new HashMap<>();
        permissionMap.put("zhangsan", Arrays.asList(new Permission("p1")));
        permissionMap.put("lisi", Arrays.asList(new Permission("p2")));
        return permissionMap.get(permissionId[0]);
    }

}
