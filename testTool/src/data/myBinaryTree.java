package data;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @description:
 * @program: usefulTools
 * @author: hyq
 * @create: 2020-06-22 15:41
 **/
public class myBinaryTree {

    /**
     * 树节点
     */
    private static class TreeNode {
        int data;
        TreeNode leftNode;
        TreeNode rightNode;

        TreeNode(int data) {
            this.data = data;
        }
    }

    /**
     * 创建二叉树
     *
     * @param inputList
     * @return
     */
    public static TreeNode createBinaryTree(LinkedList<Integer> inputList) {
        TreeNode node = null;
        if (inputList == null || inputList.isEmpty()) {
            return null;
        }
        // System.out.println(inputList);
        Integer data = inputList.removeFirst();
        if (data != null) {
            node = new TreeNode(data);
            node.leftNode = createBinaryTree(inputList);
            node.rightNode = createBinaryTree(inputList);
            // System.out.println("本节点"+data + "左节点"+ node.leftNode + "右节点"+ node.rightNode);

        }
        return node;
    }

    /**
     * 二叉树前序遍历
     *
     * @param node
     */
    public static void preOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        System.out.println(node.data);
        preOrderTraversal(node.leftNode);
        preOrderTraversal(node.rightNode);
    }

    /**
     * 中序遍历
     *
     * @param node
     */
    public static void inOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.leftNode);
        System.out.println(node.data);
        inOrderTraversal(node.rightNode);
    }

    /**
     * 二叉树后序遍历
     *
     * @param node
     */
    public static void postOrderTraversal(TreeNode node) {
        if (node == null) {
            return;
        }
        inOrderTraversal(node.leftNode);
        inOrderTraversal(node.rightNode);
        System.out.println(node.data);
    }


    public static void main(String[] args) {
        List<Integer> integers = Arrays.asList(new Integer[]{3, 2, 9, null, null, 10, null, null, 8, null, 4});
        LinkedList<Integer> inputList = new LinkedList<>(integers);
        TreeNode treeNode = createBinaryTree(inputList);

        /*System.out.println("前序遍历");
        preOrderTraversal(treeNode);
        System.out.println("中序遍历");
        inOrderTraversal(treeNode);
        System.out.println("后序遍历");
        postOrderTraversal(treeNode);*/

        /*System.out.println("非递归 前序遍历");
        preOrderTraversalWithStack(treeNode);*/
        System.out.println("非递归 中序遍历");
        inOrderTraversalWithStack(treeNode);
    }

    /**
     * 二叉树 非递归 前序遍历
     * 
     * 根  -  左  -  右
     * @param root
     */
    public static void preOrderTraversalWithStack(TreeNode root){
        // 声明一个 栈<树节点>
        Stack<TreeNode> stack = new Stack<>();
        // 根节点
        TreeNode treeNode = root;

        while (treeNode != null || !stack.isEmpty()){
            // 迭代访问节点的 左子节点 ，加入栈
            while (treeNode != null){
                System.out.println("输出节点："+treeNode.data);
                stack.push(treeNode);
                System.out.println("入栈"+treeNode.data);
                treeNode = treeNode.leftNode;
            }
            // 如果节点没有 左子节点， 则弹出栈顶节点，访问节点 右子节点
            if (!stack.isEmpty()){
                treeNode = stack.pop();
                System.out.println("出栈"+treeNode.data);
                treeNode = treeNode.rightNode;
            }

        }
    }

    /**
     * 二叉树 非递归 中序遍历
     *
     * 左  -  根  -  右
     * @param root
     */
    public static void inOrderTraversalWithStack(TreeNode root){
        // 声明一个 栈<树节点>
        Stack<TreeNode> stack = new Stack<>();
        // 根节点
        TreeNode treeNode = root;

        // 如果根节点不为空  ||  栈不为空，则进行下去
        while (treeNode != null || !stack.isEmpty()){
            // 迭代访问节点的 左子节点 ，加入栈
            while (treeNode != null){
                stack.push(treeNode);
                System.out.println("入栈"+treeNode.data);
                treeNode = treeNode.leftNode;
            }
            // 如果节点没有 左子节点， 则输出
            if (!stack.isEmpty()){
                treeNode = stack.pop();
                System.out.println("出栈"+treeNode.data);
                System.out.println("输出节点--1次弹栈"+treeNode.data);
                // 如果 右子节点 也没有，则弹栈，输出
                if (treeNode.rightNode == null){
                    treeNode = stack.pop();
                    System.out.println("输出节点--2次弹栈"+treeNode.data);
                    treeNode = treeNode.rightNode;
                }
            }

        }

    }

}
