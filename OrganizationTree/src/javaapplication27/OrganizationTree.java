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

class OrganizationTree {
    TreeNode root;

    public OrganizationTree(Employee rootEmployee) {
        this.root = new TreeNode(rootEmployee);
    }

    public void addEmployee(int managerId, Employee newEmployee) {
        TreeNode managerNode = searchEmployee(root, managerId);
        if (managerNode != null) {
            managerNode.children.add(new TreeNode(newEmployee));
        }
    }

    private TreeNode searchEmployee(TreeNode node, int id) {
        if (node == null) {
            return null;
        }
        if (node.employee.id == id) {
            return node;
        }
        for (TreeNode child : node.children) {
            TreeNode result = searchEmployee(child, id);
            if (result != null) {
                return result;
            }
        }
        return null;
    }

    public Employee searchEmployee(int id) {
        TreeNode node = searchEmployee(root, id);
        return (node != null) ? node.employee : null;
    }

    public void preOrderTraversal(TreeNode node) {
        if (node != null) {
            System.out.println(node.employee);
            for (TreeNode child : node.children) {
                preOrderTraversal(child);
            }
        }
    }

    public void preOrderTraversal() {
        preOrderTraversal(root);
    }

    public Employee findHighestPosition(TreeNode node) {
        if (node == null) {
            return null;
        }
        Employee highest = node.employee;
        for (TreeNode child : node.children) {
            Employee highestChild = findHighestPosition(child);
            if (highestChild != null && highestChild.position.compareTo(highest.position) < 0) {
                highest = highestChild;
            }
        }
        return highest;
    }

    public Employee findLowestPosition(TreeNode node) {
        if (node == null) {
            return null;
        }
        Employee lowest = node.employee;
        for (TreeNode child : node.children) {
            Employee lowestChild = findLowestPosition(child);
            if (lowestChild != null && lowestChild.position.compareTo(lowest.position) > 0) {
                lowest = lowestChild;
            }
        }
        return lowest;
    }

    public static void main(String[] args) {
        Employee ceo = new Employee(1, "John Doe", "CEO");
        OrganizationTree orgTree = new OrganizationTree(ceo);

        orgTree.addEmployee(1, new Employee(2, "Jane Smith", "CTO"));
        orgTree.addEmployee(1, new Employee(3, "Robert Brown", "CFO"));
        orgTree.addEmployee(2, new Employee(4, "Emily Davis", "Senior Developer"));
        orgTree.addEmployee(2, new Employee(5, "Michael Wilson", "Developer"));
        orgTree.addEmployee(3, new Employee(6, "Sarah Johnson", "Accountant"));

        System.out.println("Pre-order traversal of the organization tree:");
        orgTree.preOrderTraversal();

        int searchId = 4;
        Employee employee = orgTree.searchEmployee(searchId);
        System.out.println("\nSearch for employee with ID " + searchId + ":");
        System.out.println(employee != null ? employee : "Employee not found");

        Employee highestPosition = orgTree.findHighestPosition(orgTree.root);
        System.out.println("\nEmployee with the highest position:");
        System.out.println(highestPosition);

        Employee lowestPosition = orgTree.findLowestPosition(orgTree.root);
        System.out.println("\nEmployee with the lowest position:");
        System.out.println(lowestPosition);
    }
}
