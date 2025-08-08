package com.rabbiter.am.service;

import com.rabbiter.am.dao.FixedassetsDao;
import com.rabbiter.am.entity.Employee;
import com.rabbiter.am.entity.Fixedassets;
import com.rabbiter.am.entity.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class FixedassetService {

    @Autowired
    FixedassetsDao fixedassetsDao;
    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskTypeService taskTypeService;
    @Autowired
    private EmployeeService employeeService;

    public int deleteById(String id) {
        return fixedassetsDao.deleteById(id);
    }

    public int insert(Fixedassets fixedassets) {
        Fixedassets fixedassets1 = findByNumber(fixedassets.getNumber());
        if(fixedassets1 != null){
            return 1;
        }else {
            String id1 = UUID.randomUUID().toString();
            fixedassets.setId(id1);
            Employee employee2 = employeeService.findByNumber(fixedassets.getEmployeeNumber());
            if(employee2.getType().equals("3")){
                fixedassets.setStatus("1");
                fixedassets.setApprovalID(employee2.getId());
                fixedassetsDao.insert(fixedassets);
            }else {
                String id2 = UUID.randomUUID().toString();
                fixedassets.setApprovalID(id2);
                fixedassets.setStatus("0");
                fixedassetsDao.insert(fixedassets);
                Task task = new Task();
                task.setId(id2);
                task.setApplyID(id1);
                task.setApplyTime(fixedassets.getApplyTime());
                task.setTypeID(fixedassets.getTaskTypeID());
                task.setApplyNumber(fixedassets.getEmployeeNumber());
                String username = fixedassets.getEmployeeName();
                String typeName = taskTypeService.selectById(fixedassets.getTaskTypeID()).getName();
                task.setName(username+"的"+typeName);
                task.setStatus("0");
                Employee employee = employeeService.findByNumber(fixedassets.getEmployeeNumber());
                
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
                            System.out.println("固定资产申请任务分配给领导兼管理员: " + leader.getNumber());
                            taskService.insert(task);
                        } else {
                            // 领导不是管理员，创建两个任务
                            task.setReceiveNumber(leader.getNumber());
                            System.out.println("固定资产申请任务分配给领导: " + leader.getNumber());
                            taskService.insert(task);
                            
                            // 创建管理员任务（如果有管理员）
                            if (adminNumber != null) {
                                Task adminTask = new Task();
                                String adminTaskId = UUID.randomUUID().toString();
                                adminTask.setId(adminTaskId);
                                adminTask.setApplyID(id1);
                                adminTask.setApplyTime(fixedassets.getApplyTime());
                                adminTask.setTypeID(fixedassets.getTaskTypeID());
                                adminTask.setApplyNumber(fixedassets.getEmployeeNumber());
                                adminTask.setName(username+"的"+typeName);
                                adminTask.setStatus("0");
                                adminTask.setReceiveNumber(adminNumber);
                                System.out.println("固定资产申请任务同时分配给管理员: " + adminNumber);
                                taskService.insert(adminTask);
                            }
                        }
                    } else {
                        // 如果没有找到领导，只分配给管理员
                        if (adminNumber != null) {
                            task.setReceiveNumber(adminNumber);
                            System.out.println("固定资产申请任务分配给管理员: " + adminNumber);
                            taskService.insert(task);
                        } else {
                            // 如果既没有领导也没有管理员，尝试使用默认账号
                            Employee adminUser = employeeService.findByNumber("admin");
                            if (adminUser != null) {
                                task.setReceiveNumber("admin");
                                System.out.println("固定资产申请任务分配给默认管理员: admin");
                            } else {
                                task.setReceiveNumber("1001");
                                System.out.println("固定资产申请任务分配给默认管理员: 1001");
                            }
                            taskService.insert(task);
                        }
                    }
                    
                } catch (Exception e) {
                    // 记录错误并使用默认接收者
                    System.err.println("获取审批人出错: " + e.getMessage());
                    List<Employee> adminList = employeeService.findByType("3");
                    if (adminList != null && !adminList.isEmpty()) {
                        task.setReceiveNumber(adminList.get(0).getNumber());
                        System.out.println("固定资产申请任务分配给管理员: " + adminList.get(0).getNumber());
                    } else {
                        // 尝试使用 admin 作为管理员账号
                        Employee adminUser = employeeService.findByNumber("admin");
                        if (adminUser != null) {
                            task.setReceiveNumber("admin");
                            System.out.println("固定资产申请任务分配给默认管理员: admin");
                        } else {
                            // 如果找不到 admin 账号，则使用默认值
                            task.setReceiveNumber("1001");
                            System.out.println("固定资产申请任务分配给默认管理员: 1001");
                        }
                    }
                    taskService.insert(task);
                }
            }
            return 0;
        }
    }

    public Fixedassets selectById(String id) {
        return fixedassetsDao.selectById(id);
    }

    public int update(Fixedassets fixedassets) {
        return fixedassetsDao.update(fixedassets);
    }

    public List<Fixedassets> getAll(){
        List<Fixedassets> fixedassetsList = fixedassetsDao.getAll();
        for(Fixedassets item : fixedassetsList){
            // 获取申请人信息
            Employee applyEmployee = employeeService.findByNumber(item.getEmployeeNumber());
            if(applyEmployee != null) {
                item.setEmployeeName(applyEmployee.getName());
            }
            
            // 获取审批信息
            if(item.getStatus().equals("1") || item.getStatus().equals("2")){
                Task task = taskService.selectById(item.getApprovalID());
                if(task != null) {
                    // 获取批准人信息
                    Employee employee = employeeService.findByNumber(task.getApprovalNumber());
                    if(employee != null) {
                        item.setApprovalName(employee.getName());
                    }
                    item.setApprovalTime(task.getApprovalTime());
                }
            }
            
            // 设置状态名称
            if(item.getStatus().equals("0")) {
                item.setStatusName("待审批");
            } else if(item.getStatus().equals("1")) {
                item.setStatusName("已通过");
            } else if(item.getStatus().equals("2")) {
                item.setStatusName("已驳回");
            } else if(item.getStatus().equals("3")) {
                item.setStatusName("已撤销");
            }
        }
        return fixedassetsList;
    }

    public Fixedassets findByNumber(String number){
        return fixedassetsDao.findByNumber(number);
    }

    public List<Fixedassets> findByEmployeeNumber(String number){
        List<Fixedassets> fixedassetsList = fixedassetsDao.findByEmployeeNumber(number);
        for(Fixedassets item : fixedassetsList){
            // 设置状态名称
            if(item.getStatus().equals("0")) {
                item.setStatusName("待审批");
            } else if(item.getStatus().equals("1")) {
                item.setStatusName("已通过");
            } else if(item.getStatus().equals("2")) {
                item.setStatusName("已驳回");
            } else if(item.getStatus().equals("3")) {
                item.setStatusName("已撤销");
            }
            
            if(item.getStatus().equals("1") || item.getStatus().equals("2")){
                Task task = taskService.selectById(item.getApprovalID());
                if(task == null) {
                    continue;
                }
                // 获取批准人信息
                Employee employee = employeeService.findByNumber(task.getApprovalNumber());
                if(employee != null) {
                    item.setApprovalName(employee.getName());
                }
                item.setApprovalTime(task.getApprovalTime());
            }
        }
        return fixedassetsList;
    }

    public List<Fixedassets> getRoomList(){
        return fixedassetsDao.getRoomList();
    }
}
