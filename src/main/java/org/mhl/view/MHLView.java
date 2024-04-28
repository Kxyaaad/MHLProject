package org.mhl.view;

import com.mysql.cj.xdevapi.Table;
import org.mhl.domain.*;
import org.mhl.service.BillService;
import org.mhl.service.DiningTableService;
import org.mhl.service.EmployeeService;
import org.mhl.service.MenuService;
import org.mhl.utils.Utility;

import java.util.List;
import java.util.Objects;

/**
 * @author k
 * 2024/4/25 下午5:43
 * @version 1.0
 * 主界面
 */
public class MHLView {
    private boolean loop = true;
    private String key = "";

    public void mainMenu() {
        while (loop) {
            System.out.println("==========满汉楼==========");
            System.out.println("\t\t 1 登录满汉楼");
            System.out.println("\t\t 2 退出满汉楼");
            System.out.println("请输入你的选择：");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    System.out.println("请输入员工号：");
                    String empId = Utility.readString(50);
                    System.out.println("请输入密码：");
                    String pwd = Utility.readString(50);
                    Employee employee = EmployeeService.getEmployeeByIdAndPwd(empId, pwd);
                    if (employee != null) {
                        System.out.println("登录成功[" + employee.getName() + "]");
                        //显示二级菜单
                        secondMenu();
                    } else {
                        System.out.println("登录失败");
                    }
                    break;
                case "2":
                    System.out.println("退出满汉楼");
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }

    public void secondMenu() {
        while (loop) {
            System.out.println("==========满汉楼二级菜单==========");
            System.out.println("\t\t 1 显示餐桌状态");
            System.out.println("\t\t 2 预定餐桌");
            System.out.println("\t\t 3 显示所有菜品");
            System.out.println("\t\t 4 点餐服务");
            System.out.println("\t\t 5 查看账单");
            System.out.println("\t\t 6 结账");
            System.out.println("\t\t 9 退出满汉楼");
            key = Utility.readString(1);
            switch (key) {
                case "1":
                    listDiningTable();
                    break;
                case "2":
                    orderDiningTable();
                    break;
                case "3":
                    listMenu();
                    break;
                case "4":
                    orderMenu();
                    break;
                case "5":
                    checkBills();
                    break;
                case "6":
                    checkoutBill();
                    break;
                case "9":
                    System.out.println("退出满汉楼");
                    loop = false;
                    break;
                default:
                    System.out.println("输入有误，请重新输入");
                    break;
            }
        }
    }

    private static void checkoutBill() {
        System.out.println("请选择要结账的餐桌编号(-1退出):");
        int tableId = Utility.readInt();
        if (tableId == -1) {
            return;
        }
        DiningTable diningTable = DiningTableService.getDiningTableById(tableId);
        while (diningTable == null) {
            System.out.println("该餐桌不存在，请重新输入(-1退出):");
            tableId = Utility.readInt();
            if (tableId == -1) {
                return;
            }
            diningTable = DiningTableService.getDiningTableById(tableId);
        }

        Bill bill = BillService.getBillByTableId(tableId);
        if (bill == null) {
            System.out.println("没有待结账的订单");
            return;
        }
        System.out.println("请输入支付方式(-1退出):");
        String payType = Utility.readString(50);
        BillService.payBill(tableId, payType);
    }

    private static void checkBills() {
//        List<Bill> bills = BillService.checkBills();
//        for (Bill bill: bills) {
//            System.out.println(bill);
//        }
        List<MultiTableBean> bills = BillService.checkBillsV2();
        for (MultiTableBean bill: bills) {
            System.out.println(bill);
        }
    }

    private static void orderMenu() {
        System.out.println("==========点餐服务==========");
        System.out.println("请选择点餐的桌号(-1)退出):");
        int tableId = Utility.readInt();
        if (tableId == -1) {
            System.out.println("取消点餐");
            return;
        }
        DiningTable diningTable = DiningTableService.getDiningTableById(tableId);
        while (diningTable == null) {
            System.out.println("输入错误，餐桌不存在，请重新输入(-1退出）");
            tableId = Utility.readInt();
            if (tableId == -1) {
                return;
            }
            diningTable = DiningTableService.getDiningTableById(tableId);
        }

        System.out.println("请输入点餐的菜品编号(-1退出):");
        int orderMenuId = Utility.readInt();
        if (orderMenuId == -1) {
            System.out.println("取消点餐");
            return;
        }

        Menu menu = MenuService.getMenuById(orderMenuId);
        while (menu == null) {
            System.out.println("菜品不存在，请重新输入(-1退出)");
            orderMenuId = Utility.readInt();
            if (orderMenuId == -1) {
                return;
            }else {
                menu = MenuService.getMenuById(orderMenuId);
            }
        }

        System.out.println("请输入点餐的菜品数量(-1退出):");
        int orderNum = Utility.readInt();
        if (orderNum == -1 ) {
            System.out.println("取消点餐");
            return;
        }

        if (BillService.orderMenu(orderMenuId, orderNum, tableId)) {
            System.out.println("点餐成功");
        }else{
            System.out.println("点餐失败");
        }

    }

    private static void listMenu() {
        List<Menu> menus = MenuService.listMenu();
        for (Menu menu : menus) {
            System.out.println(menu);
        }
    }

    private static void orderDiningTable() {
        System.out.println("请输入要预定的餐桌号码:");
        int diningTableId = Utility.readInt();
        System.out.print("确认是否预定");
        char c = Utility.readConfirmSelection();
        if (c == 'N') {
            System.out.println("取消预定");
            return;
        }
        String diningTableStatus = DiningTableService.getDiningTableStatus(diningTableId);
        if (!Objects.equals(diningTableStatus, "空")) {
            System.out.println("该餐桌不能预定");
        } else {
            System.out.println("请输入您的姓名");
            String name = Utility.readString(50);
            System.out.println("请输入您的联系电话");
            String phone = Utility.readString(30);
            DiningTableService.orderDiningTable(diningTableId, name, phone);
        }
    }

    private static void listDiningTable() {
        List<DiningTable> list = DiningTableService.getList();
        System.out.println("\t餐桌编号\t\t餐桌状态");
        for (DiningTable diningTable : list) {
            System.out.println(diningTable.toString());
        }
    }
}
