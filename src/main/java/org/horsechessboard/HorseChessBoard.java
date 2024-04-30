package org.horsechessboard;

import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;

/**
 * @author k
 * 2024/4/29 下午4:58
 * @version 1.0
 * 马踏飞燕问题
 */
public class HorseChessBoard {

    private static int x = 6;
    private static int y = 6;
    private static int[][] chessBoard = new int[y][x];
    private static boolean[] visitedPosition = new boolean[x * y];
    private static boolean isFinished = false;
    private static int countTimes = 0;

    public static void main(String[] args) {

        int row = 2;
        int col = 2;

        traversalChessBoard(chessBoard, row - 1, col - 1, 1);

        for (int[] rows : chessBoard) {
            for (int step : rows) { //表示该位置是马应该走的第几部
                System.out.print(step + "\t");
            }
            System.out.println();
        }

        System.out.println("计算次数" + countTimes);
    }

    /**
     * 遍历期盼，如果遍历成功，就把isFinished设置为true，并且，将马儿走的每一步记录到chessboard
     */
    private static void traversalChessBoard(int[][] chessBoard, int row, int col, int step) {
        countTimes += 1;
        //递归算法，先把step记录到chessBoard中
        chessBoard[row][col] = step;
        //把这个位置设置为已经访问
        visitedPosition[row * x + col] = true;
        //获取当前这个位置可以走的下一个位置有哪些
        ArrayList<Point> nextPoints = next(new Point(col, row));
        sort(nextPoints);
        //遍历
        while (!nextPoints.isEmpty()) {
            //取出一个位置
            Point removePoint = nextPoints.remove(0);
            //判断该位置有没有走过，如果没有走过就递归遍历
            if (!visitedPosition[removePoint.y * x + removePoint.x]) {
                traversalChessBoard(chessBoard, removePoint.y, removePoint.x, step + 1);
            }
        }
        //当退出while后，看看是否遍历成功，如果没有成功就重置相应的值，然后进行回溯
        if (step < x * y && !isFinished) {
            chessBoard[row][col] = 0;
            visitedPosition[row * x + col] = false;
        } else {
            isFinished = true;
        }
    }

    /**
     *  对nextPoints的各个位置进行排序，可以走的下一个位置的次数进行排序，把可能走的下一个位置从小到大排序
     */
    private static void sort(ArrayList<Point> ps){
        ps.sort(new Comparator<Point>() {
            @Override
            public int compare(Point o1, Point o2) {
                return next(o1).size() - next(o2).size();
            }
        });
    }


    /**
     * 获取当前位置可以走的下一步的所有位置
     *
     * @param currentPoint
     */
    private static ArrayList<Point> next(Point currentPoint) {
        //创建一个ArrayList
        ArrayList<Point> points = new ArrayList<>();
        // 创建一个Point对象，准飞放入到points;
        Point point = new Point();
        //判断在这个currentPoint是否能走point这个位置，如果可以，则放入points，判断四周的八个点坐标

        if ((point.x = currentPoint.x + 2) < x && (point.y = currentPoint.y - 1) >= 0) {
            points.add(new Point(point));
        }

        if ((point.x = currentPoint.x - 2) >= 0 && (point.y = currentPoint.y - 1) >= 0) {
            points.add(new Point(point));
        }
        if ((point.x = currentPoint.x - 1) >= 0 && (point.y = currentPoint.y - 2) >= 0) {
            points.add(new Point(point));
        }

        if ((point.x = currentPoint.x + 1) < x && (point.y = currentPoint.y - 2) >= 0) {
            points.add(new Point(point));
        }


        if ((point.x = currentPoint.x + 2) < x && (point.y = currentPoint.y + 1) < y) {
            points.add(new Point(point));
        }

        if ((point.x = currentPoint.x + 1) < x && (point.y = currentPoint.y + 2) < y) {
            points.add(new Point(point));
        }

        if ((point.x = currentPoint.x - 1) >= 0 && (point.y = currentPoint.y + 2) < y) {
            points.add(new Point(point));
        }

        if ((point.x = currentPoint.x - 2) >= 0 && (point.y = currentPoint.y + 1) < y) {
            points.add(new Point(point));
        }

        return points;
    }
}
