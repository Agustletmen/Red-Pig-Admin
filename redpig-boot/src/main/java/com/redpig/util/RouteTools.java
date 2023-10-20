package com.redpig.util;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redpig.entity.Menu;
import com.redpig.entity.Perm;
import com.redpig.entity.Role;
import com.redpig.service.IMenuService;
import com.redpig.service.IUserService;
import com.redpig.vo.RouteVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Component
public class RouteTools {

    @Autowired
    IMenuService menuService;

    @Autowired
    IUserService userService;

    public List<RouteVO> buildRouteTree(){

        List<Menu> roots = menuService.listMenu(new QueryWrapper<Menu>().eq("m.parent_id", 0L).eq("m.deleteStatus",1));

        List<RouteVO> routes = new ArrayList<>();

        menu(roots,routes);

        System.out.println(JSONUtil.toJsonPrettyStr(routes));
        log.info(JSONUtil.toJsonPrettyStr(routes));

        return routes;
    }

    private void menu(List<Menu> roots,List<RouteVO> routes){
        if(CollUtil.isNotEmpty(roots)){
            for (Menu parent : roots) {
                RouteVO root = route(parent);

                if(parent.getType()==0 && parent.getParentId()==0){
                    routes.add(root);
                }

                List<Menu> children = menuService.listMenu(new QueryWrapper<Menu>().eq("m.parent_id", parent.getId()).ne("m.type",2).eq("m.deleteStatus",1));
                if(CollUtil.isNotEmpty(children)){

                    List<RouteVO> childrenRoute = childRoute(children);

                    root.setChildren(childrenRoute);

                    menu(children,routes);
                }



            }
        }
    }

    private RouteVO route(Menu menu){
        RouteVO root = new RouteVO();
        root.setPath(menu.getPath());
        root.setName(menu.getName());
        root.setComponent(menu.getComponent());

        RouteVO.Meta meta = new RouteVO.Meta();
        meta.setTitle(menu.getTitle());
        meta.setIcon(menu.getIcon());
        meta.setShowLink(menu.getShowLink());
        meta.setRank(menu.getRank());
        meta.setRoles(menu.getRoles().stream().map(Role::getTag).collect(Collectors.toList()));//菜单里面需要有哪些角色才可以访问:前端拿到角色和登录账号的角色进行匹配

        //前端的按钮是否展示:通过返回给前端路由中是否有该权限按钮标识来判断是否需要展示该按钮 前端通过hasAuth来判断
        //所以这里放的是用户的权限标识
        //另外:权限还需要配置在菜单表中 例:菜单/menu/add-->权限menu:add 用户:zs:menu:add 这时候接口/menu/add需要用户zs具有menu:add才能访问
        List<Perm> perms = RedPigTools.getUser().getPerms();

        meta.setAuths(perms.stream().map(Perm::getTag).collect(Collectors.toList()));

        root.setMeta(meta);

        return root;
    }

    private List<RouteVO> childRoute(List<Menu> menus){
        List<RouteVO> children = new ArrayList<>();

        if(CollUtil.isNotEmpty(menus)){
            for (Menu menu : menus) {
                RouteVO child = route(menu);
                children.add(child);
            }
        }

        return children;
    }

}
