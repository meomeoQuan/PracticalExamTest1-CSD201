/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package javaapplication27;

/**
 *
 * @author mac
 */


import java.util.ArrayList;
import java.util.List;

class TreeNode {
    Employee employee;
    List<TreeNode> children;

    public TreeNode(Employee employee) {
        this.employee = employee;
        this.children = new ArrayList<>();
    }
}

