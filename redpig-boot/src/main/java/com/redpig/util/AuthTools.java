package com.redpig.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.redpig.entity.Menu;
import com.redpig.entity.Perm;
import com.redpig.service.IMenuService;
import com.redpig.service.IPermService;
import com.redpig.vo.MenuPermTreeVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class AuthTools {

    @Autowired
    IMenuService menuService;

    @Autowired
    IPermService permService;

    public List<MenuPermTreeVO> getMenuPerms(){

        List<Menu> menus = menuService.list(new QueryWrapper<Menu>().ne("type",2));

        List<Perm> perms = permService.list();

        List<MenuPermTreeVO> menuPerms = new ArrayList<>();

        for (Menu menu : menus) {
            for (Perm perm : perms) {
                if(perm.getMenuId() == menu.getId()){
                    MenuPermTreeVO menuPerm = new MenuPermTreeVO();
                    menuPerm.setId(perm.getId());
                    menuPerm.setName(perm.getName()+":"+perm.getTag());
                    menuPerm.setParentId(perm.getMenuId()+10000);
                    menuPerms.add(menuPerm);
                }else{
                    MenuPermTreeVO menuPerm = new MenuPermTreeVO();
                    menuPerm.setId(menu.getId()+10000);//防止和perm的id冲突
                    menuPerm.setName(menu.getTitle());
                    menuPerm.setParentId(menu.getParentId()+10000);
                    menuPerm.setDisabled(true);
                    menuPerms.add(menuPerm);
                }
            }
        }
        Set<MenuPermTreeVO> menuPermVOSet = new TreeSet<>(new Comparator<MenuPermTreeVO>(){
            @Override
            public int compare(MenuPermTreeVO o1, MenuPermTreeVO o2) {
                return o1.getId().compareTo(o2.getId());
            }
        });

        menuPermVOSet.addAll(menuPerms);

        return menuPermVOSet.stream().collect(Collectors.toList());
    }

}
