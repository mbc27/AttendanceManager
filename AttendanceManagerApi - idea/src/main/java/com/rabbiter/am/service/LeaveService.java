package com.rabbiter.am.service;

import com.rabbiter.am.dao.LeaveDao;
import com.rabbiter.am.entity.Employee;
import com.rabbiter.am.entity.Leave;
import com.rabbiter.am.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class LeaveService {

    @Autowired
    private LeaveDao leaveDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private EmployeeService employeeService;

    public int deleteById(String id) {
        return leaveDao.deleteById(id);
    }

    public int insert(Leave leave) {
        // 调试信息：输出所有管理员信息
        try {
            List<Employee> adminList = employeeService.findByType("3");
            System.out.println("系统中的管理员数量: " + (adminList == null ? 0 : adminList.size()));
            if (adminList != null && !adminList.isEmpty()) {
                for (Employee admin : adminList) {
                    System.out.println("管理员 ID: " + admin.getId() + ", 工号: " + admin.getNumber() + ", 姓名: " + admin.getName());
                }
            } else {
                System.out.println("系统中没有找到管理员 (type=3)");
            }
            
            // 查找默认管理员（1001）
            Employee defaultAdmin = employeeService.findByNumber("1001");
            if (defaultAdmin != null) {
                System.out.println("默认管理员信息 - 工号: 1001, 姓名: " + defaultAdmin.getName() + ", 类型: " + defaultAdmin.getType());
            } else {
                System.out.println("系统中没有找到默认管理员 (number=1001)");
            }
        } catch (Exception e) {
            System.err.println("获取管理员信息异常: " + e.getMessage());
        }
        
        String id1 = UUID.randomUUID().toString();
        leave.setId(id1);
        String id2 = UUID.randomUUID().toString();
        leave.setApprovalID(id2);
        leave.setStatus("0");
        leaveDao.insert(leave);
        Task task = new Task();
        task.setId(id2);
        task.setApplyID(id1);
        task.setApplyTime(leave.getApplyTime());
        task.setTypeID(leave.getTaskTypeID());
        task.setApplyNumber(leave.getApplyNumber());
        String username = leave.getApplyName();
        String typeName = taskTypeService.selectById(leave.getTaskTypeID()).getName();
        task.setName(username+"的"+typeName);
        task.setStatus("0");

        // 获取申请人信息
        Employee employee = employeeService.findByNumber(leave.getApplyNumber());
        if (employee == null) {
            // 如果找不到申请人信息，尝试分配给类型为3的员工（总经理/管理员）
            List<Employee> adminList = employeeService.findByType("3");
            if (adminList != null && !adminList.isEmpty()) {
                task.setReceiveNumber(adminList.get(0).getNumber());
                System.out.println("请假申请任务分配给管理员: " + adminList.get(0).getNumber());
            } else {
                // 如果找不到管理员，则使用默认值
                task.setReceiveNumber("1001");
                System.out.println("请假申请任务分配给默认管理员: 1001");
            }
            taskService.insert(task);
            return 0;
        }

        try {
            // 查找管理员列表
            List<Employee> adminList = employeeService.findByType("3");
            String adminNumber = null;
            if (adminList != null && !adminList.isEmpty()) {
                adminNumber = adminList.get(0).getNumber();
            }
            
            // 尝试获取领导信息
            Employee leader = employeeService.getLeader(employee);
            
            // 如果领导和管理员是同一个人，只创建一个任务
            if (leader != null && leader.getNumber() != null) {
                if (adminNumber != null && leader.getNumber().equals(adminNumber)) {
                    // 领导同时是管理员，只创建一个任务
                    task.setReceiveNumber(leader.getNumber());
                    System.out.println("请假申请任务分配给领导兼管理员: " + leader.getNumber());
                    taskService.insert(task);
                } else {
                    // 领导不是管理员，创建两个任务
                    task.setReceiveNumber(leader.getNumber());
                    System.out.println("请假申请任务分配给领导: " + leader.getNumber());
                    taskService.insert(task);
                    
                    // 创建管理员任务（如果有管理员）
                    if (adminNumber != null) {
                        Task adminTask = new Task();
                        String adminTaskId = UUID.randomUUID().toString();
                        adminTask.setId(adminTaskId);
                        adminTask.setApplyID(id1);
                        adminTask.setApplyTime(leave.getApplyTime());
                        adminTask.setTypeID(leave.getTaskTypeID());
                        adminTask.setApplyNumber(leave.getApplyNumber());
                        adminTask.setName(username+"的"+typeName);
                        adminTask.setStatus("0");
                        adminTask.setReceiveNumber(adminNumber);
                        System.out.println("请假申请任务同时分配给管理员: " + adminNumber);
                        taskService.insert(adminTask);
                    }
                }
            } else {
                // 如果没有找到领导，只分配给管理员
                if (adminNumber != null) {
                    task.setReceiveNumber(adminNumber);
                    System.out.println("请假申请任务分配给管理员: " + adminNumber);
                    taskService.insert(task);
                } else {
                    // 如果既没有领导也没有管理员，尝试使用默认账号
                    Employee adminUser = employeeService.findByNumber("admin");
                    if (adminUser != null) {
                        task.setReceiveNumber("admin");
                        System.out.println("请假申请任务分配给默认管理员: admin");
                    } else {
                        task.setReceiveNumber("1001");
                        System.out.println("请假申请任务分配给默认管理员: 1001");
                    }
                    taskService.insert(task);
                }
            }
            
            return 0;
        } catch (Exception e) {
            // 记录错误并使用默认接收者
            System.err.println("获取审批人出错: " + e.getMessage());
            List<Employee> adminList = employeeService.findByType("3");
            if (adminList != null && !adminList.isEmpty()) {
                task.setReceiveNumber(adminList.get(0).getNumber());
                System.out.println("请假申请任务分配给管理员: " + adminList.get(0).getNumber());
            } else {
                // 尝试使用 admin 作为管理员账号
                Employee adminUser = employeeService.findByNumber("admin");
                if (adminUser != null) {
                    task.setReceiveNumber("admin");
                    System.out.println("请假申请任务分配给默认管理员: admin");
                } else {
                    // 如果找不到 admin 账号，则使用默认值
                    task.setReceiveNumber("1001");
                    System.out.println("请假申请任务分配给默认管理员: 1001");
                }
            }
            taskService.insert(task);
            return 0;
        }
    }

    public Leave selectById(String id) {
        return leaveDao.selectById(id);
    }

    public int update(Leave leave) {
        return leaveDao.update(leave);
    }

    public List<Leave> findByEmployeeNumber(String number){
        System.out.println("查询员工工号为: " + number + " 的请假申请记录");
        List<Leave> leaveList = leaveDao.findByEmployeeNumber(number);
        System.out.println("查询结果数量: " + (leaveList == null ? 0 : leaveList.size()));
        
        if (leaveList == null || leaveList.isEmpty()) {
            System.out.println("没有找到员工 " + number + " 的请假申请记录");
            return leaveList;
        }
        
        for(Leave item : leaveList){
            try {
                System.out.println("处理请假记录: " + item.getId() + ", 状态: " + item.getStatus());
                if(item.getStatus().equals("1") || item.getStatus().equals("2")){
                    if(item.getApprovalID() == null) {
                        System.out.println("警告: 请假记录 " + item.getId() + " 的审批ID为空");
                        continue;
                    }
                    Task task = taskService.selectById(item.getApprovalID());
                    if(task == null) {
                        System.out.println("警告: 找不到任务ID为 " + item.getApprovalID() + " 的任务记录");
                        continue;
                    }
                    Employee employee = employeeService.findByNumber(task.getApprovalNumber());
                    if(employee == null) {
                        System.out.println("警告: 找不到工号为 " + task.getApprovalNumber() + " 的员工记录");
                        continue;
                    }
                    item.setApprovalName(employee.getName());
                    item.setApprovalTime(task.getApprovalTime());
                }
            } catch (Exception e) {
                System.err.println("处理请假记录时出错: " + e.getMessage());
                e.printStackTrace();
            }
        }
        return leaveList;
    }
}
