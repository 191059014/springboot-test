package com.hb.test.springsecurity.config;

import com.hb.test.springsecurity.model.Permission;
import com.hb.test.springsecurity.model.Role;
import com.hb.test.springsecurity.model.User;
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
        List<Role> roleList = getRoleListByRoleIdArr(s);
        System.out.println("角色信息：" + roleList);
        List<Permission> permissionList = getPermissionListByPermissionIdArr(s);
        System.out.println("权限信息：" + permissionList);
        user.setRoles(roleList);
        user.setPermissions(permissionList);
        return user;
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
        User user1 = new User();
        user1.setUserId(123L);
        user1.setUserName("zhangsan");
        user1.setPassword("$2a$10$DBYNsfDsGsjIDmL/LL7uku1JJvMAESHwFbyfNNvINYPiFxOfCXq0q");// 123
        userMap.put("zhangsan", user1);

        User user2 = new User();
        user2.setUserId(456L);
        user2.setUserName("lisi");
        user2.setPassword("$2a$10$furPmcpdd5uKPwkJ3HEN8OuMNfqPKEkG8Ci9XII2K5.Jwdti9FHQi");// 456
        userMap.put("lisi", user2);

        return userMap.get(userName);
    }

    /**
     * 根据用户名获取角色集合
     *
     * @param username
     *            用户名
     * @return 角色集合
     */
    public static List<Role> getRoleListByRoleIdArr(String username) {
        Map<String, List<Role>> roleMap = new HashMap<>();
        roleMap.put("zhangsan", Arrays.asList(new Role("r1")));
        roleMap.put("lisi", Arrays.asList(new Role("r2")));
        return roleMap.get(username);
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
