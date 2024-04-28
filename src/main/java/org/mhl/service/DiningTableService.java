package org.mhl.service;

import org.mhl.dao.DiningTableDao;
import org.mhl.domain.DiningTable;

import java.util.List;

/**
 * @author k
 * 2024/4/26 下午3:55
 * @version 1.0
 */
public class DiningTableService {

    private static final DiningTableDao diningTableDao = new DiningTableDao();

    public static List<DiningTable> getList() {
        return diningTableDao.queryMulti("SELECT * FROM diningTable", DiningTable.class);
    }

    public static DiningTable getDiningTableById(Integer id) {
        return diningTableDao.querySingle("SELECT * FROM diningTable WHERE id = ?", DiningTable.class, id);
    }

    public static String getDiningTableStatus(Integer id) {
        if (getDiningTableById(id) != null) {
            return (String) diningTableDao.queryScalar("SELECT state FROM diningTable WHERE id = ?", id);
        }
        System.out.println("该餐桌不存在");
        return "该餐桌不存在";
    }

    public static void orderDiningTable(int id, String orderName, String orderTel) {
        boolean success = diningTableDao.update("UPDATE diningTable set state = '已经预定', orderName = ?, orderTel = ? WHERE id = ?", orderName, orderTel, id) > 0;
        if (success) {
            System.out.println("预定成功");
        } else {
            System.out.println("预定失败");
        }
    }

    public static boolean updateDiningTable(int id, String states) {
        return  diningTableDao.update("UPDATE diningTable SET state = ? WHERE id = ?", states, id) > 0;
    }

    public static boolean reSetDiningTable(int id, String state) {
        return diningTableDao.update("UPDATE diningTable SET state = ?, orderName = '', orderTel = '' WHERE id = ?", state, id) > 0;
    }
}
