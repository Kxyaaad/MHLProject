package org.mhl.service;

import jdk.jfr.Category;
import org.mhl.dao.BillDao;
import org.mhl.dao.MultiTableDao;
import org.mhl.domain.Bill;
import org.mhl.domain.MultiTableBean;

import java.util.List;
import java.util.UUID;

/**
 * @author k
 * 2024/4/26 下午5:50
 * @version 1.0
 */
public class BillService {
    private final static BillDao billDao = new BillDao();
    private final static MultiTableDao multiTableDao = new MultiTableDao();

    //编写点餐的方法
    //1,生成账单
    //2,需要更新对应餐桌的状态
    public static boolean orderMenu(int menuId, int nums, int diningTableId) {
        //生成一个账单号
        String billId = UUID.randomUUID().toString();
        //查询菜品价格

        //生成账单到bill表
        int update = billDao.update("INSERT INTO bill VALUES(null, ?, ?, ?, ?, ?, now(), '未结账')", billId, menuId, nums, MenuService.getMenuById(menuId).getPrice() * nums, diningTableId);
        if (update <= 0) {
            return false;
        } else {
            return DiningTableService.updateDiningTable(diningTableId, "就餐中");
        }
    }

    public static List<Bill> checkBills() {
        return billDao.queryMulti("SELECT * FROM bill", Bill.class);
    }

    public static List<MultiTableBean> checkBillsV2() {
        return multiTableDao.queryMulti("SELECT bill.*, name FROM bill, menu WHERE bill.menuId = menu.id", MultiTableBean.class);
    }

    public static Bill getBillByTableId(int tableId) {
        return billDao.querySingle("SELECT * FROM bill WHERE diningTableId = ? AND state = '未结账' LIMIT 0, 1", Bill.class, tableId);
    }

    public static boolean payBill(int tableId, String payType) {
        //修改bill表
        int update = billDao.update("UPDATE bill SET state = ? WHERE diningTableId = ? and state = '未结账'", payType, tableId);
        if (update <= 0 ) {
            return false;
        }
        //修改diningTable表
        return  DiningTableService.reSetDiningTable(tableId, "空");
    }
}
