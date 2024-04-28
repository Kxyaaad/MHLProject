package org.mhl.service;

import org.mhl.dao.MenuDao;
import org.mhl.domain.Menu;

import java.util.List;

/**
 * @author k
 * 2024/4/26 下午5:25
 * @version 1.0
 */
public class MenuService {
    private final static MenuDao menuDao = new MenuDao();

    public static List<Menu> listMenu() {
        return menuDao.queryMulti("SELECT * FROM menu", Menu.class);
    }

    //根据id返回Menu对象
    public static Menu getMenuById(int id) {
        return menuDao.querySingle("SELECT * from menu where id = ?", Menu.class, id);
    }
}
